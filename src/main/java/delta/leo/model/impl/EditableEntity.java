package delta.leo.model.impl;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.collections.ListOrderedMap;
import delta.common.utils.properties.SymbolicPropertiesSet;
import delta.common.utils.properties.SymbolicProperty;
import delta.leo.model.entity.Entity;
import delta.leo.model.entity.field.Field;

/**
 * Represents an entity in the Entity-Relationships data modeling paradigm.
 * @author DAM
 */
public class EditableEntity implements Entity
{
  /**
   * Entity's name.
   */
  private String _name;
  /**
   * Parent entity.
   */
  private EditableEntity _superEntity;

  /**
   * Entity's own fields.
   */
  private ListOrderedMap<EditableField> _fieldsList;

  /**
   * Constructor.
   * @param entityName Entity name.
   */
  public EditableEntity(String entityName)
  {
    this(entityName,null);
  }

  /**
   * Constructor.
   * @param entityName Entity name.
   * @param superEntity Parent entity (<code>null</code> if the new entity
   * has no parent entity).
   */
  public EditableEntity(String entityName, EditableEntity superEntity)
  {
    _name=entityName;
    _superEntity=superEntity;
    _fieldsList=new ListOrderedMap<EditableField>();
  }

  public String getName()
  {
    return _name;
  }

  public Entity getSuperEntity()
  {
    return _superEntity;
  }

  /**
   * Indicates if this entity is the specified entity or inherits from it.
   * @param entity Entity to test.
   * @return <code>true</code> or <code>false</code>.
   */
  public boolean isInstanceOf(Entity entity)
  {
    if (entity==this) return true;
    if (_superEntity!=null) return _superEntity.isInstanceOf(entity);
    return false;
  }

  /**
   * Add a new field to this entity.
   * @param name Name of the new field.
   * @return The newly created field.
   */
  public EditableField newField(String name)
  {
    EditableField oldField=getEditableField(name);
    if (oldField!=null)
    {
      throw new IllegalStateException("Cannot add a new field with the same as an existing field.");
    }
    EditableField newField=new EditableField(name);
    _fieldsList.put(name,newField);
    return newField;
  }

  public Field getField(String fieldName)
  {
    return getEditableField(fieldName);
  }

  /**
   * Get a field by name.
   * @param fieldName Name of the field to get.
   * @return A field or <code>null</code> if there's no such field. 
   */
  public EditableField getEditableField(String fieldName)
  {
    EditableField f=_fieldsList.get(fieldName);
    if (f==null)
    {
      if (_superEntity!=null)
      {
        f=_superEntity.getEditableField(fieldName);
      }
    }
    return f;
  }

  public List<Field> getFields()
  {
    List<Field> ret=new ArrayList<Field>();
    if (_superEntity!=null)
    {
      ret.addAll(_superEntity.getFields());
    }
    for(EditableField field : _fieldsList)
    {
      ret.add(field);
    }
    return ret;
  }

  public List<Field> getFields(SymbolicProperty property)
  {
    List<EditableField> fields=getEditableFields(property);
    List<Field> ret=new ArrayList<Field>(fields.size());
    ret.addAll(fields);
    return ret;
  }

  /**
   * Get the fields that have a given property.
   * @param property Property to test.
   * @return A (possibly empty) list of all the fields of this entity
   * that have the specified property (including fields defined in parent entities, if any). 
   */
  public List<EditableField> getEditableFields(SymbolicProperty property)
  {
    List<EditableField> ret=new ArrayList<EditableField>();
    if (_superEntity!=null)
    {
      ret.addAll(_superEntity.getEditableFields(property));
    }
    for(EditableField field : _fieldsList)
    {
      if (field.hasProperty(property))
      {
        ret.add(field);
      }
    }
    return ret;
  }

  public List<Field> getFields(SymbolicPropertiesSet properties)
  {
    List<Field> ret=new ArrayList<Field>();
    if (_superEntity!=null)
    {
      ret.addAll(_superEntity.getFields(properties));
    }
    for(EditableField field : _fieldsList)
    {
      if (field.hasProperties(properties))
      {
        ret.add(field);
      }
    }
    return ret;
  }

  @Override
  public String toString()
  {
    return _name;
  }
}
