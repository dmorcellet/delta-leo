package delta.leo.connector.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import delta.common.utils.jdbc.CleanupManager;
import delta.leo.binding.ClassBinding;
import delta.leo.connector.DataConnector;
import delta.leo.data.ObjectId;
import delta.leo.data.ObjectInstance;
import delta.leo.data.ObjectsSource;
import delta.leo.location.DataLocation;
import delta.leo.location.sql.SqlDataLocation;
import delta.leo.metadata.ObjectClass;
import delta.leo.model.entity.field.Field;

/**
 * A data connector for SQL databases (through JDBC).
 * @author DAM
 */
public class SqlDataConnector extends DataConnector
{
  private static final Logger LOGGER=Logger.getLogger(SqlDataConnector.class);

  /**
   * Pool of SQL connections.
   */
  private SqlConnectionsPool _pool;

  /**
   * Constructor.
   * @param objectsSource Parent objects source.
   */
  public SqlDataConnector(ObjectsSource objectsSource)
  {
    super(objectsSource);
    DataLocation dataLocation=objectsSource.getDataLocation();
    SqlDataLocation sqlDataLocation=(SqlDataLocation)dataLocation;
    _pool=new SqlConnectionsPool(sqlDataLocation);
  }

  /**
   * Initialization method.
   * @return <code>true</code> if this connector was successfully initialized, <code>false</code> otherwise.
   */
  @Override
  public boolean init()
  {
    return _pool.init();
  }

  /**
   * Termination method.
   * @return <code>true</code> if this connector was successfully terminated, <code>false</code> otherwise.
   */
  @Override
  public boolean terminate()
  {
    _pool.terminate();
    return true;
  }

  @Override
  public ObjectInstance get(ObjectId id)
  {
    // ToDo Auto-generated method stub
    return super.get(id);
  }

  /**
   * Get all the instances of a class in a given data source.
   * @param binding Class binding to use.
   * @return The list of loaded instances.
   */
  @Override
  public List<ObjectInstance> getAll(ClassBinding binding)
  {
    List<ObjectInstance> ret=null;
    if (binding==null)
    {
      // todo warning
      return ret;
    }
    ObjectClass clazz=binding.getObjectClass();
    List<Field> fields=clazz.getAllFields();
    SqlSelectRequest requestMgr=new SqlSelectRequest(binding,fields);

    Connection connection=_pool.getConnection();
    if (connection==null)
    {
      return null;
    }
    String sqlRequest=requestMgr.getSqlRequest();
    Statement statement=null;
    ResultSet rs=null;
    ObjectInstance instance;
    try
    {
      ret=new ArrayList<ObjectInstance>();
      statement=connection.createStatement();
      rs=statement.executeQuery(sqlRequest);
      ResultSetMetaData metadata=rs.getMetaData();

      int nbColumns=metadata.getColumnCount();
      Object object;
      int rowIndex=0;
      while (rs.next())
      {
        instance=clazz.newInstance();
        Field field;
        for(int columnIndex=1;columnIndex<=nbColumns;columnIndex++)
        {
          object=rs.getObject(columnIndex);
          if (!rs.wasNull())
          {
            field=requestMgr.getFieldAtRSIndex(columnIndex-1);
            instance.setValue(field,object);
          }
        }
        rowIndex++;
        ret.add(instance);
      }
      _pool.releaseConnection(connection);
    }
    catch (SQLException sqlException)
    {
      LOGGER.error("SQL exception",sqlException);
      _pool.releaseBadConnection(connection);
      ret=null;
    }
    finally
    {
      CleanupManager.cleanup(rs);
      CleanupManager.cleanup(statement);
    }
    return ret;
  }

  @Override
  public void create(ClassBinding binding, ObjectInstance object)
  {
    SqlCreateRequest requestMgr=new SqlCreateRequest(binding,object);
    requestMgr.getSqlRequest();
  }
}
