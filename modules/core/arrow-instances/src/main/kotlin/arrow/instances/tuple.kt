package arrow.instances

import arrow.Kind
import arrow.core.*
import arrow.core.Either.Left
import arrow.core.Either.Right
import arrow.instance
import arrow.typeclasses.*

@instance(Tuple2::class)
interface Tuple2FunctorInstance<F> : Functor<Tuple2PartialOf<F>> {
    override fun <A, B> map(fa: Tuple2Of<F, A>, f: (A) -> B) =
            fa.fix().map(f)
}

@instance(Tuple2::class)
interface Tuple2ApplicativeInstance<F> : Tuple2FunctorInstance<F>, Applicative<Tuple2PartialOf<F>> {
    fun MF(): Monoid<F>

    override fun <A, B> map(fa: Tuple2Of<F, A>, f: (A) -> B) =
            fa.fix().map(f)

    override fun <A, B> ap(fa: Tuple2Of<F, A>, ff: Tuple2Of<F, (A) -> B>) =
            fa.fix().ap(ff.fix())

    override fun <A> pure(a: A) =
            MF().empty() toT a
}

@instance(Tuple2::class)
interface Tuple2MonadInstance<F> : Tuple2ApplicativeInstance<F>, Monad<Tuple2PartialOf<F>> {
    override fun <A, B> map(fa: Tuple2Of<F, A>, f: (A) -> B) =
            fa.fix().map(f)

    override fun <A, B> ap(fa: Tuple2Of<F, A>, ff: Tuple2Of<F, (A) -> B>) =
            fa.fix().ap(ff)

    override fun <A, B> flatMap(fa: Tuple2Of<F, A>, f: (A) -> Tuple2Of<F, B>) =
            fa.fix().flatMap { f(it).fix() }

    override tailrec fun <A, B> tailRecM(a: A, f: (A) -> Tuple2Of<F, Either<A, B>>): Tuple2<F, B> {
        val b = f(a).fix().b
        return when (b) {
            is Left -> tailRecM(b.a, f)
            is Right -> pure(b.b)
        }
    }
}

@instance(Tuple2::class)
interface Tuple2ComonadInstance<F> : Tuple2FunctorInstance<F>, Comonad<Tuple2PartialOf<F>> {
    override fun <A, B> coflatMap(fa: Tuple2Of<F, A>, f: (Tuple2Of<F, A>) -> B) =
            fa.fix().coflatMap(f)

    override fun <A> Kind<Tuple2PartialOf<F>, A>.extract() =
            fix().extract()
}

@instance(Tuple2::class)
interface Tuple2FoldableInstance<F> : Foldable<Tuple2PartialOf<F>> {
    override fun <A, B> foldLeft(fa: Tuple2Of<F, A>, b: B, f: (B, A) -> B) =
            fa.fix().foldL(b, f)

    override fun <A, B> foldRight(fa: Tuple2Of<F, A>, lb: Eval<B>, f: (A, Eval<B>) -> Eval<B>) =
            fa.fix().foldR(lb, f)
}

@instance(Tuple2::class)
interface Tuple2TraverseInstance<F> : Tuple2FoldableInstance<F>, Traverse<Tuple2PartialOf<F>> {
    override fun <G, A, B> Applicative<G>.traverse(fa: Kind<Tuple2PartialOf<F>, A>, f: (A) -> Kind<G, B>) =
            fa.fix().run { map(f(b), a::toT) }
}

@instance(Tuple2::class)
interface Tuple2MonoidInstance<A, B> : Monoid<Tuple2<A, B>> {

    fun MA(): Monoid<A>

    fun MB(): Monoid<B>

    override fun empty(): Tuple2<A, B> = Tuple2(MA().empty(), MB().empty())

    override fun Tuple2<A, B>.combine(b: Tuple2<A, B>): Tuple2<A, B> {
        val (xa, xb) = this
        val (ya, yb) = b
        return Tuple2(MA().run { xa.combine(ya) }, MB().run { xb.combine(yb) })
    }
}

@instance(Tuple2::class)
interface Tuple2EqInstance<A, B> : Eq<Tuple2<A, B>> {

    fun EQA(): Eq<A>

    fun EQB(): Eq<B>

    override fun Tuple2<A, B>.eqv(b: Tuple2<A, B>): Boolean =
            EQA().run { a.eqv(b.a) && EQB().run { this@eqv.b.eqv(b.b) } }
}

@instance(Tuple2::class)
interface Tuple2ShowInstance<A, B> : Show<Tuple2<A, B>> {
    override fun show(a: Tuple2<A, B>): String =
            a.toString()
}

@instance(Tuple3::class)
interface Tuple3EqInstance<A, B, C> : Eq<Tuple3<A, B, C>> {

