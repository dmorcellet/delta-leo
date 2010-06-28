package delta.leo.utils;

import org.apache.log4j.Logger;

import delta.common.utils.traces.LoggersRegistry;
import delta.common.utils.traces.LoggingConstants;

/**
 * Management class for all Leo loggers.
 * @author DAM
 */
public abstract class LeoLoggers
{
  /**
   * Name of the "LEO" logger.
   */
  public static final String LEO="LEO";

  /**
   * Name of the SQL related Leo logger.
   */
  public static final String LEO_SQL=LEO+LoggingConstants.SEPARATOR+"SQL";

  /**
   * Name of the model related Leo logger.
   */
  public static final String LEO_MODEL=LEO+LoggingConstants.SEPARATOR+"MODEL";

  /**
   * Name of the data related Leo logger.
   */
  public static final String LEO_DATA=LEO+LoggingConstants.SEPARATOR+"DATA";

  private static final Logger _leoLogger=LoggersRegistry.getLogger(LEO);
  private static final Logger _leoSqlLogger=LoggersRegistry.getLogger(LEO_SQL);
  private static final Logger _leoModelLogger=LoggersRegistry.getLogger(LEO_MODEL);
  private static final Logger _leoDataLogger=LoggersRegistry.getLogger(LEO_DATA);

  /**
   * Get the logger used for Leo.
   * @return the logger used for Leo.
   */
  public static Logger getLeoLogger()
  {
    return _leoLogger;
  }

  /**
   * Get the logger used for Leo/SQL.
   * @return the logger used for Leo/SQL.
   */
  public static Logger getLeoSqlLogger()
  {
    return _leoSqlLogger;
  }

  /**
   * Get the logger used for Leo/Model.
   * @return the logger used for Leo/Model.
   */
  public static Logger getLeoModelLogger()
  {
    return _leoModelLogger;
  }

  /**
   * Get the logger used for Leo/Data.
   * @return the logger used for Leo/Data.
   */
  public static Logger getLeoDataLogger()
  {
    return _leoDataLogger;
  }
}
