package delta.leo.tools.sql;

import java.util.ArrayList;
import java.util.List;

public class SqlTable
{
	private final String _name;
	private List<SqlField> _fields;

	public SqlTable(String name)
	{
    _name=name;
    _fields=new ArrayList<SqlField>();
	}

	public void addField(SqlField f, boolean notNull, boolean pk)
	{
    // Nothing to do here
	}

  public SqlField getField(int index)
  {
    SqlField field=_fields.get(index);
    return field;
  }

  public String getName()
  {
    return _name;
  }
}
