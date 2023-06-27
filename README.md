# amazon-warehouse-simulator
This project creates a virtual Amazon Fulfillment Center and simulates the Simple Bin Count (SBC) inventory management process.

IMPORTANT: This is the same project as JGAguilar/simple-bin-count-simulator. I created this additional repository with the intention of having a clean project repo, while using the JGAguilar/simple-bin-count-simulator repo to learn and experiment with Git/GitHub.

## Project Description
This project creates a virtual Amazon Fulfillment Center and simulates the Simple Bin Count (SBC) inventory management process.

The virtual FC created by this program is modeled after legacy Amazon FCs. Legacy (aka "traditional") Amazon FCs are those without Amazon Robotics (formerly Kiva) technology. The program creates an FC with 76,384 inventory receptacles and assigns each one an unique location address and pick-path ID.

Users can log-in to the SBC process and count the number of items physically present in the bin, checking for mismatches between physical and virtual inventory amounts.

## Project Aim
The initial aim of this project was to help me learn good programming fundamentals and to simulate the SBC process.

In 2020, I left my job as an Amazon FC associate in order to pursue a few personal projects, the main one being teaching myself software development.

When thinking of ideas for good portfolio projects, I decided to simulate the SBC process, something I spent many hours doing as an Amazon ICQA (Inventory Control/Quality Assurance) associate.

On a whim, I decided to expand the project's scope by challenging myself to reverse engineer ONT2's (an Amazon legacy FC) pick-path pattern. An efficient pick-path pattern is crucial for a warehouse/FC the size of ONT2, which I estimate has about half a million inventory receptacles, each one requiring an unique and precise pick-path ID. Manual pick-path ID assignment being out of the question for obvious reasons, I was faced with creating my first set of business-critical algorithms.

## Technologies, Set-Up and Configuration
This program uses Amazon Coretto JDK 17.0.0_35.

This program reads-in inventory receptacle locations and their corresponding pick-path IDs from two text files.\
Copy/Paste these two files (p1a-receptacles-locations.txt / p1a-receptacles-pickpath-ids.txt) directly to the project's root folder.

The values were generated using algorithms that I have decided to omit from this version of the program.

## Program Instructions
At the program start screen where it prompts the user to enter an employee ID,

![start-screen](https://github.com/JGAguilar626/simple-bin-count-simulator/assets/129235347/a66fdc77-f0e7-4720-ba4e-f6661db7096b)

Enter:\
**10404** for employee Jose Aguilar (joseag) - has SBC process permissions\
**100** for employee Zachary Griffith (griffiz) - has SBC process permissions\
**101** for employee Justin Daniels (judanie) - has SBC process permissions, can access Mastermind with password "123"\
**102** for employee Jonathan Torres (jontorre) - has SBC process permissions, can access Mastermind with password "123"

-----

While in the SBC process, when prompted to "Scan location or enter 1", additional valid inputs are:

![sbc-scan-location-prompt](https://github.com/JGAguilar626/simple-bin-count-simulator/assets/129235347/592d707b-c1e5-4eca-a1c7-cf6401a91ef4)

**p** or **P**- for (P)roblem Menu, in case the user needs to create a location-related andon\
**n** or **N** - for (n)ext bin, which allows the user to skip the current bin\
**s** or **S** - for (s)ign-out of the SBC process, takes the user back to the ICQA Process Selection menu

-----

While in the SBC process, when prompted to count/re-count items, the user can also input:

![sbc-count-items-prompt](https://github.com/JGAguilar626/simple-bin-count-simulator/assets/129235347/e8da4d94-2497-4a11-aa64-49556d2ee5a5)

**p** or **P**- for (P)roblem Menu, which allows the user to create an inventory-related andon

## Features
- The program creates 1 floor of inventory receptacles (76,384 bins) and assigns each one a unique address.
- The program assigns each bin a unique pick-path id and uses this value to form a pick-path.
- The program creates a simplified version of Amazon's Simple Bin Count (SBC) process, an inventory management process.
- Via Mastermind, certain employees can see FC-related metrics.
- Via Test Mode, the user can verify that every bin is given a precise address and pick-path id.

-----

Additional details related to the floor layout and inventory receptacles:

This program creates 1 floor with 8 rows of 124 aisles. Each aisle (per row) contains 77 bins.\
\
Inventory receptacles addresses are based on the following convention:\
PrimeFloorLevelModLetter-AisleNumber-BinLevel-BinRowBinNumber.\
\
Thus, all inventory receptacles have addresses such as: P1A-102-A-100\
\
Using this naming convention makes it possible for an employee to locate an exact bin in a warehouse that, I estimate, can have around 500,000 locations.\
\
For example, below is what the wall of bins at aisle P1A-102-100s looks like:

![P1A-102-100s Bin Wall](https://github.com/JGAguilar626/simple-bin-count-simulator/assets/129235347/a96c373e-6bb1-4db8-be0b-0489328d1fc0)

-----

Additional details related to the pick-path:

Amazon's pick-path is a virtual line that, in a "snake-line" manner, passes through every bin and connects them all together. Imagine a scenario in which an employee is tasked with picking one item from every bin in the FC. The most efficient way to complete this task is to follow a snake-like pattern that minimizes unnecessary movements from bin to bin, aisle to aisle, row to row, floor to floor, and mod to mod.\
\
Below are 2 illustrations of what the pick-path at the P1A-102/103-100s looks like. The encircled numbers are the pick-path ids, and the red line demonstrates the direction of the pick-path. The photo with the sticky note gives a bird's eye representation of an associate in between two walls of bins. To his left are the P1A-102-100s, and to his right are the P1A-103-100s. The pick-path travels "up" towards the P1A-102-200s and P1A-103-200s.\
\
![P1A-102-100s-with-pick-path-id-and-line](https://github.com/JGAguilar626/simple-bin-count-simulator/assets/129235347/ba34e312-7657-48a1-8fec-e3af2365bfbc)\
\
![P1A-103-100s-with-pick-path-id-and-line](https://github.com/JGAguilar626/simple-bin-count-simulator/assets/129235347/6ab2716b-ca06-4667-8fbc-e8b5838d7d20)\
\
![star-between-walls](https://github.com/JGAguilar626/simple-bin-count-simulator/assets/129235347/bd3fed2a-b2a0-4484-92c3-65c1860e0ada)

Below, I include a hand-drawn diagram of the floor created by my program. It's almost exactly the same as real-life ONT2's floor P1A layout. Note that although it might seem like the conveyors are preventing travel between certain rows, in real-life they are actually elevated at the points in which the pick-path (red-line) passes through them. At these points (and at other points not represented in the illustration) the conveyor is physically above associates' heads as they move between rows. For the sake of simplicity, I omitted aisles 126-204 from the diagram, but they are present in the program.

![SBC Sim P1A Floor Overview](https://github.com/JGAguilar626/simple-bin-count-simulator/assets/129235347/f3a9bf9b-f2a2-4429-8592-5238becf4c78)

## Additional Information
***Please note that I have never seen a single line of code pertaining to Amazon FC software. Nor did I receive help/guidance from anybody formerly/currently employed by Amazon.*** I created the algorithms myself based on my own memories and experiences working as a Level 1 Fulfillment Associate at ONT2, an Amazon FC in San Bernardino, CA. I am currently working in the same role
at SAN3, an Amazon FC in San Diego, CA.
