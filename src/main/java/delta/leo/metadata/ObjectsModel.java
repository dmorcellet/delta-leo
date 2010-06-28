package delta.leo.metadata;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import delta.leo.binding.BindingsManager;
import delta.leo.binding.BindingsXMLParser;
import delta.leo.binding.ClassBinding;
import delta.leo.location.DataLocation;
import delta.leo.location.basic.BasicDataLocation;
import delta.leo.location.sql.SqlDataLocation;
import delta.leo.model.Model;
import delta.leo.model.io.xml.ModelXMLParser;

/**
 * @author DAM
 */
public class ObjectsModel
{
  /**
   * Maps class names to <tt>ObjectClass</tt>es.
   */
  private HashMap<String,ObjectClass> _classes;
  /**
   * Maps location names to <tt>DataLocation</tt>s.
   */
  private HashMap<String,DataLocation> _locations;
  /**
   * Maps location names to the corresponding <tt>BindingsManager</tt>.
   */
  private HashMap<String,BindingsManager> _bindings;

  /**
   * Constructor.
   * @param modelURL URL of XML model description.
   * @param bindingsURL URL of XML bindings description.
   */
  public ObjectsModel(URL modelURL, URL bindingsURL)
  {
    init();
    readLocations(null);
    readClasses(modelURL);
    if (bindingsURL!=null)
    {
      readBindings(bindingsURL);
    }
  }

  private void init()
  {
    _classes=new HashMap<String,ObjectClass>();
    _locations=new HashMap<String,DataLocation>();
    _bindings=new HashMap<String,BindingsManager>();
  }

  /**
   * Get a class by its name.
   * @param className Class name.
   * @return An <tt>ObjectClass</tt> instance or <code>null</code>.
   */
  public ObjectClass getClassByName(String className)
  {
    ObjectClass ret=_classes.get(className);
    return ret;
  }

  /**
   * Get a list of all managed locations.
   * @return a list of all managed locations.
   */
  public List<DataLocation> getLocations()
  {
    List<DataLocation> ret=new ArrayList<DataLocation>();
    ret.addAll(_locations.values());
    return ret;
  }

  /**
   * Get a location by its name.
   * @param locationName Location name.
   * @return A <tt>DataLocation</tt> instance or <code>null</code>.
   */
  public DataLocation getLocationByName(String locationName)
  {
    DataLocation ret=_locations.get(locationName);
    return ret;
  }

  /**
   * Get the bindings manager associated with a data location.
   * @param location Data location.
   * @return A bindings manager or <code>null</code> if not found.
   */
  public BindingsManager getBindingsManager(DataLocation location)
  {
    String locationName=location.getId();
    BindingsManager bindingsMgr=_bindings.get(locationName);
    if (bindingsMgr==null)
    {
      bindingsMgr=new BindingsManager(location);
      _bindings.put(locationName,bindingsMgr);
    }
    return bindingsMgr;
  }

  /**
   * Get the binding for the specified class in the given location.
   * @param location Location to use.
   * @param clazz Class to look for.
   * @return A <tt>Binding</tt> instance or <code>null</code> if not found.
   */
  public ClassBinding getBinding(DataLocation location, ObjectClass clazz)
  {
    ClassBinding ret=null;
    String locationName=location.getId();
    BindingsManager bindingsManager=_bindings.get(locationName);
    if (bindingsManager!=null)
    {
      String className=clazz.getName();
      ret=bindingsManager.getBindingForClass(className);
    }
    return ret;
  }

  private void readLocations(URL locationsURL)
  {
    byte nbMax=5;
    String driver="com.mysql.jdbc.Driver";
    SqlDataLocation location=new SqlDataLocation("SQL", "jdbc:mysql://localhost:3306/genea", driver, "dada", "glor4fin3del", "genea", (byte)2, nbMax);
    _locations.put(location.getId(),location);
    BasicDataLocation defaultLocation=new BasicDataLocation(DataLocation.DEFAULT);
    _locations.put(defaultLocation.getId(),defaultLocation);
    // todo Get it from XML
  }

  private void readClasses(URL modelURL)
  {
    ModelXMLParser parser=new ModelXMLParser();
    Model m=parser.parseXML(modelURL);
    Model2Classes m2c=new Model2Classes();
    List<ObjectClass> classes=m2c.build(m);
    ObjectClass clazz;
    String className;
    for(Iterator<ObjectClass> it=classes.iterator();it.hasNext();)
    {
      clazz=it.next();
      className=clazz.getName();
      _classes.put(className,clazz);
    }
  }

  private void readBindings(URL bindingsURL)
  {
    BindingsXMLParser parser=new BindingsXMLParser();
    parser.parseXML(this,bindingsURL);
  }
}
