// https://github.com/jrudolph/sbt-dependency-graph
addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.9.0")

// https://github.com/rtimush/sbt-updates
addSbtPlugin("com.timushev.sbt" % "sbt-updates" % "0.3.4")

addSbtPlugin("com.lucidchart" % "sbt-scalafmt" % "1.15")

// https://github.com/softprops/bintray-sbt
addSbtPlugin("org.foundweekends" % "sbt-bintray" % "0.5.2")

resolvers += Resolver.sonatypeRepo("releases")
