package delta.leo.tmp;

import java.util.Collection;

import delta.leo.data.values.FieldValue;
import delta.leo.model.entity.field.Field;

public interface ValueSet
{
  public boolean setValue(FieldValue value);
  public int getNbValues();
  public FieldValue getValue(Field field);
  public Collection<FieldValue> getValues();
  public void clear();
}
