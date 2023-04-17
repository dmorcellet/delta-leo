package delta.leo.tools.sql.mysql;

import delta.common.utils.types.IntegerType;
import delta.common.utils.types.Type;
import delta.leo.model.entity.field.Field;
import delta.leo.tools.sql.SqlField;

/**
 * Factory for MySQL SQL fields.
 * @author DAM
 */
public class MySQLSqlFieldFactory
{
  /**
   * Build a SQL field for a model field.
   * @param field Input field.
   * @return the result field.
   */
	public SqlField buildField(Field field)
	{
    Type type=field.getType();
    if (type instanceof IntegerType)
    {
      IntegerType intType=(IntegerType)type;
			buildIntegerField(intType);
		}
		return null;
	}

	/**
	 * Build a SQL field for integer values.
	 * @param intType Integer type.
	 * @return a SQL field.
	 */
	public SqlField buildIntegerField(IntegerType intType)
	{
		Integer min=intType.getMin();
    Integer max=intType.getMax();
    System.out.println(min+","+max);
		return null;
	}
}
