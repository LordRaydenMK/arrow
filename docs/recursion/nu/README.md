---
layout: docs
title: Nu
permalink: /docs/recursion/nu/
---

## Nu

{:.advanced}
advanced

The Nu datatype is the simplest way to model corecursion via a direct encoding of the
ana function.

```kotlin
@higherkind
class Nu<out F>(val a: Any?, val unNu: Coalgebra<F, Any?>) : NuOf<F>
```

Nu's constructor is isomorphic to ana.

### Comparison to Fix and Nu

Nu warns that a datatype can be be infinite, and should be used for any algorithms
that can work on infinite data (for example `map`). Due to it being a direct encoding
of ana, Nu also has a very fast Corecursive instance.

### Supported Type Classes

| Module | Type classes |
|__arrow.recursion.typeclasses__|[Birecursive]({{ '/docs/arrow/recursion/typeclasses/birecursive' | relative_url }}), [Corecursive]({{ '/docs/arrow/recursion/typeclasses/corecursive' | relative_url }}), [Recursive]({{ '/docs/arrow/recursion/typeclasses/recursive' | relative_url }})|
