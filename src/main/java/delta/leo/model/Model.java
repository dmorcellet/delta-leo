package delta.leo.model;

import java.util.List;

import delta.leo.model.entity.Entity;
import delta.leo.model.relation.Relation;
import delta.leo.model.relation.RelationLink;

/**
 * Represents an entity-relation data model.
 * @author DAM
 */
public interface Model
{
  /**
   * Get the name of this model.
   * @return the name of this model.
   */
  public String getName();

  /**
   * Get an entity by name.
   * @return An entity or <code>null</code> if no such entity was found in this model.
   */
  public Entity getEntity(String entityName);

  public List<Entity> getEntities();

  public Relation getRelation(String relationName);

  public List<Relation> getRelations();

  public List<RelationLink> getLinksFrom(Entity e, boolean useTraversability);
}
