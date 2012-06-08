scilube
===========

Science libraries for Scala


Building
# Install Spire into local maven repo
 1. Check out Spire
   git clone https://github.com/non/spire.git
 2.  Build Spire
   sbt clean compile package make-pom package-src
 3. Install in Maven repo
   cd target/scala-2.9.2
   mvn install:install-file -DartifactId=spire \
    -DgroupId=com.github.non \
    -Dpackaging=jar \
    -DpomFile=spire_2.9.2-0.2.0.pom \
    -Dfile=spire_2.9.2-0.2.0.jar \
    -Dversion=0.2.0 \
    -Dclassifier=2.9.2 \
    -Dsources=spire_2.9.2-0.2.0-sources.jar

# Reference in your project as
    <dependency>
        <groupId>com.github.non</groupId>
        <artifactId>spire</artifactId>
        <version>0.2.0</version>
        <classifier>2.9.2</classifier>
    </dependency>