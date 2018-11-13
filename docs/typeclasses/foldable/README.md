---
layout: docs
title: Foldable
permalink: /docs/typeclasses/foldable/
---

## Foldable

{:.intermediate}
intermediate

The Typeclass `Foldable` provide us the ability of, given a type `Kind<F, A>`, aggregates their values `A`.

`Foldable<F>` is implemented in terms of two basic methods:

- `fa.foldLeft(init, f)` eagerly folds `fa` from left-to-right.
- `fa.foldRight(init, f)` lazily folds `fa` from right-to-left.

Beyond these it provides many other useful methods related to folding over `Kind<F, A>` values.

For the following examples we are going to use some common imports 

```kotlin
import arrow.Kind
import arrow.core.*
import arrow.data.ListK
import arrow.data.k
import arrow.instances.monoid
import arrow.instances.listk.foldable.foldable
import arrow.instances.option.foldable.foldable
import arrow.typeclasses.Foldable
```
 
and the same two variables to see the different behaviors of `Foldable`:

```kotlin
val maybeStr: Option<String> = Some("abc")
val strList: ListK<String> = listOf("a", "b", "c").k()
```

### FoldLeft
Left associative fold on `F` using the provided function.

```kotlin
fun <F> concatenateStringFromLeft(strKind: Kind<F, String>, FO: Foldable<F>): String =
  FO.run {
    strKind.foldLeft("str: ") { base: String, value: String -> base + value }
  }
```

```kotlin
concatenateStringFromLeft(maybeStr, Option.foldable())
// str: abc
```

```kotlin
concatenateStringFromLeft(None, Option.foldable())
// str: 
```

```kotlin
concatenateStringFromLeft(strList, ListK.foldable())
// str: abc
```

### FoldRight
Right associative lazy fold on `F` using the provided function.

This method evaluates `lb` lazily, and returns a lazy value to support laziness in a stack-safe way avoiding StackOverflows.

For more detailed information about how this method works see the documentation for [`Eval<A>`]({{ '/docs/datatypes/eval' | relative_url }}).

```kotlin
fun <F> concatenateStringFromRight(strKind: Kind<F, String>, FO: Foldable<F>): String =
  FO.run {
    strKind.foldRight(Eval.now("str: ")) { value: String, base: Eval<String> -> base.map { it + value } }
      .value()
  }
```

```kotlin
concatenateStringFromRight(maybeStr, Option.foldable())
// str: abc
```

```kotlin
concatenateStringFromRight(None, Option.foldable())
// str: 
```

```kotlin
concatenateStringFromRight(strList, ListK.foldable())
// str: cba
```

### Fold
Fold implemented using the given `Monoid<A>` instance.

```kotlin
fun <F> concatenateString(strKind: Kind<F, String>, FO: Foldable<F>): String =
  FO.run {
    "str: " + strKind.fold(String.monoid())
  }
```

```kotlin
concatenateString(maybeStr, Option.foldable())
// str: abc
```

```kotlin
concatenateString(None, Option.foldable())
// str: 
```

```kotlin
concatenateString(strList, ListK.foldable())
// str: abc
```

Besides we have `combineAll` which is an alias for fold.

```kotlin
fun <F> combineAllString(strKind: Kind<F, String>, FO: Foldable<F>): String =
  FO.run {
    "str: " + strKind.combineAll(String.monoid())
  }
```

```kotlin
combineAllString(maybeStr, Option.foldable())
// str: abc
```

```kotlin
combineAllString(None, Option.foldable())
// str: 
```

```kotlin
combineAllString(strList, ListK.foldable())
// str: abc
```

### ReduceLeftToOption

```kotlin
fun <F> reduceLeftToOption(strKind: Kind<F, String>, FO: Foldable<F>): Option<Int> =
  FO.run {
    strKind.reduceLeftToOption({ it.length }) { base: Int, value: String -> base + value.length }
  }
```

