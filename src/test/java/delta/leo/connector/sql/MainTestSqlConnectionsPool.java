package delta.leo.connector.sql;

import java.sql.Connection;

import delta.leo.location.sql.SqlDataLocation;

/**
 * Test class for the SQL connections pool.
 * @author DAM
 */
public class MainTestSqlConnectionsPool
{
  /**
   * Constructor.
   */
  public MainTestSqlConnectionsPool()
  {
    byte nbMax=5;
    String driver="com.mysql.jdbc.Driver";

    SqlDataLocation location=new SqlDataLocation("loc1", "jdbc:mysql://localhost:3306/genea", driver, "dada", "glor4fin3del", "genea", (byte)2, nbMax);

    SqlConnectionsPool pool=new SqlConnectionsPool(location);
    pool.init();
    // Get connections
    Connection[] connections=new Connection[nbMax];
    for(byte i=0;i<nbMax;i++)
    {
      connections[i]=pool.getConnection();
    }
    // Uncomment these to wait for EVER
    //Connection c=pool.getConnection();
    // Release connections
    for(byte i=0;i<nbMax;i++)
    {
      pool.releaseConnection(connections[i]);
    }
    pool.terminate();
  }

  /**
   * Main method for this test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    new MainTestSqlConnectionsPool();
  }
}
