package delta.leo.data.workspace;

public class AbstractWorkSpaceListener implements WorkSpaceListener
{
  public AbstractWorkSpaceListener()
  {
    // Nothing to do !!
  }

  public void handleEvent(WorkSpaceEvent event)
  {
    switch(event.getType())
    {
      case WorkSpaceEvent.OBJECT_ADDED:
      {
        objectAdded(event);
      }
      break;
      case WorkSpaceEvent.OBJECT_REMOVED:
      {
        objectRemoved(event);
      }
      break;
      case WorkSpaceEvent.OBJECT_CHANGED:
      {
        objectChanged(event);
      }
      break;
      default:
      {
        // todo warning
      }
    }
  }

  public void objectAdded(WorkSpaceEvent event)
  {
    // Nothing to do !!
  }

  public void objectRemoved(WorkSpaceEvent event)
  {
    // Nothing to do !!
  }

  public void objectChanged(WorkSpaceEvent event)
  {
    // Nothing to do !!
  }
}
