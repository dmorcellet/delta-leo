package delta.leo.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import delta.leo.metadata.ObjectClass;
import delta.leo.model.entity.field.Field;

/**
 * Storage for an object value.
 * @author DAM
 */
public class ObjectValue
{
  private static final Logger LOGGER=Logger.getLogger(ObjectValue.class);

  private ObjectClass _class;
  private Object[] _values;
  private BitSet _setValues;

  /**
   * Constructor.
   * @param clazz Class of this object value.
   */
  public ObjectValue(ObjectClass clazz)
  {
    int nb=clazz.getNumberOfFields();
    _class=clazz;
    _values=new Object[nb];
    _setValues=new BitSet();
  }

  /**
   * Get the number of managed fields.
   * @return the number of managed fields.
   */
  public int getNbFields()
  {
    return _values.length;
  }

  /**
   * Get the class of the object.
   * @return the class of the object.
   */
  public ObjectClass getObjectClass()
  {
    return _class;
  }

  /**
   * Set the value for a field.
   * @param field Targeted field.
   * @param value Value for this field.
   * @return <code>true</code> if the value was successfully set, <code>false</code> otherwise.
   */
  public boolean setValue(Field field, Object value)
  {
    boolean set=false;
    Integer index=_class.getFieldIndex(field);
    if (index!=null)
    {
      int intIndex=index.intValue();
      _setValues.set(intIndex);
      _values[intIndex]=value;
      set=true;
    }
    return set;
  }

  /**
   * Get the values for a list of fields.
   * @param fields Targeted fields.
   * @return A list of values (A value is <code>null</code> if it is not set or null).
   */
  public List<Object> getValuesAsList(List<Field> fields)
  {
    List<Object> ret=new ArrayList<Object>();
    Field field;
    Object value;
    for(Iterator<Field> it=fields.iterator();it.hasNext();)
    {
      field=it.next();
      value=getValue(field);
      ret.add(value);
    }
    return ret;
  }

  /**
   * Get the values for a list of fields.
   * @param fields Targeted fields.
   * @return An array of values (A value is <code>null</code> if it is not set or null).
   */
  public Object[] getValuesAsArray(List<Field> fields)
  {
    int nbFields=fields.size();
    Object[] ret=new Object[nbFields];
    Field field;
    Object value;
    int index=0;
    for(Iterator<Field> it=fields.iterator();it.hasNext();)
    {
      field=it.next();
      value=getValue(field);
      ret[index]=value;
      index++;
    }
    return ret;
  }

  /**
   * Get the value for a field.
   * @param field Targeted field.
   * @return A value or <code>null</code> if value is not set or null.
   */
  public Object getValue(Field field)
  {
    Object value=null;
    Integer index=_class.getFieldIndex(field);
    if (index!=null)
    {
      int intIndex=index.intValue();
      value=_values[intIndex];
    }
    else
    {
      LOGGER.error("Cannot get value of non-existing field ["+field.getName()+"]");
    }
    return value;
  }

  /**
   * Clear all stored values.
   */
  public void clear()
  {
    _setValues.clear();
    Arrays.fill(_values,null);
  }
}
