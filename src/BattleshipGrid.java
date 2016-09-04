/*
 * CSC 221 - Assignment 3
 * Battleship Game
 * by Klaudio Vito
 * 10/07/2015
 */

import java.util.Random;
/**
 * This class represents a Battleship game grid.
 */
public class BattleshipGrid
{
    //declare random object
    private static Random rnd = new Random();
    
    public static final int OUT_OF_BOUNDS = -1;

    // values to be used in the 2D grid
    public static final int EMPTY = 0;
    public static final int SHIP = 1;
    public static final int HIT = 2;
    public static final int MISS = 3;

    // directions used for shape placement
    private static final int DIRECTION_RIGHT = 0;
    private static final int DIRECTION_DOWN = 1;

    // lengths of the various ships in the game
    public static int AIRCRAFT_CARRIER_LENGTH = 2 +  rnd.nextInt(3);
    public static int BATTLESHIP_LENGTH = 2 +  rnd.nextInt(3);
    public static int DESTROYER_LENGTH = 2 +  rnd.nextInt(3);
    public static int SUBMARINE_LENGTH = 2 +  rnd.nextInt(3);
    public static int PATROL_BOAT_LENGTH = 2 +  rnd.nextInt(3);

    // array of all ship lengths
    private static int[] SHIP_LENGTHS =
    {
    	AIRCRAFT_CARRIER_LENGTH, //twice because we will place two aircrafts
        AIRCRAFT_CARRIER_LENGTH,
        BATTLESHIP_LENGTH,
        DESTROYER_LENGTH,
        SUBMARINE_LENGTH,
        PATROL_BOAT_LENGTH
    };

    // the total number of hits required to destroy all ships    
    private static int totalHitsRequired;

    // the number of rows and columns in this grid
    public final int NUM_ROWS;
    public final int NUM_COLS;

    //declare 2d array, random row, random column, and random direction variables
    private int [][]grid;
    private int randomRow;
    private int randomCol;
    private int randomDir;

    /**
     * Creates a new BattleshipGrid instance and randomly places
     * ships on the grid.
     *
     * @param    numRows    the number of rows in this grid
     * @param    numCols    the number of columns in this grid
     */
    public BattleshipGrid(int numRows, int numCols){
    	/*
    	//get random length for ships
    	AIRCRAFT_CARRIER_LENGTH = 2 +  rnd.nextInt(3);
    	BATTLESHIP_LENGTH = 2 +  rnd.nextInt(3);
    	DESTROYER_LENGTH = 2 +  rnd.nextInt(3);
    	SUBMARINE_LENGTH = 2 +  rnd.nextInt(3);
    	PATROL_BOAT_LENGTH = 2 +  rnd.nextInt(3);
    	*/
    	//set number of rows and columns
    	NUM_ROWS = numRows;
        NUM_COLS = numCols;

        // initialize the grid so all cells are EMPTY
        initializeGrid();

        // determine the total number of hits required to win
        calculateTotalHitsRequired();

        // randomly place all of the ships on the grid
        placeAllShips();
    }
    
    /**
     * Initializes the game grid to size [NUM_ROWS][NUM_COLS]
     * and sets each element of the grid to the constant EMPTY.
     */
    private void initializeGrid()
    {
    	
        // initialize grid as a 2D ARRAY OF THE APPROPRIATE DIMENSIONS
    	grid = new int [NUM_ROWS][NUM_COLS];


        //INITIALIZE ALL ELEMENTS OF grid TO THE VALUE EMPTY
    	for(int row = 0; row < NUM_ROWS; row++){
    		for(int col = 0; col < NUM_COLS; col++){
    			grid[row][col] = EMPTY;
    		}
    	}
    }

    /**
     * Calculates the total number of hits required to
     * destroy all ships and win the game.
     */
    private static void calculateTotalHitsRequired()
    {
        totalHitsRequired = 0;
        
        // add all the ship lengths together to get
        // the total number of hits required
        for (int length : SHIP_LENGTHS)
        {
            totalHitsRequired += length;
        }
    }

