package delta.leo.model.relation;

/**
 * Relation type.
 * @author DAM
 */
public class RelationType
{
  /**
   * Association.
   */
  public static final RelationType ASSOCIATION=new RelationType("ASSOCIATION",2,Integer.MAX_VALUE);
  /**
   * Composition.
   */
  public static final RelationType COMPOSITION=new RelationType("COMPOSITION",2,2);

  private String _name;
  /**
   * Minimum number of tiers.
   */
  private int _minTiers;
  /**
   * Maximum number of tiers.
   */
  private int _maxTiers;

  private RelationType(String name, int minTiers, int maxTiers)
  {
    _name=name;
    _minTiers=minTiers;
    _maxTiers=maxTiers;
  }

  /**
   * Get the name of this relation type.
   * @return a name.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Get the minimum number of tiers in a relation of this type.
   * @return a number of tiers.
   */
  public int getMininumNumberOfTiers()
  {
    return _minTiers;
  }

  /**
   * Get the maximum number of tiers in a relation of this type.
   * @return a number of tiers.
   */
  public int getMaximumNumberOfTiers()
  {
    return _maxTiers;
  }
}
