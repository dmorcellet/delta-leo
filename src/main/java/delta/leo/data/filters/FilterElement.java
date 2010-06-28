package delta.leo.data.filters;

import delta.leo.data.ObjectInstance;

public abstract class FilterElement
{
  public abstract boolean match(ObjectInstance o);
}
