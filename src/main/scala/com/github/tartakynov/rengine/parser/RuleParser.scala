package com.github.tartakynov.rengine.parser
import com.github.tartakynov.rengine.tree.Expression
import org.antlr.v4.runtime.{ANTLRInputStream, CommonTokenStream}

class RuleParser {
  val astBuilder = new AstBuilder()

  def parse(rule: String): Expression = {
    val lexer = new RuleBaseLexer(new ANTLRInputStream(rule))
    lexer.removeErrorListeners()
    val tokenStream = new CommonTokenStream(lexer)
    val parser = new RuleBaseParser(tokenStream)
    astBuilder.visitSingleExpression(parser.singleExpression())
  }

}
