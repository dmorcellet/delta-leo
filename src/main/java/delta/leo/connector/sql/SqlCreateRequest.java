package delta.leo.connector.sql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import delta.leo.binding.BindingProperties;
import delta.leo.binding.ClassAndField;
import delta.leo.binding.ClassBinding;
import delta.leo.binding.FieldBindingInfo;
import delta.leo.binding.TableBindingInfo;
import delta.leo.connector.RequestManager;
import delta.leo.data.ObjectInstance;
import delta.leo.metadata.ObjectClass;
import delta.leo.model.entity.field.Field;

public class SqlCreateRequest extends RequestManager
{
  private String _sqlRequest;

  /**
   * Constructor.
   * @param classBinding Class binding to use.
   * @param object Object to create.
   */
  public SqlCreateRequest(ClassBinding classBinding, ObjectInstance object)
  {
    super(classBinding);
    buildCreateRequest(object);
  }

  /**
   * Get the text of computed SQL request.
   * @return the text of computed SQL request.
   */
  public String getSqlRequest()
  {
    return _sqlRequest;
  }

  private void buildCreateRequest(ObjectInstance object)
  {
    ClassBinding classBinding=getClassBinding();

    TablesCreateInfo infos=new TablesCreateInfo(classBinding);

    // Fill known values
    ObjectClass clazz=object.getObjectClass();
    List<Field> fields=clazz.getAllFields();
    Field field;
    Object value;
    FieldBindingInfo fieldBinding;
    FieldCreateInfos fieldCreateInfos;
    for(Iterator<Field> it=fields.iterator();it.hasNext();)
    {
      field=it.next();
      value=object.getValue(field);
      fieldBinding=classBinding.getBinding(field);
      fieldCreateInfos=infos.getFieldCreateInfos(fieldBinding);
      fieldCreateInfos.setValue(value);
    }

    // Build insert

    TableCreateInfos createInfos=infos.getTableCreateInfos("personne");
    String sql=buildInsert(createInfos);
    _sqlRequest=sql;
    System.out.println(sql);
  }

  private String buildInsert(TableCreateInfos tableInfos)
  {
    StringBuilder sb=new StringBuilder();
    sb.append("INSERT INTO ");
    TableBindingInfo tableBinding=tableInfos.getTableBindingInfo();
    String tableName=tableBinding.getTableName();
    sb.append(tableName);
    List<FieldCreateInfos> fieldsCreateInfos=tableInfos.getFields();
    int nbFields=fieldsCreateInfos.size();
    {
      sb.append(" (");
      FieldCreateInfos field;
      String fieldName;
      for(int i=0;i<nbFields;i++)
      {
        if (i>0) sb.append(',');
        field=fieldsCreateInfos.get(i);
        fieldName=field._fieldInfo.getFieldName();
        sb.append(fieldName);
      }
      sb.append(')');
    }
    sb.append(" VALUES ");
    {
      sb.append(" (");
      FieldCreateInfos field;
      Object value;
      for(int i=0;i<nbFields;i++)
      {
        if (i>0) sb.append(',');
        field=fieldsCreateInfos.get(i);
        value=field._value;
        // todo SQL encode
        sb.append(value);
      }
      sb.append(')');
    }
    String sql=sb.toString();
    return sql;
  }

/*
  private void executeInsert()
  {
	Connection connection=_pool.getConnection();
    if (connection==null)
    {
      return null;
    }
    Statement statement=connection.createStatement();
    String sql=buildInsert(null);
    statement.executeUpdate(sql);
    ResultSet rs=statement.getGeneratedKeys();
    rs.get
    if (rs.next())
    {
    	rs.getObject()
    	long primaryKey=rs.getLong(1);
    }
    String sqlRequest=requestMgr.getSqlRequest();
	    Statement statement=null;
	    ResultSet rs=null;
	    ObjectInstance instance;
	    try
	    {
	        _psInsert.executeUpdate();
	        if (usesHSQLDB())
	        {
	          if (key==0)
	          {
	            long primaryKey=JDBCTools.getPrimaryKey(connection,1);
	            person.setPrimaryKey(primaryKey);
	          }
	        }
	        else
	        {
	          ResultSet rs=_psInsert.getGeneratedKeys();
	          if (rs.next())
	          {
	            long primaryKey=rs.getLong(1);
	            person.setPrimaryKey(primaryKey);
	          }
	        }
	      _pool.releaseConnection(connection);
	    }
	    catch (SQLException sqlException)
	    {
	      _logger.error("SQL exception",sqlException);
	      _pool.releaseBadConnection(connection);
	      ret=null;
	    }
	    finally
	    {
	      CleanupManager.cleanup(rs);
	      CleanupManager.cleanup(statement);
	    }
  }
*/

