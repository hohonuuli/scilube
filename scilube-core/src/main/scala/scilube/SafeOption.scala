package scilube

import org.slf4j.LoggerFactory

/**
 * Safe-dereference Operator. Returns an <code>Option</code>. The wrapped operation will
 * always return <code>Some(x)</code> or <code>None</code>.
 * See http://hohonuuli.blogspot.com/2010/07/safe-dereference-operator-in-scala-ive.html
 */
object SafeOption {

    private[this] val log = LoggerFactory.getLogger(getClass)

    def apply[A](x: => A):Option[A] = {
        try {
            Option(x)
        }
        catch {
            case e: Exception => {
                log.debug("An exception occurred; we've handled it but thought we'd log it too.", e)
                None
            }
        }
    }
}
