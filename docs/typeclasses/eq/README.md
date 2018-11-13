---
layout: docs
title: Eq
permalink: /docs/typeclasses/eq/
---

## Eq

{:.beginner}
beginner

The `Eq` typeclass abstracts the ability to compare two instances of any object.
It can be considered the typeclass equivalent of Java's `Object#equals`.

Depending on your needs this comparison can be structural -the content of the object-, referential -the memory address of the object-, based on an identity -like an Id fields-, or any combination of the above.

```kotlin
import arrow.instances.*

// Enable the extension functions inside Eq using run
String.eq().run {
  "1".eqv("2")
}
// false
```

### Main Combinators

#### F.eqv

Compares two instances of `F` and returns true if they're considered equal for this instance.
It is the opposite comparison of `neqv`.

`fun F.eqv(b: F): Boolean`


```kotlin
Int.eq().run { 1.eqv(2) }
// false
```

#### neqv

Compares two instances of `F` and returns true if they're not considered equal for this instance.
It is the opposite comparison of `eqv`.

`fun neqv(a: F, b: F): Boolean`

```kotlin
Int.eq().run { 1.neqv(2) }
// true
```

### Laws

Arrow provides `EqLaws` in the form of test cases for internal verification of lawful instances and third party apps creating their own `Eq` instances.

#### Creating your own `Eq` instances

Eq provides one special instance that can be potentially applicable to most datatypes.
It uses kotlin's == comparison to compare any two instances.
Note that this instance will fail on many all datatypes that contain a property or field that doesn't implement structural equality, i.e. functions, typeclasses, non-data classes

```kotlin
import arrow.core.*
import arrow.typeclasses.*

// Option is a data class with a single value
Eq.any().run { Some(1).eqv(Option.just(1)) }
// true
```

```kotlin
// Fails because the wrapped function is not evaluated for comparison
Eq.any().run { Eval.later { 1 }.eqv(Eval.later { 1 }) }
// false
```

```kotlin
// using invoke constructor
val intEq = Eq<Int> { a, b -> a == b }
```

See [Deriving and creating custom typeclass]({{ '/docs/patterns/glossary' | relative_url }}) to provide your own `Eq` instances for custom datatypes.

### Data types

| Module | Data types |
|__arrow.core__|[Either]({{ '/docs/arrow/core/either' | relative_url }}), [Id]({{ '/docs/arrow/core/id' | relative_url }}), [Option]({{ '/docs/arrow/core/option' | relative_url }}), [Try]({{ '/docs/arrow/core/try' | relative_url }}), [Tuple10]({{ '/docs/arrow/core/tuple10' | relative_url }}), [Tuple2]({{ '/docs/arrow/core/tuple2' | relative_url }}), [Tuple3]({{ '/docs/arrow/core/tuple3' | relative_url }}), [Tuple4]({{ '/docs/arrow/core/tuple4' | relative_url }}), [Tuple5]({{ '/docs/arrow/core/tuple5' | relative_url }}), [Tuple6]({{ '/docs/arrow/core/tuple6' | relative_url }}), [Tuple7]({{ '/docs/arrow/core/tuple7' | relative_url }}), [Tuple8]({{ '/docs/arrow/core/tuple8' | relative_url }}), [Tuple9]({{ '/docs/arrow/core/tuple9' | relative_url }})|
|__arrow.data__|[Ior]({{ '/docs/arrow/data/ior' | relative_url }}), [ListK]({{ '/docs/arrow/data/listk' | relative_url }}), [MapK]({{ '/docs/arrow/data/mapk' | relative_url }}), [NonEmptyList]({{ '/docs/arrow/data/nonemptylist' | relative_url }}), [SequenceK]({{ '/docs/arrow/data/sequencek' | relative_url }}), [SetK]({{ '/docs/arrow/data/setk' | relative_url }}), [Validated]({{ '/docs/arrow/data/validated' | relative_url }})|
|__arrow.free__|[FreeApplicative]({{ '/docs/arrow/free/freeapplicative' | relative_url }}), [Free]({{ '/docs/arrow/free/free' | relative_url }})|
|__arrow.typeclasses__|[Const]({{ '/docs/arrow/typeclasses/const' | relative_url }})|

Additionally all instances of [`Order`]({{ '/docs/typeclasses/order' | relative_url }}) and their MTL variants implement the `Eq` typeclass directly since they are all subtypes of `Eq`
