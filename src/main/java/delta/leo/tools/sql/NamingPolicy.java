package delta.leo.tools.sql;

public interface NamingPolicy
{
	public String buildTableName(String className);
	public String buildFieldName();
}
