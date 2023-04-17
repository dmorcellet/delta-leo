package delta.leo.info.relations;

import delta.leo.model.entity.Entity;

/**
 * Information storage class for a relation of type 'composition' between two
 * classes (master class A includes slave class B).
 * An instance of A may include to 0-1, 1, 0-N, 1-N, N, N-P instances of B.
 */
public class CompositionRelationInfo extends RelationInfo
{
  private BinaryRelationInfo _infos;

  /**
   * Constructor.
   * @param name Relation name.
   * @param master Master class.
   * @param slave Slave class.
   * @param minSlaves Minimum slaves cardinality.
   * @param maxSlaves Maximum slaves cardinality.
   * @param ordered Ordered relation or not.
   */
  public CompositionRelationInfo(String name,
      Entity master, Entity slave,
      int minSlaves, int maxSlaves, boolean ordered)
  {
    super(name);
    _infos=new BinaryRelationInfo(master,slave,minSlaves,maxSlaves,ordered);
  }

  /**
   * Get the minimum number of slaves.
   * @return An entities count.
   */
  public int getMinSlaves()
  {
    return _infos.getMinOccurrences();
  }

  /**
   * Get the maximum number of slaves.
   * @return An entities count.
   */
  public int getMaxSlaves()
  {
    return _infos.getMaxOccurrences();
  }

  /**
   * Get the master class.
   * @return A class.
   */
  public Entity getMasterClass()
  {
    return _infos.getSource();
  }

  /**
   * Get the slave class.
   * @return A class.
   */
  public Entity getSlaveClass()
  {
    return _infos.getTarget();
  }

  @Override
  public int getType()
  {
    return RelationInfo.TYPE_COMPOSITION;
  }

  @Override
  public String toString()
  {
    StringBuffer sb=new StringBuffer();
    sb.append("Composition ");
    sb.append(getName());
    sb.append(" : ");
    sb.append(_infos.getDisplayableLink());

    return sb.toString();
  }
}
