package com.github.tartakynov.rengine
import com.github.tartakynov.rengine.parser.RuleParser

object Main {

  def main(args: Array[String]): Unit = {
    val parser = new RuleParser()
    val expression = parser.parse("A = 1 AND B = 2")
    System.out.println(expression.toString)
  }
}
