---
layout: docs
title: Store
permalink: /docs/datatypes/store/
---

## Store

{:.intermediate}

intermediate

`Store` is a data structure that holds a state and a function for extracting a representation of it.

If we think in a component oriented fashion when building user interfaces, this datatype is the most basic unit. 

This structure is also a [`Comonad`]({{ '/docs/typeclasses/comonad' | relative_url }}) because it represents a lazy unfolding of all possible states of our user interface.

```kotlin
import arrow.data.*

val store = Store(0) { "The current value is: $it" }
store.extract() 
// The current value is: 0
```

If we want to change the initial state of the store we have a `move` method:

```kotlin
val newStore = store.move(store.state + 1)
newStore.extract()
// The current value is: 1
```

We also have two methods from `Comonad`:

* `extract` for rendering the current state.

* `coflatMap` for replacing the representation type in each future state.

```kotlin
import arrow.core.*

val tupleStore = store.coflatMap { it: Store<Int, String> -> Tuple2("State", it.state) }
tupleStore.extract()
// Tuple2(a=State, b=0)
```

And as a `Comonad` is also a `Functor` we have `map` which allows us to transform the state representation:

```kotlin
val upperCaseStore = store.map { it: String -> it.toUpperCase() } 
upperCaseStore.extract()
// THE CURRENT VALUE IS: 0
```

### Supported type classes

| Module | Type classes |
|__arrow.typeclasses__|[Comonad]({{ '/docs/arrow/typeclasses/comonad' | relative_url }}), [Functor]({{ '/docs/arrow/typeclasses/functor' | relative_url }})|


