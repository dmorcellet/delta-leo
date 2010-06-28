package delta.leo.binding;

/**
 * A class and field pair.
 * @author DAM
 */
public class ClassAndField
{
  private String _className;
  private String _fieldName;

  /**
   * Constructor.
   * @param className Class of field.
   * @param fieldName Name of field.
   */
  public ClassAndField(String className, String fieldName)
  {
    _className=className;
    _fieldName=fieldName;
  }

  /**
   * Get the name of the class.
   * @return the name of the class.
   */
  public String getClassName()
  {
    return _className;
  }

  /**
   * Get the name of the field.
   * @return the name of the field.
   */
  public String getFieldName()
  {
    return _fieldName;
  }

  /**
   * Get a stringified representation of this object.
   * @return a stringified representation of this object.
   */
  @Override
  public String toString()
  {
    return _className+"/"+_fieldName;
  }
}
