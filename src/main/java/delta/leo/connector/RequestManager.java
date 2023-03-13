package delta.leo.connector;

import delta.leo.binding.ClassBinding;

/**
 * Base class for a request manager.
 * @author DAM
 */
public class RequestManager
{
  private ClassBinding _classBinding;

  /**
   * Constructor.
   * @param classBinding Class binding.
   */
  public RequestManager(ClassBinding classBinding)
  {
    _classBinding=classBinding;
  }

  protected ClassBinding getClassBinding()
  {
    return _classBinding;
  }
}
