package delta.leo.info;

public class Condition
{
  public static final int CONTAINS=0;
  public static final int EQUALS=1;
  public static final int GREATER_THAN=2;
  public static final int GREATER_OR_EQUALS=3;
  public static final int LOWER_THAN=4;
  public static final int LOWER_OR_EQUALS=5;
  public static final int STARTS_WITH=6;
  public static final int ENDS_WITH=7;

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
