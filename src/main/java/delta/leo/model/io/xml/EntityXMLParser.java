package delta.leo.model.io.xml;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import delta.common.utils.properties.SymbolicPropertiesRegistry;
import delta.common.utils.properties.SymbolicPropertiesSet;
import delta.common.utils.properties.io.xml.PropertiesXMLParser;
import delta.common.utils.types.Type;
import delta.common.utils.types.io.xml.TypeXMLParser;
import delta.common.utils.xml.DOMParsingTools;
import delta.leo.model.entity.field.FieldProperties;
import delta.leo.model.impl.EditableEntity;
import delta.leo.model.impl.EditableField;
import delta.leo.model.impl.EditableModel;

/**
 * Parser for entities stored in XML.
 * @author DAM
 */
public class EntityXMLParser
{
  private static final Logger LOGGER=Logger.getLogger(EntityXMLParser.class);

  // Tags
  private static final String ENTITY_TAG="ENTITY";
  private static final String PROPERTIES_TAG="PROPERTIES";
  // Attributes
  private static final String NAME_ATTR="NAME";
  private static final String SUPER_ATTR="SUPER";

  private TypeXMLParser _typeParser;
  private PropertiesXMLParser _propertiesParser;

  /**
   * Constructor.
   */
  public EntityXMLParser()
  {
    _typeParser=new TypeXMLParser();
    SymbolicPropertiesRegistry registry=FieldProperties.getFieldPropertiesRegistry();
    _propertiesParser=new PropertiesXMLParser(registry);
  }

  /**
   * Parse the entities described in the given root node, and attach
   * them to the given model.
   * @param model Model.
   * @param rootNode Root node.
   */
  public void parseEntities(EditableModel model, Element rootNode)
  {
    NodeList nl=rootNode.getElementsByTagName(ENTITY_TAG);
    int nbNodes=nl.getLength();
    Node n;
    NamedNodeMap attrs;
    EditableEntity e;
    EditableEntity superEntity=null;
    String name, superName;
    for(int i=0;i<nbNodes;i++)
    {
      n=nl.item(i);
      attrs=n.getAttributes();
      name=DOMParsingTools.getStringAttribute(attrs,NAME_ATTR,null);
      if (name!=null)
      {
        superName=DOMParsingTools.getStringAttribute(attrs,SUPER_ATTR,null);
        if (superName!=null)
        {
          superEntity=model.getEditableEntity(superName);
        }
        else
        {
          superEntity=null;
        }
        e=model.newEntity(name,superEntity);
        parseFields(e,(Element)n);
      }
      else
      {
        LOGGER.warn("No name attribute for this entity.");
      }
    }
  }

  private void parseFields(EditableEntity entity, Element entityNode)
  {
    NodeList nl=entityNode.getChildNodes();
    int nbNodes=nl.getLength();
    Node n;
    Element e;
    for(int i=0;i<nbNodes;i++)
    {
      n=nl.item(i);
      if (n.getNodeType()==Node.ELEMENT_NODE)
      {
        e=(Element)n;
        parseFieldNode(e,entity);
      }
    }
  }

  private void parseFieldNode(Element element, EditableEntity entity)
  {
    NamedNodeMap attrs=element.getAttributes();
    String fieldName=DOMParsingTools.getStringAttribute(attrs,"NAME","");
    EditableField field=entity.newField(fieldName);
    Type type=null;
    Element typeElement=DOMParsingTools.getChildTagByName(element,"TYPE");
    if (typeElement!=null)
    {
      type=_typeParser.parseTypeNode(typeElement);
    }
    field.setType(type);
    SymbolicPropertiesSet properties=parseFieldProperties(element);
    field.setProperties(properties);
  }

  private SymbolicPropertiesSet parseFieldProperties(Element fieldNode)
  {
    Element propertiesTag=DOMParsingTools.getChildTagByName(fieldNode,PROPERTIES_TAG);
    SymbolicPropertiesSet properties;
    if (propertiesTag!=null)
    {
      properties=_propertiesParser.parseFieldProperties(propertiesTag);
    }
    else
    {
      SymbolicPropertiesRegistry registry=FieldProperties.getFieldPropertiesRegistry();
      properties=registry.getEmptySet();
    }
    return properties;
  }
}
