*Student Name: Zhiwei Zhang*  
*Estimated Time: 18 hours*

# Summary

In this assignment a4 folder, some key components to a card game are implemented. The card game is NEUchre, which is a 2-player version of a game called Euchre. The traditional version is [*here*][1].

For the Part 1 of this assignment, the required functions which are implemented in ``deck.c`` are listed below:

  ``CreateDeck()``
  ``PushCardToDeck()``
  ``PeekAtTopCard()``
  ``PopCardFromDeck()``
  ``IsDeckEmpty()``
  ``PopulateDeck()``

Additionally, ``Shuffle()`` is also implemented with two helper functions: ``Swap()`` and ``Randomize()`` for Part 1. This function is modified from [*GeeksforGeeks*][2].

There are two milestones to this project. Milestone 1 is the Part 1 with some implementations of data structures for the game. Milestone 2 is to implement some logic to support the game play, including hand funciton and game function (already given).

For the other parts of this assignment, which are Milestone 2, the required functions which are implemented in ``a4.c`` are listed below:

  ``CreateHand()``
  ``AddCardToHand()``
  ``RemoveCardFromHand()``
  ``IsHandEmpty()``
  ``DestroyHand()``
  ``Shuffle()``
  ``Deal()``
  ``IsLegalMove()``
  ``WhoWon()``
  ``ReturnHandToDeck()``

Hand functionality is implemented as a doubly-linked list. 

Detailed definitions of these functions can be found in ``a4.h`` file.

[1]: https://cardgames.io/euchre/ "Title"
[2]: https://www.youtube.com/watch?v=LP7YQdT5eps "Title"

# How to build and run the code

The functions for Milestone 2 were initially commented out in ``a4_test.c`` except ``Shuffle()``. After the completion of Milestone 2, full test is enabled.

To build the test code in terminal:
```
make test
```
To run the test code in terminal:
```
make run_test
```

To check the memory leak:
```
valgrind --leak-check=full ./test
```

To build the game playing code in terminal:
```
make play
```
To run the game playing code in terminal:
```
make run_play
```

For detailed definitions of Makefile, see the ``Makefile`` file in this folder.

Note that when the game is at the selection of Round or Game, lower-case letters are valid ones.

# Files

This a4 folder includes 8 files in total: ``deck.c``, ``a4_test.c``, ``a4.h``, ``a4.c``, ``a4_run.c``, ``a4_helpers.c``, ``README.md``, ``Makefile``.

- Euchre: https://cardgames.io/euchre/
- Reference: https://www.youtube.com/watch?v=LP7YQdT5eps

