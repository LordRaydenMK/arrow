---
layout: docs
title: NonEmptyList
permalink: /docs/datatypes/nonemptylist/
video: TC6IzE61OyE
---

## NonEmptyList

{:.beginner}
beginner

`NonEmptyList` is a data type used in __Λrrow__ to model ordered lists that guarantee to have at least one value.
`NonEmptyList` is available in the `arrow-data` module under the `import arrow.data.NonEmptyList`

```groovy
// gradle
compile "io.arrow-kt:arrow-data:$arrow_version"
```

```kotlin
// namespace
import arrow.data.*
```

## of

A `NonEmptyList` guarantees the list always has at least 1 element.

```kotlin
NonEmptyList.of(1, 2, 3, 4, 5) // NonEmptyList<Int>
NonEmptyList.of(1, 2) // NonEmptyList<Int>
//NonEmptyList.of() // does not compile
```

## head

Unlike `List#[0]`, `NonEmptyList#head` it's a safe operation that guarantees no exception throwing.

```kotlin
NonEmptyList.of(1, 2, 3, 4, 5).head
```

## foldLeft

When we fold over a `NonEmptyList`, we turn a `NonEmptyList< A >` into `B` by providing a __seed__ value and a __function__ that carries the state on each iteration over the elements of the list.
The first argument is a function that addresses the __seed value__, this can be any object of any type which will then become the resulting typed value.
The second argument is a function that takes the current state and element in the iteration and returns the new state after transformations have been applied.

```kotlin
fun sumNel(nel: NonEmptyList<Int>): Int =
  nel.foldLeft(0) { acc, n -> acc + n }

sumNel(NonEmptyList.of(1, 1, 1, 1))
// 4
```

## map

`map` allows us to transform `A` into `B` in `NonEmptyList< A >`

```kotlin
NonEmptyList.of(1, 1, 1, 1).map { it + 1 }
// NonEmptyList(all=[2, 2, 2, 2])
```

## flatMap

`flatMap` allows us to compute over the contents of multiple `NonEmptyList< * >` values

```kotlin
val nelOne: NonEmptyList<Int> = NonEmptyList.of(1)
val nelTwo: NonEmptyList<Int> = NonEmptyList.of(2)

nelOne.flatMap { one ->
  nelTwo.map { two ->
    one + two
  }
}
// NonEmptyList(all=[3])
```

## Monad binding

Λrrow allows imperative style comprehensions to make computing over `NonEmptyList` values easy.

```kotlin
import arrow.typeclasses.*
import arrow.instances.*

val nelOne: NonEmptyList<Int> = NonEmptyList.of(1)
val nelTwo: NonEmptyList<Int> = NonEmptyList.of(2)
val nelThree: NonEmptyList<Int> = NonEmptyList.of(3)

ForNonEmptyList extensions {
  binding {
    val one = nelOne.bind()
    val two = nelTwo.bind()
    val three = nelThree.bind()
    one + two + three
  }.fix()
}
// NonEmptyList(all=[6])
```

Monad binding in `NonEmptyList` and other collection related data type can be used as generators

```kotlin
ForNonEmptyList extensions {
  binding {
    val x = NonEmptyList.of(1, 2, 3).bind()
    val y = NonEmptyList.of(1, 2, 3).bind()
    x + y
  }.fix()
}
// NonEmptyList(all=[2, 3, 4, 3, 4, 5, 4, 5, 6])
```

## Applicative Builder

Λrrow contains methods that allow you to preserve type information when computing over different `NonEmptyList` typed values.

