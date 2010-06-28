package delta.leo.model.entity.field;

import delta.common.utils.properties.SymbolicPropertiesSet;
import delta.common.utils.properties.SymbolicProperty;
import delta.common.utils.types.Type;

public interface Field
{
  public String getName();

  public Type getType();

  public SymbolicPropertiesSet getProperties();

  public boolean hasProperty(SymbolicProperty property);
}
