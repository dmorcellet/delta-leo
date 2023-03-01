package delta.leo.data;

import java.util.List;

import org.apache.log4j.Logger;

import delta.common.utils.types.Type;
import delta.leo.metadata.ObjectClass;
import delta.leo.model.entity.field.Field;

/**
 * A generic Object Identifier based on a list of field values.
 * @author DAM
 */
public class ObjectId
{
  private static final Logger LOGGER=Logger.getLogger(ObjectId.class);

  private ObjectsSource _objectsSource;
  private ObjectClass _class;
  private Object[] _values;

  /**
   * Constructor.
   * @param os Identified object's objects source.
   * @param clazz Identified object's class.
   */
  ObjectId(ObjectsSource os, ObjectClass clazz)
  {
    _objectsSource=os;
    _class=clazz;
    List<Field> idFields=clazz.getIDFields();
    int nbIDFields=idFields.size();
    _values=new Object[nbIDFields];
  }

  /**
   * Constructor.
   * @param os Identified object's objects source.
   * @param clazz Identified object's class.
   * @param values ID fields values.
   */
  public ObjectId(ObjectsSource os, ObjectClass clazz, Object[] values)
  {
    _objectsSource=os;
    _class=clazz;
    List<Field> idFields=clazz.getIDFields();
    int nbIDFields=idFields.size();
    _values=new Object[nbIDFields];
    int nbValues=values.length;
    int nbValuesToSet=nbIDFields;
    if (nbValues!=nbIDFields)
    {
      LOGGER.error("Invalid number of values for an object ID. Expected "+nbIDFields+", got "+nbValues);
      nbValuesToSet=Math.min(nbIDFields,nbValues);
    }
    for(int i=0;i<nbValuesToSet;i++)
    {
      setFieldValue(i,values[i]);
    }
  }

  /**
   * Get the objects source of the identified object.
   * @return the objects source of the identified object.
   */
  public ObjectsSource getObjectsSource()
  {
    return _objectsSource;
  }

  void setObjectsSource(ObjectsSource objectsSource)
  {
    _objectsSource=objectsSource;
  }

  void setFieldValue(int fieldIndex, Object value)
  {
    List<Field> idFields=_class.getIDFields();
    Field idField=idFields.get(fieldIndex);
    Type type=idField.getType();
    Object valueToSet=type.buildInstanceFromValue(value);
    _values[fieldIndex]=valueToSet;
  }

  /**
   * Get the class of the identified object.
   * @return the class of the identified object.
   */
  public ObjectClass getClassInfo()
  {
    return _class;
  }

  /**
   * Get the number of fields in this Object ID.
   * @return the number of fields in this Object ID.
   */
  public int getNumberOfFields()
  {
    return _values.length;
  }

  /**
   * Get the value of a field in this Object ID.
   * @param fieldIndex Index of field to get.
   * @return The value of the targeted field.
   */
  public Object getFieldValue(int fieldIndex)
  {
    // todo check
    return _values[fieldIndex];
  }

  /**
   * Equality test.
   * @param obj Object to compare to.
   * @return <code>true</code> if both objects are equal, <code>false</code> otherwise.
   */
  @Override
  public boolean equals(Object obj)
  {
    if (!(obj instanceof ObjectId)) return false;
    ObjectId other=(ObjectId)obj;
    if (other._objectsSource!=_objectsSource) return false;
    if (other._class!=_class) return false;
    int length=_values.length;
    Object value;
    for(int i=0;i<length;i++)
    {
      value=_values[i];
      if (value!=null)
      {
        if (!value.equals(other._values[i])) return false;
      }
      else
      {
        if (other._values[i]!=null) return false;
      }
    }
    return true;
  }

  /**
   * Get a hash-code for this object ID.
   * @return a hash-code for this object ID.
   */
  @Override
  public int hashCode()
  {
    int ret=0;
    int length=_values.length;
    Object value;
    for(int i=0;i<length;i++)
    {
      value=_values[i];
      if (value!=null)
      {
        ret+=value.hashCode();
      }
    }
    return ret;
  }

  /**
   * Famous toString() method.
   * @return A stringified representation of this object.
   */
  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder(_class.getName());
    int length=_values.length;
    Object value;
    for(int i=0;i<length;i++)
    {
      sb.append('/');
      value=_values[i];
      if (value!=null)
      {
        sb.append(value);
      }
      else
      {
        sb.append('-');
      }
    }
    if (_objectsSource!=null)
    {
      sb.append('@');
      sb.append(_objectsSource.getName());
    }
    String ret=sb.toString();
    return ret;
  }
}
