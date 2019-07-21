grammar RuleBase;

tokens {
    DELIMITER
}

singleExpression
    : expression EOF
    ;

expression
    : booleanExpression
    ;

booleanExpression
    : valueExpression                                                             #booleanDefault
    | NOT booleanExpression                                                       #logicalNot
    | left=booleanExpression logicalOperator right=booleanExpression              #logicalOperation
    ;

valueExpression
    : primaryExpression                                                           #valueExpressionDefault
    | left=valueExpression comparisonOperator right=valueExpression               #comparisonOperation
    ;

primaryExpression
    : literal                                                                     #literalExpression
    | IDENTIFIER '(' (argument+=expression (',' argument+=expression)*)? ')'      #functionCall
    | IDENTIFIER                                                                  #featureReference
    | '(' expression ')'                                                          #parenthesizedExpression
    ;

literal
    : STRING                                                                      #stringLiteral
    | number                                                                      #numericLiteral
    | booleanValue                                                                #booleanLiteral
    ;

number
    : MINUS? DECIMAL_VALUE                                                        #decimalLiteral
    | MINUS? INTEGER_VALUE                                                        #integerLiteral
    ;

booleanValue
    : TRUE | FALSE
    ;

logicalOperator
    : AND | OR
    ;

comparisonOperator
    : EQ | NEQ | LT | LTE | GT | GTE
    ;

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

MINUS: '-';

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
