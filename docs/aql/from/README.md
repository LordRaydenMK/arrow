---
layout: docs
title: from
permalink: /docs/aql/from/
---

## from

{:.beginner}
beginner

AQL does not require of a `from` clause to operate because the data source with the shape `Kind<F, A>` is already used as the initial point to compose a query.

The example below shows what a classic SQL statement looks like in AQL.

__SQL__
```roomsql
select * from list
```

__AQL__

{: data-executable='true'}
```kotlin
import arrow.aql.instances.list.select.*
import arrow.aql.instances.listk.select.select
fun main(args: Array<String>) {
//sampleStart
val result: List<Int> =
  listOf(1, 2, 3).query { // `listOf(1, 2, 3)` is what the source of data and what we use as `from`
    select { this }
  }.value()
//sampleEnd
println(result)
}
```

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
|__arrow.core__|[Either]({{ '/docs/arrow/core/either' | relative_url }}), [Eval]({{ '/docs/arrow/core/eval' | relative_url }}), [Function0]({{ '/docs/arrow/core/function0' | relative_url }}), [Id]({{ '/docs/arrow/core/id' | relative_url }}), [Option]({{ '/docs/arrow/core/option' | relative_url }}), [Try]({{ '/docs/arrow/core/try' | relative_url }})|
|__arrow.data__|[ListK]({{ '/docs/arrow/data/listk' | relative_url }}), [NonEmptyList]({{ '/docs/arrow/data/nonemptylist' | relative_url }}), [SequenceK]({{ '/docs/arrow/data/sequencek' | relative_url }})|

{:.advanced}
advanced

[Adapt AQL to your own _custom data types_](/docs/aql/custom/)
