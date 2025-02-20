# fastLang: User Manual & Language Specification

## 1. Introduction
fastLang is a simple programming language designed for educational purposes. It helps beginners learn basic programming concepts such as variable declarations, control flow, and input/output operations.

## 2. Keywords
fastLang supports the following reserved keywords:

| Keyword  | Description                        |
| -------- | ----------------------------------- |
| `int`    | Defines an integer variable         |
| `float`  | Defines a floating-point variable   |
| `bool`   | Defines a boolean variable          |
| `string` | Defines a string variable           |
| `if`     | Conditional statement               |
| `else`   | Alternative block for `if`          |
| `while`  | Looping construct                   |
| `return` | Returns a value from a function     |
| `true`   | Boolean literal (true)              |
| `false`  | Boolean literal (false)             |
| `print`  | Prints output to the console        |
| `scanf`  | Reads input from the user           |

## 3. Lexical Rules
### 3.1 Identifiers
- Must begin with a lowercase letter (a-z).
- Can contain digits (0-9) after the first letter.
- Cannot be a reserved keyword.

Example:
```fastlang
int myVar = 10;
float myNumber = 3.14;
```

### 3.2 Constants
#### 3.2.1 Integer Constants
- A sequence of digits (0-9).
- Cannot have leading zeros unless 0 itself.
- Example: `0, 42, 123`

#### 3.2.2 Decimal Constants
- Must contain a decimal point (.) with at least one digit before and after.
- Example: `3.14, 0.5, 99.999`

#### 3.2.3 Boolean Constants
- `true` or `false`.

#### 3.2.4 String Constants
- Enclosed in double quotes (" ").
- Example: `"Hello, world!"`

#### 3.2.5 Character Constants
- A single character enclosed in single quotes (' ').
- Example: `'a', '5', '*'`

### 3.3 Operators
#### 3.3.1 Arithmetic Operators

| Operator | Description  |
| -------- | ------------ |
| `+`      | Addition     |
| `-`      | Subtraction  |
| `*`      | Multiplication|
| `/`      | Division     |
| `%`      | Modulus      |

#### 3.3.2 Relational Operators

| Operator | Description             |
| -------- | ----------------------- |
| `==`     | Equal to                |
| `!=`     | Not equal to            |
| `>`      | Greater than            |
| `<`      | Less than               |
| `>=`     | Greater than or equal to|
| `<=`     | Less than or equal to   |

#### 3.3.3 Logical Operators

| Operator | Description       |
| -------- | ----------------- |
| `&&`     | Logical AND        |
| `||`     | Logical OR         |
| `!`      | Logical NOT        |

#### 3.3.4 Assignment Operators

| Operator | Description       |
| -------- | ----------------- |
| `=`      | Assign a value     |

### 3.4 Grouping Symbols & Delimiters

| Symbol | Description                           |
| ------ | ------------------------------------- |
| `()`   | Parentheses for function calls        |
| `{}`   | Braces for block statements           |
| `[]`   | Square brackets (reserved for future use) |
| `;`    | Statement terminator                  |
| `,`    | Separator for function arguments      |

### 3.5 Comments
- **Single-line Comments:** Begin with `//` and continue until the end of the line.
- **Multi-line Comments:** Start with `/*` and end with `*/`.

Example:
```fastlang
// This is a single-line comment
/* This is a
multi-line comment */
```

## 4. Syntax Rules
### 4.1 Variable Declarations
```fastlang
int x = 10;
float y = 20.5;
bool flag = true;
string name = "John";
```

### 4.2 Conditional Statements
```fastlang
if (x > 0) {
  print("Positive");
} else {
  print("Non-positive");
}
```

### 4.3 Loops
```fastlang
while (x > 0) {
  print(x);
  x = x - 1;
}
```

### 4.4 Function Calls
```fastlang
print("Hello, World!");
scanf(x);
```

## 5. Using the Lexical Analyzer
To analyze fastLang code:
1. Run the Lexical Analyzer on your source file.
2. Tokenize the input based on the rules above.
3. Errors are reported for missing semicolons, undeclared variables, and invalid tokens.

### Common Errors Detected

| Error Type         | Example                                      |
| ------------------ | -------------------------------------------- |
| Undeclared variable | `y = 20.5;` (y was not declared)             |
| Duplicate declaration | `int x = 10; int x = 5;`                  |
| Unrecognized token  | `@x = 5;` (invalid character @)             |

## 6. Example Program
```fastlang
int x = 10;
float y = 20.5;
int result = x + y;
if (result > 20) {
  print("Result is greater than 20");
}
```