```kotlin
import arrow.data.*
import java.util.*

data class Person(val id: UUID, val name: String, val year: Int)

// Note each NonEmptyList is of a different type
val nelId: NonEmptyList<UUID> = NonEmptyList.of(UUID.randomUUID(), UUID.randomUUID())
val nelName: NonEmptyList<String> = NonEmptyList.of("William Alvin Howard", "Haskell Curry")
val nelYear: NonEmptyList<Int> = NonEmptyList.of(1926, 1900)

ForNonEmptyList extensions {
 map(nelId, nelName, nelYear, { (id, name, year) ->
  Person(id, name, year)
 })
}
// NonEmptyList(all=[Person(id=ad36d42b-3ec0-45c4-9a23-331f910ea8d0, name=William Alvin Howard, year=1926), Person(id=ad36d42b-3ec0-45c4-9a23-331f910ea8d0, name=Haskell Curry, year=1926), Person(id=9d1f2342-3f35-4d75-b45e-ccd400a9719e, name=William Alvin Howard, year=1926), Person(id=9d1f2342-3f35-4d75-b45e-ccd400a9719e, name=Haskell Curry, year=1926), Person(id=ad36d42b-3ec0-45c4-9a23-331f910ea8d0, name=William Alvin Howard, year=1900), Person(id=ad36d42b-3ec0-45c4-9a23-331f910ea8d0, name=Haskell Curry, year=1900), Person(id=9d1f2342-3f35-4d75-b45e-ccd400a9719e, name=William Alvin Howard, year=1900), Person(id=9d1f2342-3f35-4d75-b45e-ccd400a9719e, name=Haskell Curry, year=1900)])
```

### Summary

- `NonEmptyList` is __used to model lists that guarantee at least one element__
- We can easily construct values of `NonEmptyList` with `NonEmptyList.of`
- `foldLeft`, `map`, `flatMap` and others are used to compute over the internal contents of a `NonEmptyList` value.
- `binding { ... } Comprehensions` can be __used to imperatively compute__ over multiple `NonEmptyList` values in sequence.
- `NonEmptyList.applicative().map { ... }` can be used to compute over multiple `NonEmptyList` values preserving type information and __abstracting over arity__ with `map`

### Supported type classes

| Module | Type classes |
|__arrow.aql__|[Count]({{ '/docs/arrow/aql/count' | relative_url }}), [From]({{ '/docs/arrow/aql/from' | relative_url }}), [GroupBy]({{ '/docs/arrow/aql/groupby' | relative_url }}), [OrderBy]({{ '/docs/arrow/aql/orderby' | relative_url }}), [Select]({{ '/docs/arrow/aql/select' | relative_url }}), [Sum]({{ '/docs/arrow/aql/sum' | relative_url }}), [Union]({{ '/docs/arrow/aql/union' | relative_url }}), [Where]({{ '/docs/arrow/aql/where' | relative_url }})|
|__arrow.mtl.typeclasses__|[FunctorFilter]({{ '/docs/arrow/mtl/typeclasses/functorfilter' | relative_url }})|
|__arrow.optics.typeclasses__|[Each]({{ '/docs/arrow/optics/typeclasses/each' | relative_url }}), [FilterIndex]({{ '/docs/arrow/optics/typeclasses/filterindex' | relative_url }}), [Index]({{ '/docs/arrow/optics/typeclasses/index' | relative_url }})|
|__arrow.typeclasses__|[Applicative]({{ '/docs/arrow/typeclasses/applicative' | relative_url }}), [Bimonad]({{ '/docs/arrow/typeclasses/bimonad' | relative_url }}), [Comonad]({{ '/docs/arrow/typeclasses/comonad' | relative_url }}), [Eq]({{ '/docs/arrow/typeclasses/eq' | relative_url }}), [Foldable]({{ '/docs/arrow/typeclasses/foldable' | relative_url }}), [Functor]({{ '/docs/arrow/typeclasses/functor' | relative_url }}), [Monad]({{ '/docs/arrow/typeclasses/monad' | relative_url }}), [Semigroup]({{ '/docs/arrow/typeclasses/semigroup' | relative_url }}), [SemigroupK]({{ '/docs/arrow/typeclasses/semigroupk' | relative_url }}), [Show]({{ '/docs/arrow/typeclasses/show' | relative_url }}), [Traverse]({{ '/docs/arrow/typeclasses/traverse' | relative_url }})|
