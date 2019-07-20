grammar RuleBase;

tokens {
    DELIMITER
}

expression
    : booleanExpression
    ;

booleanExpression
    : NOT booleanExpression                                           #logicalNot
    | left=booleanExpression operator=AND right=booleanExpression     #logicalBinary
    | left=booleanExpression operator=OR right=booleanExpression      #logicalBinary
    | valueExpression                                                 #booleanDefault
    ;

valueExpression
    : primaryExpression                                               #valueExpressionDefault
    | left=valueExpression comparisonOperator right=valueExpression   #comparison
    ;

primaryExpression
    : literal                                                         #literalExpression
    | IDENTIFIER '(' ')'                                              #functionCall
    | IDENTIFIER '(' (expression (',' expression)*)? ')'              #functionCall
    | IDENTIFIER                                                      #columnReference
    | '(' expression ')'                                              #parenthesizedExpression
    ;

comparisonOperator
    : EQ | NEQ | LT | LTE | GT | GTE
    ;

literal
    : STRING                                                          #stringLiteral
    | number                                                          #numericLiteral
    | booleanValue                                                    #booleanLiteral
    ;

number
    : DECIMAL_VALUE  #decimalLiteral
    | INTEGER_VALUE  #integerLiteral
    ;

booleanValue
    : TRUE | FALSE
    ;

OR: 'OR';
AND: 'AND';
TRUE: 'TRUE';
FALSE: 'FALSE';
NOT: 'NOT' | '!';

EQ  : '=';
NEQ : '<>' | '!=';
LT  : '<';
LTE : '<=';
GT  : '>';
GTE : '>=';


STRING
    : '"' ( ~('"'|'\\') | ('\\' .) )* '"'
    ;

INTEGER_VALUE
    : DIGIT+
    ;

DECIMAL_VALUE
    : DIGIT+ '.' DIGIT*
    | '.' DIGIT+
    | DIGIT+ ('.' DIGIT*)? EXPONENT
    | '.' DIGIT+ EXPONENT
    ;

IDENTIFIER
    : (LETTER | '_') (LETTER | DIGIT | '_' )* ('.' IDENTIFIER)*
    ;

fragment EXPONENT
    : 'E' [+-]? DIGIT+
    ;

fragment DIGIT
    : [0-9]
    ;

fragment LETTER
    : [A-Z]
    ;

WS
    : [ \r\n\t]+ -> channel(HIDDEN)
    ;

// Catch-all for anything we can't recognize.
// We use this to be able to ignore and recover all the text
// when splitting statements with DelimiterLexer
UNRECOGNIZED
    : .
    ;
