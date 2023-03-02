package delta.leo.data.workspace;

/**
 * Basic implementation of a workspace listener.
 * @author DAM
 */
public class AbstractWorkSpaceListener implements WorkSpaceListener
{
  /**
   * Constructor.
   */
  public AbstractWorkSpaceListener()
  {
    // Nothing to do !!
  }

  @Override
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

  /**
   * Called when an object was added.
   * @param event Source event.
   */
  public void objectAdded(WorkSpaceEvent event)
  {
    // Nothing to do !!
  }

  /**
   * Called when an object was removed.
   * @param event Source event.
   */
  public void objectRemoved(WorkSpaceEvent event)
  {
    // Nothing to do !!
  }

  /**
   * Called when an object changed.
   * @param event Source event.
   */
  public void objectChanged(WorkSpaceEvent event)
  {
    // Nothing to do !!
  }
}
