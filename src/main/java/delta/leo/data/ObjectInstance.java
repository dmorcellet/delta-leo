package delta.leo.data;

import java.util.List;

import delta.leo.metadata.ObjectClass;
import delta.leo.model.entity.field.Field;

public class ObjectInstance
{
  private ObjectClass _objectClass;
  private ObjectId _id;
  private ObjectValue _values;

  // todo manage object state flags (id locked, locked, modified...)

  public ObjectInstance(ObjectClass classInfo)
  {
    _objectClass=classInfo;
    _id=new ObjectId(null,classInfo);
    _values=new ObjectValue(classInfo);
  }

  public ObjectClass getObjectClass()
  {
    return _objectClass;
  }

  public ObjectId getId()
  {
    return _id;
  }

  public Object getValue(Field field)
  {
    Object ret=_values.getValue(field);
    return ret;
  }

  public boolean setValue(Field field, Object value)
  {
    boolean ret=_values.setValue(field,value);
    if (ret)
    {
      List<Field> idFields=_objectClass.getIDFields();
      int fieldIndex=idFields.indexOf(field);
      if (fieldIndex>=0)
      {
        _id.setFieldValue(fieldIndex,value);
      }
    }
    return ret;
  }

  public boolean checkValues()
  {
    return true;
  }

  @Override
  public String toString()
  {
    return _id.toString();
  }
}
