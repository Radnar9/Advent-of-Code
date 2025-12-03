# Advent of Code 2025

## [Day 01](./src/Day01.kt): _Secret Entrance_

The safe's dial ranges from 0 to 99 and starts at 50. A list of rotations (L for left, R for right)
moves the dial by a specified number of clicks, wrapping around the circle. 

- **Part 1:** Count how many times the dial ends a rotation on 0; 
- **Part 2:** Updated rules require counting every time a click lands on 0,
including mid-rotation; large moves may hit 0 many times.

**→ Solution:** It's like finding the index in a circular array, using the modulus with the size of the array,
but in this case, we have the number of dial numbers.

## [Day 02](./src/Day02.kt): _Gift Shop_

Based on given numeric ranges: 

- **Part 1:** Identify all the numbers in the range that have a digit sequence repeated exactly twice (like 55 or 123123) and sum them;

- **Part 2:** Identify all the numbers in the range that have any sequence that repeats two or more times (like 999 or 1010) and sum them.

**→ Solution:** Brute force by testing every number inside each range.
