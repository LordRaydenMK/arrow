---
layout: docs
title: Each
permalink: /docs/optics/each/
---

## Each

{:.beginner}
beginner

`Each` provides a [`Traversal`]({{ '/docs/optics/traversal/' | relative_url }}) that can focus into a structure `S` to see all its foci `A`.

### Example

`Each` can easily be created given a `Traverse` instance.

```kotlin
import arrow.data.*
import arrow.optics.*
import arrow.optics.typeclasses.*
import arrow.instances.listk.traverse.*

val each: Each<ListKOf<Int>, Int> = Each.fromTraverse(ListK.traverse())

val listTraversal: Traversal<ListKOf<Int>, Int> = each.each()

listTraversal.lastOption(listOf(1, 2, 3).k())
// Some(3)
```
```kotlin
listTraversal.lastOption(ListK.empty())
// None
```

#### Creating your own `Each` instances

Arrow provides `Each` instances for some common datatypes in Arrow. You can look them up by calling `Each.each()`.

You may create instances of `Each` for your own datatypes which you will be able to use as demonstrated in the [example](#example) above.

See [Deriving and creating custom typeclass]({{ '/docs/patterns/glossary' | relative_url }}) to provide your own `Each` instances for custom datatypes.

### Data types

| Module | Data types |
|__arrow.core__|[Either]({{ '/docs/arrow/core/either' | relative_url }}), [Option]({{ '/docs/arrow/core/option' | relative_url }}), [Try]({{ '/docs/arrow/core/try' | relative_url }})|
|__arrow.data__|[ListK]({{ '/docs/arrow/data/listk' | relative_url }}), [MapK]({{ '/docs/arrow/data/mapk' | relative_url }}), [NonEmptyList]({{ '/docs/arrow/data/nonemptylist' | relative_url }}), [SequenceK]({{ '/docs/arrow/data/sequencek' | relative_url }})|
