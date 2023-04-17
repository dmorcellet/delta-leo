package delta.leo.data.workspace;

import delta.leo.data.ObjectInstance;

/**
 * Workspace event.
 * @author DAM
 */
public class WorkSpaceEvent
{
  /**
   * Event: object added.
   */
  public static final byte OBJECT_ADDED=0;
  /**
   * Event: object removed.
   */
  public static final byte OBJECT_REMOVED=1;
  /**
   * Event: object changed.
   */
  public static final byte OBJECT_CHANGED=2;

  private byte _eventType;
  private ObjectInstance _object;

  /**
   * Constructor.
   * @param type Event type.
   * @param object Involved object.
   */
  public WorkSpaceEvent(byte type, ObjectInstance object)
  {
    _eventType=type;
    _object=object;
  }

  /**
   * Get the event type.
   * @return an event type code.
   */
  public byte getType()
  {
    return _eventType;
  }

  /**
   * Get the involved object.
   * @return An object instance.
   */
  public ObjectInstance getObject()
  {
  	return _object;
  }
}
