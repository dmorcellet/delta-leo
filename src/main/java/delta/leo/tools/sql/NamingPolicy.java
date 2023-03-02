package delta.leo.tools.sql;

/**
 * Naming policy.
 * @author DAM
 */
public interface NamingPolicy
{
  /**
   * Get the name of the table for the given class.
   * @param className Class name.
   * @return A table name.
   */
	public String buildTableName(String className);
	/**
	 * Get the name of the table field for the given class field name.
	 * @param fieldName Class field name.
	 * @return A table field name.
	 */
	public String buildFieldName(String fieldName);
}