  static class TablesCreateInfo
  {
    private ClassBinding _classBinding;
    private Map<String,TableCreateInfos> _tablesMap;

    TablesCreateInfo(ClassBinding classBinding)
    {
      _classBinding=classBinding;
      _tablesMap=new HashMap<String,TableCreateInfos>();

      List<String> classNames=_classBinding.getClassNames();
      TableBindingInfo tableBinding;
      TableCreateInfos tableCreateInfos;
      String tableName;
      for(Iterator<String> it=classNames.iterator();it.hasNext();)
      {
        tableName=it.next();
        tableBinding=_classBinding.getTableBinding(tableName);
        tableCreateInfos=initTable(tableBinding);
        _tablesMap.put(tableName,tableCreateInfos);
      }
    }

    TableCreateInfos getTableCreateInfos(String tableName)
    {
      TableCreateInfos ret=_tablesMap.get(tableName);
      return ret;
    }

    FieldCreateInfos getFieldCreateInfos(ClassAndField field)
    {
      FieldCreateInfos ret=null;
      String tableName=field.getClassName();
      TableCreateInfos tableInfos=getTableCreateInfos(tableName);
      if (tableInfos!=null)
      {
        String fieldName=field.getFieldName();
        ret=tableInfos.getFieldCreateInfos(fieldName);
      }
      return ret;
    }

    private TableCreateInfos initTable(TableBindingInfo tableBinding)
    {
      TableCreateInfos tableInfo=new TableCreateInfos(tableBinding);
      List<FieldBindingInfo> fieldInfos=tableBinding.getFields();
      FieldBindingInfo fieldInfo;
      for(Iterator<FieldBindingInfo> it=fieldInfos.iterator();it.hasNext();)
      {
        fieldInfo=it.next();
        tableInfo.addField(fieldInfo);
      }
      return tableInfo;
    }
  }

  static class TableCreateInfos
  {
    TableBindingInfo _tableInfo;
    List<FieldCreateInfos> _fieldInfosList;
    Map<String,FieldCreateInfos> _fieldInfosMap;

    TableCreateInfos(TableBindingInfo binding)
    {
      _tableInfo=binding;
      _fieldInfosList=new ArrayList<FieldCreateInfos>();
      _fieldInfosMap=new HashMap<String,FieldCreateInfos>();
    }

    TableBindingInfo getTableBindingInfo()
    {
      return _tableInfo;
    }
    void addField(FieldBindingInfo fieldInfo)
    {
      FieldCreateInfos info=new FieldCreateInfos(fieldInfo);
      _fieldInfosList.add(info);
      String fieldName=fieldInfo.getFieldName();
      _fieldInfosMap.put(fieldName,info);
    }

    FieldCreateInfos getFieldCreateInfos(String fieldName)
    {
      FieldCreateInfos ret=_fieldInfosMap.get(fieldName);
      return ret;
    }

    List<FieldCreateInfos> getFields()
    {
      return _fieldInfosList;
    }
  }

  static class FieldCreateInfos
  {
    private FieldBindingInfo _fieldInfo;
    private Object _value;
    private boolean _set;
    private boolean _auto;

