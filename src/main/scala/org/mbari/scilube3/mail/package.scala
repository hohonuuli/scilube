package org.mbari.scilube3

/** Fluent Email API from https://gist.github.com/mariussoutier/3436111
  */
package object mail {

  implicit def stringToSeq(single: String): Seq[String] = Seq(single)
  implicit def liftToOption[T](t: T): Option[T]         = Some(t)

  sealed abstract class MailType
  case object Plain     extends MailType
  case object Rich      extends MailType
  case object MultiPart extends MailType

  object send {
    def a(mail: Mail): Unit = {
      import org.apache.commons.mail._

      val format =
        if (mail.attachment.isDefined) MultiPart
        else if (mail.richMessage.isDefined) Rich
        else Plain

      val commonsMail: Email = format match {
        case Plain     => new SimpleEmail().setMsg(mail.message)
        case Rich      =>
          new HtmlEmail().setHtmlMsg(mail.richMessage.get).setTextMsg(mail.message)
        case MultiPart => {
          val attachment = new EmailAttachment()
          attachment.setPath(mail.attachment.get.getAbsolutePath)
          attachment.setDisposition(EmailAttachment.ATTACHMENT)
          attachment.setName(mail.attachment.get.getName)
          new MultiPartEmail().attach(attachment).setMsg(mail.message)
        }
      }

      // TODO Set authentication from your configuration, sys properties or w/e

      // Can't add these via fluent API because it produces exceptions
      mail.to foreach (commonsMail.addTo(_))
      mail.cc foreach (commonsMail.addCc(_))
      mail.bcc foreach (commonsMail.addBcc(_))

      commonsMail.setHostName(mail.hostName)

      commonsMail.setFrom(mail.from._1, mail.from._2).setSubject(mail.subject).send()
    }
  }

}

/* EXAMPLE USAGE

package something

object Demo {
  import mail._

  send a new Mail (
    from = ("john.smith@mycompany.com", "John Smith"),
    to = "boss@mycompany.com",
    cc = "hr@mycompany.com",
    subject = "Import stuff",
    message = "Dear Boss..."
  )

  send a new Mail (
    from = "john.smith@mycompany.com" -> "John Smith",
    to = Seq("dev@mycompany.com", "marketing@mycompany.com"),
    subject = "Our New Strategy (tm)",
    message = "Please find attach the latest strategy document.",
    richMessage = "Here's the <blink>latest</blink> <strong>Strategy</strong>..."
  )

  send a new Mail (
    from = "john.smith@mycompany.com" -> "John Smith",
    to = "dev@mycompany.com" :: "marketing@mycompany.com" :: Nil,
    subject = "Our 5-year plan",
    message = "Here is the presentation with the stuff we're going to for the next five years.",
    attachment = new java.io.File("/home/boss/important-presentation.ppt")
  )
}

 */
