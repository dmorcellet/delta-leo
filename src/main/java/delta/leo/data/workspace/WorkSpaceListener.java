package delta.leo.data.workspace;

/**
 * Listener on a workspace.
 * @author DAM
 */
public interface WorkSpaceListener
{
  /**
   * Handle a workspace event.
   * @param event Event to handle.
   */
  public void handleEvent(WorkSpaceEvent event);
}
