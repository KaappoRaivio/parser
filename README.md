# Parser

### Introduction
This is my attempt to create a parser capable of parsing mathematical expressions for use in my upcoming calculator app for Android.

**Currently supported functionalities are:**
* User-definable operators
    * These can be binary or unary operators. With unary operators, they can be either prefix (ie. square root) or suffix (ie. factorial).
    * Comes with some built-in operators
    * You can either bind your own operators to the built-in tokens or define your own.
    * With binary operators, you can choose them to be either left or right associative.
* Ability to use rational numbers
    * Able to parse rationals from cycling, repeating decimals (ie. 3.33... &rarr; (1 / 3), 0.142857142857... &rarr; (1 / 7))

#### Getting started
###### Quickstart
1. Make sure that you have Java 8 or never installed.
1. Clone the repository to your computer and ```cd``` into it.
2. Execute the following command: ```java -cp parser.jar Main```
4. Now, you can play with different inputs. The demo program also prints out the lexer and parse tree.
<details><summary>Here are some snippets with different inputs and outputs:</summary>
    
```
>>> (1 + sqrt 5) / 2
Lexer: [Left parenthesis, Number{1}, Plus, Square root, Number{5}, Right parenthesis, Slash, Number{2}, End] at 0
Parse tree: Slash {
    Parenthesis {
        Plus {
            1
            Square root {
                5
            }
        }
    }
    2
}
Result: 1.6180339887498950
in decimal: 1.618033988749895
Calculating took 28 milliseconds.
```

```
>>> 3 ^ 2 ^ 1 * (-3) / |-10|
Lexer: [Number{3}, Power, Number{2}, Power, Number{1}, Asterisk, Left parenthesis, Minus, Number{3}, Right parenthesis, Slash, Pipe, Minus, Number{10}, Pipe, End] at 0
Parse tree: Slash {
    Asterisk {
        Power {
            3
            Power {
                2
                1
            }
        }
        Parenthesis {
            Minus {
                3
            }
        }
    }
    Absolute value {
        Minus {
            10
        }
    }
}
Result: (-27 / 10)
in decimal: -2.7
Calculating took 7 milliseconds. 
```

```
>>> 0.142857142857...
Lexer: [Number{0.142857142857}, Ellipsis, End] at 0
Parse tree: Ellipsis {
	(142857142857 / 1000000000000)
}
Result: (1 / 7)
in decimal: 0.1428571428571429
Calculating took 3 milliseconds.
```

```
>>> 0.33... * 0.66... + 0.142857142857...
Lexer: [Number{0.33}, Ellipsis, Asterisk, Number{0.66}, Ellipsis, Plus, Number{0.142857142857}, Ellipsis, End] at 0
Parse tree: Plus {
	Asterisk {
		Ellipsis {
			(33 / 100)
		}
		Ellipsis {
			(33 / 50)
		}
	}
	Ellipsis {
		(142857142857 / 1000000000000)
	}
}
Result: (23 / 63)
in decimal: 0.3650793650793651
Calculating took 6 milliseconds.
```
</details>
