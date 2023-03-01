package delta.leo.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import delta.leo.binding.BindingsManager;
import delta.leo.location.DataLocation;
import delta.leo.metadata.ObjectsModel;

/**
 * @author DAM
 */
public class ObjectsRealm
{
  private static final Logger LOGGER=Logger.getLogger(ObjectsRealm.class);

  private ObjectsModel _model;
  /**
   * Map location names to object sources.
   */
  private HashMap<String,ObjectsSource> _sources;

  /**
   * Constructor.
   * @param model Managed <tt>ObjectsModel</tt>.
   */
  public ObjectsRealm(ObjectsModel model)
  {
    _model=model;
    _sources=new HashMap<String,ObjectsSource>();
  }

  /**
   * Get the managed <tt>ObjectsModel</tt>.
   * @return the managed <tt>ObjectsModel</tt>.
   */
  public ObjectsModel getObjectsModel()
  {
    return _model;
  }

  public void start()
  {
    buildObjectsSources();
    initObjectsSources();
  }

  public void terminate()
  {
    terminateObjectSources();
    _sources.clear();
  }

  /**
   * Build all objects source for the managed <tt>ObjectsModel</tt>.
   */
  private void buildObjectsSources()
  {
    List<DataLocation> locations=_model.getLocations();
    DataLocation location;
    ObjectsSource objectsSource;
    String locationName;
    BindingsManager bindingsMgr;
    for(Iterator<DataLocation> it=locations.iterator();it.hasNext();)
    {
      location=it.next();
      bindingsMgr=_model.getBindingsManager(location);
      objectsSource=new ObjectsSource(this,location,bindingsMgr);
      locationName=location.getId();
      _sources.put(locationName,objectsSource);
    }
  }

  /**
   * Get the objects source for default, in memory location.
   * @return An <tt>ObjectsSource</tt> instance.
   */
  public ObjectsSource getDefaultObjectsSource()
  {
    ObjectsSource ret=getObjectsSource(DataLocation.DEFAULT);
    return ret;
  }

  /**
   * Get the objects source for the specified location.
   * @param locationName Location name.
   * @return An <tt>ObjectsSource</tt> instance or <code>null</code> if not found.
   */
  public ObjectsSource getObjectsSource(String locationName)
  {
    ObjectsSource ret=_sources.get(locationName);
    return ret;
  }

  private int initObjectsSources()
  {
    int nbFailed=0;
    Collection<ObjectsSource> sources=_sources.values();
    ObjectsSource objectsSource;
    for(Iterator<ObjectsSource> it=sources.iterator();it.hasNext();)
    {
      objectsSource=it.next();
      if (!objectsSource.init())
      {
        LOGGER.warn("Could not init objects source "+objectsSource);
        nbFailed++;
      }
    }
    return nbFailed;
  }

  private void terminateObjectSources()
  {
    Collection<ObjectsSource> sources=_sources.values();
    ObjectsSource objectsSource;
    for(Iterator<ObjectsSource> it=sources.iterator();it.hasNext();)
    {
      objectsSource=it.next();
      if (!objectsSource.terminate())
      {
        LOGGER.warn("Could not terminate objects source "+objectsSource);
      }
    }
  }
}
