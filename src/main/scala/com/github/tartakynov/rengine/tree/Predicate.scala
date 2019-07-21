package com.github.tartakynov.rengine.tree

trait Predicate extends Expression

case class And(left: Expression, right: Expression)
    extends BinaryExpression
    with Predicate

case class Or(left: Expression, right: Expression)
    extends BinaryExpression
    with Predicate

case class Not(child: Expression) extends UnaryExpression with Predicate

trait BinaryComparison extends BinaryExpression with Predicate

case class GreaterThan(left: Expression, right: Expression)
    extends BinaryComparison

case class GreaterThanOrEqual(left: Expression, right: Expression)
    extends BinaryComparison

case class LessThan(left: Expression, right: Expression)
    extends BinaryComparison

case class LessThanOrEqual(left: Expression, right: Expression)
    extends BinaryComparison

case class EqualTo(left: Expression, right: Expression) extends BinaryComparison
