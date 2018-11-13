---
layout: docs
title: select
permalink: /docs/aql/select/
---

{:.beginner}
beginner

## select

`select` allows obtaining and transforming data from any data source containing `A` given a function `(A) -> B` where `A` denotes the input type and `B` the transformed type.

`select` over `List`

{: data-executable='true'}
```kotlin
import arrow.aql.instances.list.select.*
import arrow.aql.instances.listk.select.select
fun main(args: Array<String>) {
//sampleStart
val result: List<Int> =
  listOf(1, 2, 3).query {
    select { this + 1 }
  }.value()
//sampleEnd
println(result)
}
```

`select` over `Option`

{: data-executable='true'}
```kotlin
import arrow.core.Option
import arrow.aql.instances.option.select.*

fun main(args: Array<String>) {
//sampleStart
val result: Option<Int> =
  Option(1).query {
    select { this * 10 }
  }.value()
//sampleEnd
println(result)
}
```

`select` over `Sequence`

{: data-executable='true'}
```kotlin
import arrow.aql.instances.sequence.select.*
import arrow.aql.instances.sequencek.select.select

fun main(args: Array<String>) {
//sampleStart
val result: List<Int> =
  sequenceOf(1, 2, 3, 4).query {
    select { this * 10 }
  }.value().toList()
//sampleEnd
  println(result)
}
```

{:.intermediate}
intermediate

`select` works with any data type that provides an instance of `Functor<F>` where `F` is the higher kinded representation of the data type. For example `ForOption` when targeting the `Option<A>` data type or `ForListK` when targeting the `List<A>` data type

Learn more about the `AQL` combinators

- [_select_](/docs/aql/select/)
- [_from_](/docs/aql/from/)
- [_where_](/docs/aql/where/)
- [_groupBy_](/docs/aql/groupby/)
- [_orderBy_](/docs/aql/orderby/)
- [_sum_](/docs/aql/sum/)
- [_union_](/docs/aql/union/)

### Supported Data types

| Module | Data types |
|__arrow.aql__|[Box]({{ '/docs/arrow/aql/box' | relative_url }})|
|__arrow.core__|[Either]({{ '/docs/arrow/core/either' | relative_url }}), [Eval]({{ '/docs/arrow/core/eval' | relative_url }}), [Function0]({{ '/docs/arrow/core/function0' | relative_url }}), [Id]({{ '/docs/arrow/core/id' | relative_url }}), [Option]({{ '/docs/arrow/core/option' | relative_url }}), [Try]({{ '/docs/arrow/core/try' | relative_url }})|
|__arrow.data__|[ListK]({{ '/docs/arrow/data/listk' | relative_url }}), [NonEmptyList]({{ '/docs/arrow/data/nonemptylist' | relative_url }}), [SequenceK]({{ '/docs/arrow/data/sequencek' | relative_url }})|

{:.advanced}
advanced

[Adapt AQL to your own _custom data types_](/docs/aql/custom/)
