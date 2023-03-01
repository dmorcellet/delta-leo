package delta.leo.connector;

import org.apache.log4j.Logger;

import delta.common.utils.misc.GenericFactory;
import delta.leo.data.ObjectsSource;
import delta.leo.location.DataLocation;

public class DataConnectorFactory
{
  private static final Logger LOGGER=Logger.getLogger(DataConnectorFactory.class);

  private static DataConnectorFactory _instance=null;

  private GenericFactory<DataConnector> _connectorsFactory;

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
