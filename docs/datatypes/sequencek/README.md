---
layout: docs
title: SequenceK
permalink: /docs/datatypes/sequencek/
---

## SequenceK

{:.beginner}
beginner

SequenceK implements lazy lists representing lazily-evaluated ordered sequence of homogenous values.

It can be created from Kotlin Sequence type with a convenient `k()` function.

```kotlin
import arrow.*
import arrow.data.*

sequenceOf(1, 2, 3).k()
// SequenceK(sequence=kotlin.collections.ArraysKt___ArraysKt$asSequence$$inlined$Sequence$1@43a48989)
```

SequenceK derives many useful typeclasses. For instance, it has a [`SemigroupK`](/docs/typeclasses/semigroupk/) instance.

```kotlin
val hello = sequenceOf('h', 'e', 'l', 'l', 'o').k()
val commaSpace = sequenceOf(',', ' ').k()
val world = sequenceOf('w', 'o', 'r', 'l', 'd').k()

hello.combineK(commaSpace.combineK(world)).toList() == hello.combineK(commaSpace).combineK(world).toList()
// true
```

[`Functor`](/docs/typeclasses/functor/)

Transforming a sequence:
```kotlin
val fibonacci = generateSequence(0 to 1) { it.second to it.first + it.second }.map { it.first }.k()
fibonacci.map { it * 2 }.takeWhile { it < 10 }.toList()
// [0, 2, 2, 4, 6]
```

[`Applicative`](/docs/typeclasses/applicative/)

Applying a sequence of functions to a sequence:
```kotlin
import arrow.instances.*
ForSequenceK extensions {
  sequenceOf(1, 2, 3).k()
    .ap(sequenceOf({ x: Int -> x + 1}, { x: Int -> x * 2}).k())
    .toList() 
}
// [2, 3, 4, 2, 4, 6]
```

SequenceK is a [`Monad`](/docs/typeclasses/monad/) too. For example, it can be used to model non-deterministic computations. (In a sense that the computations return an arbitrary number of results.)

```kotlin
import arrow.typeclasses.*

val positive = generateSequence(1) { it + 1 }.k() // sequence of positive numbers
val positiveEven = positive.filter { it % 2 == 0 }.k()

ForSequenceK extensions { 
  binding {
   val p = positive.bind()
   val pe = positiveEven.bind()
   p + pe
  }.fix().take(5).toList()
}
// [3, 5, 7, 9, 11]
```

Folding a sequence,

```kotlin
sequenceOf('a', 'b', 'c', 'd', 'e').k().foldLeft("") { x, y -> x + y }
// abcde
```

### Supported type classes

| Module | Type classes |
|__arrow.aql__|[Count]({{ '/docs/arrow/aql/count' | relative_url }}), [From]({{ '/docs/arrow/aql/from' | relative_url }}), [GroupBy]({{ '/docs/arrow/aql/groupby' | relative_url }}), [OrderBy]({{ '/docs/arrow/aql/orderby' | relative_url }}), [Select]({{ '/docs/arrow/aql/select' | relative_url }}), [Sum]({{ '/docs/arrow/aql/sum' | relative_url }}), [Union]({{ '/docs/arrow/aql/union' | relative_url }}), [Where]({{ '/docs/arrow/aql/where' | relative_url }})|
|__arrow.mtl.typeclasses__|[FunctorFilter]({{ '/docs/arrow/mtl/typeclasses/functorfilter' | relative_url }}), [MonadCombine]({{ '/docs/arrow/mtl/typeclasses/monadcombine' | relative_url }}), [MonadFilter]({{ '/docs/arrow/mtl/typeclasses/monadfilter' | relative_url }})|
|__arrow.optics.typeclasses__|[Each]({{ '/docs/arrow/optics/typeclasses/each' | relative_url }}), [FilterIndex]({{ '/docs/arrow/optics/typeclasses/filterindex' | relative_url }}), [Index]({{ '/docs/arrow/optics/typeclasses/index' | relative_url }})|
|__arrow.typeclasses__|[Applicative]({{ '/docs/arrow/typeclasses/applicative' | relative_url }}), [Eq]({{ '/docs/arrow/typeclasses/eq' | relative_url }}), [Foldable]({{ '/docs/arrow/typeclasses/foldable' | relative_url }}), [Functor]({{ '/docs/arrow/typeclasses/functor' | relative_url }}), [Monad]({{ '/docs/arrow/typeclasses/monad' | relative_url }}), [Monoid]({{ '/docs/arrow/typeclasses/monoid' | relative_url }}), [MonoidK]({{ '/docs/arrow/typeclasses/monoidk' | relative_url }}), [Semigroup]({{ '/docs/arrow/typeclasses/semigroup' | relative_url }}), [SemigroupK]({{ '/docs/arrow/typeclasses/semigroupk' | relative_url }}), [Show]({{ '/docs/arrow/typeclasses/show' | relative_url }}), [Traverse]({{ '/docs/arrow/typeclasses/traverse' | relative_url }})|