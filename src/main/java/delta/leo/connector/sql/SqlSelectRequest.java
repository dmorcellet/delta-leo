package delta.leo.connector.sql;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import delta.leo.binding.ClassAndField;
import delta.leo.binding.ClassBinding;
import delta.leo.binding.FieldBindingInfo;
import delta.leo.binding.Join;
import delta.leo.connector.RequestManager;
import delta.leo.model.entity.field.Field;

public class SqlSelectRequest extends RequestManager
{
  private String _sqlRequest;
  private Field[] _mapRSIndexesToFieldIndexes;

  /**
   * Constructor.
   * @param classBinding Class binding to use.
   * @param fields List of fields to read.
   */
  public SqlSelectRequest(ClassBinding classBinding, List<Field> fields)
  {
    super(classBinding);
    buildSelectRequest(fields);
  }

  /**
   * Builds the WHERE clause used to join tables.
   * @param joins List of joins to use.
   * @return A SQL string (with no starting WHERE).
   */
  private String buildJoinClause(List<Join> joins)
  {
    int nbJoins=joins.size();
    StringBuffer sb=new StringBuffer();
    ClassAndField field1,field2;
    Join join;
    for(int i=0;i<nbJoins;i++)
    {
      join=joins.get(i);
      field1=join.getField1();
      field2=join.getField2();
      if(i>0)
      {
        sb.append(" AND ");
      }
      sb.append(field1.getClassName()).append(".").append(field1.getFieldName());
      sb.append("=");
      sb.append(field2.getClassName()).append(".").append(field2.getFieldName());
    }
    String joinClause=sb.toString();
    return joinClause;
  }

  /**
   * Get the field instance that corresponds to the given index in the <tt>ResultSet</tt>.
   * @param rsIndex <tt>ResultSet</tt> index.
   * @return A field instance.
   */
  public Field getFieldAtRSIndex(int rsIndex)
  {
    return _mapRSIndexesToFieldIndexes[rsIndex];
  }

  /**
   * Get the text of computed SQL request.
   * @return the text of computed SQL request.
   */
  public String getSqlRequest()
  {
    return _sqlRequest;
  }

  /**
   * Build the SQL select request that will read the specified fields.
   * @param fields List of fields to read.
   */
  private void buildSelectRequest(List<Field> fields)
  {
    _mapRSIndexesToFieldIndexes=new Field[fields.size()];

    // Find field bindings for requested fields
    // Compute the map (RS indexes -> field indexes) at the same time
    Set<String> tableNames=new HashSet<String>();
    ArrayList<FieldBindingInfo> fieldBindings=new ArrayList<FieldBindingInfo>();
    {
      Field field;
      FieldBindingInfo fieldBinding;
      String tableName;
      int rsIndex=0;
      ClassBinding classBinding=getClassBinding();
      for(Iterator<Field> it=fields.iterator();it.hasNext();)
      {
        field=it.next();
        fieldBinding=classBinding.getBinding(field);
        if (fieldBinding!=null)
        {
          fieldBindings.add(fieldBinding);
          tableName=fieldBinding.getClassName();
          tableNames.add(tableName);
          _mapRSIndexesToFieldIndexes[rsIndex]=field;
          rsIndex++;
        }
        else
        {
          System.out.println("No binding for field : "+field);
          // todo warning
        }
      }
    }

    int nbTables=tableNames.size();

    StringBuffer sb=new StringBuffer();
    // SELECT clause
    sb.append("SELECT ");
    int nbFields=fieldBindings.size();
    FieldBindingInfo fieldBinding;
    for(int i=0;i<nbFields;i++)
    {
      fieldBinding=fieldBindings.get(i);
      if(i>0)
      {
        sb.append(',');
      }
      if(nbTables>1)
      {
        sb.append(fieldBinding.getClassName());
        sb.append('.');
      }
      sb.append(fieldBinding.getFieldName());
    }
    // FROM clause
    sb.append(" FROM ");
    {
      String tableName;
      boolean doneOne=false;
      for(Iterator<String> itTableNames=tableNames.iterator();itTableNames.hasNext();)
      {
        tableName=itTableNames.next();
        if(doneOne)
        {
          sb.append(',');
        }
        sb.append(tableName);
        doneOne=true;
      }
    }
    boolean whereDone=false;
    // WHERE clause (join)
    if (tableNames.size()>1)
    {
      if (!whereDone)
      {
        sb.append(" WHERE ");
        whereDone=true;
      }
      // Bindings involves several tables.
      // We need additional join fields
      ClassBinding classBinding=getClassBinding();
      List<Join> joins=classBinding.getJoins(tableNames);
      String joinClause=buildJoinClause(joins);
      sb.append(joinClause);
    }

/*
    if (whereDone)
    {
      sb.append(" AND ");
    }
    else
    {
      sb.append(" WHERE ");
    }
    sb.append("cle=787");
*/
    _sqlRequest=sb.toString();
  }
}
