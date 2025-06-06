# Life Engine: The Cellular Automata Engine
An easy to use, java based program for creating your own cellular automata

If you run the program, the default rules are those of Conway's Game of Life.\
If you want to test your own cellular automata rules, there are a few simple steps you need to follow.

## Setting Up Custom Rules
### Creating a Custom Rule Set
In the resources > rules folder, create a new json file.\
Below is an example of the GameOfLife rules json file:
```
{
	"deathValues" : [1, 4],
	"survivialValues": [2, 3],
	"populationValues": [3],
	"neighborPadding" : 1
}
```
Cells will die when they have less than or equal to ```deathValues[0]``` neighbors, and more than or equal to ```deathValues[1]```.\
A cell will die when when it has equal to ```deathValues[ANY_NUMBER_GREATER_THAN_TWO]``` neighbors.\
For example, ```"deathValues" : [3, 9, 4, 6, 7],``` will cause cells to die when they have less than or equal to 3 neigbors, more than or equal to 9 neghbors, and exactly 4, 6, and 7 neighbors.\
Cells will survive for the next iteration if the amount of neighbors they have equals any of the survivialValues. An empty cell will become populated if the amount of neighbors it has equals any of the population values.\
To update the amount of cells in each direction you want to check for neighbors, update ```neighborPadding```. In the case of Conway's Game of Life, a padding of 1 checks the 8 surrounding cells.

### Applying a Custom Rule Set
In the ``Rules`` class, change the line ```InputStream ruleFile = GraphicsManager.class.getResourceAsStream("/rules/GameOfLife_rules.json");``` to load the json file you just wrote: ```InputStream ruleFile = GraphicsManager.class.getResourceAsStream("/rules/MY_NEW_RULES.json");```

