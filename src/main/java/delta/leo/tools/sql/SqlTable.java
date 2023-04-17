package delta.leo.tools.sql;

import java.util.ArrayList;
import java.util.List;

/**
 * Representation of a SQL table.
 * @author DAM
 */
public class SqlTable
{
	private final String _name;
	private List<SqlField> _fields;

	/**
	 * Constructor.
	 * @param name Table name.
	 */
	public SqlTable(String name)
	{
    _name=name;
    _fields=new ArrayList<SqlField>();
	}

	/**
	 * Add a field.
	 * @param f Field to add.
	 * @param notNull <code>true</code> if 'not null', <code>false</code> if nullable.
	 * @param pk <code>true</code> if it is a primary key, <code>false</code> otherwise.
	 */
	public void addField(SqlField f, boolean notNull, boolean pk)
	{
    // Nothing to do here
	}

	/**
	 * Get a SQL field.
	 * @param index Field index (starting at 0).
	 * @return A SQL field.
	 */
  public SqlField getField(int index)
  {
    SqlField field=_fields.get(index);
    return field;
  }

  /**
   * Get the name of this table.
   * @return A table name.
   */
  public String getName()
  {
    return _name;
  }
}
