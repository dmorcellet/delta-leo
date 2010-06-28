package delta.leo.info;

public class LogicalOperator
{
  public static final LogicalOperator AND=new LogicalOperator("AND");
  public static final LogicalOperator OR=new LogicalOperator("OR");
  private String _name;

  private LogicalOperator(String name)
  {
    _name=name;
  }

  public String getName()
  {
    return _name;
  }
}
