package org.oppia.android.testing.math

import org.junit.Assert.assertThrows
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.oppia.android.util.math.MathTokenizer.Companion.Token

/** Tests for [TokenSubject]. */
@RunWith(JUnit4::class)
class TokenSubjectTest {

  @Test
  fun testTokenSubject_hasStartIndexThat_correctIndex_passes() {
    val token = Token.PositiveInteger(42, 10, 15)
    TokenSubject.assertThat(token).hasStartIndexThat().isEqualTo(10)
  }

  @Test
  fun testTokenSubject_hasStartIndexThat_incorrectIndex_fails() {
    val token = Token.PositiveInteger(42, 10, 15)
    assertThrows(AssertionError::class.java) {
      TokenSubject.assertThat(token).hasStartIndexThat().isEqualTo(11)
    }
  }

  @Test
  fun testTokenSubject_hasEndIndexThat_correctIndex_passes() {
    val token = Token.PositiveInteger(42, 10, 15)
    TokenSubject.assertThat(token).hasEndIndexThat().isEqualTo(15)
  }

  @Test
  fun testTokenSubject_hasEndIndexThat_incorrectIndex_fails() {
    val token = Token.PositiveInteger(42, 10, 15)
    assertThrows(AssertionError::class.java) {
      TokenSubject.assertThat(token).hasEndIndexThat().isEqualTo(14)
    }
  }

  @Test
  fun testTokenSubject_isPositiveIntegerWhoseValue_correctValue_passes() {
    val token = Token.PositiveInteger(42, 10, 15)
    TokenSubject.assertThat(token).isPositiveIntegerWhoseValue().isEqualTo(42)
  }

  @Test
  fun testTokenSubject_isPositiveIntegerWhoseValue_incorrectType_fails() {
    val token = Token.VariableName("x", 10, 15)
    assertThrows(AssertionError::class.java) {
      TokenSubject.assertThat(token).isPositiveIntegerWhoseValue()
    }
  }

  @Test
  fun testTokenSubject_isPositiveRealNumberWhoseValue_correctValue_passes() {
    val token = Token.PositiveRealNumber(3.14, 10, 15)
    TokenSubject.assertThat(token).isPositiveRealNumberWhoseValue().isEqualTo(3.14)
  }

  fun testTokenSubject_isPositiveRealNumberWhoseValue_incorrectType_fails() {
    val token = Token.PositiveInteger(42, 10, 15)
    TokenSubject.assertThat(token).isPositiveRealNumberWhoseValue().isNotEqualTo(25)
  }

  @Test
  fun testTokenSubject_isVariableWhoseName_correctName_passes() {
    val token = Token.VariableName("x", 10, 15)
    TokenSubject.assertThat(token).isVariableWhoseName().isEqualTo("x")
  }

  @Test
  fun testTokenSubject_isVariableWhoseName_incorrectType_fails() {
    val token = Token.PositiveInteger(42, 10, 15)
    assertThrows(AssertionError::class.java) {
      TokenSubject.assertThat(token).isVariableWhoseName()
    }
  }

  @Test
  fun testTokenSubject_isFunctionNameThat_correctNameAndAllowedStatus_passes() {
    val token = Token.FunctionName("sqrt", true, 10, 15)
    TokenSubject.assertThat(token)
      .isFunctionNameThat()
      .hasNameThat().isEqualTo("sqrt")
  }

  @Test
  fun testTokenSubject_symbolIsMinusSymbol() {
    val token = Token.MinusSymbol(10, 11)
    TokenSubject.assertThat(token).isMinusSymbol()
  }

  @Test
  fun testTokenSubject_symbolIsSquareRootSymbol() {
    val token = Token.SquareRootSymbol(10, 11)
    TokenSubject.assertThat(token).isSquareRootSymbol()
  }

  @Test
  fun testTokenSubject_symbolIsPlusSymbol() {
    val token = Token.PlusSymbol(10, 11)
    TokenSubject.assertThat(token).isPlusSymbol()
  }

  @Test
  fun testTokenSubject_symbolIsMultiplySymbol() {
    val token = Token.MultiplySymbol(10, 11)
    TokenSubject.assertThat(token).isMultiplySymbol()
  }

  @Test
  fun testTokenSubject_symbolIsDivideSymbol() {
    val token = Token.DivideSymbol(10, 11)
    TokenSubject.assertThat(token).isDivideSymbol()
  }

  @Test
  fun testTokenSubject_symbolIsExponentiationSymbol() {
    val token = Token.ExponentiationSymbol(10, 11)
    TokenSubject.assertThat(token).isExponentiationSymbol()
  }

  @Test
  fun testTokenSubject_symbolIsEqualsSymbol() {
    val token = Token.EqualsSymbol(10, 11)
    TokenSubject.assertThat(token).isEqualsSymbol()
  }

  @Test
  fun testTokenSubject_symbolIsLeftParenthesisSymbol() {
    val token = Token.LeftParenthesisSymbol(10, 11)
    TokenSubject.assertThat(token).isLeftParenthesisSymbol()
  }

  @Test
  fun testTokenSubject_symbolIsRightParenthesisSymbol() {
    val token = Token.RightParenthesisSymbol(10, 11)
    TokenSubject.assertThat(token).isRightParenthesisSymbol()
  }

  @Test
  fun testTokenSubject_symbolMethodsWithIncorrectType_fails() {
    assertThrows(AssertionError::class.java) {
      TokenSubject.assertThat(Token.PositiveInteger(10, 11, 42)).isMinusSymbol()
    }
  }

  @Test
  fun testTokenSubject_invalidTokenMethods_correctTypes_pass() {
    TokenSubject.assertThat(Token.InvalidToken(10, 11)).isInvalidToken()
    TokenSubject.assertThat(Token.IncompleteFunctionName(10, 11)).isIncompleteFunctionName()
  }

  @Test
  fun testTokenSubject_invalidTokenMethods_incorrectType_fails() {
    val token = Token.PositiveInteger(10, 11, 42)
    assertThrows(AssertionError::class.java) {
      TokenSubject.assertThat(token).isInvalidToken()
    }
  }

  @Test
  fun testTokenSubject_functionNameSubject_nameCheck_passes() {
    val token = Token.FunctionName("sqrt", true, 10, 15)
    TokenSubject.assertThat(token)
      .isFunctionNameThat()
      .hasNameThat().isEqualTo("sqrt")
  }

  @Test
  fun testTokenSubject_functionNameSubject_nameCheck_fails() {
    val token = Token.FunctionName("sqrt", true, 10, 15)
    assertThrows(AssertionError::class.java) {
      TokenSubject.assertThat(token)
        .isFunctionNameThat()
        .hasNameThat().isEqualTo("sin")
    }
  }

  @Test
  fun testTokenSubject_functionNameSubject_allowedPropertyCheck_passes() {
    val token = Token.FunctionName("sqrt", true, 10, 15)
    TokenSubject.assertThat(token)
      .isFunctionNameThat()
      .hasIsAllowedPropertyThat().isTrue()
  }

  @Test
  fun testTokenSubject_functionNameSubject_allowedPropertyCheck_fails() {
    val token = Token.FunctionName("sqrt", false, 10, 15)
    assertThrows(AssertionError::class.java) {
      TokenSubject.assertThat(token)
        .isFunctionNameThat()
        .hasIsAllowedPropertyThat().isTrue()
    }
  }
}
