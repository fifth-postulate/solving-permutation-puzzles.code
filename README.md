# Code for Solving Permutation Puzzles
Code samples accompanying the book solving permutation puzzles with the aid of a computer

## Organization
This repository is organized in different branches. Each branch contains samples in a different language.

## Files
There are files that are common to all branches. The format of those files will be described now.

### Graphs
Graphs are encoded in files with the `.graph` extension. It consist of a line for each vertex in the graph. A line lists
a vertex label, a literal colon and possibly an empty sequence of vertices seperated by literal commas.

```EBNF
graph      = line*
line       = vertex ':' neighbours
neighbours = ''
           | vertex (',' vertex)*
vertex     = [1-9]\d*
```

### Groups
Groups are encoded in files with the `.group` extension. It consists of a line for each generator in the graph. A line
describes the optional generator symbol, a literal colon and the image of the permutation.

```EBNF
group       = line*
line        = (symbol ':')'? permutation
permutation = image (',' image)*
symbol      = \w+
image       = [1-9]\d*
```