    FieldCreateInfos(FieldBindingInfo info)
    {
      _fieldInfo=info;
      _value=null;
      _set=false;
      Field field=info.getField();
      if (field!=null)
      {
        _auto=field.hasProperty(BindingProperties.AUTOGENERATED);
      }
      else
      {
        _auto=false;
      }
    }

    boolean setValue(Object value)
    {
      if (!_set)
      {
        _value=value;
        _set=true;
        return true;
      }
      return false;
    }
  }
}

/*
  protected void buildPreparedStatements(Connection newConnection)
  {
    try
    {
      String fields="cle,nom,prenoms,sexe,signature,date_naissance,infos_n,cle_ln,date_deces,infos_d,cle_ld,cle_pere,cle_mere,cle_parrain,cle_marraine,commentaire,sans_descendance";
      String sql="SELECT "+fields+" FROM personne WHERE cle = ?";
      _psGetByPrimaryKey=newConnection.prepareStatement(sql);
      _psGetByPrimaryKey=PreparedStatementWrapper.buildProxy(_psGetByPrimaryKey);
      sql="SELECT "+fields+" FROM personne";
      _psGetAll=newConnection.prepareStatement(sql);
      sql="INSERT INTO personne ("+fields
          +") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      if (usesHSQLDB())
      {
        _psInsert=newConnection.prepareStatement(sql);
      }
      else
      {
        _psInsert=newConnection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
      }
      sql="SELECT COUNT(*) FROM personne WHERE cle = ?";
      _psCount=newConnection.prepareStatement(sql);
      // Added date_naissance to field list for HSQLDB compatibility
      sql="SELECT DISTINCT cle,date_naissance FROM personne WHERE cle_pere = ? OR cle_mere = ? order by date_naissance";
      _psChildren=newConnection.prepareStatement(sql);
      // Added nom,prenoms to field list for HSQLDB compatibility
      sql="SELECT DISTINCT cle,nom,prenoms FROM personne WHERE nom like ? order by nom,prenoms";
      _psPatronyme=newConnection.prepareStatement(sql);
      // Added date_naissance to field list for HSQLDB compatibility
      sql="SELECT DISTINCT cle,date_naissance FROM personne WHERE cle_parrain = ? OR cle_marraine = ? order by date_naissance";
      _psGodChildren=newConnection.prepareStatement(sql);
      sql="SELECT DISTINCT cle_cousin1,cle_cousin2 FROM cousins WHERE cle_cousin1 = ? OR cle_cousin2 = ?";
      _psCousins=newConnection.prepareStatement(sql);
      sql="SELECT cle_personne,annee,profession,lieu FROM profession WHERE cle_personne = ? ORDER BY annee,profession";
      _psGetOccupations=newConnection.prepareStatement(sql);
      sql="INSERT INTO profession (cle_personne,annee,profession,lieu) VALUES (?,?,?,?)";
      _psInsertOccupation=newConnection.prepareStatement(sql);
      String partialFields="cle,nom,prenoms,sexe,date_naissance,infos_n,date_deces,infos_d,sans_descendance";
      sql="SELECT "+partialFields+" FROM personne WHERE cle = ?";
      _psPartialGetByPrimaryKey=newConnection.prepareStatement(sql);
    }
    catch (SQLException sqlException)
    {
      _logger.error("Exception while building prepared statements for class Person",sqlException);
    }
  }

  public Person getByPrimaryKey(long primaryKey)
  {
    Connection connection=getConnection();
    synchronized (connection)
    {
      Person ret=null;
      ResultSet rs=null;
      try
      {
        _psGetByPrimaryKey.setLong(1,primaryKey);
        //System.out.println("LOAD person "+primaryKey);
        rs=_psGetByPrimaryKey.executeQuery();
        if (rs.next())
        {
          ret=new Person(primaryKey,_mainDataSource.getPersonDataSource());
          fillPerson(ret,rs);
          List<OccupationForPerson> occupations=loadOccupations(primaryKey);
          ret.setOccupations(occupations);
        }
      }
      catch (SQLException sqlException)
      {
        _logger.error("",sqlException);
        CleanupManager.cleanup(_psGetByPrimaryKey);
      }
      finally
      {
        CleanupManager.cleanup(rs);
      }
      return ret;
    }
  }

  public Person getPartialByPrimaryKey(long primaryKey)
  {
    Connection connection=getConnection();
    synchronized (connection)
    {
      Person ret=null;
      ResultSet rs=null;
      try
      {
        _psPartialGetByPrimaryKey.setLong(1,primaryKey);
        //System.out.println("LOAD PARTIAL person "+primaryKey);
        rs=_psPartialGetByPrimaryKey.executeQuery();
        if (rs.next())
        {
          ret=new Person(primaryKey,_mainDataSource.getPersonDataSource());
          fillPartialPerson(ret,rs);
        }
      }
      catch (SQLException sqlException)
      {
        _logger.error("",sqlException);
        CleanupManager.cleanup(_psPartialGetByPrimaryKey);
      }
      finally
      {
        CleanupManager.cleanup(rs);
      }
      return ret;
    }
  }

  private void fillPerson(Person person, ResultSet rs) throws SQLException
  {
    int n=2;
    person.setSurname(rs.getString(n));
    n++;
    person.setFirstname(rs.getString(n));
    n++;
    person.setSex(Sex.getFromValue(rs.getString(n).charAt(0)));
    n++;
    person.setSignature(rs.getString(n));
    n++;
    person.setBirthDate(rs.getDate(n++),rs.getString(n++));
    person.setBirthPlaceProxy(new DataProxy<Place>(rs.getLong(n),
        _mainDataSource.getPlaceDataSource()));
    n++;
    person.setDeathDate(rs.getDate(n++),rs.getString(n++));
    person.setDeathPlaceProxy(new DataProxy<Place>(rs.getLong(n),
        _mainDataSource.getPlaceDataSource()));
    n++;
    long fatherKey=rs.getLong(n);
    DataProxy<Person> fatherProxy=null;
    if (!rs.wasNull())
    {
      fatherProxy=new DataProxy<Person>(fatherKey,person.getSource());
    }
    person.setFatherProxy(fatherProxy);
    n++;
    long motherKey=rs.getLong(n);
    DataProxy<Person> motherProxy=null;
    if (!rs.wasNull())
    {
      motherProxy=new DataProxy<Person>(motherKey,person.getSource());
    }
    person.setMotherProxy(motherProxy);
    n++;
    long godFatherKey=rs.getLong(n);
    DataProxy<Person> godFatherProxy=null;
    if (!rs.wasNull())
    {
      godFatherProxy=new DataProxy<Person>(godFatherKey,person.getSource());
    }
    person.setGodFatherProxy(godFatherProxy);
    n++;
    long godMotherKey=rs.getLong(n);
    DataProxy<Person> godMotherProxy=null;
    if (!rs.wasNull())
    {
      godMotherProxy=new DataProxy<Person>(godMotherKey,person.getSource());
    }
    person.setGodMotherProxy(godMotherProxy);
    n++;
    person.setComments(rs.getString(n));
    n++;
    person.setNoDescendants(rs.getBoolean(n));
    n++;
  }

  private void fillPartialPerson(Person person, ResultSet rs)
      throws SQLException
  {
    int n=2;
    person.setSurname(rs.getString(n));
    n++;
    person.setFirstname(rs.getString(n));
    n++;
    person.setSex(Sex.getFromValue(rs.getString(n).charAt(0)));
    n++;
    person.setBirthDate(rs.getDate(n++),rs.getString(n++));
    person.setDeathDate(rs.getDate(n++),rs.getString(n++));
    person.setNoDescendants(rs.getBoolean(n));
    n++;
  }

  private List<OccupationForPerson> loadOccupations(long primaryKey)
  {
    Connection connection=getConnection();
    synchronized (connection)
    {
      List<OccupationForPerson> ret=null;
      OccupationForPerson tmp=null;
      ResultSet rs=null;
      try
      {
        _psGetOccupations.setLong(1,primaryKey);
        //System.out.println("LOAD occupations FOR "+primaryKey);
        rs=_psGetOccupations.executeQuery();
        while (rs.next())
        {
          tmp=new OccupationForPerson();
          int n=1;
          tmp.setPersonProxy(new DataProxy<Person>(rs.getLong(n),
              _mainDataSource.getPersonDataSource()));
          n++;
          tmp.setYear(rs.getInt(n));
          n++;
          tmp.setOccupation(rs.getString(n));
          n++;
          long place=rs.getLong(n);
          if (!rs.wasNull())
          {
            tmp.setPlaceProxy(new DataProxy<Place>(place,_mainDataSource.getPlaceDataSource()));
          }
          n++;
          if (ret==null)
          {
            ret=new ArrayList<OccupationForPerson>();
          }
          ret.add(tmp);
        }
      }
      catch (SQLException sqlException)
      {
        _logger.error("",sqlException);
        CleanupManager.cleanup(_psGetOccupations);
      }
      finally
      {
        CleanupManager.cleanup(rs);
      }
      return ret;
    }
  }

  public List<Person> getAll()
  {
    Connection connection=getConnection();
    synchronized (connection)
    {
      ArrayList<Person> ret=new ArrayList<Person>();
      Person person=null;
      ResultSet rs=null;
      try
      {
        //System.out.println("GET ALL persons");
        rs=_psGetAll.executeQuery();
        while (rs.next())
        {
          long primaryKey=rs.getLong(1);
          person=new Person(primaryKey,_mainDataSource.getPersonDataSource());
          fillPerson(person,rs);
          List<OccupationForPerson> occupations=loadOccupations(primaryKey);
          person.setOccupations(occupations);
          ret.add(person);
        }
      }
      catch (SQLException sqlException)
      {
        _logger.error("",sqlException);
        CleanupManager.cleanup(_psGetAll);
      }
      finally
      {
        CleanupManager.cleanup(rs);
      }
      return ret;
    }
  }

  public boolean exists(long primaryKey)
  {
    Connection connection=getConnection();
    synchronized (connection)
    {
      boolean ret=false;
      ResultSet rs=null;
      try
      {
        _psCount.setLong(1,primaryKey);
        rs=_psGetByPrimaryKey.executeQuery();
        if (rs.next())
        {
          long count=rs.getLong(1);
          if (count>0)
          {
            ret=true;
          }
        }
      }
      catch (SQLException sqlException)
      {
        _logger.error("",sqlException);
        CleanupManager.cleanup(_psGetByPrimaryKey);
      }
      finally
      {
        CleanupManager.cleanup(rs);
      }
      return ret;
    }
  }

  public ArrayList<Long> getChildren(long primaryKey)
  {
    Connection connection=getConnection();
    synchronized (connection)
    {
      ArrayList<Long> ret=new ArrayList<Long>();
      ResultSet rs=null;
      try
      {
        _psChildren.setLong(1,primaryKey);
        _psChildren.setLong(2,primaryKey);
        //System.out.println("GET childrens FOR "+primaryKey);
        rs=_psChildren.executeQuery();
        while (rs.next())
        {
          ret.add(Long.valueOf(rs.getLong(1)));
        }
      }
      catch (SQLException sqlException)
      {
        _logger.error("",sqlException);
        CleanupManager.cleanup(_psChildren);
      }
      finally
      {
        CleanupManager.cleanup(rs);
      }
      return ret;
    }
  }

  public ArrayList<Long> getGodChildren(long primaryKey)
  {
    Connection connection=getConnection();
    synchronized (connection)
    {
      ArrayList<Long> ret=new ArrayList<Long>();
      ResultSet rs=null;
      try
      {
        _psGodChildren.setLong(1,primaryKey);
        _psGodChildren.setLong(2,primaryKey);
        //System.out.println("GET god childrens FOR "+primaryKey);
        rs=_psGodChildren.executeQuery();
        while (rs.next())
        {
          ret.add(Long.valueOf(rs.getLong(1)));
        }
      }
      catch (SQLException sqlException)
      {
        _logger.error("",sqlException);
        CleanupManager.cleanup(_psGodChildren);
      }
      finally
      {
        CleanupManager.cleanup(rs);
      }
      return ret;
    }
  }

  public ArrayList<Long> getCousins(long primaryKey)
  {
    Connection connection=getConnection();
    synchronized (connection)
    {
      ArrayList<Long> ret=new ArrayList<Long>();
      ResultSet rs=null;
      try
      {
        _psCousins.setLong(1,primaryKey);
        _psCousins.setLong(2,primaryKey);
        //System.out.println("GET cousins FOR "+primaryKey);
        rs=_psCousins.executeQuery();
        while (rs.next())
        {
          if (rs.getLong(1)!=primaryKey) ret.add(Long.valueOf(rs.getLong(1)));
          else if (rs.getLong(2)!=primaryKey) ret.add(Long.valueOf(rs.getLong(2)));
        }
      }
      catch (SQLException sqlException)
      {
        _logger.error("",sqlException);
        CleanupManager.cleanup(_psCousins);
      }
      finally
      {
        CleanupManager.cleanup(rs);
      }
      return ret;
    }
  }

  public ArrayList<Long> getByName(String name)
  {
    Connection connection=getConnection();
    synchronized (connection)
    {
      ArrayList<Long> ret=new ArrayList<Long>();
      ResultSet rs=null;
      try
      {
        _psPatronyme.setString(1,name);
        //System.out.println("GET BY NAME "+name);
        rs=_psPatronyme.executeQuery();
        while (rs.next())
        {
          ret.add(Long.valueOf(rs.getLong(1)));
        }
      }
      catch (SQLException sqlException)
      {
        _logger.error("",sqlException);
        CleanupManager.cleanup(_psPatronyme);
      }
      finally
      {
        CleanupManager.cleanup(rs);
      }
      return ret;
    }
  }

  public List<Long> getRelatedObjectIDs(String relationName, long primaryKey)
  {
    List<Long> ret=new ArrayList<Long>();
    if (relationName.equals(Person.CHILDREN_RELATION))
    {
      ret=getChildren(primaryKey);
    }
    else if (relationName.equals(Person.GOD_CHILDREN_RELATION))
    {
      ret=getGodChildren(primaryKey);
    }
    else if (relationName.equals(Person.COUSINS_RELATION))
    {
      ret=getCousins(primaryKey);
    }
    return ret;
  }

  public List<Long> getObjectIDsSet(String setID, Object[] parameters)
  {
    List<Long> ret=new ArrayList<Long>();
    if (setID.equals(Person.NAME_SET))
    {
      String name=(String)parameters[0];
      ret=getByName(name);
    }
    return ret;
  }

  public void create(Person person)
  {
    Connection connection=getConnection();
    synchronized (connection)
    {
      try
      {
        int n=1;
        long key=person.getPrimaryKey();
        if (key==0)
        {
          _psInsert.setNull(n,Types.INTEGER);
        }
        else
        {
          _psInsert.setLong(n,key);
        }
        n++;
        _psInsert.setString(n,person.getSurname());
        n++;
        _psInsert.setString(n,person.getFirstname());
        n++;
        _psInsert.setString(n,String.valueOf(person.getSex().getValue()));
        n++;
        _psInsert.setString(n,person.getSignature());
        n++;
        Long birthDate=person.getBirthDate();
        if (birthDate!=null)
        {
          _psInsert.setDate(n,new java.sql.Date(birthDate.longValue()));
        }
        else
        {
          _psInsert.setNull(n,Types.DATE);
        }
        n++;
        _psInsert.setString(n,person.getBirthInfos());
        n++;
        DataProxy<Place> birthPlace=person.getBirthPlaceProxy();
        if (birthPlace!=null)
        {
          _psInsert.setLong(n,birthPlace.getPrimaryKey());
        }
        else
        {
          _psInsert.setNull(n,Types.INTEGER);
        }
        n++;
        Long deathDate=person.getDeathDate();
        if (deathDate!=null)
        {
          _psInsert.setDate(n,new java.sql.Date(deathDate.longValue()));
        }
        else
        {
          _psInsert.setNull(n,Types.DATE);
        }
        n++;
        _psInsert.setString(n,person.getDeathInfos());
        n++;
        DataProxy<Place> deathPlace=person.getDeathPlaceProxy();
        if (deathPlace!=null)
        {
          _psInsert.setLong(n,deathPlace.getPrimaryKey());
        }
        else
        {
          _psInsert.setNull(n,Types.INTEGER);
        }
        n++;
        DataProxy<Person> fatherProxy=person.getFatherProxy();
        if (fatherProxy!=null)
        {
          _psInsert.setLong(n,fatherProxy.getPrimaryKey());
        }
        else
        {
          _psInsert.setNull(n,Types.INTEGER);
        }
        n++;
        DataProxy<Person> motherProxy=person.getMotherProxy();
        if (motherProxy!=null)
        {
          _psInsert.setLong(n,motherProxy.getPrimaryKey());
        }
        else
        {
          _psInsert.setNull(n,Types.INTEGER);
        }
        n++;
        DataProxy<Person> godFatherProxy=person.getGodFatherProxy();
        if (godFatherProxy!=null)
        {
          _psInsert.setLong(n,godFatherProxy.getPrimaryKey());
        }
        else
        {
          _psInsert.setNull(n,Types.INTEGER);
        }
        n++;
        DataProxy<Person> godMotherProxy=person.getGodMotherProxy();
        if (godMotherProxy!=null)
        {
          _psInsert.setLong(n,godMotherProxy.getPrimaryKey());
        }
        else
        {
          _psInsert.setNull(n,Types.INTEGER);
        }
        n++;
        _psInsert.setString(n,person.getComments());
        n++;
        _psInsert.setBoolean(n,person.getNoDescendants());
        n++;
        _psInsert.executeUpdate();
        if (usesHSQLDB())
        {
          if (key==0)
          {
            long primaryKey=JDBCTools.getPrimaryKey(connection,1);
            person.setPrimaryKey(primaryKey);
          }
        }
        else
        {
          ResultSet rs=_psInsert.getGeneratedKeys();
          if (rs.next())
          {
            long primaryKey=rs.getLong(1);
            person.setPrimaryKey(primaryKey);
          }
        }
        List<OccupationForPerson> occupations=person.getOccupations();
        if ((occupations!=null)&&(occupations.size()>0))
        {
          int nb=occupations.size();
          for (int i=0; i<nb; i++)
          {
            createOccupation(person,occupations.get(i));
          }
        }
      }
      catch (SQLException sqlException)
      {
        _logger.error("",sqlException);
        CleanupManager.cleanup(_psInsert);
      }
    }
  }

  public void createOccupation(Person p, OccupationForPerson occupation)
  {
    Connection connection=getConnection();
    synchronized (connection)
    {
      try
      {
        int n=1;
        long key=p.getPrimaryKey();
        occupation.setPersonProxy(new DataProxy<Person>(key,_mainDataSource
            .getPersonDataSource()));
        _psInsertOccupation.setLong(n,key);
        n++;
        _psInsertOccupation.setInt(n,occupation.getYear());
        n++;
        _psInsertOccupation.setString(n,occupation.getOccupation());
        n++;
        DataProxy<Place> place=occupation.getPlaceProxy();
        if (place!=null)
        {
          _psInsertOccupation.setLong(n,place.getPrimaryKey());
        }
        else
        {
          _psInsertOccupation.setNull(n,Types.INTEGER);
        }
        n++;
        _psInsertOccupation.executeUpdate();
      }
      catch (SQLException sqlException)
      {
        _logger.error("",sqlException);
        CleanupManager.cleanup(_psInsertOccupation);
      }
    }
  }
*/
