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

## [Day 03](./src/Day03.kt): _Lobby_

Before powering the escalator, we need to evaluate each bank of batteries to determine the strongest joltage it can produce.

- **Part 1:** Identify the two digits in each battery bank that form the largest possible two-digit number while preserving their left-to-right order;
- **Part 2:** Choose exactly twelve digits per bank to create the largest possible 12-digit number, still respecting the original order.

**→ Solution:** Transverse through each bank and check if the current battery has a larger joltage than the one saved. But everytime we swap a battery, we need to invalidate all the saved ones in front of it.

## [Day 04](./src/Day04.kt): _Printing Department_

To progress deeper into the North Pole base, the Elves need help managing their massive rolls of paper so the forklifts can free up time.

- **Part 1:** Determine which paper rolls can be reached by forklifts based on their surroundings. A roll is accessible only if fewer than four adjacent positions (out of eight) contain other rolls.
- **Part 1:** Simulate repeated removals of accessible rolls. Each time rolls are removed, accessibility changes, potentially unlocking more removable rolls.

**→ Solution:** Iterate through each line of the input and check every possible direction.

## [Day 05](./src/Day05.kt): _Cafeteria_

The kitchen’s new inventory system has left the Elves unsure which ingredients are usable. To help them sort fresh items from spoiled ones, we must interpret their list of ID ranges.

- **Part 1:** Identify which available ingredient IDs fall within any of the listed fresh ID ranges. Because ranges may overlap, each ID must be checked against all intervals to determine whether it qualifies as fresh.
- **Part 2:** Ignore the list of available IDs and calculate every possible ID covered by the combined fresh ranges. The challenge lies in merging overlapping intervals and counting all unique IDs included within them.

**→ Solution:** Sort ranges and merge overlapping ones.
