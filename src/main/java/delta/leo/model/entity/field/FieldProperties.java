package delta.leo.model.entity.field;

import delta.common.utils.properties.SymbolicPropertiesRegistry;
import delta.common.utils.properties.SymbolicPropertiesSet;
import delta.common.utils.properties.SymbolicProperty;

/**
 * @author DAM
 */
public class FieldProperties
{
  private static SymbolicPropertiesRegistry _instance;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static SymbolicPropertiesRegistry getFieldPropertiesRegistry()
  {
    synchronized (SymbolicPropertiesRegistry.class)
    {
      if (_instance==null)
      {
        _instance=new SymbolicPropertiesRegistry();
        _instance.createProperty(FieldPropertyNames.MANDATORY);
        _instance.createProperty(FieldPropertyNames.ID);
        _instance.createProperty(FieldPropertyNames.NAME);
      }
      return _instance;
    }
  }

  /**
   * "ID" field property.
   */
  public static final SymbolicProperty ID=getFieldPropertiesRegistry().getProperty(FieldPropertyNames.ID);

  /**
   * Default, empty field properties set.
   */
  public static final SymbolicPropertiesSet DEFAULT_PROPERTIES=getFieldPropertiesRegistry().getEmptySet();
}
