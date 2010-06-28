package delta.leo.model.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import delta.common.utils.collections.ListOrderedMap;
import delta.leo.model.Model;
import delta.leo.model.entity.Entity;
import delta.leo.model.relation.Relation;
import delta.leo.model.relation.RelationLink;
import delta.leo.model.relation.RelationType;
import delta.leo.model.relation.impl.EditableRelation;

/**
 * Represents an entity-relation data model.
 * @author DAM
 */
public class EditableModel implements Model
{
  private final String _name;
  // Entities
  private ListOrderedMap<EditableEntity> _entities;
  // Relations
  private List<EditableRelation> _relations;

  /**
   * Constructor.
   * @param modelName Name of the model.
   */
  public EditableModel(String modelName)
  {
    _name=modelName;
    _entities=new ListOrderedMap<EditableEntity>();
    _relations=new ArrayList<EditableRelation>();
  }

  /**
   * Get the name of this model.
   * @return the name of this model.
   */
  public String getName()
  {
    return _name;
  }

  public EditableEntity newEntity(String name, EditableEntity superEntity)
  {
    EditableEntity old=_entities.get(name);
    if (old!=null)
    {
      throw new IllegalStateException("This model already has an entity named ["+name+"]");
    }
    EditableEntity entity=new EditableEntity(name,superEntity);
    _entities.put(name,entity);
    return entity;
  }

  /**
   * Get an entity by name.
   * @return An entity or <code>null</code> if no such entity was found in this model.
   */
  public Entity getEntity(String entityName)
  {
    return _entities.get(entityName);
  }

  /**
   * Get an entity by name.
   * @return An entity or <code>null</code> if no such entity was found in this model.
   */
  public EditableEntity getEditableEntity(String entityName)
  {
    return _entities.get(entityName);
  }

  public List<Entity> getEntities()
  {
    List<Entity> ret=new ArrayList<Entity>();
    ret.addAll(_entities.values());
    return ret;
  }

  public EditableRelation addBinaryAssociation(Entity e1, Entity e2)
  {
    checkEntity(e1);
    checkEntity(e2);
    List<Entity> entities=new ArrayList<Entity>(2);
    entities.add(e1);
    entities.add(e2);
    EditableRelation relation=new EditableRelation(RelationType.ASSOCIATION,entities);
    _relations.add(relation);
    return relation;
  }

  private void checkEntity(Entity e)
  {
    String name=e.getName();
    Entity myEntity=getEntity(name);
    if (myEntity!=e)
    {
      throw new IllegalArgumentException("Entity ["+e.getName()+"] does not belong to this model !");
    }
  }

  public Relation getRelation(String relationName)
  {
    for(Relation relation : _relations)
    {
      if (relation.getName().equals(relationName))
      {
        return relation;
      }
    }
    return null;
  }

  public List<Relation> getRelations()
  {
    List<Relation> ret=new ArrayList<Relation>();
    ret.addAll(_relations);
    return ret;
  }

  public List<RelationLink> getLinksFrom(Entity e, boolean useTraversability)
  {
    List<RelationLink> ret=new ArrayList<RelationLink>();
    List<RelationLink> tmp;
    for(EditableRelation relation : _relations)
    {
      tmp=relation.getLinksFrom(e);
      if (tmp!=null)
      {
        if (useTraversability)
        {
          RelationLink tmpLink;
          for(Iterator<RelationLink> it2=tmp.iterator();it2.hasNext();)
          {
            tmpLink=it2.next();
            if (tmpLink.isTraversable())
            {
              ret.add(tmpLink);
            }
          }
        }
        else
        {
          ret.addAll(tmp);
        }
      }
    }
    return ret;
  }
}
