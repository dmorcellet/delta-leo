package delta.leo.metadata;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import delta.common.utils.properties.SymbolicPropertiesRegistry;
import delta.common.utils.properties.SymbolicPropertiesSet;
import delta.common.utils.properties.SymbolicProperty;
import delta.common.utils.types.IntegerType;
import delta.common.utils.types.Type;
import delta.leo.data.ObjectInstance;
import delta.leo.model.entity.Entity;
import delta.leo.model.entity.field.Field;
import delta.leo.model.entity.field.FieldProperties;
import delta.leo.model.entity.field.FieldPropertyNames;
import delta.leo.model.impl.EditableField;
import delta.leo.model.relation.RelationLink;
import delta.leo.utils.LeoLoggers;

public class ObjectClass
{
  private static final Logger _logger=LeoLoggers.getLeoDataLogger();

  /**
   * Parent class (if any).
   */
  private ObjectClass _parent;
  /**
   * Class name.
   */
  private String _name;
  /**
   * Own fields.
   */
  private Field[] _ownFields;
  /**
   * All fields (including those from parent classes).
   */
	private Field[] _allFields;

  private List<Field> _idFieldsList;
  private List<Field> _allFieldsList;

  /**
   * Map field IDs to field indexes.
   */
  private HashMap<String,Integer> _map;

  static ObjectClass build(Entity e, List<RelationLink> relations, ObjectClass parent)
  {
    List<Field> basicFields=e.getFields();
    // todo relations
    /*
    List<Field> relFields=new ArrayList<Field>(relations.size());
    RelationLink link;
    RelationField relField;
    for(Iterator<RelationLink> it=relations.iterator();it.hasNext();)
    {
      link=it.next();
      relField=new RelationField(link);
      relFields.add(relField);
    }
    */

    int nbRelationFields=0/*relFields.size()*/;
    int nbOwnFields=basicFields.size()+nbRelationFields;
    Field[] ownFields=new Field[nbOwnFields];
    {
      int index=0;
      Field field;
      for(int i=0;i<basicFields.size();i++)
      {
        field=basicFields.get(i);
        ownFields[index]=field;
        index++;
      }
      // todo relations
      /*
      for(int i=0;i<relFields.size();i++)
      {
        ownFields[index]=relFields.get(i);
        index++;
      }
      */
    }
    return new ObjectClass(e,ownFields,parent);
  }

  private ObjectClass(Entity e, Field[] ownFields, ObjectClass parent)
	{
    _name=e.getName();
		_parent=parent;
    _ownFields=ownFields;

		// Compute the _allFields attribute
    if (parent!=null)
    {
      Field[] parentFields=parent._allFields;
      int nbParentFields=parentFields.length;
      if (nbParentFields>0)
      {
        Field[] allFields=new Field[_ownFields.length+nbParentFields];
        System.arraycopy(parentFields,0,allFields,0,nbParentFields);
        System.arraycopy(_ownFields,0,allFields,nbParentFields,_ownFields.length);
        _allFields=allFields;
      }
    }
    else
    {
      // Add "ID" field
      Type idType=getIDType();
      SymbolicPropertiesRegistry registry=FieldProperties.getFieldPropertiesRegistry();
      SymbolicPropertiesSet properties=registry.getPropertiesSet(FieldPropertyNames.ID);
      Field idField=new EditableField("ID",idType,properties);

      Field[] allFields=new Field[_ownFields.length+1];
      allFields[0]=idField;
      System.arraycopy(_ownFields,0,allFields,1,_ownFields.length);
      _allFields=allFields;
    }

    // Compute fields map
    _map=new HashMap<String,Integer>();
    int nb=_allFields.length;
    Field f;
    Integer integer;
    for(int i=0;i<nb;i++)
    {
      f=_allFields[i];
      integer=_map.put(f.getName(),Integer.valueOf(i));
      if (integer!=null)
      {
        _logger.warn("Duplicate field ID : "+f.getName());
      }
    }
  }

	public String getName()
	{
	  return _name;
	}

	/**
   * Get an unmodifiable list of all the fields of this class.
   * @return an unmodifiable list of all the fields of this class.
	 */
  public List<Field> getAllFields()
  {
    if (_allFieldsList==null)
    {
      int size=_allFields.length;
      List<Field> tmp=new ArrayList<Field>(size);
      for(int i=0;i<size;i++)
      {
        tmp.add(_allFields[i]);
      }
      _allFieldsList=Collections.unmodifiableList(tmp);
    }
    return _allFieldsList;
  }

  /**
   * Get an unmodifiable list of all the ID fields of this class.
   * @return an unmodifiable list of all the ID fields of this class.
   */
  public List<Field> getIDFields()
  {
    if (_idFieldsList==null)
    {
      List<Field> tmp=getFields(FieldProperties.ID);
      _idFieldsList=Collections.unmodifiableList(tmp);
    }
    return _idFieldsList;
  }

  private List<Field> getFields(SymbolicProperty property)
  {
    List<Field> ret=new ArrayList<Field>();
    int nbFields=_allFields.length;
    Field f;
    for(int i=0;i<nbFields;i++)
    {
      f=_allFields[i];
      if (f.hasProperty(property)) ret.add(f);
    }
    return ret;
  }

  public Field getFieldByName(String name)
  {
    Integer index=_map.get(name);
    if (index!=null)
    {
      return _allFields[index.intValue()];
    }
    return null;
  }

  /**
   * Get the index of the specified field in this class.
   * @param field Targeted field.
   * @return A field index or <code>null</code> if not found.
   */
  public Integer getFieldIndex(Field field)
  {
    String fieldName=field.getName();
    Integer index=_map.get(fieldName);
    return index;
  }

  public Field getField(int index)
  {
    return _allFields[index];
  }

  public int getNumberOfFields()
  {
    return _allFields.length;
  }

  public ObjectClass getParent()
	{
		return _parent;
	}

  public ObjectInstance newInstance()
  {
    ObjectInstance ret=new ObjectInstance(this);
    return ret;
  }

  public void dump(PrintStream out)
  {
    out.println("Name="+getName());
    if (_parent!=null)
      out.println("Parent="+_parent.getName());
    int nb=_allFields.length;
    Field f;
    int index;
    for(int i=0;i<nb;i++)
    {
      f=_allFields[i];
      index=_map.get(f.getName()).intValue();
      out.println("   "+f+" : "+index);
    }
  }

  private static Type _idType=null;

  private static Type getIDType()
  {
    if (_idType==null)
    {
      _idType=new IntegerType(0);
    }
    return _idType;
  }
}
