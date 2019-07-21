package com.github.tartakynov.rengine.parser
import com.github.tartakynov.rengine.tree._
import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.tree.ParseTree

import scala.collection.JavaConverters._

class AstBuilder extends RuleBaseBaseVisitor[AnyRef] {
  protected def typedVisit[T](ctx: ParseTree): T = {
    ctx.accept(this).asInstanceOf[T]
  }

  /**
    * Create an expression from the given context. This method just passes the context on to the
    * visitor and only takes care of typing (We assume that the visitor returns an Expression here).
    */
  protected def expression(ctx: ParserRuleContext): Expression = {
    typedVisit(ctx)
  }

  override def visitBooleanLiteral(
    ctx: RuleBaseParser.BooleanLiteralContext
  ): Literal = {
    if (ctx.getText.toBoolean) {
      BooleanLiteral(true)
    } else {
      BooleanLiteral(false)
    }
  }

  override def visitDecimalLiteral(
    ctx: RuleBaseParser.DecimalLiteralContext
  ): Literal = {
    DoubleLiteral(ctx.getText.toDouble)
  }

  override def visitIntegerLiteral(
    ctx: RuleBaseParser.IntegerLiteralContext
  ): Literal = {
    IntegerLiteral(ctx.getText.toLong)
  }

  override def visitStringLiteral(
    ctx: RuleBaseParser.StringLiteralContext
  ): Literal = {
    StringLiteral(ctx.STRING().getText)
  }

  override def visitFunctionCall(
    ctx: RuleBaseParser.FunctionCallContext
  ): Expression = {
    val name = ctx.IDENTIFIER().getText
    val arguments = ctx.argument.asScala.map(expression)
    FunctionCall(name, arguments)
  }

  override def visitFeatureReference(
    ctx: RuleBaseParser.FeatureReferenceContext
  ): FeatureReference = {
    FeatureReference(ctx.IDENTIFIER().getText)
  }

  override def visitParenthesizedExpression(
    ctx: RuleBaseParser.ParenthesizedExpressionContext
  ): Expression = {
    expression(ctx.expression())
  }

  override def visitComparisonOperator(
    ctx: RuleBaseParser.ComparisonOperatorContext
  ): Expression = {
    val left = expression(ctx.left)
    val right = expression(ctx.right)
    val operator = ctx.operator.getType
    operator match {
      case RuleBaseParser.EQ  => EqualTo(left, right)
      case RuleBaseParser.NEQ => Not(EqualTo(left, right))
      case RuleBaseParser.LT  => LessThan(left, right)
      case RuleBaseParser.LTE => LessThanOrEqual(left, right)
      case RuleBaseParser.GT  => GreaterThan(left, right)
      case RuleBaseParser.GTE => GreaterThanOrEqual(left, right)
    }
  }

  override def visitLogicalNot(
    ctx: RuleBaseParser.LogicalNotContext
  ): Expression = {
    Not(expression(ctx.booleanExpression()))
  }

  override def visitLogicalOperator(
    ctx: RuleBaseParser.LogicalOperatorContext
  ): Expression = {
    val left = expression(ctx.left)
    val right = expression(ctx.right)
    val operator = ctx.operator.getType
    operator match {
      case RuleBaseParser.AND => And(left, right)
      case RuleBaseParser.OR  => Or(left, right)
    }
  }
}
