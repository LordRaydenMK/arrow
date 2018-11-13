---
layout: docs
title: sum
permalink: /docs/aql/sum/
---

{:.beginner}
beginner

## sum

`sum` adds up all the values of a selected numeric property and returns the total sum as a `Long`

`sum` over `List`
{:data-executable='true'}
```kotlin
import arrow.aql.instances.list.select.*
import arrow.aql.instances.list.where.*
import arrow.aql.instances.list.sum.*
import arrow.aql.instances.listk.select.selectAll
import arrow.aql.instances.id.select.value

data class Student(val name: String, val age: Int)

val john = Student("John", 30)
val jane = Student("Jane", 32)
val jack = Student("Jack", 32)

fun main(args: Array<String>) {
//sampleStart
val result = 
  listOf(john, jane, jack).query {
    selectAll() where { age > 30 } sum { age.toLong() }
  }.value()
//sampleEnd
println(result)
}
```

{:.intermediate}
intermediate

`sum` works with any data type that provides an instance of `Foldable<F>` where `F` is the higher kinded representation of the data type. For example `ForOption` when targeting the `Option<A>` data type or `ForListK` when targeting the `List<A>` data type

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
|__arrow.core__|[Either]({{ '/docs/arrow/core/either' | relative_url }}), [Option]({{ '/docs/arrow/core/option' | relative_url }}), [Try]({{ '/docs/arrow/core/try' | relative_url }})|
|__arrow.data__|[ListK]({{ '/docs/arrow/data/listk' | relative_url }}), [NonEmptyList]({{ '/docs/arrow/data/nonemptylist' | relative_url }}), [SequenceK]({{ '/docs/arrow/data/sequencek' | relative_url }})|

{:.advanced}
advanced

[Adapt AQL to your own _custom data types_](/docs/aql/custom/)