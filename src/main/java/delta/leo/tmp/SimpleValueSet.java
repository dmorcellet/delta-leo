package delta.leo.tmp;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import delta.leo.data.values.FieldValue;
import delta.leo.model.entity.field.Field;

public class SimpleValueSet implements ValueSet
{
  private HashMap<Field, FieldValue> _values;

  public SimpleValueSet()
  {
    _values=new HashMap<Field, FieldValue>();
  }

  public void setValues(ValueSet values)
  {
    Collection<FieldValue> collection_l=values.getValues();
    FieldValue value;
    for(Iterator<FieldValue> it=collection_l.iterator();it.hasNext();)
    {
      value=it.next();
      _values.put(value.getField(),value);
    }
  }

  public int getNbValues()
  {
    return _values.size();
  }

  public boolean setValue(FieldValue value)
  {
    _values.put(value.getField(), value);
    return true;
  }

  public FieldValue getValue(Field field)
  {
    return _values.get(field);
  }

  public Collection<FieldValue> getValues()
  {
    return _values.values();
  }

  public void clear()
  {
    _values.clear();
  }
}
