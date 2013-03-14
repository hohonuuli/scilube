# scilube

**Science libraries for Scala**


Scilube is bult against Scala 2.10.0. It provides modules for doing numerical analysis (ala Matlab).
This is *NOT* a Matrix library. Instead, Scilube provides functions for working with Double Arrays.
To get started:

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

**Building _scilube_**
  1. Check it out of git
  2. Use Maven to build.
<pre>
mvn clean install
</pre>

_scilube_ can be included in other projects with:
<pre>
    &lt;dependency&gt;
        &lt;groupId&gt;scilube&lt;/groupId&gt;
        &lt;artifactId&gt;scilube-core&lt;/artifactId&gt;
        &lt;version&gt;1.0-SNAPSHOT&lt;/version&gt;
        &lt;classifier&gt;2.10.0&lt;/classifier&gt;
    &lt;/dependency&gt;
</pre>