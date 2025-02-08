# Project-1-CS-361

## Overview
This project is a Java implementation of a **Deterministic Finite Automaton (DFA)** for CS 361. The project models DFA behavior, including state transitions, string acceptance, and symbol swapping.

## Features
- Implements `DFA.java` following the `DFAInterface.java` specification.
- Uses `DFAState.java` to represent DFA states.
- Supports adding states, setting start and final states, and defining transitions.
- Implements `swap(char symb1, char symb2)` to swap transition labels.
- Provides a `toString()` method for DFA representation.
- Includes JUnit 5 test cases in `DFATest.java`.


## Running Tests
To run the JUnit tests:
"mvn test"

Expected output:

[INFO] Running test.dfa.DFATest
[INFO] Tests run: X, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS


## Usage
The DFA implementation supports the following operations:
- **Add states:** `dfa.addState("a");`
- **Define alphabet:** `dfa.addSigma('0');`
- **Set start/final states:** `dfa.setStart("a"); dfa.setFinal("b");`
- **Add transitions:** `dfa.addTransition("a", "b", '1');`
- **Check string acceptance:** `dfa.accepts("101");`
- **Swap transition symbols:** `dfa.swap('0', '1');`


