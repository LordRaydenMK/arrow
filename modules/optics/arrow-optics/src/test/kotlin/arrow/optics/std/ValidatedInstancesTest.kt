package arrow.optics

import arrow.core.*
import arrow.instances.applicative
import arrow.test.UnitSpec
import arrow.test.generators.*
import arrow.test.laws.IsoLaws
import arrow.typeclasses.Eq
import arrow.typeclasses.Monoid
import io.kotlintest.KTestJUnitRunner
import io.kotlintest.properties.Gen
import org.junit.runner.RunWith

@RunWith(KTestJUnitRunner::class)
class ValidatedInstancesTest : UnitSpec() {

  init {

    testLaws(
      IsoLaws.laws(
        iso = validatedToEither(),
        aGen = genValidated(Gen.string(), Gen.int()),
        bGen = genEither(Gen.string(), Gen.int()),
        funcGen = genFunctionAToB(genEither(Gen.string(), Gen.int())),
        EQA = Eq.any(),
        EQB = Eq.any(),
        bMonoid = object : Monoid<Either<String, Int>> {
          override fun empty() = Right(0)

          override fun Either<String, Int>.combine(b: Either<String, Int>): Either<String, Int> =
            Either.applicative<String>().run { this@combine.map2(b) { (a, b) -> a + b }.fix() }
        }),

      IsoLaws.laws(
        iso = validatedToTry(),
        aGen = genValidated(genThrowable(), Gen.int()),
        bGen = genTry(Gen.int()),
        funcGen = genFunctionAToB(genTry(Gen.int())),
        EQA = Eq.any(),
        EQB = Eq.any(),
        bMonoid = object : Monoid<Try<Int>> {
          override fun Try<Int>.combine(b: Try<Int>) = Try.applicative().run { this@combine.map2(b) { (a, b) -> a + b }.fix() }

          override fun empty(): Try<Int> = Try.Success(0)
        })
    )

  }

}
