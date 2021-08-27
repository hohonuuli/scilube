// https://github.com/jrudolph/sbt-dependency-graph
addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.9.0")
addSbtPlugin("com.timushev.sbt" % "sbt-updates" % "0.3.4")
addSbtPlugin("com.lucidchart" % "sbt-scalafmt" % "1.15")
addSbtPlugin("com.codecommit" % "sbt-github-packages" % "0.5.3")


// https://github.com/softprops/bintray-sbt

resolvers += Resolver.sonatypeRepo("releases")