```kotlin
reduceLeftToOption(maybeStr, Option.foldable())
// Some(3)
```

```kotlin
reduceLeftToOption(None, Option.foldable())
// None
```

```kotlin
reduceLeftToOption(strList, ListK.foldable())
// Some(3)
```

### ReduceRightToOption

```kotlin
fun <F> reduceRightToOption(strKind: Kind<F, String>, FO: Foldable<F>): Option<Int> =
  FO.run {
    strKind.reduceRightToOption({ it.length }) { value: String, base: Eval<Int> -> base.map { it + value.length } }
      .value()
  }
```

```kotlin
reduceRightToOption(maybeStr, Option.foldable())
// Some(3)
```

```kotlin
reduceRightToOption(None, Option.foldable())
// None
```

```kotlin
reduceRightToOption(strList, ListK.foldable())
// Some(3)
```

### ReduceLeftOption
Reduce the elements of this structure down to a single value by applying the provided aggregation function in
a left-associative manner.

Return None if the structure is empty, otherwise the result of combining the cumulative left-associative result
of the f operation over all of the elements.

```kotlin
fun <F> getLengthFromLeft(strKind: Kind<F, String>, FO: Foldable<F>): Option<String> =
  FO.run {
    strKind.reduceLeftOption { base: String, value: String -> base + value }
  }
```

```kotlin
getLengthFromLeft(maybeStr, Option.foldable())
// Some(abc)
```

```kotlin
getLengthFromLeft(None, Option.foldable())
// None
```

```kotlin
getLengthFromLeft(strList, ListK.foldable())
// Some(abc)
```

### ReduceRightOption
Reduce the elements of this structure down to a single value by applying the provided aggregation function in
a right-associative manner.

Return None if the structure is empty, otherwise the result of combining the cumulative right-associative
result of the f operation over the A elements.

```kotlin
fun <F> getLengthFromRight(strKind: Kind<F, String>, FO: Foldable<F>): Option<String> =
  FO.run {
    strKind.reduceRightOption { value: String, base: Eval<String> -> base.map { it + value } }
      .value()
  }
```

```kotlin
getLengthFromRight(maybeStr, Option.foldable())
// Some(abc)
```

```kotlin
getLengthFromRight(None, Option.foldable())
// None
```

```kotlin
getLengthFromRight(strList, ListK.foldable())
// Some(cba)
```

### FoldMap
Fold implemented by mapping `A` values into `B` and then combining them using the given `Monoid<B>` instance.

```kotlin
fun <F> getLenght(strKind: Kind<F, String>, FO: Foldable<F>): Int =
  FO.run {
    strKind.foldMap(Int.monoid()) { it.length }
  }
```

```kotlin
getLenght(maybeStr, Option.foldable())
// 3
```

```kotlin
getLenght(None, Option.foldable())
// 0
```

```kotlin
getLenght(strList, ListK.foldable())
// 3
```
  
### Traverse_
A typed values will be mapped into `Kind<G, B>` by function `f` and combined using `Applicative#map2`.

This method is primarily useful when `<_>` represents an action or effect, and the specific `A` aspect of `Kind<G, A>` is
not otherwise needed.

```kotlin
import arrow.instances.either.applicative.applicative

fun <F> traverse(strKind: Kind<F, String>, FO: Foldable<F>): Either<Int,Unit> =
  FO.run {
    strKind.traverse_(Either.applicative<Int>()) { Right(it.length) }
  }.fix()
```

```kotlin
traverse(maybeStr, Option.foldable())
// Right(b=kotlin.Unit)
```

```kotlin
traverse(None, Option.foldable())
// Right(b=kotlin.Unit)
```

```kotlin
traverse(strList, ListK.foldable())
// Right(b=kotlin.Unit)
```

### Sequence_
Similar to `traverse_` except it operates on `Kind<F, Kind<G, A>>` values, so no additional functions are needed.

