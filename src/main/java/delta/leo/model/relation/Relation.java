package delta.leo.model.relation;

import java.util.List;

import delta.leo.model.entity.Entity;

/**
 * Interface of a relation in the Entity-Relationships data modeling paradigm.
 * @author DAM
 */
public interface Relation
{
  /**
   * Get the name of this relation.
   * @return a name.
   */
  public String getName();

  /**
   * Get the type of this relation.
   * @return a relation type (association, aggregation...).
   */
  public RelationType getType();

  /**
   * Get the number of tiers in this relation.
   * @return a number of tiers.
   */
  public int getNumberOfTier();

  /**
   * Get the tier that plays the given role in this relation.
   * @param role Role to search.
   * @return A relation tier, or <code>null</code> if not found
   */
  public RelationTier getTierByRole(String role);

  /**
   * Get a list of all relation tiers.
   * @return a list of all the tiers involved in this relation.
   */
  public List<RelationTier> getRelationsTiers();

  /**
   * Get the link that links the specified roles.
   * @param sourceRole Source role.
   * @param targetRole Target role.
   * @return A relation link or <code>null</code> if not found.
   */
  public RelationLink getLinkByRoles(String sourceRole, String targetRole);

  /**
   * Get the link that links the specified tiers.
   * @param from Source tier.
   * @param to Target tier.
   * @return A relation link or <code>null</code> if not found.
   */
  public RelationLink getLink(RelationTier from, RelationTier to);

  /**
   * Get all the links that start from a given entity.
   * @param e Source entity.
   * @return A (possibly empty) list of relation links.
   */
  public List<RelationLink> getLinksFrom(Entity e);

  /**
   * Get all the links that start from a given tier.
   * @param tier Source tier.
   * @return A (possibly empty) list of relation links.
   */
  public List<RelationLink> getLinksFrom(RelationTier tier);
}
