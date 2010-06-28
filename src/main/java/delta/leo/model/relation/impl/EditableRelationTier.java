package delta.leo.model.relation.impl;

import delta.leo.model.entity.Entity;
import delta.leo.model.relation.RelationTier;

public class EditableRelationTier implements RelationTier
{
  private int _index;
  private Entity _entity;
  private String _role;

  public EditableRelationTier(int index, Entity entity)
  {
    _index=index;
    _entity=entity;
    _role="";
  }

  public int getIndex()
  {
    return _index;
  }

  public Entity getEntity()
  {
    return _entity;
  }

  public String getRole()
  {
    return _role;
  }

  public void setRole(String role)
  {
    _role=role;
  }
}
