package delta.leo.data.values;

import delta.leo.model.entity.field.Field;

/**
 * Field value.
 * @author DAM
 */
public interface FieldValue
{
  /**
   * Get the targeted field.
   * @return a field.
   */
  public Field getField();
}
