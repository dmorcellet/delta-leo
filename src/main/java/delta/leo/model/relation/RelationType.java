package delta.leo.model.relation;

/**
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
  /**
   * Specialization.
   */
  public static final RelationType SPECIALIZATION=new RelationType("SPECIALIZATION",2,2);

  private String _name;
  // Minimum number of tiers
  private int _minTiers;
  // Maximum number of tiers
  private int _maxTiers;

  private RelationType(String name, int minTiers, int maxTiers)
  {
    _name=name;
    _minTiers=minTiers;
    _maxTiers=maxTiers;
  }

  public String getName()
  {
    return _name;
  }

  public int getMininumNumberOfTiers()
  {
    return _minTiers;
  }

  public int getMaximumNumberOfTiers()
  {
    return _maxTiers;
  }
}
