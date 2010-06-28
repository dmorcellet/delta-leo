package delta.leo.info.relations;

import delta.leo.model.entity.Entity;

public abstract class AssociationRelationInfo extends RelationInfo
{
  public AssociationRelationInfo(String name)
  {
    super(name);
  }

  public abstract int getNbRelatedClasses();

  public abstract Entity getClass(int index);

  @Override
  public int getType()
  {
    return RelationInfo.TYPE_ASSOCIATION;
  }
}
