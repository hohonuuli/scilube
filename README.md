# scilube

**Science libraries for Scala**


## Building

**First, install Spire into local maven repo**
 1. Check out Spire
   git clone https://github.com/non/spire.git
 2.  Build Spire
   `sbt clean compile package make-pom package-src`
 3. Install in Maven repo
<pre>
   cd target/scala-2.9.2
   mvn install:install-file -DartifactId=spire \
    -DgroupId=com.github.non \
    -Dpackaging=jar \
    -DpomFile=spire_2.9.2-0.2.0.pom \
    -Dfile=spire_2.9.2-0.2.0.jar \
    -Dversion=0.2.0 \
    -Dclassifier=2.9.2 \
    -Dsources=spire_2.9.2-0.2.0-sources.jar
</pre>

Spire is referenced in this project using:
<pre>
    &lt;dependency&gt;
        &lt;groupId&gt;com.github.non&lt;/groupId&gt;
        &lt;artifactId&gt;spire&lt;/artifactId&gt;
        &lt;version&gt;0.2.0&lt;/version&gt;
        &lt;classifier&gt;2.9.2&lt;/classifier&gt;
    &lt;/dependency&gt;
</pre>

**Second, build _scilube_**
  1. Check it out of git
  2. Use Maven to build
<pre>
mvn clean install
</pre>