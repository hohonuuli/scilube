// https://github.com/jrudolph/sbt-dependency-graph
addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.8.1")

// https://github.com/xerial/sbt-pack
addSbtPlugin("org.xerial.sbt" % "sbt-pack" % "0.7.9")

// https://github.com/sksamuel/sbt-versions
addSbtPlugin("com.sksamuel.sbt-versions" % "sbt-versions" % "0.2.0")

// https://github.com/rtimush/sbt-updates
addSbtPlugin("com.timushev.sbt" % "sbt-updates" % "0.1.10")

// https://github.com/sbt/sbt-scalariform
addSbtPlugin("org.scalariform" % "sbt-scalariform" % "1.6.0")

// http://www.scalastyle.org/sbt.html
addSbtPlugin("org.scalastyle" %% "scalastyle-sbt-plugin" % "0.8.0")

// https://github.com/fedragon/sbt-todolist
addSbtPlugin("com.github.fedragon" % "sbt-todolist" % "0.6")

// https://github.com/sbt/sbt-man (allows man commands in sbt console)
addSbtPlugin("com.eed3si9n" % "sbt-man" % "0.1.1")

// https://github.com/softprops/bintray-sbt
addSbtPlugin("me.lessis" % "bintray-sbt" % "0.3.0")

resolvers += Resolver.sonatypeRepo("releases")
