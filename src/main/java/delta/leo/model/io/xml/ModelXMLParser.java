package delta.leo.model.io.xml;

import java.net.URL;

import org.w3c.dom.Element;

import delta.common.utils.xml.DOMParsingTools;
import delta.leo.model.Model;
import delta.leo.model.impl.EditableModel;

public class ModelXMLParser
{
  private static final String NAME_ATTR="NAME";

  /**
   * Parse the XML file.
   * @param url URL of the XML stream to parse.
   * @return Parsed model or null.
   */
  public Model parseXML(URL url)
  {
    Model model=null;
    Element root=DOMParsingTools.parse(url);
    if (root!=null)
    {
      model=parseModel(root);
    }
    return model;
  }

  private Model parseModel(Element root)
  {
    long now1=System.currentTimeMillis();
    String name=DOMParsingTools.getStringAttribute(root.getAttributes(),NAME_ATTR,"DEFAULT");
    EditableModel model=new EditableModel(name);
    // Entities
    EntityXMLParser entityParser=new EntityXMLParser();
    entityParser.parseEntities(model,root);
    // Relations
    RelationXMLParser relationParser=new RelationXMLParser();
    relationParser.parseRelations(model,root);
    long now2=System.currentTimeMillis();
    System.out.println("Parsed model '"+model.getName()+"' in "+(now2-now1)+"ms.");
    return model;
  }
}
