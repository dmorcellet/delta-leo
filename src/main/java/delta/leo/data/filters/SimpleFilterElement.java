package delta.leo.data.filters;

import delta.leo.data.ObjectInstance;
import delta.leo.data.values.FieldValue;
import delta.leo.model.entity.field.Field;

public class SimpleFilterElement extends FilterElement
{
  private int _condition;
  private boolean _inverted;
  private FieldValue _value;

  public SimpleFilterElement(int condition, boolean inverted, FieldValue value)
  {
    _condition=condition;
    _inverted=inverted;
    _value=value;
  }

  public SimpleFilterElement(int condition, FieldValue value)
  {
    _condition=condition;
    _inverted=false;
    _value=value;
  }

  @Override
  public boolean match(ObjectInstance o)
  {
    Field field=_value.getField();
    Object value=o.getValue(field);
    // todo real code
    boolean match=true;
    if (_inverted) return !match;
    return match;
  }
}
