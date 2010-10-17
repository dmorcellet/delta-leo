package delta.leo.model.relation;

import delta.leo.model.entity.Entity;

/**
 * Interface of a relation tier.
 * @author DAM
 */
public interface RelationTier
{
  /**
   * Get the index of this tier is the parent relation.
   * @return An index (from 0 to the number of tiers-1).
   */
  public int getIndex();
  /**
   * Get the entity of this tier.
   * @return an entity.
   */
  public Entity getEntity();

  /**
   * Get the role of this tier.
   * @return a role.
   */
  public String getRole();
}
