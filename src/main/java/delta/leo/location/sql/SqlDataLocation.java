package delta.leo.location.sql;

import delta.leo.location.DataLocation;
import delta.leo.location.DataLocationType;

/**
 * 'SQL' data location.
 * @author DAM
 */
public class SqlDataLocation extends DataLocation
{
  /**
   * 'SQL' data location type.
   */
  public static final DataLocationType SQL_LOCATION=new DataLocationType("SQL");

  private String _url;
  private String _jdbcDriver;
  private String _user;
  private String _password;
  private String _dbName;
  private byte _nbConnectionsInit;
  private byte _nbConnectionsMax;

  /**
   * Constructor.
   * @param id Location identifier.
   * @param url Data location URL.
   * @param jdbcDriver JDBC driver class.
   * @param user User.
   * @param password Password.
   * @param dbName Database name.
   * @param nbConnectionsInit Initial number of connections.
   * @param nbConnectionsMax Maximum number of connections.
   */
  public SqlDataLocation(String id, String url, String jdbcDriver, String user, String password, String dbName, byte nbConnectionsInit, byte nbConnectionsMax)
  {
    super(id);
    if (nbConnectionsInit>nbConnectionsMax)
    {
      throw new IllegalArgumentException("Bad values for init and max number of connections ("+nbConnectionsInit+"/"+nbConnectionsMax+")");
    }
    _url=url;
    _jdbcDriver=jdbcDriver;
    _user=user;
    _password=password;
    _dbName=dbName;
    _nbConnectionsInit=nbConnectionsInit;
    _nbConnectionsMax=nbConnectionsMax;
  }

  @Override
  public DataLocationType getType()
  {
    return SQL_LOCATION;
  }

  /**
   * Get the URL of the data location.
   * @return An URL.
   */
  public String getURL()
  {
    return _url;
  }

  /**
   * Get the JDBC driver class.
   * @return A class name.
   */
  public String getJdbcDriver()
  {
    return _jdbcDriver;
  }

  /**
   * Get the user.
   * @return a user name.
   */
  public String getUser()
  {
    return _user;
  }

  /**
   * Get the password.
   * @return a password.
   */
  public String getPassword()
  {
    return _password;
  }

  /**
   * Get the database name.
   * @return A database name.
   */
  public String getDbName()
  {
    return _dbName;
  }

  /**
   * Get the initial number of connections.
   * @return A connections count.
   */
  public byte getNbConnectionInit()
  {
    return _nbConnectionsInit;
  }

  /**
   * Get the maximum number of connections.
   * @return A connections count.
   */
  public byte getNbConnectionMax()
  {
    return _nbConnectionsMax;
  }
}
