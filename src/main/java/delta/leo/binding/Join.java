package delta.leo.binding;

/**
 * Join definition.
 * A join makes the link between two classes (tables)
 * by defining two fields (one in each class/table) that have the same value.
 * @author DAM
 */
public class Join
{
  private ClassAndField _field1;
  private ClassAndField _field2;

  /**
   * Constructor.
   * @param field1 A field.
   * @param field2 Another field.
   */
  public Join(ClassAndField field1, ClassAndField field2)
  {
    _field1=field1;
    _field2=field2;
  }

  /**
   * Get the field infos of field 1.
   * @return the field infos of field 1.
   */
  public ClassAndField getField1()
  {
    return _field1;
  }

  /**
   * Get the field infos of field 2.
   * @return the field infos of field 2.
   */
  public ClassAndField getField2()
  {
    return _field2;
  }

  /**
   * Get the name of the first class.
   * @return the name of the first class.
   */
  public String getClass1()
  {
    return _field1.getClassName();
  }

  /**
   * Get the name of the second class.
   * @return the name of the second class.
   */
  public String getClass2()
  {
    return _field2.getClassName();
  }
}
