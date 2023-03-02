package delta.leo.data;

import java.util.Iterator;
import java.util.List;

import delta.leo.binding.ClassBinding;
import delta.leo.connector.DataConnector;
import delta.leo.metadata.ObjectClass;

/**
 * Manages all the objects of a single class in a single objects source.
 * @author DAM
 */
public class ObjectsManager
{
  /**
   * Related objects source.
   */
  private ObjectsSource _source;
  /**
   * Managed class.
   */
  private ObjectClass _class;
  /**
   * Binding.
   */
  private ClassBinding _binding;

  /**
   * Cache for the objects of this class.
   */
  private ObjectsCache _cache;

  /**
   * Constructor.
   * @param source Data source.
   * @param clazz Managed class.
   * @param binding Class binding.
   */
  public ObjectsManager(ObjectsSource source, ObjectClass clazz, ClassBinding binding)
  {
    _source=source;
    _class=clazz;
    _binding=binding;
    _cache=new ObjectsCache();
  }

  public ObjectClass getObjectClass()
  {
    return _class;
  }

  protected ClassBinding getBinding()
  {
    return _binding;
  }

  public void lockObject(ObjectInstance object)
  {
    // todo
  }

  public void create(ObjectInstance object)
  {
    DataConnector connector=_source.getConnector();
    connector.create(_binding,object);
    _cache.put(object);
  }

  public ObjectInstance get(ObjectId id)
  {
    ObjectInstance ret=_cache.get(id);
    if (ret==null)
    {
      DataConnector connector=_source.getConnector();
      ret=connector.get(id);
      if (ret!=null)
      {
        _cache.put(ret);
      }
    }
    return ret;
  }

  public List<ObjectInstance> getAll()
  {
    DataConnector connector=_source.getConnector();
    List<ObjectInstance> ret=connector.getAll(_binding);
    if (ret!=null)
    {
      ObjectInstance instance;
      for(Iterator<ObjectInstance> it=ret.iterator();it.hasNext();)
      {
        instance=it.next();
        instance.getId().setObjectsSource(_source);
        _cache.put(instance);
      }
    }
    return ret;
  }

  public void unlockObject(ObjectInstance object)
  {
    // todo
  }
}