    //method to check if there is an overlap
    //if there are other ships in the way or the new ship is out of bounds it returns false
    //otherwise it returns true
    private boolean checkOverlap(int row, int col, int length){
    	if(!inBounds(row,col)){
    		return false;
    	}
    	for(int i = row; i < row + length; i++ ){
			for(int j = col; j < col + length; j++){
				if(grid[i][col] == 1 || row + length >= 10)
					return false;
				if(grid[row][j] == 1 || col + length >= 10)
					return false;
			}
    		
    	}
    	return true;
    }
    /**
     * Places all of the game's ships onto the grid.
     */
    private void placeAllShips()
    {
    	
    	//place 2 aircrafts
    	for(int n = 0; n < 2; n++){
    	randomRow = rnd.nextInt(10); //get random integer from 0-9
    	randomCol = rnd.nextInt(10);//get random integer from 0-9
    	randomDir = rnd.nextInt(2);//get random integer from 0-1
    	while(!checkOverlap(randomRow,randomCol,AIRCRAFT_CARRIER_LENGTH)){ //check if random integers overlap existing ones
    		randomRow = rnd.nextInt(10);
        	randomCol = rnd.nextInt(10);
    	}
    	//place aircraft if no overlap
    	for(int i = randomRow; i <randomRow + AIRCRAFT_CARRIER_LENGTH ; i++){
    		for(int j = randomCol; j < randomCol + AIRCRAFT_CARRIER_LENGTH; ++j){
    			if(randomDir == DIRECTION_RIGHT)
    				grid[i][randomCol] = SHIP;
    			if(randomDir == DIRECTION_DOWN)
    				grid[randomRow][j] = SHIP;
    			}
    		}
    	}
    	
    	
    	//place battleship
    	randomRow = rnd.nextInt(10);
    	randomCol = rnd.nextInt(10);
    	randomDir = rnd.nextInt(2);
    	while(!checkOverlap(randomRow,randomCol,BATTLESHIP_LENGTH)){
    		randomRow = rnd.nextInt(10);
        	randomCol = rnd.nextInt(10);
    	}
    	for(int i = randomRow; i <randomRow + BATTLESHIP_LENGTH ; i++){
			for(int j = randomCol; j < randomCol + BATTLESHIP_LENGTH; j++){
				if(randomDir == DIRECTION_RIGHT)
					grid[i][randomCol] = SHIP;
				if(randomDir == DIRECTION_DOWN)
					grid[randomRow][j] = SHIP;
			}
		}	
    	//place destroyer
    	randomRow = rnd.nextInt(10);
    	randomCol = rnd.nextInt(10);
    	randomDir = rnd.nextInt(2);
    	while(!checkOverlap(randomRow,randomCol,DESTROYER_LENGTH)){
    		randomRow = rnd.nextInt(10);
        	randomCol = rnd.nextInt(10);
    	}
    	for(int i = randomRow; i < randomRow + DESTROYER_LENGTH ; i++){
			for(int j = randomCol; j < randomCol + DESTROYER_LENGTH; j++){
				if(randomDir == DIRECTION_RIGHT)
					grid[i][randomCol] = SHIP;
				if(randomDir == DIRECTION_DOWN)
					grid[randomRow][j] = SHIP;
			}
		}	
    	//place submarine
    	randomRow = rnd.nextInt(10);
    	randomCol = rnd.nextInt(10);
    	randomDir = rnd.nextInt(2);
    	while(!checkOverlap(randomRow,randomCol,SUBMARINE_LENGTH)){
    		randomRow = rnd.nextInt(10);
        	randomCol = rnd.nextInt(10);
    	}
    	for(int i = randomRow; i < randomRow + SUBMARINE_LENGTH ; i++){
			for(int j = randomCol; j < randomCol + SUBMARINE_LENGTH; j++){
				if(randomDir == DIRECTION_RIGHT)
					grid[i][randomCol] = SHIP;
				if(randomDir == DIRECTION_DOWN)
					grid[randomRow][j] = SHIP;
			}
		}
    	//place patrol
    	randomRow = rnd.nextInt(10);
    	randomCol = rnd.nextInt(10);
    	randomDir = rnd.nextInt(2);
    	while(!checkOverlap(randomRow,randomCol,PATROL_BOAT_LENGTH)){
    		randomRow = rnd.nextInt(10);
        	randomCol = rnd.nextInt(10);
    	}
    	for(int i = randomRow; i < randomRow + PATROL_BOAT_LENGTH ; i++){
			for(int j = randomCol; j < randomCol + PATROL_BOAT_LENGTH; j++){
				if(randomDir == DIRECTION_RIGHT)
					grid[i][randomCol] = SHIP;
				if(randomDir == DIRECTION_DOWN)
					grid[randomRow][j] = SHIP;
			}
		}
    }
    /**
     * Attacks the grid cell at the specified row and column.
     * If the grid cell contains:
     *   - SHIP: the value of the cell is set to HIT
     *   - HIT: the value of the cell does not change
     * Otherwise, the value of the cell is set to MISS.
     *
     * This method returns true if the attack resulted in a ship being hit,
     * and false otherwise.
     *
     * Note: this method also returns true if a cell that has already
     * been hit is attacked.
     *
     * @param    row    the row of the cell to attack
     * @param    col    the column of the cell to attack
     *
     * @return   false if the attack results in a ship being hit (even
     *           if the ship at that cell has already been hit),
     *           false otherwise
     */
    public boolean attack(int row, int col)
    {
        
    	if(inBounds(row,col) == false)
    		return false;
    	else if(grid[row][col]==EMPTY){
    		grid[row][col] = MISS;
    		return false;
    	}
    	else if(grid[row][col] == HIT)
    		return false;
    	else if(grid[row][col]==SHIP){
    		grid[row][col] = HIT;
    		return true;
    	}
    	
    	
    	
        return true;
    }

