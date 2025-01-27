package org.oppia.android.testing.math

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.oppia.android.app.model.ComparableOperation
import org.oppia.android.app.model.Real

/** Tests for [ComparableOperationSubject]. */
@RunWith(JUnit4::class)
class ComparableOperationSubjectTest {

  private fun createConstantOperation(value: Int): ComparableOperation {
    return ComparableOperation.newBuilder()
      .setConstantTerm(Real.newBuilder().setInteger(value))
      .build()
  }

  private fun createVariableOperation(name: String): ComparableOperation {
    return ComparableOperation.newBuilder()
      .setVariableTerm(name)
      .build()
  }

  private fun createCommutativeAccumulation(
    type: ComparableOperation.CommutativeAccumulation.AccumulationType,
    vararg operations: ComparableOperation
  ): ComparableOperation {
    val accumulation = ComparableOperation.CommutativeAccumulation.newBuilder()
      .setAccumulationType(type)
    operations.forEach { accumulation.addCombinedOperations(it) }
    return ComparableOperation.newBuilder()
      .setCommutativeAccumulation(accumulation)
      .build()
  }

  @Test
  fun testHasStructureThatMatches_success() {
    val operation = createConstantOperation(42)

    ComparableOperationSubject.assertThat(operation).hasStructureThatMatches {
      constantTerm {
        withValueThat().isIntegerThat().isEqualTo(42)
      }
    }
  }

  @Test(expected = AssertionError::class)
  fun testHasStructureThatMatches_withInvalidStructure_fails() {
    val operation = createConstantOperation(42)

    ComparableOperationSubject.assertThat(operation).hasStructureThatMatches {
      variableTerm {
        withNameThat().isEqualTo("x")
      }
    }
  }

  @Test
  fun testHasNegatedPropertyThat_withFalseDefault_matchesSuccessfully() {
    val operation = createConstantOperation(42)

    ComparableOperationSubject.assertThat(operation).hasStructureThatMatches {
      hasNegatedPropertyThat().isFalse()
    }
  }

  @Test
  fun testHasNegatedPropertyThat_withExplicitTrue_matchesSuccessfully() {
    val operation = ComparableOperation.newBuilder()
      .setConstantTerm(Real.newBuilder().setInteger(42))
      .setIsNegated(true)
      .build()

    ComparableOperationSubject.assertThat(operation).hasStructureThatMatches {
      hasNegatedPropertyThat().isTrue()
    }
  }

  @Test
  fun testHasInvertedPropertyThat_withFalseDefault_matchesSuccessfully() {
    val operation = createConstantOperation(42)

    ComparableOperationSubject.assertThat(operation).hasStructureThatMatches {
      hasInvertedPropertyThat().isFalse()
    }
  }

  @Test
  fun testHasInvertedPropertyThat_withExplicitTrue_matchesSuccessfully() {
    val operation = ComparableOperation.newBuilder()
      .setConstantTerm(Real.newBuilder().setInteger(42))
      .setIsInverted(true)
      .build()

    ComparableOperationSubject.assertThat(operation).hasStructureThatMatches {
      hasInvertedPropertyThat().isTrue()
    }
  }

  @Test
  fun testCommutativeAccumulation_withValidSummation_matchesSuccessfully() {
    val operation = createCommutativeAccumulation(
      ComparableOperation.CommutativeAccumulation.AccumulationType.SUMMATION,
      createConstantOperation(1),
      createConstantOperation(2)
    )

    ComparableOperationSubject.assertThat(operation).hasStructureThatMatches {
      commutativeAccumulationWithType(
        ComparableOperation.CommutativeAccumulation
          .AccumulationType.SUMMATION
      ) {
        hasOperandCountThat().isEqualTo(2)
        index(0) {
          constantTerm {
            withValueThat().isIntegerThat().isEqualTo(1)
          }
        }
        index(1) {
          constantTerm {
            withValueThat().isIntegerThat().isEqualTo(2)
          }
        }
      }
    }
  }

