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

## [Day 06](./src/Day06.kt): _Trash Compactor_

Stranded in a garbage smasher, you pass the time helping a young cephalopod with her unusual math worksheet.

- **Part 1:** Extract each vertically aligned problem, ignoring internal spacing, and evaluate it left-to-right using the indicated operator.
- **Part 2:** Reinterpret the worksheet by reading each problem right-to-left by column. This requires reconstructing numbers from top to bottom per column, identifying problem boundaries, and recalculating totals under this reversed reading rule.

**→ Solution:** Read each digit until we find a full empty column, and then add the current total to the final total.

## [Day 07](./src/Day07.kt): _Laboratories_

Exploring a teleportation lab, you encounter a faulty tachyon manifold that must be understood before repairs can begin. The system routes downward-moving beams through a grid filled with beam splitters.

- **Part 1:** Simulate how tachyon beams travel through the manifold. Beams move straight down until they hit a splitter `^`, where they stop and generate new beams to the left and right. The challenge is accurately tracking all beams, handling overlaps, and counting every split event.
- **Part 2:** Reinterpret the manifold using quantum rules. A single particle follows both paths at every splitter, creating branching timelines. The challenge is to count all distinct possible end states by modeling every branching path through the grid.

**→ Solution:** _For the first part_, we need to keep track of the points we've already seen and store the beams we are currently following in a queue. _For the second part_, we need to use a `backtracking strategy` with a `cache` that stores the timelines we've already explored.
