package delta.leo.connector;

import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.misc.GenericFactory;
import delta.common.utils.url.URLTools;
import delta.common.utils.xml.DOMParsingTools;
import delta.leo.data.ObjectsSource;
import delta.leo.utils.LeoLoggers;

/**
 * Parser used to build a data connector factory from the connectors.xml file.
 * @author DAM
 */
public class DataConnectorXMLParser
{
  private static final Logger _logger=LeoLoggers.getLeoLogger();

  // Tags
  private static final String CONNECTOR_TAG="connector";
  // Attributes
  private static final String TYPE_ATTR="type";
  private static final String PROVIDER_CLASS_ATTR="providerClass";

  /**
   * Read connectors definitions.
   * @return a connectors factory.
   */
  public static GenericFactory<DataConnector> readConnectors()
  {
    GenericFactory<DataConnector> ret=new GenericFactory<DataConnector>(DataConnector.class,ObjectsSource.class);
    URL url=URLTools.getFromClassPath("connectors.xml",DataConnectorXMLParser.class.getPackage());
    if (url!=null)
    {
      Element root=DOMParsingTools.parse(url);
      List<Element> connectorElements=DOMParsingTools.getChildTagsByName(root,CONNECTOR_TAG);
      Element connectorElement;
      for(Iterator<Element> it=connectorElements.iterator();it.hasNext();)
      {
        connectorElement=it.next();
        NamedNodeMap attrs=connectorElement.getAttributes();
        String type=DOMParsingTools.getStringAttribute(attrs,TYPE_ATTR,"");
        String className=DOMParsingTools.getStringAttribute(attrs,PROVIDER_CLASS_ATTR,"");
        ret.registerProduct(type,className);
      }
    }
    else
    {
      _logger.error("Cannot find the connectors.xml description file !");
    }
    return ret;
  }
}
