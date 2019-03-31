#Parser
###Introduction
This is my attempt to create a parser capable of parsing mathematical expressions for use in my upcoming calculator app in Android.

Currently supported functionalities and operators are:
* Add, subtract, multiply and divide
* Exponentation and roots
* Absolute value with pipe operators ("|")
* uses fractions to represent the numbers exactly (ending or repeating decimal) or apporoximately (enless non-repeating decimal)
    * It has the ability to parse repeating decimals into fractions

###Order of Operations
1. **Ellipsis** (left to right)
2. **Exponentiation** (right to left)
3. **Unary minus** (left to right)
4. **Multiplication**, division (left to right)
5. **Addition**, **subtraction** (left to right)
6. 



