package delta.leo.data.workspace;

import delta.leo.data.ObjectInstance;

public class WorkSpaceEvent
{
  public static final byte OBJECT_ADDED=0;
  public static final byte OBJECT_REMOVED=1;
  public static final byte OBJECT_CHANGED=2;

  private byte _eventType;
  private ObjectInstance _object;

  public WorkSpaceEvent(byte type, ObjectInstance object)
  {
    _eventType=type;
    _object=object;
  }

  public byte getType()
  {
    return _eventType;
  }

  public ObjectInstance getObject()
  {
  	return _object;
  }
}
