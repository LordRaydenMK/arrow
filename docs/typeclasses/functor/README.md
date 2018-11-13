---
layout: docs
title: Functor
permalink: /docs/typeclasses/functor/
video: EUqg3fSahhk
---

## Functor

{:.beginner}
beginner

The `Functor` typeclass abstracts the ability to `map` over the computational context of a type constructor.
Examples of type constructors that can implement instances of the Functor typeclass include `Option`, `NonEmptyList`,
`List` and many other datatypes that include a `map` function with the shape `fun F<A>.map(f: (A) -> B): F<B>` where `F`
refers to `Option`, `List` or any other type constructor whose contents can be transformed.

### Example

Oftentimes we find ourselves in situations where we need to transform the contents of some datatype. `Functor#map` allows
us to safely compute over values under the assumption that they'll be there returning the transformation encapsulated in the same context.

Consider both `Option` and `Try`:

`Option<A>` allows us to model absence and has two possible states, `Some(a: A)` if the value is not absent and `None` to represent an empty case.

In a similar fashion `Try<A>` may have two possible cases `Success(a: A)` for computations that succeed and `Failure(e: Throwable)` if they fail with an exception.

Both `Try` and `Option` are example datatypes that can be computed over transforming their inner results.

```kotlin
import arrow.*
import arrow.core.*
import arrow.data.*

Try { "1".toInt() }.map { it * 2 }
Option(1).map { it * 2 }
// Some(2)
```

Both `Try` and `Option` include ready to use `Functor` instances:

```kotlin
import arrow.instances.option.functor.*

val optionFunctor = Option.functor()
```

```kotlin
import arrow.instances.`try`.functor.*

val tryFunctor = Try.functor()
```

Mapping over the empty/failed cases is always safe since the `map` operation in both Try and Option operate under the bias of those containing success values

```kotlin

Try { "x".toInt() }.map { it * 2 }
none<Int>().map { it * 2 }
// None
```

### Main Combinators

#### Kind<F, A>#map

Transforms the inner contents

`fun <A, B> Kind<F, A>.map(f: (A) -> B): Kind<F, B>`

```kotlin
optionFunctor.run { Option(1).map { it + 1 } }
// Some(2)
```

#### lift

Lift a function to the Functor context so it can be applied over values of the implementing datatype

`fun <A, B> lift(f: (A) -> B): (Kind<F, A>) -> Kind<F, B>`

```kotlin
val lifted = optionFunctor.lift({ n: Int -> n + 1 })
lifted(Option(1))
// Some(2)
```

#### Other combinators

For a full list of other useful combinators available in `Functor` see the [Source][functor_source]{:target="_blank"}

### Laws

Arrow provides [`FunctorLaws`][functor_laws_source]{:target="_blank"} in the form of test cases for internal verification of lawful instances and third party apps creating their own Functor instances.

#### Creating your own `Functor` instances

Arrow already provides Functor instances for most common datatypes both in Arrow and the Kotlin stdlib.
Oftentimes you may find the need to provide your own for unsupported datatypes.

You may create or automatically derive instances of functor for your own datatypes which you will be able to use in the context of abstract polymorphic code
as demonstrated in the [example](#example) above.

See [Deriving and creating custom typeclass]({{ '/docs/patterns/glossary' | relative_url }})

### Data types

| Module | Data types |
|__arrow.aql__|[Box]({{ '/docs/arrow/aql/box' | relative_url }})|
|__arrow.core__|[Either]({{ '/docs/arrow/core/either' | relative_url }}), [Eval]({{ '/docs/arrow/core/eval' | relative_url }}), [Function0]({{ '/docs/arrow/core/function0' | relative_url }}), [Function1]({{ '/docs/arrow/core/function1' | relative_url }}), [Id]({{ '/docs/arrow/core/id' | relative_url }}), [Option]({{ '/docs/arrow/core/option' | relative_url }}), [Try]({{ '/docs/arrow/core/try' | relative_url }}), [Tuple2]({{ '/docs/arrow/core/tuple2' | relative_url }})|
|__arrow.data__|[Coproduct]({{ '/docs/arrow/data/coproduct' | relative_url }}), [Day]({{ '/docs/arrow/data/day' | relative_url }}), [Sum]({{ '/docs/arrow/data/sum' | relative_url }}), [Ior]({{ '/docs/arrow/data/ior' | relative_url }}), [Kleisli]({{ '/docs/arrow/data/kleisli' | relative_url }}), [ListK]({{ '/docs/arrow/data/listk' | relative_url }}), [MapK]({{ '/docs/arrow/data/mapk' | relative_url }}), [Moore]({{ '/docs/arrow/data/moore' | relative_url }}), [NonEmptyList]({{ '/docs/arrow/data/nonemptylist' | relative_url }}), [OptionT]({{ '/docs/arrow/data/optiont' | relative_url }}), [SequenceK]({{ '/docs/arrow/data/sequencek' | relative_url }}), [StateT]({{ '/docs/arrow/data/statet' | relative_url }}), [Store]({{ '/docs/arrow/data/store' | relative_url }}), [Validated]({{ '/docs/arrow/data/validated' | relative_url }}), [WriterT]({{ '/docs/arrow/data/writert' | relative_url }})|
|__arrow.effects__|[DeferredK]({{ '/docs/arrow/effects/deferredk' | relative_url }}), [FlowableK]({{ '/docs/arrow/effects/flowablek' | relative_url }}), [FluxK]({{ '/docs/arrow/effects/fluxk' | relative_url }}), [MaybeK]({{ '/docs/arrow/effects/maybek' | relative_url }}), [MonoK]({{ '/docs/arrow/effects/monok' | relative_url }}), [ObservableK]({{ '/docs/arrow/effects/observablek' | relative_url }}), [SingleK]({{ '/docs/arrow/effects/singlek' | relative_url }}), [IO]({{ '/docs/arrow/effects/io' | relative_url }})|
|__arrow.free__|[Cofree]({{ '/docs/arrow/free/cofree' | relative_url }}), [Coyoneda]({{ '/docs/arrow/free/coyoneda' | relative_url }}), [FreeApplicative]({{ '/docs/arrow/free/freeapplicative' | relative_url }}), [Free]({{ '/docs/arrow/free/free' | relative_url }}), [Yoneda]({{ '/docs/arrow/free/yoneda' | relative_url }})|
|__arrow.typeclasses__|[Const]({{ '/docs/arrow/typeclasses/const' | relative_url }})|

Additionally all instances of [`Applicative`]({{ '/docs/typeclasses/applicative' | relative_url }}), [`Monad`]({{ '/docs/typeclasses/monad' | relative_url }}) and their MTL variants implement the `Functor` typeclass directly
since they are all subtypes of `Functor`

[functor_source]: https://github.com/arrow-kt/arrow/blob/master/modules/core/arrow-typeclasses/src/main/kotlin/arrow/typeclasses/Functor.kt
[functor_laws_source]: https://github.com/arrow-kt/arrow/blob/master/modules/core/arrow-test/src/main/kotlin/arrow/test/laws/FunctorLaws.kt
