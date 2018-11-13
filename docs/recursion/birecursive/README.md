---
layout: docs
title: Birecursive
permalink: /docs/recursion/birecursive/
---

## Birecursive

{:.advanced}
advanced

A datatype that's both `Recursive` and `Corecursive`, which enables applying both `fold` and `unfold`
operations to it.

### Data types

Arrow provides three datatypes that are instances of `Birecursive`, each modeling a
different way of defining birecursion.

| Module | Data types |
|__arrow.recursion.data__|[Fix]({{ '/docs/arrow/recursion/data/fix' | relative_url }}), [Mu]({{ '/docs/arrow/recursion/data/mu' | relative_url }}), [Nu]({{ '/docs/arrow/recursion/data/nu' | relative_url }})|
