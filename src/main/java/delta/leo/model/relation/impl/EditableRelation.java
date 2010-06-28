package delta.leo.model.relation.impl;

import java.util.ArrayList;
import java.util.List;

import delta.leo.model.entity.Entity;
import delta.leo.model.relation.Relation;
import delta.leo.model.relation.RelationLink;
import delta.leo.model.relation.RelationTier;
import delta.leo.model.relation.RelationType;

public class EditableRelation implements Relation
{
  private String _name;
  private final RelationType _type;
  private final EditableRelationTier[] _tiers;
  private final EditableRelationLink[] _links;

  public EditableRelation(RelationType type, List<Entity> involvedEntities)
  {
    _name="";
    _type=type;
    int nbTiers=involvedEntities.size();
    checkNumberOfTiers(type,nbTiers);
    _tiers=new EditableRelationTier[nbTiers];
    for(int i=0;i<nbTiers;i++)
    {
      _tiers[i]=new EditableRelationTier(i,involvedEntities.get(i));
    }
    int nbLinks=nbTiers*nbTiers;
    _links=new EditableRelationLink[nbLinks];
    for(int i=0;i<nbTiers;i++)
    {
      for(int j=0;j<nbTiers;j++)
      {
        int index=getLinkIndex(i,j);
        if (i!=j)
        {
          _links[index]=new EditableRelationLink(_tiers[i],_tiers[j]);
        }
      }
    }
  }

  private int getLinkIndex(int tierIndex1, int tierIndex2)
  {
    int ret=(tierIndex1*_tiers.length)+tierIndex2;
    return ret;
  }

  public RelationType getType()
  {
    return _type;
  }

  public String getName()
  {
    return _name;
  }

  public void setName(String name)
  {
    _name=name;
  }

  public EditableRelationTier getTier(int index)
  {
    return _tiers[index];
  }

  public List<RelationTier> getRelationsTiers()
  {
    List<RelationTier> ret=new ArrayList<RelationTier>();
    for(int i=0;i<_tiers.length;i++)
    {
      ret.add(_tiers[i]);
    }
    return ret;
  }

  public RelationTier getTierByRole(String role)
  {
    for(int i=0;i<_tiers.length;i++)
    {
      if (_tiers[i].getRole().equals(role))
      {
        return _tiers[i];
      }
    }
    return null;
  }

  public RelationLink getLinkByRoles(String sourceRole, String targetRole)
  {
    RelationLink ret=null;
    for(RelationLink link : _links)
    {
      if ((link!=null) && (link.getSource().getRole().equals(sourceRole)) &&
          (link.getTarget().getRole().equals(targetRole)))
      {
        ret=link;
        break;
      }
    }
    return ret;
  }

  public RelationLink getLink(RelationTier from, RelationTier to)
  {
    return getEditableLink(from,to);
  }

  public EditableRelationLink getEditableLink(RelationTier from, RelationTier to)
  {
    int fromIndex=from.getIndex();
    if ((fromIndex==-1) || (_tiers[fromIndex]!=from))
    {
      throw new IllegalArgumentException("Tier ["+from+" is not involved in relation ["+this+"]");
    }
    int toIndex=to.getIndex();
    if ((toIndex==-1) || (_tiers[toIndex]!=to))
    {
      throw new IllegalArgumentException("Tier ["+to+" is not involved in relation ["+this+"]");
    }
    int linkIndex=getLinkIndex(fromIndex,toIndex);
    EditableRelationLink link=_links[linkIndex];
    return link;
  }

  public List<RelationLink> getLinksFrom(Entity e)
  {
    List<RelationLink> ret=new ArrayList<RelationLink>();
    for(RelationLink link : _links)
    {
      if ((link!=null) && (link.getSource().getEntity()==e))
      {
        ret.add(link);
      }
    }
    return ret;
  }

  public List<RelationLink> getLinksFrom(RelationTier tier)
  {
    List<RelationLink> ret=new ArrayList<RelationLink>();
    for(RelationLink link : _links)
    {
      if ((link!=null) && (link.getSource()==tier))
      {
        ret.add(link);
      }
    }
    return ret;
  }

  public int getNumberOfTier()
  {
    return _tiers.length;
  }

  /**
   * Check that the specified number of entities is compatible
   * with the specified relation type.
   * @param type relation type.
   * @param nb number of entities.
   */
  private void checkNumberOfTiers(RelationType type, int nb)
  {
    int min=type.getMininumNumberOfTiers();
    if (nb<min)
    {
      String message="Bad number of entities in this relation ! Expected at least "+min+" entities. Got "+nb+".";
      throw new IllegalArgumentException(message);
    }
    int max=type.getMininumNumberOfTiers();
    if ((max!=Integer.MAX_VALUE) && (nb>max))
    {
      String message="Bad number of entities in this relation ! Expected at most "+min+" entities. Got "+nb+".";
      throw new IllegalArgumentException(message);
    }
  }
}