    fun EQA(): Eq<A>

    fun EQB(): Eq<B>

    fun EQC(): Eq<C>

    override fun Tuple3<A, B, C>.eqv(b: Tuple3<A, B, C>): Boolean =
            EQA().run { a.eqv(b.a) }
                    && EQB().run { this@eqv.b.eqv(b.b) }
                    && EQC().run { c.eqv(b.c) }
}

@instance(Tuple3::class)
interface Tuple3ShowInstance<A, B, C> : Show<Tuple3<A, B, C>> {
    override fun show(a: Tuple3<A, B, C>): String =
            a.toString()
}

@instance(Tuple4::class)
interface Tuple4EqInstance<A, B, C, D> : Eq<Tuple4<A, B, C, D>> {

    fun EQA(): Eq<A>

    fun EQB(): Eq<B>

    fun EQC(): Eq<C>

    fun EQD(): Eq<D>

    override fun Tuple4<A, B, C, D>.eqv(b: Tuple4<A, B, C, D>): Boolean =
            EQA().run { a.eqv(b.a) }
                    && EQB().run { this@eqv.b.eqv(b.b) }
                    && EQC().run { c.eqv(b.c) }
                    && EQD().run { d.eqv(b.d) }
}

@instance(Tuple4::class)
interface Tuple4ShowInstance<A, B, C, D> : Show<Tuple4<A, B, C, D>> {
    override fun show(a: Tuple4<A, B, C, D>): String =
            a.toString()
}

@instance(Tuple5::class)
interface Tuple5EqInstance<A, B, C, D, E> : Eq<Tuple5<A, B, C, D, E>> {

    fun EQA(): Eq<A>

    fun EQB(): Eq<B>

    fun EQC(): Eq<C>

    fun EQD(): Eq<D>

    fun EQE(): Eq<E>

    override fun Tuple5<A, B, C, D, E>.eqv(b: Tuple5<A, B, C, D, E>): Boolean =
            EQA().run { a.eqv(b.a) }
                    && EQB().run { this@eqv.b.eqv(b.b) }
                    && EQC().run { c.eqv(b.c) }
                    && EQD().run { d.eqv(b.d) }
                    && EQE().run { e.eqv(b.e) }

}

@instance(Tuple5::class)
interface Tuple5ShowInstance<A, B, C, D, E> : Show<Tuple5<A, B, C, D, E>> {
    override fun show(a: Tuple5<A, B, C, D, E>): String =
            a.toString()
}

@instance(Tuple6::class)
interface Tuple6EqInstance<A, B, C, D, E, F> : Eq<Tuple6<A, B, C, D, E, F>> {

    fun EQA(): Eq<A>

    fun EQB(): Eq<B>

    fun EQC(): Eq<C>

    fun EQD(): Eq<D>

    fun EQE(): Eq<E>

    fun EQF(): Eq<F>

    override fun Tuple6<A, B, C, D, E, F>.eqv(b: Tuple6<A, B, C, D, E, F>): Boolean =
            EQA().run { a.eqv(b.a) }
                    && EQB().run { this@eqv.b.eqv(b.b) }
                    && EQC().run { c.eqv(b.c) }
                    && EQD().run { d.eqv(b.d) }
                    && EQE().run { e.eqv(b.e) }
                    && EQF().run { f.eqv(b.f) }

}

@instance(Tuple6::class)
interface Tuple6ShowInstance<A, B, C, D, E, F> : Show<Tuple6<A, B, C, D, E, F>> {
    override fun show(a: Tuple6<A, B, C, D, E, F>): String =
            a.toString()
}

@instance(Tuple7::class)
interface Tuple7EqInstance<A, B, C, D, E, F, G> : Eq<Tuple7<A, B, C, D, E, F, G>> {

    fun EQA(): Eq<A>

    fun EQB(): Eq<B>

    fun EQC(): Eq<C>

    fun EQD(): Eq<D>

    fun EQE(): Eq<E>

    fun EQF(): Eq<F>

    fun EQG(): Eq<G>

    override fun Tuple7<A, B, C, D, E, F, G>.eqv(b: Tuple7<A, B, C, D, E, F, G>): Boolean =
            EQA().run { a.eqv(b.a) }
                    && EQB().run { this@eqv.b.eqv(b.b) }
                    && EQC().run { c.eqv(b.c) }
                    && EQD().run { d.eqv(b.d) }
                    && EQE().run { e.eqv(b.e) }
                    && EQF().run { f.eqv(b.f) }
                    && EQG().run { g.eqv(b.g) }

}

