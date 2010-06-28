package delta.leo.location;

public abstract class DataLocation
{
  public static final String DEFAULT="DefaultLocation";

  private String _id;

  public DataLocation(String id)
  {
    _id=id;
  }

  public String getId()
  {
    return _id;
  }

  public abstract DataLocationType getType();
}