```kotlin
import arrow.instances.option.applicative.applicative

fun <F> sequence(strKind: Kind<F, Kind<ForOption, String>>, FO: Foldable<F>):Option<Unit> =
  FO.run {
    strKind.sequence_(Option.applicative())
  }.fix()
  
val maybeStrOpt = Some("abc".some())
val strNoneList = listOf("a".some(), None, "c".some()).k()
val strOptList = listOf("a".some(), "b".some(), "c".some()).k()
```

```kotlin
sequence(maybeStrOpt, Option.foldable())
// Some(kotlin.Unit)
```

```kotlin
sequence(None, Option.foldable())
// Some(kotlin.Unit)
```

```kotlin
sequence(strNoneList, ListK.foldable())
// None
```

```kotlin
sequence(strOptList, ListK.foldable())
// Some(kotlin.Unit)
```

### Find
Find the first element matching the predicate, if one exists.

```kotlin
fun <F> getIfNotBlank(strKind: Kind<F, String>, FO: Foldable<F>): Option<String> =
  FO.run {
    strKind.find { it.isNotBlank() }
  }
```

```kotlin
getIfNotBlank(maybeStr, Option.foldable())
// Some(abc)
```

```kotlin
getIfNotBlank(None, Option.foldable())
// None
```

```kotlin
getIfNotBlank(strList, ListK.foldable())
// Some(a)
```

### Exists
Check whether at least one element satisfies the predicate.

If there are no elements, the result is false.

```kotlin
fun <F> containsNotBlank(strKind: Kind<F, String>, FO: Foldable<F>): Boolean =
  FO.run {
    strKind.exists { it.isNotBlank() }
  }
```

```kotlin
containsNotBlank(maybeStr, Option.foldable())
// true
```

```kotlin
containsNotBlank(None, Option.foldable())
// false
```

```kotlin
containsNotBlank(strList, ListK.foldable())
// true
```

### ForAll
Check whether all elements satisfy the predicate.

If there are no elements, the result is true.

```kotlin
fun <F> isNotBlank(strKind: Kind<F, String>, FO: Foldable<F>): Boolean =
  FO.run {
    strKind.forAll { it.isNotBlank() }
  }
```

```kotlin
isNotBlank(maybeStr, Option.foldable())
// true
```

```kotlin
isNotBlank(None, Option.foldable())
// true
```

```kotlin
isNotBlank(strList, ListK.foldable())
// true
```

### IsEmpty
Returns true if there are no elements. Otherwise false.

```kotlin
fun <F> isFoldableEmpty(strKind: Kind<F, String>, FO: Foldable<F>): Boolean =
  FO.run {
    strKind.isEmpty()
  }
```

```kotlin
isFoldableEmpty(maybeStr, Option.foldable())
// false
```

```kotlin
isFoldableEmpty(None, Option.foldable())
// true
```

```kotlin
isFoldableEmpty(strList, ListK.foldable())
// false
```

### NonEmpty
Returns true if there is at least one element. Otherwise false.

```kotlin
fun <F> foldableNonEmpty(strKind: Kind<F, String>, FO: Foldable<F>): Boolean =
  FO.run {
    strKind.nonEmpty()
  }
```

```kotlin
foldableNonEmpty(maybeStr, Option.foldable())
// true
```

```kotlin
foldableNonEmpty(None, Option.foldable())
// false
```

```kotlin
foldableNonEmpty(strList, ListK.foldable())
// true
```

### Size
The size of this `Foldable`.

Note: will not terminate for infinite-sized collections.

```kotlin
fun <F> foldableSize(strKind: Kind<F, String>, FO: Foldable<F>): Long =
  FO.run {
    strKind.size(Long.monoid())
  }
```

```kotlin
foldableSize(maybeStr, Option.foldable())
// 1
```

```kotlin
foldableSize(None, Option.foldable())
// 0
```

```kotlin
foldableSize(strList, ListK.foldable())
// 3
```

