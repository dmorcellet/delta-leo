package delta.leo.tools.sql.mysql;

import delta.common.utils.types.IntegerType;
import delta.common.utils.types.Type;
import delta.leo.model.entity.field.Field;
import delta.leo.tools.sql.SqlField;

public class MySQLSqlFieldFactory
{
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

	public SqlField buildIntegerField(IntegerType intType)
	{
		Integer min=intType.getMin();
    Integer max=intType.getMax();
    System.out.println(min+","+max);
		return null;
	}
}
