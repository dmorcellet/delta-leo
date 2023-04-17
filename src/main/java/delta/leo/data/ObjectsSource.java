package delta.leo.data;

import java.util.HashMap;
import java.util.List;

import delta.leo.binding.BindingsManager;
import delta.leo.binding.ClassBinding;
import delta.leo.connector.DataConnector;
import delta.leo.connector.DataConnectorFactory;
import delta.leo.location.DataLocation;
import delta.leo.metadata.ObjectClass;
import delta.leo.metadata.ObjectsModel;

/**
 * Manages all objects of a location.
 * @author DAM
 */
public class ObjectsSource
{
  /**
   * Parent realm.
   */
  private ObjectsRealm _realm;
  /**
   * Managed data location.
   */
  private DataLocation _location;
  /**
   * Associated bindings manager.
   */
  private BindingsManager _bindingsMgr;
  /**
   * Managed data connector.
   */
  private DataConnector _connector;
  /**
   * Maps class names to objects managers.
   */
  private HashMap<String,ObjectsManager> _objectsManagers;

  /**
   * Constructor.
   * @param realm Objects realm that owns this objects source.
   * @param location Managed location.
   * @param bindingsMgr Bindings manager.
   */
  public ObjectsSource(ObjectsRealm realm, DataLocation location, BindingsManager bindingsMgr)
  {
    _realm=realm;
    _location=location;
    _bindingsMgr=bindingsMgr;
    DataConnectorFactory factory=DataConnectorFactory.getInstance();
    _connector=factory.buildConnector(this);
    _objectsManagers=new HashMap<String,ObjectsManager>();
  }

  /**
   * Get the data location managed by this objects source.
   * @return the data location managed by this objects source.
   */
  public DataLocation getDataLocation()
  {
    return _location;
  }

  /**
   * Get the associated objects model.
   * @return the associated objects model.
   */
  public ObjectsModel getObjectsModel()
  {
    ObjectsModel ret=_realm.getObjectsModel();
    return ret;
  }

  /**
   * Get the name of the managed location.
   * @return A name.
   */
  public String getName()
  {
    return _location.getId();
  }

  /**
   * Get the managed connector.
   * @return the managed connector.
   */
  public DataConnector getConnector()
  {
    return _connector;
  }

  /**
   * Get the objects manager for the given class.
   * @param className Name of the class to use.
   * @return An object manager or <code>null</code> if not known.
   */
  public ObjectsManager getManager(String className)
  {
    ObjectsManager ret=_objectsManagers.get(className);
    if (ret==null)
    {
      ObjectsModel model=getObjectsModel();
      ObjectClass clazz=model.getClassByName(className);
      if (clazz!=null)
      {
        ClassBinding binding=null;
        if (_bindingsMgr!=null)
        {
          binding=_bindingsMgr.getBindingForClass(className);
        }
        ret=new ObjectsManager(this,clazz,binding);
        _objectsManagers.put(className,ret);
      }
    }
    return ret;
  }

  /**
   * Init.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean init()
  {
    boolean ret=_connector.init();
    return ret;
  }

  /**
   * Terminate.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean terminate()
  {
    boolean ret=_connector.terminate();
    return ret;
  }

  /**
   * Create (persist) an object instance.
   * @param object Object to create.
   */
  public void create(ObjectInstance object)
  {
    ObjectClass clazz=object.getObjectClass();
    String className=clazz.getName();
    ObjectsManager manager=getManager(className);
    manager.create(object);
    ObjectId oid=object.getId();
    oid.setObjectsSource(this);
  }

  /**
   * Get an object.
   * @param id Object identifier.
   * @return An object instance or <code>null</code>.
   */
  public ObjectInstance get(ObjectId id)
  {
    ObjectClass clazz=id.getClassInfo();
    String className=clazz.getName();
    ObjectsManager manager=getManager(className);
    ObjectInstance ret=manager.get(id);
    return ret;
  }

  /**
   * Get all the managed instances.
   * @param clazz Targeted class.
   * @return A list of object instances.
   */
  public List<ObjectInstance> getAll(ObjectClass clazz)
  {
    String className=clazz.getName();
    ObjectsManager manager=getManager(className);
    List<ObjectInstance> ret=manager.getAll();
    return ret;
  }
}
