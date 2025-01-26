package org.oppia.android.testing.math

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.oppia.android.app.model.Polynomial
import org.oppia.android.app.model.Real

@RunWith(JUnit4::class)
class PolynomialSubjectTest {

  @Test
  fun testIsNotValidPolynomial_withNullPolynomial_passes() {
    PolynomialSubject.assertThat(null).isNotValidPolynomial()
  }

  @Test(expected = AssertionError::class)
  fun testIsNotValidPolynomial_withNonNullPolynomial_fails() {
    val polynomial = Polynomial.newBuilder()
      .addTerm(Polynomial.Term.newBuilder().setCoefficient(Real.newBuilder().setInteger(1)))
      .build()
    PolynomialSubject.assertThat(polynomial).isNotValidPolynomial()
  }

  @Test
  fun testIsConstantThat_constantPolynomial_passes() {
    val constantPolynomial = Polynomial.newBuilder()
      .addTerm(Polynomial.Term.newBuilder().setCoefficient(Real.newBuilder().setInteger(5)))
      .build()
    PolynomialSubject.assertThat(constantPolynomial)
      .isConstantThat()
      .isIntegerThat()
      .isEqualTo(5)
  }

  @Test(expected = AssertionError::class)
  fun testIsConstantThat_nonConstantPolynomial_fails() {
    val nonConstantPolynomial = Polynomial.newBuilder()
      .addTerm(
        Polynomial.Term.newBuilder()
          .setCoefficient(Real.newBuilder().setInteger(5))
          .addVariable(Polynomial.Term.Variable.newBuilder().setName("x").setPower(1))
      )
      .build()
    PolynomialSubject.assertThat(nonConstantPolynomial).isConstantThat()
  }

  @Test
  fun testHasTermCountThat_zeroTerms_passes() {
    val emptyPolynomial = Polynomial.newBuilder().build()
    PolynomialSubject.assertThat(emptyPolynomial)
      .hasTermCountThat()
      .isEqualTo(0)
  }

  @Test
  fun testHasTermCountThat_multipleTerms_passes() {
    val multiTermPolynomial = Polynomial.newBuilder()
      .addTerm(Polynomial.Term.newBuilder().setCoefficient(Real.newBuilder().setInteger(1)))
      .addTerm(Polynomial.Term.newBuilder().setCoefficient(Real.newBuilder().setInteger(2)))
      .build()
    PolynomialSubject.assertThat(multiTermPolynomial)
      .hasTermCountThat()
      .isEqualTo(2)
  }

  @Test
  fun testTerm_validIndex_passes() {
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

  @Test(expected = IndexOutOfBoundsException::class)
  fun testTerm_invalidIndex_throws() {
    val polynomial = Polynomial.newBuilder().build()
    PolynomialSubject.assertThat(polynomial).term(0)
  }

  @Test
  fun testEvaluatesToPlainTextThat_constantPolynomial_passes() {
    val constantPolynomial = Polynomial.newBuilder()
      .addTerm(Polynomial.Term.newBuilder().setCoefficient(Real.newBuilder().setInteger(5)))
      .build()
    PolynomialSubject.assertThat(constantPolynomial)
      .evaluatesToPlainTextThat()
      .isEqualTo("5")
  }

  @Test
  fun testEvaluatesToPlainTextThat_complexPolynomial_passes() {
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
  fun testPolynomialTermSubject_variableCounts() {
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
  fun testPolynomialTermVariableSubject_details() {
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
