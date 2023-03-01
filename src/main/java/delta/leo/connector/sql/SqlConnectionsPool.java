package delta.leo.connector.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import delta.common.utils.Tools;
import delta.leo.location.sql.SqlDataLocation;

/**
 * Manages a pool of SQL connections to the same data source.
 * @author DAM
 */
public class SqlConnectionsPool
{
  private static final Logger LOGGER=Logger.getLogger(SqlConnectionsPool.class);

  private SqlDataLocation _location;
  private List<Connection> _freeConnections;
  private List<Connection> _busyConnections;

  /**
   * Constructor.
   * @param location SQL connection description.
   */
  public SqlConnectionsPool(SqlDataLocation location)
  {
    _location=location;
    int nbConnections=_location.getNbConnectionMax();
    _freeConnections=new ArrayList<Connection>(nbConnections);
    _busyConnections=new ArrayList<Connection>(nbConnections);
  }

  /**
   * Initialization. Loads the JDBC driver and creates connections.
   * @return <code>true</code> if success, <code>false</code> otherwise.
   */
  public boolean init()
  {
    if(!loadDriver())
    {
      return false;
    }
    boolean ret=createConnections();
    return ret;
  }

  /**
   * Loads the JDBC driver.
   * @return <code>true</code> if it was sucessfully loaded, <code>false</code> otherwise.
   */
  private boolean loadDriver()
  {
    try
    {
      Class.forName(_location.getJdbcDriver());
    }
    catch(ClassNotFoundException cnfException)
    {
      LOGGER.error("Could not find SQL driver !", cnfException);
      return false;
    }
    return true;
  }

  private boolean createConnections()
  {
    byte nbConnectionInit=_location.getNbConnectionInit();
    byte nbConnections=0;
    for(byte i=0;i<nbConnectionInit;i++)
    {
      if (createConnection())
      {
        nbConnections++;
      }
      else
      {
        break;
      }
    }
    return(nbConnections>0);
  }

  private boolean createConnection()
  {
    try
    {
      Connection connection=DriverManager.getConnection(_location.getURL(), _location.getUser(), _location.getPassword());
      _freeConnections.add(connection);
    }
    catch(SQLException sqlException)
    {
      LOGGER.error("Got exception while creating a connection for location "+_location, sqlException);
      return false;
    }
    return true;
  }

  /**
   * Get a valid connection for the pool (wait if necessary).
   * @return A valid SQL connection or <code>null</code> if an error occured.
   */
  public synchronized Connection getConnection()
  {
    if(_freeConnections.size()==0)
    {
      if(_busyConnections.size()<_location.getNbConnectionMax())
      {
        createConnection();
      }
      if(_freeConnections.size()==0)
      {
        if (_busyConnections.size()>0)
        {
          Tools.startWaiting(this);
        }
        else
        {
          LOGGER.error("No more connections available !");
          return null;
        }
      }
    }
    Connection got=_freeConnections.remove(0);
    _busyConnections.add(got);
    return got;
  }

  /**
   * Release a valid connection.
   * @param connection Connection to release.
   */
  public synchronized void releaseConnection(Connection connection)
  {
    if (_busyConnections.remove(connection))
    {
      _freeConnections.add(connection);
      notifyAll();
    }
    else
    {
      throw new IllegalArgumentException("Unknown connection !");
    }
  }

  /**
   * Release a bad connection. A new connection is created and
   * added to the pool.
   * @param connection Connection to release.
   */
  public synchronized void releaseBadConnection(Connection connection)
  {
    try
    {
      connection.close();
    }
    catch(SQLException e)
    {
      LOGGER.error("Cannot close connection !",e);
    }

    if(_busyConnections.remove(connection))
    {
      if (createConnection())
        notifyAll();
    }
    else
    {
      throw new IllegalArgumentException("Unknown connection !");
    }
  }

  /**
   * Terminate this connections pool.
   * Warns if there is still some busy connections, and then close all connections (busy+free ones).
   */
  public synchronized void terminate()
  {
    if(_busyConnections.size()>0)
    {
      LOGGER.warn("Still "+_busyConnections.size()+" busy connection(s) when terminating connection pool for location "+_location);
    }
    terminateConnectionList(_freeConnections);
    terminateConnectionList(_busyConnections);
  }

  /**
   * Terminate a collection of connections.
   * @param collection Collection of connections to terminate.
   */
  private void terminateConnectionList(List<Connection> collection)
  {
    Connection c;
    while(collection.size()>0)
    {
      c=collection.remove(0);
      try
      {
        c.close();
      }
      catch(SQLException sqlException)
      {
        LOGGER.error("Got exception while closing a connection for location "+_location, sqlException);
      }
    }
  }
}
