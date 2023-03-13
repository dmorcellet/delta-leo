package delta.leo.connector;

import delta.common.utils.misc.GenericFactory;
import delta.leo.data.ObjectsSource;
import delta.leo.location.DataLocation;

/**
 * Factory for data connectors.
 * @author DAM
 */
public class DataConnectorFactory
{
  private static DataConnectorFactory _instance=null;

  private GenericFactory<DataConnector> _connectorsFactory;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static DataConnectorFactory getInstance()
  {
    if(_instance==null)
    {
      _instance=new DataConnectorFactory();
    }
    return _instance;
  }

  private DataConnectorFactory()
  {
    _connectorsFactory=DataConnectorXMLParser.readConnectors();
  }

  /**
   * Build a data connector.
   * @param objectsSource Objects source to use.
   * @return A data connector or <code>null</code>.
   */
  public DataConnector buildConnector(ObjectsSource objectsSource)
  {
    DataConnector connector=null;
    DataLocation location=objectsSource.getDataLocation();
    if (location!=null)
    {
      String connectorType=location.getType().getType();
      connector=_connectorsFactory.build(connectorType,objectsSource);
    }
    return connector;
  }
}
