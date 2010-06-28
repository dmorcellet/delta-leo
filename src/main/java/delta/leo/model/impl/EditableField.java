package delta.leo.model.impl;

import delta.common.utils.properties.SymbolicPropertiesSet;
import delta.common.utils.properties.SymbolicProperty;
import delta.common.utils.types.Type;
import delta.leo.model.entity.field.Field;
import delta.leo.model.entity.field.FieldProperties;

public class EditableField implements Field
{
  private String _name;
  private Type _type;
  private SymbolicPropertiesSet _properties;

  public EditableField(String name)
  {
    this(name,null);
  }

  public EditableField(String name, Type type)
  {
    this(name,type,FieldProperties.DEFAULT_PROPERTIES);
  }

  public EditableField(String name, Type type, SymbolicPropertiesSet properties)
  {
    if (name==null)
    {
      name="";
    }
    _name=name;
    _type=type;
    _properties=properties;
  }

  public String getName()
  {
    return _name;
  }

  public Type getType()
  {
    return _type;
  }

  public void setType(Type type)
  {
    _type=type;
  }

  public SymbolicPropertiesSet getProperties()
  {
    return _properties;
  }

  public void addProperty(String propertyName)
  {
    _properties=_properties.addProperty(propertyName);
  }

  public void setProperties(SymbolicPropertiesSet properties)
  {
    _properties=properties;
  }

  /**
   * Tests is this fields has the specified property.
   * @param property Property to test.
   * @return <code>true</code> if it has this property, <code>false</code> otherwise.
   */
  public boolean hasProperty(SymbolicProperty property)
  {
   return _properties.hasProperty(property);
  }

  /**
   * Tests is this fields has the specified properties.
   * @param properties Properties to test.
   * @return <code>true</code> if it has those properties, <code>false</code> otherwise.
   */
  public boolean hasProperties(SymbolicPropertiesSet properties)
  {
   return _properties.hasProperties(properties);
  }

  @Override
  public String toString()
  {
    return _name;
  }
}
