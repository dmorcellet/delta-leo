package delta.leo.info.relations;

import delta.leo.model.entity.Entity;

/**
 * Base class for the description of an association relation.
 * @author DAM
 */
public abstract class AssociationRelationInfo extends RelationInfo
{
  /**
   * Constructor.
   * @param name Relation name.
   */
  public AssociationRelationInfo(String name)
  {
    super(name);
  }

  /**
   * Get the number of related classes.
   * @return A classes count.
   */
  public abstract int getNbRelatedClasses();

  /**
   * Get an involved class.
   * @param index Index of the involved class to get, starting at 0.
   * @return A class.
   */
  public abstract Entity getClass(int index);

  @Override
  public int getType()
  {
    return RelationInfo.TYPE_ASSOCIATION;
  }
}
