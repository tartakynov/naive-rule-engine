package com.github.tartakynov.rengine.tree

trait Literal extends LeafExpression

case class BooleanLiteral(value: Boolean) extends Literal

case class StringLiteral(value: String) extends Literal

case class IntegerLiteral(value: Long) extends Literal

case class DoubleLiteral(value: Double) extends Literal
