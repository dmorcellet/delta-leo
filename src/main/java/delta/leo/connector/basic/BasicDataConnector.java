package delta.leo.connector.basic;

import java.util.HashMap;

import delta.leo.connector.DataConnector;
import delta.leo.data.ObjectId;
import delta.leo.data.ObjectInstance;
import delta.leo.data.ObjectsSource;

/**
 * A very basic implementation of a data connector.
 * All objects are created directly in memory.
 * @author DAM
 */
public class BasicDataConnector extends DataConnector
{
  private HashMap<ObjectId,ObjectInstance> _objectsMap;

  /**
   * Constructor.
   * @param objectsSource Parent objects source.
   */
  public BasicDataConnector(ObjectsSource objectsSource)
  {
    super(objectsSource);
    _objectsMap=new HashMap<ObjectId,ObjectInstance>();
  }

  /**
   * Initialization method.
   * @return <code>true</code> if this connector was successfully initialized, <code>false</code> otherwise.
   */
  @Override
  public boolean init()
  {
    _objectsMap.clear();
    return true;
  }

  /**
   * Create an object in this connector.
   * The newly created object receives an object identifier.
   * @param instance Object to create.
   */
  public void create(ObjectInstance instance)
  {
    ObjectId oid=instance.getId();
    _objectsMap.put(oid,instance);
  }

  /**
   * Retrieve an object from this connector.
   * @param id Identifier of the object to retrieve.
   * @return An object instance or <code>null</code> if not found.
   */
  @Override
  public ObjectInstance get(ObjectId id)
  {
    ObjectInstance instance=_objectsMap.get(id);
    return instance;
  }

  /**
   * Termination method.
   * @return <code>true</code> if this connector was successfully terminated, <code>false</code> otherwise.
   */
  @Override
  public boolean terminate()
  {
    _objectsMap.clear();
    return true;
  }
}
