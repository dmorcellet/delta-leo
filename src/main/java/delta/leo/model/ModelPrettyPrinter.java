package delta.leo.model;

import java.io.PrintStream;
import java.util.List;

import delta.common.utils.properties.SymbolicPropertiesSet;
import delta.common.utils.types.Type;
import delta.leo.model.entity.Entity;
import delta.leo.model.entity.field.Field;
import delta.leo.model.relation.Relation;
import delta.leo.model.relation.RelationLink;
import delta.leo.model.relation.RelationTier;

/**
 * Pretty prints the contents of a model.
 * @author DAM
 */
public class ModelPrettyPrinter
{
  private int _level;

  /**
   * Constructor.
   *
   */
  public ModelPrettyPrinter()
  {
    _level=0;
  }

  /**
   * Dump the contents of the given model to the specified output stream.
   * @param model Model to dump.
   * @param ps Output stream.
   */
  public void dumpModel(Model model, PrintStream ps)
  {
    String name=model.getName();
    List<Entity> entities=model.getEntities();
    int nbEntities=entities.size();
    List<Relation> relations=model.getRelations();
    int nbRelations=relations.size();
    printLine("Name ["+name+"], "+nbEntities+" entities, "+nbRelations+" relations",ps);
    _level++;
    for(Entity e : entities)
    {
      dumpEntity(e,ps);
    }
    for(Relation r : relations)
    {
      dumpRelation(r,ps);
    }
    _level--;
  }

  /**
   * Dump the contents of the given entity to the specified output stream.
   * @param entity Entity to dump.
   * @param ps Output stream.
   */
  public void dumpEntity(Entity entity, PrintStream ps)
  {
    String name=entity.getName();
    printLine("Name ["+name+"]",ps);
    _level++;
    Entity superEntity=entity.getSuperEntity();
    if (superEntity!=null)
    {
      printLine("inherits from ["+superEntity.getName()+"]",ps);
    }
    List<Field> fields=entity.getFields();
    for(Field f : fields)
    {
      dumpField(f,ps);
    }
    _level--;
  }

  /**
   * Dump the contents of the given field to the specified output stream.
   * @param field Field to dump.
   * @param ps Output stream.
   */
  public void dumpField(Field field, PrintStream ps)
  {
    String name=field.getName();
    printLine("Name ["+name+"]",ps);
    _level++;
    Type type=field.getType();
    printLine("Type ["+type+"]",ps);
    SymbolicPropertiesSet properties=field.getProperties();
    printLine("Properties ["+properties+"]",ps);
    _level--;
  }

  /**
   * Dump the contents of the given relation to the specified output stream.
   * @param relation Relation to dump.
   * @param ps Output stream.
   */
  public void dumpRelation(Relation relation, PrintStream ps)
  {
    String name=relation.getName();
    printLine("Name ["+name+"]",ps);
    _level++;
    List<RelationTier> tiers=relation.getRelationsTiers();
    for(RelationTier tier : tiers)
    {
      dumpTier(relation,tier,ps);
    }
    _level--;
  }

  /**
   * Dump the contents of the given tier to the specified output stream.
   * @param relation Parent relation.
   * @param tier Tier to dump.
   * @param ps Output stream.
   */
  private void dumpTier(Relation relation, RelationTier tier, PrintStream ps)
  {
    String role=tier.getRole();
    Entity entity=tier.getEntity();
    String entityName=entity.getName();
    printLine("Entity ["+entityName+"], role=["+role+"]",ps);
    _level++;
    List<RelationLink> links=relation.getLinksFrom(tier);
    for(RelationLink link : links)
    {
      dumpRelationLink(link,ps);
    }
    _level--;
  }

  /**
   * Dump the contents of the given relation link to the specified output stream.
   * @param link Relation link to dump.
   * @param ps Output stream.
   */
  private void dumpRelationLink(RelationLink link, PrintStream ps)
  {
    String linkDescription=link.toString();
    printLine(linkDescription,ps);
  }

  private void printLine(String line, PrintStream ps)
  {
    for(int i=0;i<_level;i++)
    {
      ps.print('\t');
    }
    ps.println(line);
  }
}
