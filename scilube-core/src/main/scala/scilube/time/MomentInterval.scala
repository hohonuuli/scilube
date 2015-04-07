package scilube.time

import java.util.Date

/**
 * Trait that represents a Moment or Interval in time
 * @author Brian Schlining
 * @since 2011-06-28
 */
trait MomentInterval[A] {

  /**
   * Checks that a date falls with in the bounds of
   * a moment interval
   * @param instant The date to test
   * @return true if the date is within the moment interval.
   *  For momements, that means the date equal the date of the moment
   */
  def contains[B <: A](instant: B): Boolean

  /**
   * Checks that a date falls with in the bounds of
   * a moment interval using a given tolerance.
   *
   * @param instant The date to test
   * @param toleranceMillisec A tolerance in millisecs. Comparisons will
   *  be made using the MomentIntervals dates +/- this tolerance.
   * @return true if the date is within the moment interval.
   *
   */
  def contains[B <: A](instant: B, toleranceMillisec: Long): Boolean

  /**
   * The start of the moment interval
   */
  def start: A

  /**
   * The end of the moment interval
   */
  def end: A

  /**
   * @return duration of momentinterval in millisecs
   */
  def duration: Long
}

trait DateInterval extends MomentInterval[Date] {
  def contains[A <: Date](date: A, toleranceMillisec: Long): Boolean = {
    val startD = new Date(start.getTime - toleranceMillisec)
    val endD = new Date(end.getTime + toleranceMillisec)
    date.after(startD) && date.before(endD)
  }
}

object MomentInterval {

  /**
   * Factory method to create a new MomentInterval object from a
   * date
   * @param d0 A date
   */
  def apply(d0: Date) = new Moment(d0)

  /**
   * Factory method to create a new MomentInterval object from a
   * pair of dates. The dates do not need to be different, they also do
   * not need to be passed in in any particular order.
   * @param d0 A date
   * @param d1 Another date
   */
  def apply(d0: Date, d1: Date) = {
    if (d0.equals(d1)) {
      new Moment(d0)
    } else {
      val dates = List(d0, d1).sortBy(_.getTime)
      new Interval(dates.head, dates(1))
    }
  }
}

/**
 * A Moment representation of a Moment interval
 */
class Moment(val date: Date) extends DateInterval {
  def end: Date = date
  def start: Date = date
  override def contains[B <: Date](instant: B): Boolean = instant.equals(date)
  val duration = 0L
}

/**
 * A interval of time
 */
class Interval(val start: Date, val end: Date) extends DateInterval {
  require(start.before(end), "The start date was not before the end date!")
  override def contains[B <: Date](date: B): Boolean = date.after(start) && date.before(end)
  val duration = end.getTime - start.getTime
}