  @Test
  fun testCommutativeAccumulation_withEmptyAccumulation_matchesSuccessfully() {
    val operation = createCommutativeAccumulation(
      ComparableOperation.CommutativeAccumulation.AccumulationType.SUMMATION
    )

    ComparableOperationSubject.assertThat(operation).hasStructureThatMatches {
      commutativeAccumulationWithType(
        ComparableOperation.CommutativeAccumulation
          .AccumulationType.SUMMATION
      ) {
        hasOperandCountThat().isEqualTo(0)
      }
    }
  }

  @Test(expected = AssertionError::class)
  fun testCommutativeAccumulation_withInvalidType_fails() {
    val operation = createConstantOperation(42)

    ComparableOperationSubject.assertThat(operation).hasStructureThatMatches {
      commutativeAccumulationWithType(
        ComparableOperation.CommutativeAccumulation
          .AccumulationType.SUMMATION
      ) {
        hasOperandCountThat().isEqualTo(0)
      }
    }
  }

  @Test(expected = IndexOutOfBoundsException::class)
  fun testCommutativeAccumulation_withInvalidIndex_fails() {
    val operation = createCommutativeAccumulation(
      ComparableOperation.CommutativeAccumulation.AccumulationType.SUMMATION,
      createConstantOperation(1)
    )

    ComparableOperationSubject.assertThat(operation).hasStructureThatMatches {
      commutativeAccumulationWithType(
        ComparableOperation.CommutativeAccumulation
          .AccumulationType.SUMMATION
      ) {
        index(1) { }
      }
    }
  }

  @Test
  fun testExponentiation_withValidOperation_matchesSuccessfully() {
    val operation = ComparableOperation.newBuilder()
      .setNonCommutativeOperation(
        ComparableOperation.NonCommutativeOperation.newBuilder()
          .setExponentiation(
            ComparableOperation.NonCommutativeOperation.BinaryOperation.newBuilder()
              .setLeftOperand(createConstantOperation(2))
              .setRightOperand(createConstantOperation(3))
          )
      )
      .build()

    ComparableOperationSubject.assertThat(operation).hasStructureThatMatches {
      nonCommutativeOperation {
        exponentiation {
          leftOperand {
            constantTerm {
              withValueThat().isIntegerThat().isEqualTo(2)
            }
          }
          rightOperand {
            constantTerm {
              withValueThat().isIntegerThat().isEqualTo(3)
            }
          }
        }
      }
    }
  }

  @Test(expected = AssertionError::class)
  fun testExponentiation_withInvalidOperation_fails() {
    val operation = ComparableOperation.newBuilder()
      .setNonCommutativeOperation(
        ComparableOperation.NonCommutativeOperation.newBuilder()
          .setSquareRoot(createConstantOperation(4))
      )
      .build()

    ComparableOperationSubject.assertThat(operation).hasStructureThatMatches {
      nonCommutativeOperation {
        exponentiation { }
      }
    }
  }

  @Test
  fun testSquareRoot_withValidOperation_matchesSuccessfully() {
    val operation = ComparableOperation.newBuilder()
      .setNonCommutativeOperation(
        ComparableOperation.NonCommutativeOperation.newBuilder()
          .setSquareRoot(createConstantOperation(4))
      )
      .build()

    ComparableOperationSubject.assertThat(operation).hasStructureThatMatches {
      nonCommutativeOperation {
        squareRootWithArgument {
          constantTerm {
            withValueThat().isIntegerThat().isEqualTo(4)
          }
        }
      }
    }
  }

  @Test(expected = AssertionError::class)
  fun testSquareRoot_withInvalidOperation_fails() {
    val operation = ComparableOperation.newBuilder()
      .setNonCommutativeOperation(
        ComparableOperation.NonCommutativeOperation.newBuilder()
          .setExponentiation(
            ComparableOperation.NonCommutativeOperation.BinaryOperation.newBuilder()
              .setLeftOperand(createConstantOperation(2))
              .setRightOperand(createConstantOperation(3))
          )
      )
      .build()

    ComparableOperationSubject.assertThat(operation).hasStructureThatMatches {
      nonCommutativeOperation {
        squareRootWithArgument { }
      }
    }
  }

