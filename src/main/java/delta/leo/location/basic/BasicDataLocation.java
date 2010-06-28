package delta.leo.location.basic;

import delta.leo.location.DataLocation;
import delta.leo.location.DataLocationType;

public class BasicDataLocation extends DataLocation
{
  public static final DataLocationType BASIC_LOCATION=new DataLocationType("BASIC");

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
