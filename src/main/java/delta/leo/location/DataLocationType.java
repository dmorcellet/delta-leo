package delta.leo.location;

public class DataLocationType
{
  private String _type;

  public DataLocationType(String type)
  {
    _type=type;
  }

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
