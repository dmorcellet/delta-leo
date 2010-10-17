package delta.leo.model.relation;

/**
 * Interface of a relation link.
 * @author DAM
 */
public interface RelationLink
{
  /**
   * Get the source tier.
   * @return a relation tier.
   */
  public RelationTier getSource();

  /**
   * Get the target tier.
   * @return a relation tier.
   */
  public RelationTier getTarget();

  /**
   * Get the minimum number of target instances related to a single source instance
   * through this link.
   * @return a number of instances.
   */
  public int getMinOccurrences();

  /**
   * Get the maximum number of target instances related to a single source instance
   * through this link.
   * @return a number of instances.
   */
  public int getMaxOccurrences();

  /**
   * Indicates if this link is traversable, i.e. if it is possible to reach target instance
   * from a source instance.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isTraversable();

  /**
   * Indicates if this relation link is ordered, or not.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isOrdered();

  /**
   * Get the label of this link.
   * @return a label.
   */
  public String getLabel();
}
