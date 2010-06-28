package delta.leo.data;

import java.util.HashMap;

import org.apache.log4j.Logger;

import delta.common.utils.traces.LoggersRegistry;

/**
 * todo Use special Reference/ReferenceQueue objects to keep used/referenced objects in this cache.
 * @author DAM
 */
public class ObjectsCache
{
  private static final Logger _logger=LoggersRegistry.getLogger("LEO");

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
        if (_logger.isDebugEnabled())
        {
          _logger.debug("Removed old cache value for object ID : "+id);
        }
      }
    }
    else
    {
      _logger.error("Cannot cache an object with no ID.");
    }
  }

  public int getSize()
  {
    return _cache.size();
  }
}
