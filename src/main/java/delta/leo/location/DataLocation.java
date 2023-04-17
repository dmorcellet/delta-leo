package delta.leo.location;

/**
 * Base class for data locations.
 * @author DAM
 */
public abstract class DataLocation
{
  /**
   * Default location name.
   */
  public static final String DEFAULT="DefaultLocation";

  private String _id;

  /**
   * Constructor.
   * @param id Location identifier.
   */
  public DataLocation(String id)
  {
    _id=id;
  }

  /**
   * Get the location identifier.
   * @return A location identifier.
   */
  public String getId()
  {
    return _id;
  }

  /**
   * Get the data location type.
   * @return a data location type.
   */
  public abstract DataLocationType getType();
}
