---
layout: landing
title: MBARI's Video Annotation and Reference System
---

## scilube

A simple MATLAB-like DSL for working with numeric arrays.

### Adding to an SBT project

```
resolvers += Resolver.bintrayRepo("hohonuuli", "maven")
libraryDependencies += "scilube" %% "scilube-core" % "2.0.7.jre11"
```

### Array extension methods

`scilibe.RichArray` extension methods allow array operations:

```
import scilibe.RichArray // add implict element-by-element math functions to arrays

val x = (1 to 10).map(_.toDouble).toArray
// x: Array[Double] = Array(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0)

// --- Array operations

x * x
// res: Array[Double] = Array(1.0, 4.0, 9.0, 16.0, 25.0, 36.0, 49.0, 64.0, 81.0, 100.0)

x + x
// res: Array[Double] = Array(2.0, 4.0, 6.0, 8.0, 10.0, 12.0, 14.0, 16.0, 18.0, 20.0)

x / x
// res: Array[Double] = Array(1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0)

x - x
// res: Array[Double] = Array(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)

x.subset(0 until 3)
// res: Array[Double] = Array(1.0, 2.0, 3.0)

x.subset(Seq(1, 4, 9))
// res: Array[Double] = Array(2.0, 5.0, 10.0)

x.findIdx(_ < 3)
// res: Array[Int] = Array(0, 1)
```

`scilibe.RichArray` extension methods allow scalar operations on arrays:

```
import scilibe.RichArray // add implict element-by-element math functions to arrays

val x = (1 to 10).map(_.toDouble).toArray
// x: Array[Double] = Array(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0)

// --- Scalar ops
x * 2
// res: Array[Double] = Array(2.0, 4.0, 6.0, 8.0, 10.0, 12.0, 14.0, 16.0, 18.0, 20.0)

x + 10
// res: Array[Double] = Array(11.0, 12.0, 13.0, 14.0, 15.0, 16.0, 17.0, 18.0, 19.0, 20.0)

x / 2
// res: Array[Double] = Array(0.5, 1.0, 1.5, 2.0, 2.5, 3.0, 3.5, 4.0, 4.5, 5.0)

x - 10
// res: Array[Double] = Array(-9.0, -8.0, -7.0, -6.0, -5.0, -4.0, -3.0, -2.0, -1.0, 0.0)

```

MATLAB-like DSL ... `Matlib`

```
import scilibe.RichArray // add implict element-by-element math functions to arrays
import scilube.Matlib._  // Matlab-like DSL

val x = (1 to 10).map(_.toDouble).toArray
// x: Array[Double] = Array(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0)

val y = x * x
// y: Array[Double] = Array(1.0, 4.0, 9.0, 16.0, 25.0, 36.0, 49.0, 64.0, 81.0, 100.0)

corr(x, y)                 // Pearsons correlation
// res: Double = 0.9745586289152093

corrcoef(x, y)             // correlation
// res: Double = 0.08555369917386947

cumsum(x)
// res: Array[Double] = Array(1.0, 3.0, 6.0, 10.0, 15.0, 21.0, 28.0, 36.0, 45.0, 55.0)

diff(x)                    // difference between adjacent values
// res: Array[Double] = Array(1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0)

dot(x, y)                  // vector dot product
// res: Double = 3025.0

fft(x)                     // fast-Fourier transform
// res: Array[spire.math.Complex[Double]] = Array(
//   Complex(55.0, 0.0),
//   Complex(-5.000000000000002, 15.388417685876268),
//   Complex(-4.9999999999999964, 6.881909602355868),
//   Complex(-5.000000000000001, 3.6327126400268037),
//   Complex(-4.9999999999999964, 1.6245984811645322),
//   Complex(-4.999999999999998, 4.440892098500626E-16),
//   Complex(-4.9999999999999964, -1.6245984811645322),
//   Complex(-5.0, -3.632712640026805),
//   Complex(-4.9999999999999964, -6.881909602355868),
//   Complex(-4.999999999999999, -15.388417685876266)
// )

fibonacci(123)
// res: BigInt = 22698374052006863956975682

fix(1.987)                 // round towards zero
// res: Double = 1.0

gcd(34, 51)                // greatest common denominator
// res: Int = 17

interp1(Array(1, 5, 10),   // linear interpolation
  Array(1, 5, 10), 
  Array(3,4,7))
// res: Array[Double] = Array(3.0, 4.0, 7.0)

extrap1(Array(1, 5, 10),    // linear extrapolation
  Array(1, 5, 10), 
  Array(3, 5, 12))
// res: Array[Double] = Array(3.0, 5.0, 12.0)

isprime(13421)              // is it a prime number?
// res: Boolean = true

linspace(11, 20, 5)         // linerarly-spaced points
// res: Array[Double] = Array(11.0, 13.25, 15.5, 17.75, 20.0)

mad(x)                      // mean absolute deviation
// res: Array[Double] = Array(4.5, 3.5, 2.5, 1.5, 0.5, 0.5, 1.5, 2.5, 3.5, 4.5)

mean(x)
// res: Double = 5.5

median(x)
// res: Double = 5.5

near(x, 3.2)                // index of point nearest value
// res: Int = 2

norm(x)
// res15: Double = 19.621416870348583

prod(x)
// res16: Double = 3628800.0

rem(13, 5)                 // remainder after division
// res17: Double = 3.0

sign(9)
// res18: Int = 1

sign(-9)
// res19: Int = -1

std(x)                     // standard deviation
// res7: Double = 2.8722813232690143

sum(x)
// res20: Double = 55.0

trapz(x, y)                // trapezoidal integration
// res5: Double = 334.5


variance(x)
// res46: Double = 8.25
```

__And more ...__


Documentation at [http://hohonuuli.github.io/scilube](http://hohonuuli.github.io/scilube)


## API Docs
- [scilube-core](https://hohonuuli.github.io/scilube/apidocs/scilube-core/api)
- [scilube-extensions](https://hohonuuli.github.io/scilube/apidocs/scilube-extensions/api)
- [scilube-gis](https://hohonuuli.github.io/scilube/apidocs/scilube-gis/api)
- [scilube-ocean](https://hohonuuli.github.io/scilube/apidocs/scilube-ocean/api)

