package delta.leo.model.relation;

import java.util.List;

import delta.leo.model.entity.Entity;

public interface Relation
{
  public String getName();

  public RelationType getType();

  public int getNumberOfTier();

  public RelationTier getTierByRole(String role);

  public List<RelationTier> getRelationsTiers();

  public RelationLink getLinkByRoles(String sourceRole, String targetRole);

  public RelationLink getLink(RelationTier from, RelationTier to);

  public List<RelationLink> getLinksFrom(Entity e);
  public List<RelationLink> getLinksFrom(RelationTier tier);
}
