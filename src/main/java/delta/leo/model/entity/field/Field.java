package delta.leo.model.entity.field;

import delta.common.utils.properties.SymbolicPropertiesSet;
import delta.common.utils.properties.SymbolicProperty;
import delta.common.utils.types.Type;

/**
 * Interface of a field (attribute) in the Entity-Relationships data modeling paradigm.
 * @author DAM
 */
public interface Field
{
  /**
   * Get the name of this field.
   * @return a name.
   */
  public String getName();

  /**
   * Get the type of this field.
   * @return the type of this field.
   */
  public Type getType();

  /**
   * Get the properties of this field.
   * @return a properties set.
   */
  public SymbolicPropertiesSet getProperties();

  /**
   * Indicates if this field has the specified property.
   * @param property Property to test.
   * @return <code>true</code> if it has the specified property, <code>false</code> otherwise.
   */
  public boolean hasProperty(SymbolicProperty property);
}
