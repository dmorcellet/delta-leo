package delta.leo.connector;

import delta.leo.binding.ClassBinding;

public class RequestManager
{
  private ClassBinding _classBinding;

  public RequestManager(ClassBinding classBinding)
  {
    _classBinding=classBinding;
  }

  protected ClassBinding getClassBinding()
  {
    return _classBinding;
  }
}
