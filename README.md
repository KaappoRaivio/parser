#Parser

###Introduction
This is my attempt to create a parser capable of parsing mathematical expressions for use in my upcoming calculator app for Android.

Currently supported functionalities are:
* User-definable operators
    * These can be binary or unary operators. With unary operators, they can be either prefix (ie. square root) or suffix (ie. factorial).
    * Comes with some built-in operators
    * You can either bind your own operators to the built-in tokens or define your own.
* Ability to use rational numbers
    * Able to parse rationals from cycling, repeating decimals (ie. 3.33... &rarr; (1 / 3), 0.142857142857... &rarr; (1 / 7))

