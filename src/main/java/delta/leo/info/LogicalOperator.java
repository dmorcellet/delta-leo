package delta.leo.info;

/**
 * Logical operator.
 * @author DAM
 */
public class LogicalOperator
{
  /**
   * AND.
   */
  public static final LogicalOperator AND=new LogicalOperator("AND");
  /**
   * OR.
   */
  public static final LogicalOperator OR=new LogicalOperator("OR");
  private String _name;

  private LogicalOperator(String name)
  {
    _name=name;
  }

  /**
   * Get the name of this operator.
   * @return A name.
   */
  public String getName()
  {
    return _name;
  }
}
