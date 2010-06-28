package delta.leo.info.relations;

public abstract class RelationInfo
{
  // Infinite cardinality constant
  public static final int INFINITE_CARDINALITY=Integer.MAX_VALUE;

  // Relation types
  public static final int TYPE_COMPOSITION=0;
  public static final int TYPE_ASSOCIATION=1;

  private String _name;

  public RelationInfo(String name)
  {
    _name=name;
  }

  public String getName()
  {
    return _name;
  }

  public abstract int getType();
}
