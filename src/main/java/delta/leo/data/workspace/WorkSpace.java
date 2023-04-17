package delta.leo.data.workspace;

import java.util.ArrayList;

import delta.leo.data.ObjectInstance;

/**
 * Workspace.
 * <p>
 * A listen-able place to add and remove objects.
 * @author DAM
 */
public class WorkSpace
{
  private ArrayList<WorkSpaceListener> _listeners;

  /**
   * Constructor.
   */
  public WorkSpace()
  {
    _listeners=new ArrayList<WorkSpaceListener>();
  }

  /**
   * Add an object.
   * @param object Object to add.
   */
  public void addObject(ObjectInstance object)
  {
    WorkSpaceEvent event=new WorkSpaceEvent(WorkSpaceEvent.OBJECT_ADDED, object);
    callListeners(event);
  }

  /**
   * Add a listener.
   * @param listener Listener to add.
   */
  public void addListener(WorkSpaceListener listener)
  {
    _listeners.add(listener);
  }

  /**
   * Remove a listener.
   * @param listener Listener to add.
   */
  public void removeListener(WorkSpaceListener listener)
  {
    _listeners.remove(listener);
  }

  private void callListeners(WorkSpaceEvent event)
  {
    int nbListeners=_listeners.size();
    WorkSpaceListener listener;
    for(int i=0;i<nbListeners;i++)
    {
      listener=_listeners.get(i);
      listener.handleEvent(event);
    }
  }
}
