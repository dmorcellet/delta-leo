package delta.leo.model.io.xml;

import java.net.URL;

import junit.framework.Assert;
import junit.framework.TestCase;
import delta.common.utils.url.URLTools;
import delta.leo.model.Model;

public class TestParseModel extends TestCase
{
  /**
   * Constructor.
   */
  public TestParseModel()
  {
    super("Parse model");
  }

  public void testParseModel()
  {
    ModelXMLParser parser=new ModelXMLParser();
    URL url=URLTools.getFromClassPath("delta/leo/examples/test/TestModel.xml");
    Model m=parser.parseXML(url);
    Assert.assertNotNull(m);
  }
}
