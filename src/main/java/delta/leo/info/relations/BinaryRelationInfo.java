package delta.leo.info.relations;

import delta.leo.model.entity.Entity;

public class BinaryRelationInfo
{
  private Entity _source;
  private Entity _target;
  private int _minOccurrences;
  private int _maxOccurrences;

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

  public Entity getSource()
  {
    return _source;
  }

  public Entity getTarget()
  {
    return _target;
  }

  public int getMinOccurrences()
  {
    return _minOccurrences;
  }

  public int getMaxOccurrences()
  {
    return _maxOccurrences;
  }

  public String getDisplayableOccurrencesRange()
  {
    if (_minOccurrences==_maxOccurrences)
      return String.valueOf(_minOccurrences);
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
