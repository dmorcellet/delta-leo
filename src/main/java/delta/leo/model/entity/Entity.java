package delta.leo.model.entity;

import java.util.List;

import delta.common.utils.properties.SymbolicPropertiesSet;
import delta.common.utils.properties.SymbolicProperty;
import delta.leo.model.entity.field.Field;

/**
 * Represents an entity in the Entity-Relationships data modeling paradigm.
 * @author DAM
 */
public interface Entity
{
  public String getName();

  public Entity getSuperEntity();

  public Field getField(String fieldName);

  public List<Field> getFields();

  public List<Field> getFields(SymbolicProperty property);

  public List<Field> getFields(SymbolicPropertiesSet properties);

  public boolean isInstanceOf(Entity entity);
}
