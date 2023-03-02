package delta.leo.model.impl;

import delta.common.utils.properties.SymbolicPropertiesSet;
import delta.common.utils.properties.SymbolicProperty;
import delta.common.utils.types.Type;
import delta.leo.model.entity.field.Field;
import delta.leo.model.entity.field.FieldProperties;

/**
 * Represents a field (attribute) in the Entity-Relationships data modeling paradigm.
 * @author DAM
 */
public class EditableField implements Field
{
  private String _name;
  private Type _type;
  private SymbolicPropertiesSet _properties;

  /**
   * Constructor.
   * @param name Field name.
   */
  public EditableField(String name)
  {
    this(name,null);
  }

  /**
   * Constructor.
   * @param name Field name.
   * @param type Field type.
   */
  public EditableField(String name, Type type)
  {
    this(name,type,FieldProperties.DEFAULT_PROPERTIES);
  }

  /**
   * Full constructor.
   * @param name Field name.
   * @param type Field type.
   * @param properties Properties to attach to this field.
   */
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

  @Override
  public String getName()
  {
    return _name;
  }

  @Override
  public Type getType()
  {
    return _type;
  }

  /**
   * Set the field type.
   * @param type Field type.
   */
  public void setType(Type type)
  {
    _type=type;
  }

  @Override
  public SymbolicPropertiesSet getProperties()
  {
    return _properties;
  }

  /**
   * Add a property to this field.
   * @param propertyName Name of the property to attach.
   */
  public void addProperty(String propertyName)
  {
    _properties=_properties.addProperty(propertyName);
  }

  /**
   * Set the properties of this field.
   * @param properties Properties to set.
   */
  public void setProperties(SymbolicPropertiesSet properties)
  {
    _properties=properties;
  }

  /**
   * Tests is this fields has the specified property.
   * @param property Property to test.
   * @return <code>true</code> if it has this property, <code>false</code> otherwise.
   */
  @Override
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
