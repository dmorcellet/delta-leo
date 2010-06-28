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

  public CompositionRelationInfo(String name,
      Entity master, Entity slave,
      int minSlaves, int maxSlaves, boolean ordered)
  {
    super(name);
    _infos=new BinaryRelationInfo(master,slave,minSlaves,maxSlaves,ordered);
  }

  public int getMinSlaves()
  {
    return _infos.getMinOccurrences();
  }

  public int getMaxSlaves()
  {
    return _infos.getMaxOccurrences();
  }

  public Entity getMasterClass()
  {
    return _infos.getSource();
  }

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