@instance(Tuple7::class)
interface Tuple7ShowInstance<A, B, C, D, E, F, G> : Show<Tuple7<A, B, C, D, E, F, G>> {
    override fun show(a: Tuple7<A, B, C, D, E, F, G>): String =
            a.toString()
}

@instance(Tuple8::class)
interface Tuple8EqInstance<A, B, C, D, E, F, G, H> : Eq<Tuple8<A, B, C, D, E, F, G, H>> {

    fun EQA(): Eq<A>

    fun EQB(): Eq<B>

    fun EQC(): Eq<C>

    fun EQD(): Eq<D>

    fun EQE(): Eq<E>

    fun EQF(): Eq<F>

    fun EQG(): Eq<G>

    fun EQH(): Eq<H>

    override fun Tuple8<A, B, C, D, E, F, G, H>.eqv(b: Tuple8<A, B, C, D, E, F, G, H>): Boolean =
            EQA().run { a.eqv(b.a) }
                    && EQB().run { this@eqv.b.eqv(b.b) }
                    && EQC().run { c.eqv(b.c) }
                    && EQD().run { d.eqv(b.d) }
                    && EQE().run { e.eqv(b.e) }
                    && EQF().run { f.eqv(b.f) }
                    && EQG().run { g.eqv(b.g) }
                    && EQH().run { h.eqv(b.h) }

}

@instance(Tuple8::class)
interface Tuple8ShowInstance<A, B, C, D, E, F, G, H> : Show<Tuple8<A, B, C, D, E, F, G, H>> {
    override fun show(a: Tuple8<A, B, C, D, E, F, G, H>): String =
            a.toString()
}

@instance(Tuple9::class)
interface Tuple9EqInstance<A, B, C, D, E, F, G, H, I> : Eq<Tuple9<A, B, C, D, E, F, G, H, I>> {

    fun EQA(): Eq<A>

    fun EQB(): Eq<B>

    fun EQC(): Eq<C>

    fun EQD(): Eq<D>

    fun EQE(): Eq<E>

    fun EQF(): Eq<F>

    fun EQG(): Eq<G>

    fun EQH(): Eq<H>

    fun EQI(): Eq<I>

    override fun Tuple9<A, B, C, D, E, F, G, H, I>.eqv(b: Tuple9<A, B, C, D, E, F, G, H, I>): Boolean =
            EQA().run { a.eqv(b.a) }
                    && EQB().run { this@eqv.b.eqv(b.b) }
                    && EQC().run { c.eqv(b.c) }
                    && EQD().run { d.eqv(b.d) }
                    && EQE().run { e.eqv(b.e) }
                    && EQF().run { f.eqv(b.f) }
                    && EQG().run { g.eqv(b.g) }
                    && EQH().run { h.eqv(b.h) }
                    && EQI().run { i.eqv(b.i) }

}

@instance(Tuple9::class)
interface Tuple9ShowInstance<A, B, C, D, E, F, G, H, I> : Show<Tuple9<A, B, C, D, E, F, G, H, I>> {
    override fun show(a: Tuple9<A, B, C, D, E, F, G, H, I>): String =
            a.toString()
}

@instance(Tuple10::class)
interface Tuple10EqInstance<A, B, C, D, E, F, G, H, I, J> : Eq<Tuple10<A, B, C, D, E, F, G, H, I, J>> {

    fun EQA(): Eq<A>

    fun EQB(): Eq<B>

    fun EQC(): Eq<C>

    fun EQD(): Eq<D>

    fun EQE(): Eq<E>

    fun EQF(): Eq<F>

    fun EQG(): Eq<G>

    fun EQH(): Eq<H>

    fun EQI(): Eq<I>

    fun EQJ(): Eq<J>

    override fun Tuple10<A, B, C, D, E, F, G, H, I, J>.eqv(b: Tuple10<A, B, C, D, E, F, G, H, I, J>): Boolean =
            EQA().run { a.eqv(b.a) }
                    && EQB().run { this@eqv.b.eqv(b.b) }
                    && EQC().run { c.eqv(b.c) }
                    && EQD().run { d.eqv(b.d) }
                    && EQE().run { e.eqv(b.e) }
                    && EQF().run { f.eqv(b.f) }
                    && EQG().run { g.eqv(b.g) }
                    && EQH().run { h.eqv(b.h) }
                    && EQI().run { i.eqv(b.i) }
                    && EQJ().run { j.eqv(b.j) }

}

@instance(Tuple10::class)
interface Tuple10ShowInstance<A, B, C, D, E, F, G, H, I, J> : Show<Tuple10<A, B, C, D, E, F, G, H, I, J>> {
    override fun show(a: Tuple10<A, B, C, D, E, F, G, H, I, J>): String =
            a.toString()
}
