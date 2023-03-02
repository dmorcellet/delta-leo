package delta.leo.info;

/**
 * Condition.
 * @author DAM
 */
public class Condition
{
  /**
   * Contains.
   */
  public static final int CONTAINS=0;
  /**
   * Equals.
   */
  public static final int EQUALS=1;
  /**
   * Greater than.
   */
  public static final int GREATER_THAN=2;
  /**
   * Greater or equal.
   */
  public static final int GREATER_OR_EQUALS=3;
  /**
   * Lower than.
   */
  public static final int LOWER_THAN=4;
  /**
   * Lower or equal.
   */
  public static final int LOWER_OR_EQUALS=5;
  /**
   * Starts with.
   */
  public static final int STARTS_WITH=6;
  /**
   * Ends with.
   */
  public static final int ENDS_WITH=7;

  /**
   * Get a displayable label for an operator.
   * @param operator Operator code.
   * @param inverted Indicates if we shall negate the operator or not.
   * @return A displayable label for the given operator.
   */
  public static String getLabelForOperator(int operator, boolean inverted)
  {
    if (inverted) return _invertedLabels[operator];
    return _labels[operator];
  }

  private static String[] _labels={"contains","equals","greater than",
    "greater or equals","lower than","lower or equals","starts with","ends with"};
  private static String[] _invertedLabels={"does not contains","different from","lower than",
    "lower or equals","greater than","greater or equals","does not starts with","does not ends with"};
}
