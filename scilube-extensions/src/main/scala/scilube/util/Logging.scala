package scilube.util

import org.slf4j.{Logger => Slf4jLogger, LoggerFactory}

/**
 * Trait that adds logging support via SLF4J. It can be mixed into a class to add Logging support
 */
trait Logging {
    protected val log = LoggerFactory.getLogger(getClass)

    protected def debug(msg: => String): Unit =  if (log.isDebugEnabled) log.debug(msg)

    protected def debug(msg: => String, e: => Throwable): Unit =  if (log.isDebugEnabled) log.debug(msg, e)

    protected def info(msg: => String): Unit =  if (log.isInfoEnabled) log.info(msg)

    protected def info(msg: => String, e: => Throwable): Unit =  if (log.isInfoEnabled) log.info(msg, e)

    protected def warn(msg: => String): Unit =  if (log.isWarnEnabled) log.warn(msg)

    protected def warn(msg: => String, e: => Throwable): Unit =  if (log.isWarnEnabled) log.warn(msg, e)

    protected def error(msg: => String): Unit =  if (log.isErrorEnabled) log.error(msg)

    protected def error(msg: => String, e: => Throwable): Unit =  if (log.isErrorEnabled) log.error(msg, e)

    protected def trace(msg: => String): Unit =  if (log.isTraceEnabled) log.trace(msg)

    protected def trace(msg: => String, e: => Throwable): Unit =  if (log.isTraceEnabled) log.trace(msg, e)
}

/**
 * public logger class
 */
class Logger(slf4jLogger: Slf4jLogger) {

    protected val log: Slf4jLogger = slf4jLogger

    def debug(msg: => String): Unit =  if (log.isDebugEnabled) log.debug(msg)

    def debug(msg: => String, e: => Throwable): Unit =  if (log.isDebugEnabled) log.debug(msg, e)

    def info(msg: => String): Unit =  if (log.isInfoEnabled) log.info(msg)

    def info(msg: => String, e: => Throwable): Unit =  if (log.isInfoEnabled) log.info(msg, e)

    def warn(msg: => String): Unit =  if (log.isWarnEnabled) log.warn(msg)

    def warn(msg: => String, e: => Throwable): Unit =  if (log.isWarnEnabled) log.warn(msg, e)

    def error(msg: => String): Unit =  if (log.isErrorEnabled) log.error(msg)

    def error(msg: => String, e: => Throwable): Unit =  if (log.isErrorEnabled) log.error(msg, e)

    def trace(msg: => String): Unit =  if (log.isTraceEnabled) log.trace(msg)

    def trace(msg: => String, e: => Throwable): Unit =  if (log.isTraceEnabled) log.trace(msg, e)
}

/**
 * Factory for loggers
 */
object Logger {

    def apply(name: String) = {
        require(name != null, "name must not be null!")
        newLogger(LoggerFactory.getLogger(name))
    }

    def apply(clazz: Class[_]) = {
        require(clazz != null, "clazz must not be null!")
        newLogger(LoggerFactory.getLogger(clazz))
    }

    private def newLogger(slf4jLogger: Slf4jLogger) = new Logger(slf4jLogger)


}
