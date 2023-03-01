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

  public ObjectsCache()
  {
    _cache=new HashMap<ObjectId,ObjectInstance>();
  }

  public ObjectInstance get(ObjectId id)
  {
    ObjectInstance ret=_cache.get(id);
    return ret;
  }

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

  public int getSize()
  {
    return _cache.size();
  }
}
