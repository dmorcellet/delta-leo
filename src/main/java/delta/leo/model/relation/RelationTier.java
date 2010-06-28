package delta.leo.model.relation;

import delta.leo.model.entity.Entity;

public interface RelationTier
{
  public int getIndex();
  public Entity getEntity();
  public String getRole();
}