  @Test(expected = AssertionError::class)
  fun testBinaryOperation_withInvalidLeftOperand_fails() {
    val operation = ComparableOperation.newBuilder()
      .setNonCommutativeOperation(
        ComparableOperation.NonCommutativeOperation.newBuilder()
          .setExponentiation(
            ComparableOperation.NonCommutativeOperation.BinaryOperation.newBuilder()
              .setRightOperand(createConstantOperation(3))
          )
      )
      .build()

    ComparableOperationSubject.assertThat(operation).hasStructureThatMatches {
      nonCommutativeOperation {
        exponentiation {
          leftOperand {
            constantTerm {
              withValueThat().isIntegerThat().isEqualTo(2)
            }
          }
        }
      }
    }
  }

  @Test(expected = AssertionError::class)
  fun testBinaryOperation_withInvalidRightOperand_fails() {
    val operation = ComparableOperation.newBuilder()
      .setNonCommutativeOperation(
        ComparableOperation.NonCommutativeOperation.newBuilder()
          .setExponentiation(
            ComparableOperation.NonCommutativeOperation.BinaryOperation.newBuilder()
              .setLeftOperand(createConstantOperation(2))
          )
      )
      .build()

    ComparableOperationSubject.assertThat(operation).hasStructureThatMatches {
      nonCommutativeOperation {
        exponentiation {
          rightOperand {
            constantTerm {
              withValueThat().isIntegerThat().isEqualTo(3)
            }
          }
        }
      }
    }
  }

  @Test
  fun testConstantTerm_withValidValue_matchesSuccessfully() {
    val operation = createConstantOperation(42)

    ComparableOperationSubject.assertThat(operation).hasStructureThatMatches {
      constantTerm {
        withValueThat().isIntegerThat().isEqualTo(42)
      }
    }
  }

  @Test(expected = AssertionError::class)
  fun testConstantTerm_withInvalidType_fails() {
    val operation = createVariableOperation("x")

    ComparableOperationSubject.assertThat(operation).hasStructureThatMatches {
      constantTerm {
        withValueThat().isIntegerThat().isEqualTo(42)
      }
    }
  }

  @Test
  fun testVariableTerm_withValidName_matchesSuccessfully() {
    val operation = createVariableOperation("x")

    ComparableOperationSubject.assertThat(operation).hasStructureThatMatches {
      variableTerm {
        withNameThat().isEqualTo("x")
      }
    }
  }

  @Test(expected = AssertionError::class)
  fun testVariableTerm_withInvalidType_fails() {
    val operation = createConstantOperation(42)

    ComparableOperationSubject.assertThat(operation).hasStructureThatMatches {
      variableTerm {
        withNameThat().isEqualTo("x")
      }
    }
  }

  @Test
  fun testComplexExpression_withNestedOperations_matchesSuccessfully() {
    val operation = createCommutativeAccumulation(
      ComparableOperation.CommutativeAccumulation.AccumulationType.PRODUCT,
      ComparableOperation.newBuilder()
        .setNonCommutativeOperation(
          ComparableOperation.NonCommutativeOperation.newBuilder()
            .setExponentiation(
              ComparableOperation.NonCommutativeOperation.BinaryOperation.newBuilder()
                .setLeftOperand(createConstantOperation(2))
                .setRightOperand(createConstantOperation(3))
            )
        )
        .build(),
      ComparableOperation.newBuilder()
        .setNonCommutativeOperation(
          ComparableOperation.NonCommutativeOperation.newBuilder()
            .setSquareRoot(createConstantOperation(4))
        )
        .build(),
      createVariableOperation("x")
    )

    ComparableOperationSubject.assertThat(operation).hasStructureThatMatches {
      commutativeAccumulationWithType(
        ComparableOperation.CommutativeAccumulation
          .AccumulationType.PRODUCT
      ) {
        hasOperandCountThat().isEqualTo(3)
        index(0) {
          nonCommutativeOperation {
            exponentiation {
              leftOperand {
                constantTerm {
                  withValueThat().isIntegerThat().isEqualTo(2)
                }
              }
              rightOperand {
                constantTerm {
                  withValueThat().isIntegerThat().isEqualTo(3)
                }
              }
            }
          }
        }
        index(1) {
          nonCommutativeOperation {
            squareRootWithArgument {
              constantTerm {
                withValueThat().isIntegerThat().isEqualTo(4)
              }
            }
          }
        }
        index(2) {
          variableTerm {
            withNameThat().isEqualTo("x")
          }
        }
      }
    }
  }
}
