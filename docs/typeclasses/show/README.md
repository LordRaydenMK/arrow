---
layout: docs
title: Show
permalink: /docs/typeclasses/show/
---

## Show

{:.beginner}
beginner

The `Show` typeclass abstracts the ability to obtain a `String` representation of any object.

It can be considered the typeclass equivalent of Java's `Object#toString`.

```kotlin
import arrow.*
import arrow.instances.*

ForInt extensions { 1.show() }
// 1
```

### Main Combinators

#### F#show

Given an instance of `F` it returns the `String` representation of this instance.

`fun F.show(): String`

### Laws

Arrow provides `ShowLaws` in the form of test cases for internal verification of lawful instances and third party apps creating their own `Show` instances.

#### Creating your own `Show` instances

Show provides one special instance that can be potentially applicable to most datatypes.
It uses kotlin's `toString` method to get an object's literal representation.
This will work well in many cases, specially for data classes.

```kotlin
import arrow.core.*
import arrow.typeclasses.*

// Option is a data class with a single value
Show.any().run { Option.just(1).show() }
// Some(1)
```

```kotlin
// using invoke constructor
class Person(val firstName: String, val lastName: String)
val personShow = Show<Person> { "Hello $firstName $lastName" }
```

See [Deriving and creating custom typeclass]({{ '/docs/patterns/glossary' | relative_url }}) to provide your own `Show` instances for custom datatypes.


### Data types

| Module | Data types |
|__arrow.core__|[Either]({{ '/docs/arrow/core/either' | relative_url }}), [Id]({{ '/docs/arrow/core/id' | relative_url }}), [Option]({{ '/docs/arrow/core/option' | relative_url }}), [Try]({{ '/docs/arrow/core/try' | relative_url }}), [Tuple10]({{ '/docs/arrow/core/tuple10' | relative_url }}), [Tuple2]({{ '/docs/arrow/core/tuple2' | relative_url }}), [Tuple3]({{ '/docs/arrow/core/tuple3' | relative_url }}), [Tuple4]({{ '/docs/arrow/core/tuple4' | relative_url }}), [Tuple5]({{ '/docs/arrow/core/tuple5' | relative_url }}), [Tuple6]({{ '/docs/arrow/core/tuple6' | relative_url }}), [Tuple7]({{ '/docs/arrow/core/tuple7' | relative_url }}), [Tuple8]({{ '/docs/arrow/core/tuple8' | relative_url }}), [Tuple9]({{ '/docs/arrow/core/tuple9' | relative_url }})|
|__arrow.data__|[Ior]({{ '/docs/arrow/data/ior' | relative_url }}), [ListK]({{ '/docs/arrow/data/listk' | relative_url }}), [MapK]({{ '/docs/arrow/data/mapk' | relative_url }}), [NonEmptyList]({{ '/docs/arrow/data/nonemptylist' | relative_url }}), [SequenceK]({{ '/docs/arrow/data/sequencek' | relative_url }}), [SetK]({{ '/docs/arrow/data/setk' | relative_url }}), [Validated]({{ '/docs/arrow/data/validated' | relative_url }})|
|__arrow.typeclasses__|[Const]({{ '/docs/arrow/typeclasses/const' | relative_url }})|
