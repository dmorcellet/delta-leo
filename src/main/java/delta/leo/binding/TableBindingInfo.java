package delta.leo.binding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Bindings for a single table.
 * Groups field bindings for a single physical table.
 * @author DAM
 */
public class TableBindingInfo
{
  /**
   * Name of the managed table.
   */
  private String _tableName;
  /**
   * List of field bindings that use this table.
   */
  private List<FieldBindingInfo> _fields;
  /**
   * Maps field names to field bindings.
   */
  private Map<String,FieldBindingInfo> _fieldsMap;

  /**
   * Constructor.
   * @param tableName Name of the managed table.
   */
  TableBindingInfo(String tableName)
  {
    _tableName=tableName;
    _fields=new ArrayList<FieldBindingInfo>();
    _fieldsMap=new HashMap<String,FieldBindingInfo>();
  }

  /**
   * Get the name of the managed table.
   * @return the name of the managed table.
   */
  public String getTableName()
  {
    return _tableName;
  }

  /**
   * Add a new field binding.
   * @param fieldBindingInfo Field binding to add.
   * @return <code>true</code> if this binding was successfully added, <code>false</code> otherwise.
   */
  boolean addFieldBinding(FieldBindingInfo fieldBindingInfo)
  {
    String fieldName=fieldBindingInfo.getFieldName();
    FieldBindingInfo oldField=_fieldsMap.get(fieldName);
    if (oldField==null)
    {
      _fields.add(fieldBindingInfo);
      _fieldsMap.put(fieldName,fieldBindingInfo);
      return true;
    }
    return false;
  }

  /**
   * Get a field binding by its field name.
   * @param fieldName
   * @return A field binding or <code>null</code> if not found.
   */
  public FieldBindingInfo getField(String fieldName)
  {
    FieldBindingInfo ret=_fieldsMap.get(fieldName);
    return ret;
  }

  /**
   * Get all managed field bindings.
   * @return the list of all all managed field bindings.
   */
  public List<FieldBindingInfo> getFields()
  {
    return _fields;
  }
}
