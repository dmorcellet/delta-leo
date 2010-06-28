package delta.leo.connector;

import java.util.List;

import delta.leo.binding.ClassBinding;
import delta.leo.data.ObjectId;
import delta.leo.data.ObjectInstance;
import delta.leo.data.ObjectsSource;

/**
 * Makes the link between a data location and a data source.
 * It is used by the objects source to access to the data source.
 * @author DAM
 */
public class DataConnector
{
  private ObjectsSource _objectsSource;

  /**
   * Constructor.
   * @param objectsSource Parent objects source.
   */
  public DataConnector(ObjectsSource objectsSource)
  {
    _objectsSource=objectsSource;
  }

  /**
   * Get the parent objects source.
   * @return the parent objects source.
   */
  public ObjectsSource getObjectsSource()
  {
    return _objectsSource;
  }

  /**
   * Initializes this connector.
   * <p>
   * Default implementation that does nothing.
   * @return <code>true</code> if initialization succeeded, <code>false</code> otherwise.
   */
  public boolean init()
  {
    return true;
  }

  public void create(ClassBinding binding, ObjectInstance instance)
  {
    // Nothing to do...
  }

  public ObjectInstance get(ObjectId id)
  {
    return null;
  }

  public List<ObjectInstance> getAll(ClassBinding binding)
  {
    return null;
  }

  /**
   * Terminates this connector.
   * <p>
   * Default implementation that does nothing.
   * @return <code>true</code> if termination succeeded, <code>false</code> otherwise.
   */
  public boolean terminate()
  {
    return true;
  }
}
