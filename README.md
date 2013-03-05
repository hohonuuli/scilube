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

```scala











// Much of the functionality is found in the Matlib object

import scilube.Matlib._

//FFT
val data = (1 to 10).map(_.toDouble).toArray
val complexFft = fft(d)

// Basic Statistics
val meanAbsoluteDeviation = mad(data)
val medianData = median(data)
val meanData = mean(data)
val standardDeviation = std(data)
val varData = variance(data)

// Much, Much More

// CDF
val wdata = Array[Double](0, 1, 2, 3, 4, 5, 6, 7, 8, 9,10,11,12,13,14,15,16,
    17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,
    40,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,15,
    16,17,18,19,20,21,22,23,24,25,18,19,20,21,22,25,27,27,28,29,29,30,30,
    31,32,33,34,40,45,50)
val (cdfData, x) = tocdf(wdata)

// And still much more ...

```


_scilube_ can be included in other projects with:
<pre>
    &lt;dependency&gt;
        &lt;groupId&gt;scilube&lt;/groupId&gt;
        &lt;artifactId&gt;scilube-core&lt;/artifactId&gt;
        &lt;version&gt;1.0-SNAPSHOT&lt;/version&gt;
        &lt;classifier&gt;2.9.2&lt;/classifier&gt;
    &lt;/dependency&gt;
</pre>