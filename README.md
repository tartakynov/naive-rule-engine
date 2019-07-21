# Naive Rule Engine

I've made this repo mainly to play with ANTLR4. This rule engine allows to define conditions in query-like expression language and evaluate them against a JSON object.

## Example

Sample rule
```sql
(dayOfWeek = "sunday" AND isPromoAvailable("SUNDAY100")) OR order.totalAmount >= 100
```

Sample facts
```json
{
  "dayOfWeek": "sunday",
  "order": {
    "totalAmount": 123.00  
  },
  "customer": {
    "id": 75498329
  }
}
```

Sample function
```scala
/**
 * Check if given promo is available for the customer
 */
def isPromoAvailable(code: String)(implicit ctx: RuleContext): Boolean = {
  val customerId = ctx.getFact("customer.id").as[Int]
  ...
}
```

Put everything together
```scala
val rule = Rule(name = "sunday promo")
  .when("(dayOfWeek = 'sunday' AND isPromoAvailable('SUNDAY100')) OR order.totalAmount >= 100")
  .then(_ + ("discount_rate" -> 0.15))

val engine = RuleEngine(rule :: Nil)
engine.register(isPromoAvailable _)

val result = engine.evaluate(json)
println(result.get("discount_rate"))
```
