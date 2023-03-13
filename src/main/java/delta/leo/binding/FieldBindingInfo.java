package delta.leo.binding;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.properties.SymbolicPropertiesSet;
import delta.leo.model.entity.field.Field;

/**
 * Binding definition for a single field.
 * @author DAM
 */
public class FieldBindingInfo extends ClassAndField
{
  private Field _field;
  private SymbolicPropertiesSet _properties;
  private List<Join> _joins;

  /**
   * Constructor.
   * @param field Associated logical field.
   * @param className Class of field.
   * @param fieldName Name of field.
   * @param properties Binding properties.
   */
  FieldBindingInfo(String className, String fieldName, Field field, SymbolicPropertiesSet properties)
  {
    super(className,fieldName);
    _field=field;
    _properties=properties;
    _joins=null;
  }

  /**
   * Get the associated logical field.
   * @return the associated logical field.
   */
  public Field getField()
  {
    return _field;
  }

  /**
   * Get the binding properties.
   * @return the binding properties.
   */
  public SymbolicPropertiesSet getProperties()
  {
    return _properties;
  }

  void addJoin(Join join)
  {
    if (_joins==null)
    {
      _joins=new ArrayList<Join>();
    }
    _joins.add(join);
  }

  /**
   * Get joins.
   * @return a list or joins (may be <code>null</code>).
   */
  public List<Join> getJoins()
  {
    return _joins;
  }
}
