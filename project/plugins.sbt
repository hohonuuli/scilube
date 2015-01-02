// https://github.com/jrudolph/sbt-dependency-graph
addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.7.4")

// https://github.com/xerial/sbt-pack
addSbtPlugin("org.xerial.sbt" % "sbt-pack" % "0.6.2")

// https://github.com/sbt/sbt-man (allows man commands in sbt console)
addSbtPlugin("com.eed3si9n" % "sbt-man" % "0.1.1")

// https://github.com/sksamuel/sbt-versions
addSbtPlugin("com.sksamuel.sbt-versions" % "sbt-versions" % "0.2.0")

// https://github.com/rtimush/sbt-updates
addSbtPlugin("com.timushev.sbt" % "sbt-updates" % "0.1.6")

resolvers += "sonatype-releases" at "https://oss.sonatype.org/content/repositories/releases/"
