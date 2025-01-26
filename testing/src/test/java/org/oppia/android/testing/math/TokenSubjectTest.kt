package org.oppia.android.testing.math

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.oppia.android.util.math.MathTokenizer.Companion.Token

/** Tests for [TokenSubject]. */
@RunWith(JUnit4::class)
class TokenSubjectTest {

  @Test
  fun testHasStartIndexThat_correctIndex_passes() {
    val token = Token.PositiveInteger(42, 10, 15)
    TokenSubject.assertThat(token).hasStartIndexThat().isEqualTo(10)
  }

  @Test(expected = AssertionError::class)
  fun testHasStartIndexThat_incorrectIndex_fails() {
    val token = Token.PositiveInteger(42, 10, 15)
    TokenSubject.assertThat(token).hasStartIndexThat().isEqualTo(11)
  }

  @Test
  fun testHasEndIndexThat_correctIndex_passes() {
    val token = Token.PositiveInteger(42, 10, 15)
    TokenSubject.assertThat(token).hasEndIndexThat().isEqualTo(15)
  }

  @Test(expected = AssertionError::class)
  fun testHasEndIndexThat_incorrectIndex_fails() {
    val token = Token.PositiveInteger(10, 15, 42)
    TokenSubject.assertThat(token).hasEndIndexThat().isEqualTo(14)
  }

  @Test
  fun testIsPositiveIntegerWhoseValue_correctValue_passes() {
    val token = Token.PositiveInteger(42, 10, 15)
    TokenSubject.assertThat(token).isPositiveIntegerWhoseValue().isEqualTo(42)
  }

  @Test(expected = AssertionError::class)
  fun testIsPositiveIntegerWhoseValue_incorrectType_fails() {
    val token = Token.VariableName("x", 15, 10)
    TokenSubject.assertThat(token).isPositiveIntegerWhoseValue()
  }

  @Test
  fun testIsPositiveRealNumberWhoseValue_correctValue_passes() {
    val token = Token.PositiveRealNumber(3.14, 15, 10)
    TokenSubject.assertThat(token).isPositiveRealNumberWhoseValue().isEqualTo(3.14)
  }

  fun testIisPositiveRealNumberWhoseValue_incorrectType_fails() {
    val token = Token.PositiveInteger(42, 10, 15)
    TokenSubject.assertThat(token).isPositiveRealNumberWhoseValue().isNotEqualTo(25)
  }

  @Test
  fun testIsVariableWhoseName_correctName_passes() {
    val token = Token.VariableName("x", 10, 15)
    TokenSubject.assertThat(token).isVariableWhoseName().isEqualTo("x")
  }

  @Test(expected = AssertionError::class)
  fun testIsVariableWhoseName_incorrectType_fails() {
    val token = Token.PositiveInteger(42, 10, 15)
    TokenSubject.assertThat(token).isVariableWhoseName()
  }

  @Test
  fun testIsFunctionNameThat_correctNameAndAllowedStatus_passes() {
    val token = Token.FunctionName("sqrt", true, 10, 15)
    TokenSubject.assertThat(token)
      .isFunctionNameThat()
      .hasNameThat().isEqualTo("sqrt")
  }

  @Test
  fun testSymbol_IsMinusSymbol() {
    val token = Token.MinusSymbol(10, 11)
    TokenSubject.assertThat(token).isMinusSymbol()
  }

  @Test
  fun testSymbol_IsSquareRootSymbol() {
    val token = Token.SquareRootSymbol(10, 11)
    TokenSubject.assertThat(token).isSquareRootSymbol()
  }

  @Test
  fun testSymbol_IsPlusSymbol() {
    val token = Token.PlusSymbol(10, 11)
    TokenSubject.assertThat(token).isPlusSymbol()
  }

  @Test
  fun testSymbol_IsMultiplySymbol() {
    val token = Token.MultiplySymbol(10, 11)
    TokenSubject.assertThat(token).isMultiplySymbol()
  }

  @Test
  fun testSymbol_IsDivideSymbol() {
    val token = Token.DivideSymbol(10, 11)
    TokenSubject.assertThat(token).isDivideSymbol()
  }

  @Test
  fun testSymbol_IsExponentiationSymbol() {
    val token = Token.ExponentiationSymbol(10, 11)
    TokenSubject.assertThat(token).isExponentiationSymbol()
  }

  @Test
  fun testSymbol_IsEqualsSymbol() {
    val token = Token.EqualsSymbol(10, 11)
    TokenSubject.assertThat(token).isEqualsSymbol()
  }

  @Test
  fun testSymbol_IsLeftParenthesisSymbol() {
    val token = Token.LeftParenthesisSymbol(10, 11)
    TokenSubject.assertThat(token).isLeftParenthesisSymbol()
  }

  @Test
  fun testSymbol_IsRightParenthesisSymbol() {
    val token = Token.RightParenthesisSymbol(10, 11)
    TokenSubject.assertThat(token).isRightParenthesisSymbol()
  }

  @Test(expected = AssertionError::class)
  fun testSymbolMethods_incorrectType_fails() {
    TokenSubject.assertThat(Token.PositiveInteger(10, 11, 42)).isMinusSymbol()
  }

  @Test
  fun testInvalidTokenMethods_correctTypes_pass() {
    TokenSubject.assertThat(Token.InvalidToken(10, 11)).isInvalidToken()
    TokenSubject.assertThat(Token.IncompleteFunctionName(10, 11)).isIncompleteFunctionName()
  }

  @Test(expected = AssertionError::class)
  fun testInvalidTokenMethods_incorrectType_fails() {
    TokenSubject.assertThat(Token.PositiveInteger(10, 11, 42)).isInvalidToken()
  }

  @Test
  fun testFunctionNameSubject_nameCheck_passes() {
    val token = Token.FunctionName("sqrt", true, 10, 15)
    TokenSubject.assertThat(token)
      .isFunctionNameThat()
      .hasNameThat().isEqualTo("sqrt")
  }

  @Test(expected = AssertionError::class)
  fun testFunctionNameSubject_nameCheck_fails() {
    val token = Token.FunctionName("sqrt", true, 10, 15)
    TokenSubject.assertThat(token)
      .isFunctionNameThat()
      .hasNameThat().isEqualTo("sin")
  }

  @Test
  fun testFunctionNameSubject_allowedPropertyCheck_passes() {
    val token = Token.FunctionName("sqrt", true, 10, 15)
    TokenSubject.assertThat(token)
      .isFunctionNameThat()
      .hasIsAllowedPropertyThat().isTrue()
  }

  @Test(expected = AssertionError::class)
  fun testFunctionNameSubject_allowedPropertyCheck_fails() {
    val token = Token.FunctionName("sqrt", false, 10, 15)
    TokenSubject.assertThat(token)
      .isFunctionNameThat()
      .hasIsAllowedPropertyThat().isTrue()
  }
}
