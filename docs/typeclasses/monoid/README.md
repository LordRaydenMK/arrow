---
layout: docs
title: Monoid
permalink: /docs/typeclasses/monoid/
---

## Monoid

{:.beginner}
beginner

`Monoid` extends the `Semigroup` type class, adding an `empty` method to semigroup's `combine`. The empty method must return a value that when combined with any other instance of that type returns the other instance, i.e.

```kotlin
(combine(x, empty) == combine(empty, x) == x)
```

For example, if we have a `Monoid<String>` with `combine` defined as string concatenation, then `empty = ""`.

Having an empty defined allows us to combine all the elements of some potentially empty collection of `T` for which a `Monoid<T>` is defined and return a `T`, rather than an `Option<T>` as we have a sensible default to fall back to.

And let's see the instance of Monoid<String> in action.

```kotlin
import arrow.*
import arrow.instances.*
import arrow.typeclasses.*

ForString extensions { 
  empty()
}
// 
```

```kotlin
ForString extensions { 
  listOf<String>("Λ", "R", "R", "O", "W").combineAll() 
}
// ΛRROW
```

```kotlin
import arrow.core.*
import arrow.instances.option.monoid.*

Option.monoid(Int.monoid()).run { listOf<Option<Int>>(Some(1), Some(1)).combineAll() }
// Some(2)
```

The advantage of using these type class provided methods, rather than the specific ones for each type, is that we can compose monoids to allow us to operate on more complex types, e.g.

This is also true if we define our own instances. As an example, let's use `Foldable`'s `foldMap`, which maps over values accumulating the results, using the available `Monoid` for the type mapped onto.

```kotlin
import arrow.data.*

ForListK extensions { 
  listOf(1, 2, 3, 4, 5).k().foldMap(Int.monoid(), ::identity) 
}
// 15
```

```kotlin
ForListK extensions { 
  listOf(1, 2, 3, 4, 5).k().foldMap(String.monoid(), { it.toString() }) 
}
// 12345
```

To use this with a function that produces a tuple, we can define a Monoid for a tuple that will be valid for any tuple where the types it contains also have a Monoid available.

```kotlin
fun <A, B> monoidTuple(MA: Monoid<A>, MB: Monoid<B>): Monoid<Tuple2<A, B>> =
  object: Monoid<Tuple2<A, B>> {

    override fun Tuple2<A, B>.combine(y: Tuple2<A, B>): Tuple2<A, B> {
      val (xa, xb) = this
      val (ya, yb) = y
      return Tuple2(MA.run { xa.combine(ya) }, MB.run { xb.combine(yb) })
    }

    override fun empty(): Tuple2<A, B> = Tuple2(MA.empty(), MB.empty())
  }
```

This way we are able to combine both values in one pass, hurrah!

```kotlin
ForListK extensions {
  val M = monoidTuple(Int.monoid(), String.monoid())
  val list = listOf(1, 1).k()

  list.foldMap(M) { n: Int ->
   Tuple2(n, n.toString())
  }
}
// Tuple2(a=2, b=11)
```


### Data types

| Module | Data types |
|__arrow.core__|[Either]({{ '/docs/arrow/core/either' | relative_url }}), [Option]({{ '/docs/arrow/core/option' | relative_url }}), [Try]({{ '/docs/arrow/core/try' | relative_url }}), [Tuple2]({{ '/docs/arrow/core/tuple2' | relative_url }})|
|__arrow.data__|[ListK]({{ '/docs/arrow/data/listk' | relative_url }}), [MapK]({{ '/docs/arrow/data/mapk' | relative_url }}), [SequenceK]({{ '/docs/arrow/data/sequencek' | relative_url }}), [SetK]({{ '/docs/arrow/data/setk' | relative_url }})|
|__arrow.effects__|[IO]({{ '/docs/arrow/effects/io' | relative_url }})|
|__arrow.typeclasses__|[Const]({{ '/docs/arrow/typeclasses/const' | relative_url }})|