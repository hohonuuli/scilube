package org.mbari.scilube3.mail

final case class Mail(
    hostName: String,
    from: (String, String), // (email -> name)
    to: Seq[String],
    cc: Seq[String] = Seq.empty,
    bcc: Seq[String] = Seq.empty,
    subject: String,
    message: String,
    richMessage: Option[String] = None,
    attachment: Option[(java.io.File)] = None
)