    /**
     * Returns true if all of the ships have been destroyed, and
     * false otherwise.
     *
     * @return   true if all ships have been destroyed, false otherwise
     */
    public boolean allDestroyed()
    {

    	calculateTotalHitsRequired();
    	int hits = 0;
    	for (int row = 0; row < NUM_ROWS; row++){
    		for(int col = 0; col < NUM_COLS; col++){
    			if(grid[row][col] == HIT)
    				hits++;
    		}
    	}
    	if(hits == totalHitsRequired)
    		return true;

        return false;
    }



    /**
     * Determine if the cell at the specified row and column is in
     * the grid's bounds.
     *
     * @param    row    the cell's row
     * @param    col    the cell's column
     *
     * @return   true if the cell is in bounds, false otherwise
     */
    private boolean inBounds(int row, int col)
    {
        return ((row >= 0) && (row < NUM_ROWS) &&
                (col >= 0) && (col < NUM_COLS));        
    }

    /**
     * Returns the value of the cell at [row][col] if the cell is in
     * bounds. Otherwise, returns OUT_OF_BOUNDS.
     *
     * @param    row    the cell's row
     * @param    col    the cell's column
     *
     * @return   if in bounds, the value of the cell at [row][col],
     *           otherwise OUT_OF_BOUNDS
     */
    public int getCell(int row, int col)
    {
        if (inBounds(row, col))
            return grid[row][col];
        else
            return OUT_OF_BOUNDS;
    }

    /**
     * Prints the grid.
     */
    public void printGrid()
    {
        // ::: FILL IN THIS CODE
    	for(int row = 0; row < NUM_ROWS; row++){
    		for(int col = 0; col < NUM_COLS; col++){
    			System.out.print(grid[row][col] + " "); 
    		}
    		System.out.println();
    	}
    }
}
