package delta.leo.model.entity;

import java.util.List;

import delta.common.utils.properties.SymbolicPropertiesSet;
import delta.common.utils.properties.SymbolicProperty;
import delta.leo.model.entity.field.Field;

/**
 * Interface of an entity in the Entity-Relationships data modeling paradigm.
 * @author DAM
 */
public interface Entity
{
  /**
   * Get the name of this entity.
   * @return a name.
   */
  public String getName();

  /**
   * Get the parent entity, if any.
   * @return An entity or <code>null</code> if this entity has no parent entity.
   */
  public Entity getSuperEntity();

  /**
   * Get a field of this entity, designated by its name.
   * @param fieldName Name of the field to get.
   * @return A field or <code>null</code> if this entity has no such field.
   */
  public Field getField(String fieldName);

  /**
   * Get a list of all the fields of this entity.
   * @return A list of fields.
   */
  public List<Field> getFields();

  /**
   * Get a list of all the fields of this entity
   * that have the specified property.
   * @param property Property to test.
   * @return A list of fields.
   */
  public List<Field> getFields(SymbolicProperty property);

  /**
   * Get a list of all the fields of this entity
   * that have all the specified properties.
   * @param properties Properties to test.
   * @return A list of fields.
   */
  public List<Field> getFields(SymbolicPropertiesSet properties);

  /**
   * Indicates if this entity is the specified entity or inherits from it.
   * @param entity Entity to test.
   * @return <code>true</code> or <code>false</code>.
   */
  public boolean isInstanceOf(Entity entity);
}
