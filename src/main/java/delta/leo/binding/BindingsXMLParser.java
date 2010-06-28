package delta.leo.binding;

import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.Element;

import delta.common.utils.xml.DOMParsingTools;
import delta.leo.metadata.ObjectsModel;

/**
 * Parser for bindings stored in XML.
 * @author DAM
 */
public class BindingsXMLParser
{
  // Tags
  // -- Class binding tag
  private static final String BINDING_TAG="BINDING";

  /**
   * Parse the XML file.
   * @param model Objects model to use.
   * @param url URL of the XML stream to parse.
   */
  public void parseXML(ObjectsModel model, URL url)
  {
    Element root=DOMParsingTools.parse(url);
    if (root!=null)
    {
      ClassBindingXMLParser parser=new ClassBindingXMLParser();
      List<Element> bindingTags=DOMParsingTools.getChildTagsByName(root,BINDING_TAG);
      Element bindingTag;
      for(Iterator<Element> it=bindingTags.iterator();it.hasNext();)
      {
        bindingTag=it.next();
        parser.parseClassBinding(model,bindingTag);
      }
    }
  }
}
