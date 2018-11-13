---
layout: docs
title: Effect
permalink: /docs/effects/effect/
---

## Effect

{:.intermediate}
intermediate

<blockquote class="twitter-tweet" data-lang="en"><p lang="en" dir="ltr">But can I use callback? I love callbacks. Christmas tree style. with the lights and all....</p>&mdash; Hadi Hariri (@hhariri) <a href="https://twitter.com/hhariri/status/986652337543491586?ref_src=twsrc%5Etfw">April 18, 2018</a></blockquote>
<script async src="https://platform.twitter.com/widgets.js" charset="utf-8"></script>

If you want to use callbacks or running suspended datatypes, then `Effect` is the typeclass to use. It contains a single function `runAsync` that takes a callback and returns a new instance of the datatype. The operation will not yield a result immediately; to start running the suspended computation you have to evaluate that new instance using its own start operator: `unsafeRunAsync` or `unsafeRunSync` for `IO`, `subscribe` or `blocking` for `Observable`, and `await` or `runBlocking` for `Deferred`.

TODO. Meanwhile you can find a short description in the [intro to typeclasses]({{ '/docs/typeclasses/intro/' | relative_url }}).

### Data types

| Module | Data types |
|__arrow.effects__|[DeferredK]({{ '/docs/arrow/effects/deferredk' | relative_url }}), [FlowableK]({{ '/docs/arrow/effects/flowablek' | relative_url }}), [FluxK]({{ '/docs/arrow/effects/fluxk' | relative_url }}), [MaybeK]({{ '/docs/arrow/effects/maybek' | relative_url }}), [MonoK]({{ '/docs/arrow/effects/monok' | relative_url }}), [ObservableK]({{ '/docs/arrow/effects/observablek' | relative_url }}), [SingleK]({{ '/docs/arrow/effects/singlek' | relative_url }}), [IO]({{ '/docs/arrow/effects/io' | relative_url }})|
