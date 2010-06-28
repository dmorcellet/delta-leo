package delta.leo.binding;

import delta.common.utils.properties.SymbolicPropertiesRegistry;
import delta.common.utils.properties.SymbolicPropertiesSet;
import delta.common.utils.properties.SymbolicProperty;

/**
 * @author DAM
 */
public class BindingProperties
{
  private static SymbolicPropertiesRegistry _instance;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static SymbolicPropertiesRegistry getBindingPropertiesRegistry()
  {
    synchronized (SymbolicPropertiesRegistry.class)
    {
      if (_instance==null)
      {
        _instance=new SymbolicPropertiesRegistry();
        _instance.createProperty(BindingPropertyNames.PRELOAD);
        _instance.createProperty(BindingPropertyNames.AUTOGENERATED);
      }
      return _instance;
    }
  }

  /**
   * "PRELOAD" binding property.
   */
  public static final SymbolicProperty PRELOAD=getBindingPropertiesRegistry().getProperty(BindingPropertyNames.PRELOAD);

  /**
   * "AUTOGENERATED" binding property.
   */
  public static final SymbolicProperty AUTOGENERATED=getBindingPropertiesRegistry().getProperty(BindingPropertyNames.AUTOGENERATED);

  /**
   * Default, empty field properties set.
   */
  public static final SymbolicPropertiesSet DEFAULT_PROPERTIES=getBindingPropertiesRegistry().getEmptySet();
}