### FoldMapM
Monadic folding on `F` by mapping `A` values to `Kind<G, B>`, combining the `B` values using the given `Monoid<B>` instance.

Similar to `foldM`, but using a `Monoid<B>`.

```kotlin
import arrow.instances.option.monad.monad

fun <F> getLengthWithMonoid(strKind: Kind<F, String>, FO: Foldable<F>): Option<Int> =
  FO.run {
       strKind.foldMapM(Option.monad(), Int.monoid()) { Some(it.length) }
  }.fix()
```

```kotlin
getLengthWithMonoid(maybeStr, Option.foldable())
// Some(3)
```

```kotlin
getLengthWithMonoid(None, Option.foldable())
// Some(0)
```

```kotlin
getLengthWithMonoid(strList, ListK.foldable())
// Some(3)
```

### FoldM
Left associative monadic folding on `F`.

The default implementation of this is based on `foldL`, and thus will always fold across the entire structure.
Certain structures are able to implement this in such a way that folds can be short-circuited (not traverse the
entirety of the structure), depending on the `G` result produced at a given step.

```kotlin
import arrow.instances.either.monad.monad

fun <F> maybeConcatenateString(strKind: Kind<F, String>, FO: Foldable<F>): Either<String,String> =
  FO.run {
    strKind.foldM(
      Either.monad<String>(),
      "str: "
    ) { base: String, value: String -> Right(base + value) }
  }.fix()
```

```kotlin
maybeConcatenateString(maybeStr, Option.foldable())
// Right(b=str: abc)
```

```kotlin
maybeConcatenateString(None, Option.foldable())
// Right(b=str: )
```

```kotlin
maybeConcatenateString(strList, ListK.foldable())
// Right(b=str: abc)
```

### Get
Get the element at the index of the Foldable.

```kotlin
import arrow.instances.either.monad.monad
import arrow.instances.either.foldable.foldable

fun foldableGet(strKind: EitherOf<String, String>): Option<String> =
  with(Either.foldable<String>()) {
    strKind.get(Either.monad(), 0)
  }

val rightStr = Either.right("abc") as Either<String, String>

foldableGet(rightStr)
// Some(abc)
```

### Data types

| Module | Data types |
|__arrow.aql__|[Box]({{ '/docs/arrow/aql/box' | relative_url }})|
|__arrow.core__|[Either]({{ '/docs/arrow/core/either' | relative_url }}), [Id]({{ '/docs/arrow/core/id' | relative_url }}), [Option]({{ '/docs/arrow/core/option' | relative_url }}), [Try]({{ '/docs/arrow/core/try' | relative_url }}), [Tuple2]({{ '/docs/arrow/core/tuple2' | relative_url }})|
|__arrow.data__|[Coproduct]({{ '/docs/arrow/data/coproduct' | relative_url }}), [Ior]({{ '/docs/arrow/data/ior' | relative_url }}), [ListK]({{ '/docs/arrow/data/listk' | relative_url }}), [MapK]({{ '/docs/arrow/data/mapk' | relative_url }}), [NonEmptyList]({{ '/docs/arrow/data/nonemptylist' | relative_url }}), [OptionT]({{ '/docs/arrow/data/optiont' | relative_url }}), [SequenceK]({{ '/docs/arrow/data/sequencek' | relative_url }}), [SetK]({{ '/docs/arrow/data/setk' | relative_url }}), [Validated]({{ '/docs/arrow/data/validated' | relative_url }})|
|__arrow.effects__|[FlowableK]({{ '/docs/arrow/effects/flowablek' | relative_url }}), [FluxK]({{ '/docs/arrow/effects/fluxk' | relative_url }}), [MaybeK]({{ '/docs/arrow/effects/maybek' | relative_url }}), [ObservableK]({{ '/docs/arrow/effects/observablek' | relative_url }})|
|__arrow.typeclasses__|[Const]({{ '/docs/arrow/typeclasses/const' | relative_url }})|