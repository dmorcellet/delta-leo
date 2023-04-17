package delta.leo.data;

import java.util.HashMap;

import org.apache.log4j.Logger;

/**
 * todo Use special Reference/ReferenceQueue objects to keep used/referenced objects in this cache.
 * @author DAM
 */
public class ObjectsCache
{
  private static final Logger LOGGER=Logger.getLogger(ObjectsCache.class);

  private HashMap<ObjectId,ObjectInstance> _cache;

  /**
   * Constructor.
   */
  public ObjectsCache()
  {
    _cache=new HashMap<ObjectId,ObjectInstance>();
  }

  /**
   * Get an object using its identifier.
   * @param id Identifier of the object to get.
   * @return An object or <code>null</code> if not found.
   */
  public ObjectInstance get(ObjectId id)
  {
    ObjectInstance ret=_cache.get(id);
    return ret;
  }

  /**
   * Store an object.
   * @param object Object to store.
   */
  public void put(ObjectInstance object)
  {
    ObjectId id=object.getId();
    if (id!=null)
    {
      ObjectInstance oldOne=_cache.put(id,object);
      if (oldOne!=null)
      {
        if (LOGGER.isDebugEnabled())
        {
          LOGGER.debug("Removed old cache value for object ID : "+id);
        }
      }
    }
    else
    {
      LOGGER.error("Cannot cache an object with no ID.");
    }
  }

  /**
   * Get the size of this cache.
   * @return An count of objects.
   */
  public int getSize()
  {
    return _cache.size();
  }
}
