# Nexon
**Inspiration** : Combines "next" and "on," suggesting forward-thinking and modern development.

This Project is the part of my compiler construction course at **FAST-NUCES Islamabad**.

## Rules

- It will be dynamically typed language.
- **Types:**
  - true/false values (Boolean)
  - whole numbers (Integer)
  - Decimal numbers (Decimal)
  - Single letters (Character).
- Variable Names lowercase letters (a to z)
- Basic arithmetic operations like addition (+), subtraction (-), multiplication
  (*), division (/), and remainder/modulus (%).
- Accurate up to five decimal places
- Support exponents (raising numbers to a power) for both whole numbers and decimal
  numbers.
- Handle multi-line comments properly, even in
  tricky situations.
- Support for globals and local variables.
- File extension **.nx**


## Keyword
- main
- int
- float
- char
- string
- true
- false
- print

## Other Valid Tokens
- int Numbers [0-9]
- float Numbers [0-9].[0.9][0-9][0-9][0-9][0-9]
- spaces 
- Operator + * / % - ^
- Single Line Comments !!(two exclamation marks)
- Multi Line Comment !-- (Exclamation two dashes)
- string as "*"


## Regular Symbols Just for reference
- \b (Word Boundary)
- \d (Digit
- +(One or More)
- ? (Optional / Zero or One)
- +(Zero or More)
- .(Dot / Any Character)
- [] (Character Set)
- [^ ]
- \s (Whitespace)
- (?: ) (Non-capturing Group)
- | (Alternation / OR)
- \* (Escape Sequence for Literal Characters)
- \. (Escaping the Dot)
- \S (Non-Whitespace Character)
- \b (Word Boundary)

## Regular Expression for the Language
(?x)(\b(print|int|float|double|main|char|true|false)|-?\b\d+\.\d+\b|-?\b\d+\b|\"(?:[^\"\\]|\\.)*\"|'(?:[^'\\]|\\.)*'|//.*|/\*[\s\S]*?\*/|\s+|\b[a-z]+\b|(\+|=|-|\*|%|^|;))



## Our Own Re

- . -> any thing
- [] -> range
- | union
- ? -> 0 or one occurrence
- !! -> single line comment
- !-- double line comment


- Re for keywords -> (main|int|float|char|string|true|false|print)
- int Numbers ([0-9])+
- float Numbers (([0-9])+.([0-9])+)
- spaces -> S
- Operator  (+|*|/|%|-|^)
- Single Line Comments   !!.
- Multi Line Comment   !-- . --!
- string as "*"  \"([^\"\\]|\\.)*\"


By creating the union for all of the above




- (_main|_int|_float|_char|_string|_true|_false|_print) | ([0-9])+ | S | (\+|\*|\/|%|-|^) |  !!.\n  | !-- . --! |  \"([^\"\\]|\\.)*\" | ([a-z]+)



