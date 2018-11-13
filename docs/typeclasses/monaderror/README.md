---
layout: docs
title: MonadError
permalink: /docs/typeclasses/monaderror/
---

## MonadError

{:.intermediate}
intermediate

MonadError is the typeclass used to explicitly represent errors during sequential execution.
It is parametrized to an error type `E`, which means the datatype has at least a "success" and a "failure" version.
These errors can come in the form of `Throwable`, `Exception`, or any other type hierarchy of the user's choice.

`MonadError` extends from [`ApplicativeError`]({{ '/docs/typeclasses/applicativeerror' | relative_url }}), which is already used to represent errors in independent computations. This way all the methods [`ApplicativeError`]({{ '/docs/typeclasses/applicativeerror' | relative_url }}) provides to handle recovery from errors are also available in `MonadError`.

### Main Combinators

`MonadError` inherits all the combinators available in [`ApplicativeError`]({{ '/docs/typeclasses/applicativeerror' | relative_url }}) and [`Monad`]({{ '/docs/typeclasses/monad' | relative_url }}). It also adds one of its own.

#### raiseError

Inherited from [`ApplicativeError`]({{ '/docs/typeclasses/applicativeerror' | relative_url }}). A constructor function.
It lifts an exception into the computational context of a type constructor.

```kotlin
import arrow.*
import arrow.core.*
import arrow.instances.*
import arrow.instances.either.applicativeError.*

val eitherResult: Either<Throwable, Int> = 
  RuntimeException("BOOM!").raiseError()

eitherResult
// Left(a=java.lang.RuntimeException: BOOM!)
```

```kotlin
import arrow.data.*
import arrow.instances.`try`.applicativeError.*

val tryResult: Try<Int> = 
  RuntimeException("BOOM!").raiseError()

tryResult
// Failure(exception=java.lang.RuntimeException: BOOM!)
```

```kotlin
import arrow.effects.*
import arrow.effects.instances.io.applicativeError.*

val ioResult: IO<Int> = 
  RuntimeException("BOOM!").raiseError()
  
ioResult.attempt().unsafeRunSync()
// Left(a=java.lang.RuntimeException: BOOM!)
```

#### Kind<F, A>.ensure

Tests a predicate against the object, and if it fails it executes a function to create an error.

```kotlin
import arrow.instances.either.monadError.*

Either.Right(1).ensure({ RuntimeException("Failed predicate") }, { it > 0 }) 
// Right(b=1)
```

```kotlin
Either.Right(1).ensure({ RuntimeException("Failed predicate") }, { it < 0 }) 
// Left(a=java.lang.RuntimeException: Failed predicate)
```

### Comprehensions

#### bindingCatch

It starts a [Monad Comprehension]({{ '/docs/patterns/monad_comprehensions' | relative_url }}) that wraps any exception thrown in the block inside `raiseError()`.

### Laws

Arrow provides `MonadErrorLaws` in the form of test cases for internal verification of lawful instances and third party apps creating their own `MonadError` instances.

### Data types

| Module | Data types |
|__arrow.core__|[Either]({{ '/docs/arrow/core/either' | relative_url }}), [Option]({{ '/docs/arrow/core/option' | relative_url }}), [Try]({{ '/docs/arrow/core/try' | relative_url }})|
|__arrow.data__|[Kleisli]({{ '/docs/arrow/data/kleisli' | relative_url }}), [StateT]({{ '/docs/arrow/data/statet' | relative_url }})|
|__arrow.effects__|[DeferredK]({{ '/docs/arrow/effects/deferredk' | relative_url }}), [FlowableK]({{ '/docs/arrow/effects/flowablek' | relative_url }}), [FluxK]({{ '/docs/arrow/effects/fluxk' | relative_url }}), [MaybeK]({{ '/docs/arrow/effects/maybek' | relative_url }}), [MonoK]({{ '/docs/arrow/effects/monok' | relative_url }}), [ObservableK]({{ '/docs/arrow/effects/observablek' | relative_url }}), [SingleK]({{ '/docs/arrow/effects/singlek' | relative_url }}), [IO]({{ '/docs/arrow/effects/io' | relative_url }})|
