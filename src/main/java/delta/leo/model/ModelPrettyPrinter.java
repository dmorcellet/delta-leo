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
 * @author DAM
 */
public class ModelPrettyPrinter
{
  private int _level;

  public ModelPrettyPrinter()
  {
    _level=0;
  }

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

  public void dumpEntity(Entity e, PrintStream ps)
  {
    String name=e.getName();
    printLine("Name ["+name+"]",ps);
    _level++;
    Entity superEntity=e.getSuperEntity();
    if (superEntity!=null)
    {
      printLine("inherits from ["+superEntity.getName()+"]",ps);
    }
    List<Field> fields=e.getFields();
    for(Field f : fields)
    {
      dumpField(f,ps);
    }
    _level--;
  }

  public void dumpField(Field f, PrintStream ps)
  {
    String name=f.getName();
    printLine("Name ["+name+"]",ps);
    _level++;
    Type type=f.getType();
    printLine("Type ["+type+"]",ps);
    SymbolicPropertiesSet properties=f.getProperties();
    printLine("Properties ["+properties+"]",ps);
    _level--;
  }

  public void dumpRelation(Relation r, PrintStream ps)
  {
    String name=r.getName();
    printLine("Name ["+name+"]",ps);
    _level++;
    List<RelationTier> tiers=r.getRelationsTiers();
    for(RelationTier tier : tiers)
    {
      dumpTier(r,tier,ps);
    }
    _level--;
  }

  public void dumpTier(Relation r, RelationTier tier, PrintStream ps)
  {
    String role=tier.getRole();
    Entity entity=tier.getEntity();
    String entityName=entity.getName();
    printLine("Entity ["+entityName+"], role=["+role+"]",ps);
    _level++;
    List<RelationLink> links=r.getLinksFrom(tier);
    for(RelationLink link : links)
    {
      dumpRelationLink(link,ps);
    }
    _level--;
  }

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
