package delta.leo.location;

/**
 * Data location type.
 * @author DAM
 */
public class DataLocationType
{
  private String _type;

  /**
   * Constructor.
   * @param type Type identifier.
   */
  public DataLocationType(String type)
  {
    _type=type;
  }

  /**
   * Get the type identifier.
   * @return A type identifier.
   */
  public String getType()
  {
    return _type;
  }

  @Override
  public String toString()
  {
    return _type;
  }
}
