package delta.leo.data.workspace;

import java.util.ArrayList;

import delta.leo.data.ObjectInstance;

public class WorkSpace
{
  private ArrayList<WorkSpaceListener> _listeners;

  public WorkSpace()
  {
    _listeners=new ArrayList<WorkSpaceListener>();
  }

  public void addObject(ObjectInstance object)
  {
    WorkSpaceEvent event=new WorkSpaceEvent(WorkSpaceEvent.OBJECT_ADDED, object);
    callListeners(event);
  }

  public void addListener(WorkSpaceListener listener)
  {
    _listeners.add(listener);
  }

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
