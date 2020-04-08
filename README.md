# Kotlin Sudoku Code

This is a toy project written in Kotlin for representing, validating, producing
and solving Sudoku puzzles.

I make no guarantees that the code here is the most efficient, elegant or even
neat, it is simply a toy project.

## Progress

So far I have got the following working:

* Representing Sudokus in code
  - As immutable objects using a builder
  - As mutable objects using map
* Validating Sudokus
* Generating Sudokus
  - This has been done with very costly generation


## Examples

```text
┌───┬───┬───┐
│625│389│174│
│491│756│328│
│738│124│695│
├───┼───┼───┤
│584│932│761│
│163│875│942│
│279│641│853│
├───┼───┼───┤
│312│597│486│
│956│418│237│
│847│263│519│
└───┴───┴───┘
```

```text
┌───┬───┬───┐
│985│623│471│
│314│795│682│
│627│814│935│
├───┼───┼───┤
│798│136│254│
│152│487│396│
│463│259│718│
├───┼───┼───┤
│576│941│823│
│841│372│569│
│239│568│147│
└───┴───┴───┘
```