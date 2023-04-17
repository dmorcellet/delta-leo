package delta.leo.model;

import java.net.URL;

import junit.framework.TestCase;
import delta.common.utils.url.URLTools;
import delta.leo.metadata.Model2Classes;
import delta.leo.model.io.xml.ModelXMLParser;

/**
 * Test class for metadata.
 * @author DAM
 */
public class TestMetadata extends TestCase
{
  /**
   * Constructor.
   */
  public TestMetadata()
  {
    super("Metadata test");
  }

  /**
   * Test object class.
   */
  public void testObjectClass()
  {
    // Load model
    ModelXMLParser parser=new ModelXMLParser();
    URL url=URLTools.getFromClassPath("delta/leo/examples/genea/model.xml");
    Model m=parser.parseXML(url);
    Model2Classes m2c=new Model2Classes();
    m2c.build(m);
  }
}
