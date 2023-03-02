package delta.leo.info.relations;

import delta.leo.model.entity.Entity;

/**
 * Directional relation.
 * @author DAM
 */
public class BinaryRelationInfo
{
  private Entity _source;
  private Entity _target;
  private int _minOccurrences;
  private int _maxOccurrences;

  /**
   * Constructor.
   * @param source Source entity.
   * @param target Target entity.
   * @param min Minimum number of target entities.
   * @param max Maximum number of target entities.
   * @param ordered Indicates if the relation is ordered or not.
   */
  public BinaryRelationInfo(Entity source, Entity target,
      int min, int max, boolean ordered)
  {
    if ((min>=0) && (min<=max))
    {
      _source=source;
      _target=target;
      _minOccurrences=min;
      _maxOccurrences=max;
    }
    else
    {
      throw new IllegalArgumentException("Relation "+source.getName()+"->"+target.getName()+" : Min="+min+", Max="+max);
    }
  }

  /**
   * Get the source entity.
   * @return an entity.
   */
  public Entity getSource()
  {
    return _source;
  }

  /**
   * Get the target entity.
   * @return an entity.
   */
  public Entity getTarget()
  {
    return _target;
  }

  /**
   * Get the minimum number of target entities.
   * @return An entities count.
   */
  public int getMinOccurrences()
  {
    return _minOccurrences;
  }

  /**
   * Get the maximum number of target entities.
   * @return An entities count.
   */
  public int getMaxOccurrences()
  {
    return _maxOccurrences;
  }

  /**
   * Get a displayable occurrences range.
   * @return A displayable occurrences range.
   */
  public String getDisplayableOccurrencesRange()
  {
    if (_minOccurrences==_maxOccurrences)
    {
      return String.valueOf(_minOccurrences);
    }
    StringBuffer sb=new StringBuffer();
    sb.append(_minOccurrences);
    sb.append('-');
    if (_maxOccurrences==RelationInfo.INFINITE_CARDINALITY)
    {
      sb.append("+oo");
    }
    else
    {
      sb.append(_maxOccurrences);
    }
    return sb.toString();
  }

  /**
   * Get a displayable link.
   * @return A displayable link.
   */
  public String getDisplayableLink()
  {
    StringBuffer sb=new StringBuffer();
    sb.append(_source.getName());
    sb.append(" -> ");
    sb.append(_target.getName());
    sb.append(" (");
    sb.append(getDisplayableOccurrencesRange());
    sb.append(')');

    return sb.toString();
  }
}
