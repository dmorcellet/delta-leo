package delta.leo.tmp;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import delta.leo.data.values.FieldValue;
import delta.leo.model.entity.field.Field;

/**
 * Simple implementation of a values set.
 * @author DAM
 */
public class SimpleValueSet implements ValueSet
{
  private HashMap<Field, FieldValue> _values;

  /**
   * Constructor.
   */
  public SimpleValueSet()
  {
    _values=new HashMap<Field, FieldValue>();
  }

  /**
   * Set some values.
   * @param values Values to set.
   */
  public void setValues(ValueSet values)
  {
    Collection<FieldValue> collection=values.getValues();
    FieldValue value;
    for(Iterator<FieldValue> it=collection.iterator();it.hasNext();)
    {
      value=it.next();
      _values.put(value.getField(),value);
    }
  }

  @Override
  public int getNbValues()
  {
    return _values.size();
  }

  @Override
  public boolean setValue(FieldValue value)
  {
    _values.put(value.getField(), value);
    return true;
  }

  @Override
  public FieldValue getValue(Field field)
  {
    return _values.get(field);
  }

  @Override
  public Collection<FieldValue> getValues()
  {
    return _values.values();
  }

  @Override
  public void clear()
  {
    _values.clear();
  }
}
