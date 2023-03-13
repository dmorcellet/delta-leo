package delta.leo.binding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import delta.common.utils.properties.SymbolicPropertiesSet;
import delta.leo.metadata.ObjectClass;
import delta.leo.model.entity.field.Field;

/**
 * Binding definition for a single class.
 * @author DAM
 */
public class ClassBinding
{
  private static final Logger LOGGER=Logger.getLogger(ClassBinding.class);

  /**
   * Targeted class.
   */
  private ObjectClass _class;
  // Map physical class names to the binding info for this class
  private HashMap<String,TableBindingInfo> _tableBindingsMap;
  // Map logical field names to field binding infos
  private HashMap<String,FieldBindingInfo> _fieldBindingsMap;
  // List of physical class names
  private List<String> _classNames;

  /**
   * Indexed join storage (class1,class2 -> list of joins)
   */
  private HashMap<String,HashMap<String,List<Join>>> _classesToJoins;
  /**
   * Indexed join storage (class,field -> join)
   */
  private HashMap<String,HashMap<String,Join>> _classAndFieldToJoin;
  /**
   * List of all joins.
   */
  private List<Join> _joinsList;

  /**
   * Constructor.
   * @param clazz Class targeted by this binding.
   */
  public ClassBinding(ObjectClass clazz)
  {
    _class=clazz;
    _tableBindingsMap=new HashMap<String,TableBindingInfo>();
    _fieldBindingsMap=new HashMap<String,FieldBindingInfo>();
    _classNames=new ArrayList<String>();
    _classesToJoins=new HashMap<String,HashMap<String,List<Join>>>();
    _classAndFieldToJoin=new HashMap<String,HashMap<String,Join>>();
    _joinsList=new ArrayList<Join>();
  }

  /**
   * Get the class targeted by this binding.
   * @return the class targeted by this binding.
   */
  public ObjectClass getObjectClass()
  {
    return _class;
  }

  /**
   * Get the classes involved in this class binding.
   * @return the classes involved in this class binding.
   */
  public List<String> getClassNames()
  {
    return _classNames;
  }

  /**
   * Get the binding info for a single table.
   * @param tableName Table name.
   * @return A table binding info or <code>null</code> if not found.
   */
  public TableBindingInfo getTableBinding(String tableName)
  {
    TableBindingInfo tableInfo=_tableBindingsMap.get(tableName);
    return tableInfo;
  }

  /**
   * Add a field binding.
   * @param tableName Table name.
   * @param fieldName Column name.
   * @param field Field description.
   * @param properties Properties.
   * @return A new field binding.
   */
  public FieldBindingInfo addFieldBinding(String tableName, String fieldName, Field field, SymbolicPropertiesSet properties)
  {
    if (field!=null)
    {
      SymbolicPropertiesSet props=properties;
      if (props==null)
      {
        props=BindingProperties.getBindingPropertiesRegistry().getEmptySet();
      }
      String logicalFieldName=field.getName();
      if (_fieldBindingsMap.get(logicalFieldName)==null)
      {
        TableBindingInfo tableInfo=_tableBindingsMap.get(tableName);
        if (tableInfo==null)
        {
          tableInfo=new TableBindingInfo(tableName);
          _tableBindingsMap.put(tableName,tableInfo);
        }
        FieldBindingInfo info=new FieldBindingInfo(tableName,fieldName,field,props);
        boolean addOK=tableInfo.addFieldBinding(info);
        if (addOK)
        {
          _fieldBindingsMap.put(logicalFieldName,info);
          if (!_classNames.contains(tableName))
          {
            _classNames.add(tableName);
          }
          return info;
        }
        LOGGER.error("Field binding already registered ["+tableName+"/"+fieldName+"]");
        return null;
      }
      LOGGER.error("The logical field ["+logicalFieldName+"] of class ["+_class.getName()+"] already has a field binding.");
      return null;
    }
    LOGGER.error("Cannot register a field binding with no associated logical field ["+tableName+"/"+fieldName+"]");
    return null;
  }

  /**
   * Add a new join definition.
   * @param join Join to register.
   */
  public void addJoin(Join join)
  {
    String class1=join.getClass1();
    String class2=join.getClass2();

    if (class1.equals(class2))
    {
      // todo warning
      return;
    }

    HashMap<String,Join> joins;
    List<Join> joinsList1=buildJoinsList(class1,class2);
    joinsList1.add(join);
    joins=_classAndFieldToJoin.get(class1);
    if (joins==null)
    {
      joins=new HashMap<String,Join>();
    }
    joins.put(join.getField1().getFieldName(),join);
    List<Join> joinsList2=buildJoinsList(class2,class1);
    joinsList2.add(join);
    joins=_classAndFieldToJoin.get(class2);
    if (joins==null)
    {
      joins=new HashMap<String,Join>();
    }
    joins.put(join.getField2().getFieldName(),join);
    _joinsList.add(join);
  }

  private List<Join> buildJoinsList(String class1, String class2)
  {
    HashMap<String,List<Join>> tmp=_classesToJoins.get(class1);
    if (tmp==null)
    {
      tmp=new HashMap<String,List<Join>>();
      _classesToJoins.put(class1,tmp);
    }
    List<Join> joins=tmp.get(class2);
    if (joins==null)
    {
      joins=new ArrayList<Join>();
      tmp.put(class2,joins);
    }
    return joins;
  }

  /**
   * Get the <tt>Join</tt> instances that join the two specified classes.
   * @param class1 One class.
   * @param class2 Another class.
   * @return A list of <tt>Join</tt> instances or <code>null</code>.
   */
  private List<Join> getJoins(String class1, String class2)
  {
    HashMap<String,List<Join>> tmp=_classesToJoins.get(class1);
    if (tmp==null) return null;
    List<Join> ret=tmp.get(class2);
    return ret;
  }

  /**
   * Get the field binding for a field.
   * @param field Targeted field.
   * @return A field binding or <code>null</code> if not found.
   */
  public FieldBindingInfo getBinding(Field field)
  {
    String fieldName=field.getName();
    FieldBindingInfo ret=_fieldBindingsMap.get(fieldName);
    return ret;
  }

  /**
   * Get the list of joins to use to link the given set of classes.
   * @param classNames Set of classes to link together (designated by their names).
   * @return A list of joins.
   */
  public List<Join> getJoins(Set<String> classNames)
  {
    List<Join> ret=new ArrayList<Join>();
    int nbClasses=classNames.size();
    if (nbClasses>1)
    {
      Set<String> notFoundNames=new HashSet<String>(classNames);

      String className1,className2;
      for(Iterator<String> it1=classNames.iterator();it1.hasNext();)
      {
        className1=it1.next();
        for(Iterator<String> it2=notFoundNames.iterator();it2.hasNext();)
        {
          className2=it2.next();
          if (!className1.equals(className2))
          {
            List<Join> joins=getJoins(className1,className2);
            if ((joins!=null) && (joins.size()>0))
            {
              ret.addAll(joins);
              notFoundNames.remove(className1);
              notFoundNames.remove(className2);
              break;
            }
          }
        }
        if (notFoundNames.size()==0)
        {
          break;
        }
      }
    }
    return ret;
  }
}
