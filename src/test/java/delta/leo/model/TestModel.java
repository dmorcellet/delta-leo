package delta.leo.model;

import java.net.URL;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;
import delta.common.utils.url.URLTools;
import delta.leo.model.entity.Entity;
import delta.leo.model.io.xml.ModelXMLParser;
import delta.leo.model.relation.RelationLink;

/**
 * Test class for the model usage.
 * @author DAM
 */
public class TestModel extends TestCase
{
  /**
   * Constructor.
   */
  public TestModel()
  {
    super("Model test");
  }

  /**
   * Test relations.
   */
  public void testRelations()
  {
    // Load model
    ModelXMLParser parser=new ModelXMLParser();
    URL url=URLTools.getFromClassPath("delta/leo/examples/test/TestModel.xml");
    Model m=parser.parseXML(url);

    List<Entity> entities=m.getEntities();
    Entity e;
    List<RelationLink> links;
    RelationLink link;
    for(Iterator<Entity> it=entities.iterator();it.hasNext();)
    {
      e=it.next();
      System.out.println("Entity : "+e);
      links=m.getLinksFrom(e,true);
      for(Iterator<RelationLink> itLinks=links.iterator();itLinks.hasNext();)
      {
        link=itLinks.next();
        System.out.println("\t"+link);
      }
    }
  }
}
