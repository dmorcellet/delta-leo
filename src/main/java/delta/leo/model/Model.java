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
   * @param entityName Name of the entity to get.
   * @return An entity or <code>null</code> if no such entity was found in this model.
   */
  public Entity getEntity(String entityName);

  /**
   * Get a list of all the entities of this model.
   * @return a list of entities.
   */
  public List<Entity> getEntities();

  /**
   * Get a relation by name.
   * @param relationName Name of the relation to get.
   * @return A relation or <code>null</code> if no such relation was found in this model.
   */
  public Relation getRelation(String relationName);

  /**
   * Get a list of all the relations of this model.
   * @return a list of relations.
   */
  public List<Relation> getRelations();

  /**
   * Get all the relation links that start from a given entity.
   * @param e Source entity.
   * @param useTraversability Indicates if the traversability information of links
   * shall be considered or not.
   * @return A list of relation links.
   */
  public List<RelationLink> getLinksFrom(Entity e, boolean useTraversability);
}
