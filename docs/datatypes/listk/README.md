---
layout: docs
title: ListK
permalink: /docs/datatypes/listk/
---

## ListK

{:.beginner}
beginner

ListK wraps over the platform `List` type to make it a [type constructor](/docs/patterns/glossary/#type-constructors).

It can be created from Kotlin List type with a convenient `k()` function.

```kotlin
import arrow.*
import arrow.data.*

listOf(1, 2, 3).k()
// ListK(list=[1, 2, 3])
```

For most use cases you will never use `ListK` directly but `List` directly with the extension functions that Arrow projects over it.

ListK implements operators from many useful typeclasses.

The @extension type class processor expands all type class combinators that `ListK` provides automatically over `List`

For instance, it has `combineK` from the [`SemigroupK`](/docs/typeclasses/semigroupk/) typeclass.

It can be used to cheaply combine two lists:

```kotlin
import arrow.instances.list.semigroupK.*

val hello = listOf('h', 'e', 'l', 'l', 'o')
val commaSpace = listOf(',', ' ')
val world = listOf('w', 'o', 'r', 'l', 'd')

hello.combineK(commaSpace).combineK(world)
// ListK(list=[h, e, l, l, o, ,,  , w, o, r, l, d])
```

The functions `traverse` and `sequence` come from [`Traverse`](/docs/typeclasses/traverse/).

Traversing a list creates a new container [`Kind<F, A>`](/docs/patterns/glossary/#type-constructors) by combining the result of a function applied to each element:

```kotlin
import arrow.core.*
import arrow.instances.*
import arrow.instances.option.applicative.*
import arrow.instances.list.traverse.*

val numbers = listOf(Math.random(), Math.random(), Math.random())
numbers.traverse(Option.applicative(), { if (it > 0.5) Some(it) else None })
// None
```

and complements the convenient function `sequence()` that converts a list of `ListK<Kind<F, A>>` into a `Kind<F, ListK<A>>`:

```kotlin
fun andAnother() = Some(Math.random())

val requests = listOf(Some(Math.random()), andAnother(), andAnother())
requests.sequence(Option.applicative())
// Some(ListK(list=[0.48006946581230614, 0.24677349253451386, 0.5663365679676102]))
```

If you want to aggregate the elements of a list into any other value you can use `foldLeft` and `foldRight` from [`Foldable`](/docs/typeclasses/foldable).

Folding a list into a new value, `String` in this case, starting with an initial value and a combine function:

```kotlin
listOf('a', 'b', 'c', 'd', 'e').k().foldLeft("-> ") { x, y -> x + y }
// -> abcde
```

Or you can apply a list of transformations using `ap` from [`Applicative`](/docs/typeclasses/applicative/).

```kotlin
import arrow.instances.*
import arrow.instances.list.applicative.*

listOf(1, 2, 3).ap(listOf({ x: Int -> x + 10}, { x: Int -> x * 2}))
// ListK(list=[11, 12, 13, 2, 4, 6])
```

### Supported type classes

| Module | Type classes |
|__arrow.aql__|[Count]({{ '/docs/arrow/aql/count' | relative_url }}), [From]({{ '/docs/arrow/aql/from' | relative_url }}), [GroupBy]({{ '/docs/arrow/aql/groupby' | relative_url }}), [OrderBy]({{ '/docs/arrow/aql/orderby' | relative_url }}), [Select]({{ '/docs/arrow/aql/select' | relative_url }}), [Sum]({{ '/docs/arrow/aql/sum' | relative_url }}), [Union]({{ '/docs/arrow/aql/union' | relative_url }}), [Where]({{ '/docs/arrow/aql/where' | relative_url }})|
|__arrow.mtl.typeclasses__|[FunctorFilter]({{ '/docs/arrow/mtl/typeclasses/functorfilter' | relative_url }}), [MonadCombine]({{ '/docs/arrow/mtl/typeclasses/monadcombine' | relative_url }}), [MonadFilter]({{ '/docs/arrow/mtl/typeclasses/monadfilter' | relative_url }})|
|__arrow.optics.typeclasses__|[Each]({{ '/docs/arrow/optics/typeclasses/each' | relative_url }}), [FilterIndex]({{ '/docs/arrow/optics/typeclasses/filterindex' | relative_url }}), [Index]({{ '/docs/arrow/optics/typeclasses/index' | relative_url }})|
|__arrow.typeclasses__|[Applicative]({{ '/docs/arrow/typeclasses/applicative' | relative_url }}), [Eq]({{ '/docs/arrow/typeclasses/eq' | relative_url }}), [Foldable]({{ '/docs/arrow/typeclasses/foldable' | relative_url }}), [Functor]({{ '/docs/arrow/typeclasses/functor' | relative_url }}), [Monad]({{ '/docs/arrow/typeclasses/monad' | relative_url }}), [Monoid]({{ '/docs/arrow/typeclasses/monoid' | relative_url }}), [MonoidK]({{ '/docs/arrow/typeclasses/monoidk' | relative_url }}), [Semigroup]({{ '/docs/arrow/typeclasses/semigroup' | relative_url }}), [SemigroupK]({{ '/docs/arrow/typeclasses/semigroupk' | relative_url }}), [Show]({{ '/docs/arrow/typeclasses/show' | relative_url }}), [Traverse]({{ '/docs/arrow/typeclasses/traverse' | relative_url }})|
