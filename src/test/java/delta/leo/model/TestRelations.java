package delta.leo.model;

import java.net.URL;

import junit.framework.Assert;
import junit.framework.TestCase;
import delta.common.utils.url.URLTools;
import delta.leo.model.io.xml.ModelXMLParser;
import delta.leo.model.relation.Relation;
import delta.leo.model.relation.RelationLink;

public class TestRelations extends TestCase
{
  /**
   * Constructor.
   */
  public TestRelations()
  {
    super("Model test");
  }

  public void testRelations()
  {
    // Load model
    ModelXMLParser parser=new ModelXMLParser();
    URL url=URLTools.getFromClassPath("delta/leo/examples/movies/MoviesModel.xml");
    Model m=parser.parseXML(url);
    // Get a relation
    Relation bA=m.getRelation("ACTOR/MOVIE");
    Assert.assertNotNull(bA);

    String tier1="ACTOR";
    String tier2="MOVIE";
    // Check there's no BLABLA tier in that relation
    Assert.assertNull(bA.getTierByRole("BLABLA"));
    // Check there's no link to BLABLA starting from tier MOVIE
    Assert.assertNull(bA.getLinkByRoles(tier2,"BLABLA"));
    // Check there's a link between MOVIE and PERSON
    RelationLink link1=bA.getLinkByRoles(tier2,tier1);
    Assert.assertNotNull(link1);
    System.out.println(link1);
    // Check there's a link between PERSON and MOVIE
    RelationLink link2=bA.getLinkByRoles(tier1,tier2);
    System.out.println(link2);
  }
}
