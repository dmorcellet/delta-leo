package delta.leo.model.relation.impl;

import delta.leo.model.entity.Entity;
import delta.leo.model.relation.RelationTier;

/**
 * Implementation of a relation tier.
 * @author DAM
 */
public class EditableRelationTier implements RelationTier
{
  private int _index;
  private Entity _entity;
  private String _role;

  /**
   * Constructor.
   * @param index Index of this tier in the parent relation.
   * @param entity Associated entity.
   */
  EditableRelationTier(int index, Entity entity)
  {
    _index=index;
    _entity=entity;
    _role="";
  }

  @Override
  public int getIndex()
  {
    return _index;
  }

  @Override
  public Entity getEntity()
  {
    return _entity;
  }

  @Override
  public String getRole()
  {
    return _role;
  }

  /**
   * Set the role of this tier in the parent relation.
   * @param role role to set.
   */
  public void setRole(String role)
  {
    _role=role;
  }
}
