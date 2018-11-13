---
layout: docs
title: Id
permalink: /docs/datatypes/id/
video: DBvVd1pfLMo
---

## Id

{:.beginner}
beginner

The identity monad can be seen as the ambient monad that encodes the effect of having no effect.
It is ambient in the sense that plain pure values are values of `Id`.

```kotlin
import arrow.*
import arrow.core.*

Id("hello")
// Id(value=hello)
```

Using this type declaration, we can treat our Id type constructor as a `Monad` and as a `Comonad`.
The `just` method, which has type `A -> Id<A>` just becomes the identity function. The `map` method
from `Functor` just becomes function application

```kotlin
val id: Id<Int> = Id.just(3)
id.map{it + 3}
// Id(value=6)
```

### Supported type classes

| Module | Type classes |
|__arrow.aql__|[From]({{ '/docs/arrow/aql/from' | relative_url }}), [Select]({{ '/docs/arrow/aql/select' | relative_url }})|
|__arrow.typeclasses__|[Applicative]({{ '/docs/arrow/typeclasses/applicative' | relative_url }}), [Bimonad]({{ '/docs/arrow/typeclasses/bimonad' | relative_url }}), [Comonad]({{ '/docs/arrow/typeclasses/comonad' | relative_url }}), [Eq]({{ '/docs/arrow/typeclasses/eq' | relative_url }}), [Foldable]({{ '/docs/arrow/typeclasses/foldable' | relative_url }}), [Functor]({{ '/docs/arrow/typeclasses/functor' | relative_url }}), [Monad]({{ '/docs/arrow/typeclasses/monad' | relative_url }}), [Show]({{ '/docs/arrow/typeclasses/show' | relative_url }}), [Traverse]({{ '/docs/arrow/typeclasses/traverse' | relative_url }})|
