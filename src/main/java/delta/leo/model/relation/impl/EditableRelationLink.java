package delta.leo.model.relation.impl;

import delta.leo.model.Cardinality;
import delta.leo.model.relation.RelationLink;
import delta.leo.model.relation.RelationTier;

public class EditableRelationLink implements RelationLink
{
  private RelationTier _source;
  private RelationTier _target;

  private String _label;
  private int _minOccurrences;
  private int _maxOccurrences;
  private boolean _traversable;
  private boolean _ordered;

  public EditableRelationLink(RelationTier source, RelationTier target)
  {
    _source=source;
    _target=target;
    _label="";
    _minOccurrences=0;
    _minOccurrences=1;
    _traversable=false;
    _ordered=false;
  }

  public RelationTier getSource()
  {
    return _source;
  }

  public RelationTier getTarget()
  {
    return _target;
  }

  public int getMinOccurrences()
  {
    return _minOccurrences;
  }

  public void setMinOccurrences(int minOccurrences)
  {
    if (minOccurrences<0)
    {
      String message="Min value incorrect for relation link ["+this+"] : "+minOccurrences;
      throw new IllegalArgumentException(message);
    }
    _minOccurrences=minOccurrences;
  }

  public int getMaxOccurrences()
  {
    return _maxOccurrences;
  }

  public void setMaxOccurrences(int maxOccurrences)
  {
    if (maxOccurrences<0)
    {
      String message="Max value incorrect for relation link ["+this+"] : "+maxOccurrences;
      throw new IllegalArgumentException(message);
    }
    _maxOccurrences=maxOccurrences;
  }

  public boolean isTraversable()
  {
    return _traversable;
  }

  public void setTraversable(boolean traversable)
  {
    _traversable=traversable;
  }

  public boolean isOrdered()
  {
    return _ordered;
  }

  public void setOrdered(boolean ordered)
  {
    _ordered=ordered;
  }

  public String getLabel()
  {
    return _label;
  }

  public void setLabel(String label)
  {
    _label=label;
  }

  private String getDisplayableOccurrencesRange()
  {
    if (_minOccurrences==_maxOccurrences)
      return String.valueOf(_minOccurrences);
    StringBuffer sb=new StringBuffer();
    sb.append(_minOccurrences);
    sb.append('-');
    if (_maxOccurrences==Cardinality.INFINITE_CARDINALITY)
    {
      sb.append("+oo");
    }
    else
    {
      sb.append(_maxOccurrences);
    }
    return sb.toString();
  }

  @Override
  public String toString()
  {
    StringBuffer sb=new StringBuffer();
    String sourceName=_source.getEntity().getName();
    sb.append(sourceName);
    String sourceRole=_source.getRole();
    if ((sourceRole.length()>0)&&(!sourceRole.equals(sourceName)))
    {
      sb.append(" (").append(sourceRole).append(')');
    }
    if ((_label!=null) && (_label.length()>0))
    {
      sb.append(" {").append(_label).append("} ");
    }
    else
    {
      sb.append(" -> ");
    }
    String targetName=_target.getEntity().getName();
    sb.append(targetName);
    String targetRole=_target.getRole();
    if ((targetRole.length()>0)&&(!targetRole.equals(targetName)))
    {
      sb.append(" (").append(targetRole).append(')');
    }
    sb.append(" (").append(getDisplayableOccurrencesRange()).append(')');
    sb.append(" (");
    if (!_ordered)
    {
      sb.append("!");
    }
    sb.append("ordered)");
    sb.append(" (");
    if (!_traversable)
    {
      sb.append("!");
    }
    sb.append("traversable)");
    return sb.toString();
  }
}
