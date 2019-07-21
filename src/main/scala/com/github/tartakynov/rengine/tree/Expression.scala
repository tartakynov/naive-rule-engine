package com.github.tartakynov.rengine.tree

trait Expression {
  def children: Seq[Expression]
}

trait LeafExpression extends Expression {
  override def children: Seq[Expression] = Nil
}

trait UnaryExpression extends Expression {
  def child: Expression

  override def children: Seq[Expression] = child :: Nil
}

trait BinaryExpression extends Expression {
  def left: Expression

  def right: Expression

  override def children: Seq[Expression] = left :: right :: Nil
}
