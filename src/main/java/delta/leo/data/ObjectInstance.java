package delta.leo.data;

import java.util.List;

import delta.leo.metadata.ObjectClass;
import delta.leo.model.entity.field.Field;

/**
 * Instance of a class.
 * @author DAM
 */
public class ObjectInstance
{
  private ObjectClass _objectClass;
  private ObjectId _id;
  private ObjectValue _values;

  // todo manage object state flags (id locked, locked, modified...)

  /**
   * Constructor.
   * @param classInfo Associated class.
   */
  public ObjectInstance(ObjectClass classInfo)
  {
    _objectClass=classInfo;
    _id=new ObjectId(null,classInfo);
    _values=new ObjectValue(classInfo);
  }

  /**
   * Get the associated class.
   * @return a class.
   */
  public ObjectClass getObjectClass()
  {
    return _objectClass;
  }

  /**
   * Get the object identifier.
   * @return an object identifier.
   */
  public ObjectId getId()
  {
    return _id;
  }

  /**
   * Get the value for a given field.
   * @param field Field to use.
   * @return A value or <code>null</code>.
   */
  public Object getValue(Field field)
  {
    Object ret=_values.getValue(field);
    return ret;
  }

  /**
   * Set the value for a field.
   * @param field Field to use.
   * @param value Value to set.
   * @return <code>true</code> if operation succeeded, <code>false</code> otherwise.
   */
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

  /**
   * Check the value of this object.
   * @return <code>true</code> if check is OK, <code>false</code> otherwise.
   */
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
