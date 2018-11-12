package arrow.instances

import arrow.Kind
import arrow.core.Either
import arrow.core.Eval
import arrow.core.Tuple2
import arrow.data.*
import arrow.deprecation.ExtensionsDSLDeprecated
import arrow.extension
import arrow.typeclasses.*
import arrow.data.combineK as sequenceCombineK

@extension
interface SequenceKSemigroupInstance<A> : Semigroup<SequenceK<A>> {
  override fun SequenceK<A>.combine(b: SequenceK<A>): SequenceK<A> = (this.sequence + b.sequence).k()
}

@extension
interface SequenceKMonoidInstance<A> : Monoid<SequenceK<A>> {
  override fun SequenceK<A>.combine(b: SequenceK<A>): SequenceK<A> = (this.sequence + b.sequence).k()

  override fun empty(): SequenceK<A> = emptySequence<A>().k()
}

@extension
interface SequenceKEqInstance<A> : Eq<SequenceK<A>> {

  fun EQ(): Eq<A>

  override fun SequenceK<A>.eqv(b: SequenceK<A>): Boolean =
    zip(b) { aa, bb -> EQ().run { aa.eqv(bb) } }.fold(true) { acc, bool ->
      acc && bool
    }

}

@extension
interface SequenceKShowInstance<A> : Show<SequenceK<A>> {
  override fun SequenceK<A>.show(): String =
    toString()
}

@extension
interface SequenceKFunctorInstance : Functor<ForSequenceK> {
  override fun <A, B> Kind<ForSequenceK, A>.map(f: (A) -> B): SequenceK<B> =
    fix().map(f)
}

@extension
interface SequenceKApplicativeInstance : Applicative<ForSequenceK> {
  override fun <A, B> Kind<ForSequenceK, A>.ap(ff: Kind<ForSequenceK, (A) -> B>): SequenceK<B> =
    fix().ap(ff)

  override fun <A, B> Kind<ForSequenceK, A>.map(f: (A) -> B): SequenceK<B> =
    fix().map(f)

  override fun <A, B, Z> Kind<ForSequenceK, A>.map2(fb: Kind<ForSequenceK, B>, f: (Tuple2<A, B>) -> Z): SequenceK<Z> =
    fix().map2(fb, f)

  override fun <A> just(a: A): SequenceK<A> =
    SequenceK.just(a)
}

@extension
interface SequenceKMonadInstance : Monad<ForSequenceK> {
  override fun <A, B> Kind<ForSequenceK, A>.ap(ff: Kind<ForSequenceK, (A) -> B>): SequenceK<B> =
    fix().ap(ff)

  override fun <A, B> Kind<ForSequenceK, A>.flatMap(f: (A) -> Kind<ForSequenceK, B>): SequenceK<B> =
    fix().flatMap(f)

  override fun <A, B> tailRecM(a: A, f: kotlin.Function1<A, SequenceKOf<Either<A, B>>>): SequenceK<B> =
    SequenceK.tailRecM(a, f)

  override fun <A, B> Kind<ForSequenceK, A>.map(f: (A) -> B): SequenceK<B> =
    fix().map(f)

  override fun <A, B, Z> Kind<ForSequenceK, A>.map2(fb: Kind<ForSequenceK, B>, f: (Tuple2<A, B>) -> Z): SequenceK<Z> =
    fix().map2(fb, f)

  override fun <A> just(a: A): SequenceK<A> =
    SequenceK.just(a)
}

@extension
interface SequenceKFoldableInstance : Foldable<ForSequenceK> {
  override fun <A, B> Kind<ForSequenceK, A>.foldLeft(b: B, f: (B, A) -> B): B =
    fix().foldLeft(b, f)

  override fun <A, B> Kind<ForSequenceK, A>.foldRight(lb: Eval<B>, f: (A, Eval<B>) -> Eval<B>): Eval<B> =
    fix().foldRight(lb, f)
}

@extension
interface SequenceKTraverseInstance : Traverse<ForSequenceK> {
  override fun <A, B> Kind<ForSequenceK, A>.map(f: (A) -> B): SequenceK<B> =
    fix().map(f)

  override fun <G, A, B> Kind<ForSequenceK, A>.traverse(AP: Applicative<G>, f: (A) -> Kind<G, B>): Kind<G, SequenceK<B>> =
    fix().traverse(AP, f)

  override fun <A, B> Kind<ForSequenceK, A>.foldLeft(b: B, f: (B, A) -> B): B =
    fix().foldLeft(b, f)

  override fun <A, B> Kind<ForSequenceK, A>.foldRight(lb: Eval<B>, f: (A, Eval<B>) -> Eval<B>): Eval<B> =
    fix().foldRight(lb, f)
}

@extension
interface SequenceKSemigroupKInstance : SemigroupK<ForSequenceK> {
  override fun <A> Kind<ForSequenceK, A>.combineK(y: Kind<ForSequenceK, A>): SequenceK<A> =
    fix().sequenceCombineK(y)
}

@extension
interface SequenceKMonoidKInstance : MonoidK<ForSequenceK> {
  override fun <A> empty(): SequenceK<A> =
    SequenceK.empty()

  override fun <A> Kind<ForSequenceK, A>.combineK(y: Kind<ForSequenceK, A>): SequenceK<A> =
    fix().sequenceCombineK(y)
}

object SequenceKContext : SequenceKMonadInstance, SequenceKTraverseInstance, SequenceKMonoidKInstance {
  override fun <A, B> Kind<ForSequenceK, A>.map(f: (A) -> B): SequenceK<B> =
    fix().map(f)
}

@Deprecated(ExtensionsDSLDeprecated)
infix fun <A> ForSequenceK.Companion.extensions(f: SequenceKContext.() -> A): A =
  f(SequenceKContext)