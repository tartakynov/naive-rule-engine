name := "rengine"

version := "0.1"

scalaVersion := "2.12.8"

enablePlugins(Antlr4Plugin)

antlr4PackageName in Antlr4 := Some("parser")

javaSource in Antlr4 := (sourceManaged in Compile).value

