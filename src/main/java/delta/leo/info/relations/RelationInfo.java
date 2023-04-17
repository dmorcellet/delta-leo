package delta.leo.info.relations;

/**
 * Base class for relation descriptions.
 * @author DAM
 */
public abstract class RelationInfo
{
  /**
   * Infinite cardinality constant.
   */
  public static final int INFINITE_CARDINALITY=Integer.MAX_VALUE;

  // Relation types
  /**
   * Composition.
   */
  public static final int TYPE_COMPOSITION=0;
  /**
   * Association.
   */
  public static final int TYPE_ASSOCIATION=1;

  private String _name;

  /**
   * Constructor.
   * @param name Relation name.
   */
  public RelationInfo(String name)
  {
    _name=name;
  }

  /**
   * Get the name of this relation.
   * @return A relation name.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Get the relation type.
   * @return A relation type code.
   */
  public abstract int getType();
}
