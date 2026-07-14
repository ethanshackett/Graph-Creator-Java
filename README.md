# Graph Creator

This program gives the user a JavaFX GUI on which they can create a graph of nodes that the user chooses.  
It also provides the option for analysis by giving search paths, connectivity, and cyclicity.

## Features
- Creates a graph with user-inputted nodes and edges
- Displays the graph via JavaFX
- Completes depth-first search and breadth-first search
- Analyzes graph for connectivity and cyclicity

## Technologies Used
- Java 17
- JavaFX
- Apache Maven
- JUnit 5

## Prerequisites
- Java 17 or later
- Maven 3.8.5 or later
- Git if cloning via the command line

## Installation/Running
```bash
git clone https://github.com/ethanshackett/Graph-Creator-Java.git
cd Graph-Creator-Java

mvn clean javafx:run
```

## Running Tests
JUnit tests can be run with:
```bash
mvn clean test
```

In intelliJ, they can also be run through the Maven side tab.

## Usage
1. After running the JavaFX command, the JavaFX window should pop up.
2. In the blank square area, click where you want to place the nodes. 
3. Add edges by entering the names of the nodes in the two textboxes above and clicking "Add Edge". 
4. Use the buttons below for various analyses of the graph.

Vertices are named by order placed.