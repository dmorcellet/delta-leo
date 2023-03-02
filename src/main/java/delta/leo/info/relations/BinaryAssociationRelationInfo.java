package delta.leo.info.relations;

import delta.leo.model.entity.Entity;

/**
 * Information storage class for a relation of type 'association' between two
 * classes.
 * For each class, we must define minimum and maximum number of related objects of the other class.
 * Examples for class A and B :
 * An instance of A may be related to 0-1, 1, 0-N, 1-N, N, N-P instances of B.
 * An instance of B may be related to 0-1, 1, 0-N, 1-N, N, N-P instances of A.
 */
public class BinaryAssociationRelationInfo extends AssociationRelationInfo
{
  private BinaryRelationInfo _direct;
  private BinaryRelationInfo _reverse;

  /**
   * Constructor.
   * @param name Relation name.
   * @param a One entity.
   * @param b The other entity.
   * @param minBRelatedToA Minimum number of Bs related to a A.
   * @param maxBRelatedToA Maximum number of Bs related to a A.
   * @param orderedBs Indicates if Bs shall be ordered or not.
   * @param minARelatedToB Minimum number of As related to a B.
   * @param maxARelatedToB Maximum number of As related to a B.
   * @param orderedAs Indicates if As shall be ordered or not.
   */
  public BinaryAssociationRelationInfo(String name, Entity a, Entity b,
      int minBRelatedToA, int maxBRelatedToA, boolean orderedBs,
      int minARelatedToB, int maxARelatedToB, boolean orderedAs)
  {
    super(name);
    _direct=new BinaryRelationInfo(a,b,minBRelatedToA,maxBRelatedToA,orderedBs);
    _reverse=new BinaryRelationInfo(b,a,minARelatedToB,maxARelatedToB,orderedAs);
  }

  /**
   * Get a directional relation.
   * @param direct Get the direct relation or the reverse relation.
   * @return A relation.
   */
  public BinaryRelationInfo getRelation(boolean direct)
  {
    if (direct) return _direct;
    return _reverse;
  }

  /**
   * Get the direct relation.
   * @return A relation.
   */
  public BinaryRelationInfo getDirectRelation()
  {
    return _direct;
  }

  /**
   * Get the reverse relation.
   * @return A relation.
   */
  public BinaryRelationInfo getReverseRelation()
  {
    return _reverse;
  }

  @Override
  public Entity getClass(int index)
  {
    if (index==0) return _direct.getSource();
    if (index==1) return _reverse.getSource();
    return null;
  }

  @Override
  public int getNbRelatedClasses()
  {
    return 2;
  }

  @Override
  public String toString()
  {
    StringBuffer sb=new StringBuffer();
    sb.append("Binary association ");
    sb.append(getName());
    sb.append(" : ");
    sb.append(_reverse.getDisplayableOccurrencesRange());
    sb.append(' ');
    sb.append(_direct.getSource().getName());
    sb.append(" -> ");
    sb.append(_direct.getDisplayableOccurrencesRange());
    sb.append(' ');
    sb.append(_reverse.getSource().getName());

    return sb.toString();
  }
}
