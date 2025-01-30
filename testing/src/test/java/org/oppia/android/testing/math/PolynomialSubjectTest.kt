package org.oppia.android.testing.math

import org.junit.Assert.assertThrows
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.oppia.android.app.model.Polynomial
import org.oppia.android.app.model.Real

/** Tests for [PolynomialSubject]. */
@RunWith(JUnit4::class)
class PolynomialSubjectTest {

  @Test
  fun testPolynomialSubject_withNullPolynomial_isNotValidPolynomial() {
    PolynomialSubject.assertThat(null).isNotValidPolynomial()
  }

  @Test
  fun testPolynomialSubject_withNonNullPolynomial_isNotValidPolynomial_fails() {
    val polynomial = Polynomial.newBuilder()
      .addTerm(Polynomial.Term.newBuilder().setCoefficient(Real.newBuilder().setInteger(1)))
      .build()
    assertThrows(AssertionError::class.java) {
      PolynomialSubject.assertThat(polynomial).isNotValidPolynomial()
    }
  }

  @Test
  fun testPolynomialSubject_withConstantPolynomial_isConstantThat() {
    val constantPolynomial = Polynomial.newBuilder()
      .addTerm(Polynomial.Term.newBuilder().setCoefficient(Real.newBuilder().setInteger(5)))
      .build()
    PolynomialSubject.assertThat(constantPolynomial)
      .isConstantThat()
      .isIntegerThat()
      .isEqualTo(5)
  }

  @Test
  fun testPolynomialSubject_withNonConstantPolynomial_isConstantThat_fails() {
    val nonConstantPolynomial = Polynomial.newBuilder()
      .addTerm(
        Polynomial.Term.newBuilder()
          .setCoefficient(Real.newBuilder().setInteger(5))
          .addVariable(Polynomial.Term.Variable.newBuilder().setName("x").setPower(1))
      )
      .build()
    assertThrows(AssertionError::class.java) {
      PolynomialSubject.assertThat(nonConstantPolynomial).isConstantThat()
    }
  }

  @Test
  fun testPolynomialSubject_withZeroTerms_hasTermCountThat() {
    val emptyPolynomial = Polynomial.newBuilder().build()
    PolynomialSubject.assertThat(emptyPolynomial)
      .hasTermCountThat()
      .isEqualTo(0)
  }

  @Test
  fun testPolynomialSubject_withMultipleTerms_hasTermCountThat() {
    val multiTermPolynomial = Polynomial.newBuilder()
      .addTerm(Polynomial.Term.newBuilder().setCoefficient(Real.newBuilder().setInteger(1)))
      .addTerm(Polynomial.Term.newBuilder().setCoefficient(Real.newBuilder().setInteger(2)))
      .build()
    PolynomialSubject.assertThat(multiTermPolynomial)
      .hasTermCountThat()
      .isEqualTo(2)
  }

  @Test
  fun testPolynomialSubject_withValidIndex_termHasCoefficient() {
    val polynomial = Polynomial.newBuilder()
      .addTerm(
        Polynomial.Term.newBuilder()
          .setCoefficient(Real.newBuilder().setInteger(5))
          .addVariable(Polynomial.Term.Variable.newBuilder().setName("x").setPower(2))
      )
      .addTerm(
        Polynomial.Term.newBuilder()
          .setCoefficient(Real.newBuilder().setInteger(3))
          .addVariable(Polynomial.Term.Variable.newBuilder().setName("y").setPower(1))
      )
      .build()

    PolynomialSubject.assertThat(polynomial)
      .term(0)
      .hasCoefficientThat()
      .isIntegerThat()
      .isEqualTo(5)

    PolynomialSubject.assertThat(polynomial)
      .term(0)
      .variable(0)
      .hasNameThat()
      .isEqualTo("x")

    PolynomialSubject.assertThat(polynomial)
      .term(1)
      .hasCoefficientThat()
      .isIntegerThat()
      .isEqualTo(3)
  }

  @Test
  fun testPolynomialSubject_failsWithInvalidIndex() {
    val polynomial = Polynomial.newBuilder().build()
    assertThrows(IndexOutOfBoundsException::class.java) {
      PolynomialSubject.assertThat(polynomial).term(0)
    }
  }

  @Test
  fun testPolynomialSubject_withConstantPolynomial_evaluatesToPlainTextThat() {
    val constantPolynomial = Polynomial.newBuilder()
      .addTerm(Polynomial.Term.newBuilder().setCoefficient(Real.newBuilder().setInteger(5)))
      .build()
    PolynomialSubject.assertThat(constantPolynomial)
      .evaluatesToPlainTextThat()
      .isEqualTo("5")
  }

  @Test
  fun testPolynomialSubject_withComplexPolynomial_evaluatesToPlainTextThat() {
    val polynomial = Polynomial.newBuilder()
      .addTerm(
        Polynomial.Term.newBuilder()
          .setCoefficient(Real.newBuilder().setInteger(2))
          .addVariable(Polynomial.Term.Variable.newBuilder().setName("x").setPower(2))
      )
      .addTerm(
        Polynomial.Term.newBuilder()
          .setCoefficient(Real.newBuilder().setInteger(3))
          .addVariable(Polynomial.Term.Variable.newBuilder().setName("x").setPower(1))
      )
      .addTerm(Polynomial.Term.newBuilder().setCoefficient(Real.newBuilder().setInteger(1)))
      .build()
    PolynomialSubject.assertThat(polynomial)
      .evaluatesToPlainTextThat()
      .isEqualTo("2x^2 + 3x + 1")
  }

  @Test
  fun testPolynomialSubject_withTermHasVariableCount_that() {
    val term = Polynomial.Term.newBuilder()
      .setCoefficient(Real.newBuilder().setInteger(5))
      .addVariable(Polynomial.Term.Variable.newBuilder().setName("x").setPower(2))
      .addVariable(Polynomial.Term.Variable.newBuilder().setName("y").setPower(1))
      .build()

    PolynomialSubject.assertThat(Polynomial.newBuilder().addTerm(term).build())
      .term(0)
      .hasVariableCountThat()
      .isEqualTo(2)
  }

  @Test
  fun testPolynomialSubject_withTermVariableHasDetails_that() {
    val term = Polynomial.Term.newBuilder()
      .addVariable(Polynomial.Term.Variable.newBuilder().setName("x").setPower(3))
      .build()

    PolynomialSubject.assertThat(Polynomial.newBuilder().addTerm(term).build())
      .term(0)
      .variable(0)
      .apply {
        hasNameThat().isEqualTo("x")
        hasPowerThat().isEqualTo(3)
      }
  }
}
