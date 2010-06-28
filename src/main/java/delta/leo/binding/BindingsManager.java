package delta.leo.binding;

import java.util.HashMap;

import delta.leo.location.DataLocation;
import delta.leo.metadata.ObjectClass;

/**
 * Manages all the class bindings for a data location.
 * @author DAM
 */
public class BindingsManager
{
  private DataLocation _location;
  private HashMap<String,ClassBinding> _bindings;

  /**
   * Constructor.
   * @param location Managed data location.
   */
  public BindingsManager(DataLocation location)
  {
    _location=location;
    _bindings=new HashMap<String,ClassBinding>();
  }

  /**
   * Get the managed data location.
   * @return the managed data location.
   */
  public DataLocation getLocation()
  {
    return _location;
  }

  /**
   * Get the binding associated to the specified class in the
   * scope of the managed data location.
   * @param className Class name.
   * @return A <tt>Binding</tt> instance or <code>null</code> if not found.
   */
  public ClassBinding getBindingForClass(String className)
  {
    ClassBinding ret=_bindings.get(className);
    return ret;
  }

  /**
   * Register a new class binding definition.
   * @param binding Class binding.
   */
  public void registerBinding(ClassBinding binding)
  {
    ObjectClass clazz=binding.getObjectClass();
    String className=clazz.getName();
    _bindings.put(className,binding);
  }
}
