package delta.leo.location.sql;

import delta.leo.location.DataLocation;
import delta.leo.location.DataLocationType;

public class SqlDataLocation extends DataLocation
{
  public static final DataLocationType SQL_LOCATION=new DataLocationType("SQL");

  private String _url;
  private String _jdbcDriver;
  private String _user;
  private String _password;
  private String _dbName;
  private byte _nbConnectionsInit;
  private byte _nbConnectionsMax;

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

  public String getURL()
  {
    return _url;
  }

  public String getJdbcDriver()
  {
    return _jdbcDriver;
  }

  public String getUser()
  {
    return _user;
  }

  public String getPassword()
  {
    return _password;
  }

  public String getDbName()
  {
    return _dbName;
  }

  public byte getNbConnectionInit()
  {
    return _nbConnectionsInit;
  }

  public byte getNbConnectionMax()
  {
    return _nbConnectionsMax;
  }
}
