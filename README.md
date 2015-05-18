# Astar-Mystic-Square
Java Solver for the Mystic-square game (15-Puzzle) using A* algorithm

## Presentation
Develoment of a little library for the Mystic-square game containing:
* generation of boards with random moves,
* solving.

#### What's a Mystic-square/15 puzzle game ?
[...this:](http://en.wikipedia.org/wiki/15_puzzle)

![15-Puzzle grid](https://lh6.ggpht.com/BJiw2hQrNG5KEoKovdL8oJfAi8spxmWxoNq2oRHQReLy_s75V8m153YeLiumy2xcRIeE=w300-rw "15-Puzzle grid")

#### Launch Test
*java MainResolution [15-Puzzle dimension] [number of random moves from the start] [heuristique]*

heuristiques:
* **0**: number of wrongly placed cases,
* **1**: sum of the euclidian distances between current position and position on solution for all cases,
* **2**: idem but with Manhattan distance (or Taxi Block distance i.e. |x'-x|+|y-y'|) **BEST**

For details on the library, consult javadoc.

## Details on A* algorithm
Algorithm to find a way between two nodes in a graph, approximation of the shortest way. The search is guided with a heuristique estimating the distance to destination. Here a board configuration is a node with the initial being the start point and the solution being the final point.

From the current node we examine the yet unvisited neighboors and take the one with better heuristique:
* if it is the solution, we found a way,
* else this node becomes the current node.

If all nodes are visited before finding a solution, the board is unsolvable !
