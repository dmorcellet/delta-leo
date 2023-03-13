package delta.leo.binding;

import java.util.Iterator;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.properties.SymbolicPropertiesRegistry;
import delta.common.utils.properties.SymbolicPropertiesSet;
import delta.common.utils.properties.io.xml.PropertiesXMLParser;
import delta.common.utils.xml.DOMParsingTools;
import delta.leo.location.DataLocation;
import delta.leo.metadata.ObjectClass;
import delta.leo.metadata.ObjectsModel;
import delta.leo.model.entity.field.Field;

/**
 * XML parser for class bindings.
 * @author DAM
 */
public class ClassBindingXMLParser
{
  // Tags
  // -- Field binding tags
  private static final String FIELD_TAG="FIELD";
  private static final String SOURCE_TAG="SOURCE";
  private static final String PROPERTIES_TAG="PROPERTIES";
  // Attributes
  // -- Class binding attributes
  private static final String LOGICAL_CLASSNAME_ATTR="logicalClass";
  private static final String LOCATION_ATTR="location";
  // -- Field binding attributes
  private static final String NAME_ATTR="name";
  private static final String CLASS_ATTR="class";
  private static final String FIELD_ATTR="field";

  private PropertiesXMLParser _propertiesParser;
  private SymbolicPropertiesRegistry _propertiesRegistry;

  /**
   * Constructor.
   */
  public ClassBindingXMLParser()
  {
    _propertiesRegistry=BindingProperties.getBindingPropertiesRegistry();
    _propertiesParser=new PropertiesXMLParser(_propertiesRegistry);
  }

  /**
   * Parse a class binding from an XML node.
   * @param model Model for storage.
   * @param classBindingNode Input node.
   * @return the loaded class binding or <code>null</code> if an error occurred.
   */
  public ClassBinding parseClassBinding(ObjectsModel model, Element classBindingNode)
  {
    NamedNodeMap attrs=classBindingNode.getAttributes();
    String logicalClass=DOMParsingTools.getStringAttribute(attrs,LOGICAL_CLASSNAME_ATTR,null);
    if (logicalClass==null)
    {
      // todo error
      return null;
    }
    String locationId=DOMParsingTools.getStringAttribute(attrs,LOCATION_ATTR,null);
    if (locationId==null)
    {
      // todo error
      return null;
    }
    DataLocation location=model.getLocationByName(locationId);
    if (location==null)
    {
      // todo error
      return null;
    }
    ObjectClass clazz=model.getClassByName(logicalClass);
    if (clazz==null)
    {
      // todo error
      return null;
    }
    ClassBinding binding=new ClassBinding(clazz);
    List<Element> fieldBindingElements=DOMParsingTools.getChildTagsByName(classBindingNode,FIELD_TAG);
    Element fieldBindingElement;
    for(Iterator<Element> it=fieldBindingElements.iterator();it.hasNext();)
    {
      fieldBindingElement=it.next();
      parseFieldBinding(binding,fieldBindingElement);
    }
    BindingsManager bindingsMgr=model.getBindingsManager(location);
    bindingsMgr.registerBinding(binding);
    return binding;
  }

  private FieldBindingInfo parseFieldBinding(ClassBinding binding, Element fieldBindingNode)
  {
    NamedNodeMap fieldAttrs=fieldBindingNode.getAttributes();
    String logicalFieldName=DOMParsingTools.getStringAttribute(fieldAttrs,NAME_ATTR,null);
    if (logicalFieldName==null)
    {
      // todo error
      return null;
    }
    Element sourceTag=DOMParsingTools.getChildTagByName(fieldBindingNode,SOURCE_TAG);
    if (sourceTag==null)
    {
      // todo error
      return null;
    }
    NamedNodeMap attrs=sourceTag.getAttributes();
    String className=DOMParsingTools.getStringAttribute(attrs,CLASS_ATTR,"");
    if (className.length()==0)
    {
      // todo error
      return null;
    }
    String fieldName=DOMParsingTools.getStringAttribute(attrs,FIELD_ATTR,"");
    if (fieldName.length()==0)
    {
      // todo error
      return null;
    }
    ObjectClass clazz=binding.getObjectClass();
    Field field=clazz.getFieldByName(logicalFieldName);
    if (field==null)
    {
      // todo error
      return null;
    }
    SymbolicPropertiesSet properties=parseFieldProperties(fieldBindingNode);
    FieldBindingInfo ret=binding.addFieldBinding(className,fieldName,field,properties);
    return ret;
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
      properties=_propertiesRegistry.getEmptySet();
    }
    return properties;
  }
}
