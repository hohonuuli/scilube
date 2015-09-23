---
layout: landing
title: MBARI's Video Annotation and Reference System
---

## Including in Projects

Scilube artifacts are currently hosted at [https://bintray.com/hohonuuli/maven](https://bintray.com/hohonuuli/maven). You can add this to your SBT project using:

```
resolvers += Resolver.bintrayRepo("hohonuuli", "maven")
```

### scilube-core

SBT coordinates: `"scilube" %% "scilube-core" % "2.0"`

__scilube-core__ contains a Matlab-like DSL for working with Arrays. For example:

```

import scilibe.RichArray // add implict element-by-element math functions to arrays
import scilube.Matlib._  // Matlab-like DSL

val x = (1 to 10).map(_.toDouble).toArray
// x: Array[Double] = Array(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0)

x * x
// res0: Array[Double] = Array(1.0, 4.0, 9.0, 16.0, 25.0, 36.0, 49.0, 64.0, 81.0, 100.0)

x + x
// res1: Array[Double] = Array(2.0, 4.0, 6.0, 8.0, 10.0, 12.0, 14.0, 16.0, 18.0, 20.0)

x / x
// res2: Array[Double] = Array(1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0)

x - x
// res3: Array[Double] = Array(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)

cumsum(x)
// res4: Array[Double] = Array(1.0, 3.0, 6.0, 10.0, 15.0, 21.0, 28.0, 36.0, 45.0, 55.0)

val y = x * x
// y: Array[Double] = Array(1.0, 4.0, 9.0, 16.0, 25.0, 36.0, 49.0, 64.0, 81.0, 100.0)

trapz(x, y)
// res5: Double = 334.5

dot(x, y)
// res6: Double = 3025.0

std(x)
// res7: Double = 2.8722813232690143

scala> mean(x)
// res8: Double = 5.5

scala> median(x)
// res9: Double = 5.5

```

## API Docs
- [scilube-core](https://hohonuuli.github.io/scilube/apidocs/scilube-core/api)
- [scilube-extensions](https://hohonuuli.github.io/scilube/apidocs/scilube-extensions/api)
- [scilube-gis](https://hohonuuli.github.io/scilube/apidocs/scilube-gis/api)
- [scilube-ocean](https://hohonuuli.github.io/scilube/apidocs/scilube-ocean/api)

