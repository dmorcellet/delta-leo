package delta.leo.tmp;

import java.util.Collection;

import delta.leo.data.values.FieldValue;
import delta.leo.model.entity.field.Field;

/**
 * Interface of a values set.
 * @author DAM
 */
public interface ValueSet
{
  /**
   * Set a field value.
   * @param value Value to set.
   * @return <code>true</code> if successful, <code>false</code> otherwise.
   */
  public boolean setValue(FieldValue value);
  /**
   * Get the number of values in this set.
   * @return A values count.
   */
  public int getNbValues();
  /**
   * Get the value for the given field.
   * @param field Field to use.
   * @return A field value or <code>null</code> if not found.
   */
  public FieldValue getValue(Field field);
  /**
   * Get all values in this set.
   * @return A collection of field values.
   */
  public Collection<FieldValue> getValues();
  /**
   * Remove all values.
   */
  public void clear();
}
