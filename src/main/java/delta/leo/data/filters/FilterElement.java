package delta.leo.data.filters;

import delta.leo.data.ObjectInstance;

/**
 * Base class for filter elements.
 * @author DAM
 */
public abstract class FilterElement
{
  /**
   * Match the given object instance.
   * @param o Object instance to use.
   * @return <code>true</code> if the given instance does match this filter, <code>false</code> otherwise.
   */
  public abstract boolean match(ObjectInstance o);
}
