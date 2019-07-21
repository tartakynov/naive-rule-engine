package com.github.tartakynov.rengine.tree

case class FunctionCall(name: String, children: Seq[Expression])
    extends Expression
