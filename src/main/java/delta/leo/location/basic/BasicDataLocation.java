package delta.leo.location.basic;

import delta.leo.location.DataLocation;
import delta.leo.location.DataLocationType;

/**
 * 'Basic' data location.
 * @author DAM
 */
public class BasicDataLocation extends DataLocation
{
  /**
   * 'Basic' data location type.
   */
  public static final DataLocationType BASIC_LOCATION=new DataLocationType("BASIC");

  /**
   * Constructor.
   * @param id Location identifier.
   */
  public BasicDataLocation(String id)
  {
    super(id);
  }

  @Override
  public DataLocationType getType()
  {
    return BASIC_LOCATION;
  }
}
