/*
 * CSC 221 - Assignment 3
 * Battleship Game
 * by Klaudio Vito
 * 10/07/2015
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

//graphical representation of the game
@SuppressWarnings("serial")
public class BattleshipPanel extends JPanel
{
	//declare sound player object
	private AudioPlayer sound;

    // the width and height of this panel
    private final int PANEL_WIDTH;
    private final int PANEL_HEIGHT;
    
    //create objects grid and hitMissPanel
    private BattleshipGrid grid;
    private HitMissPanel hitMissPanel;
    
    //declare number of hits and misses variables that will be used in the hitMissPanel
    private int numHits;
    private int numMisses;
 	
    //initialize the buttons, reset, quit, and hint
    private JButton reset = new JButton("Reset");
    private JButton quit = new JButton("Quit");
    private JButton hint = new JButton ("Hint"); 
    
    //boolean variable that will be used to display hints
    public static boolean clicked = false;
    
    //initialize message string to display information about the game and its rules
    private String message = 
    		"Welcome to the Battleship Game!!!\n"
    		+ "Sink all six enemy ships.\n"
    		+ "Hint can help!\n"
    		+ "Click 'OK' to start, or 'CANCEL' to exit";
    @SuppressWarnings("static-access")
    //battleship constructor, takes four arguments
    public BattleshipPanel(int width, int height, BattleshipGrid battleshipGrid, HitMissPanel hmp)
    {
    	
    	//load battleship.wav from sounds folder
    	sound.loadClip("battleship", getClass().getClassLoader().getResource("sounds/battleship.wav"));
    	
    	//set panel width and height, grid, and hitMissPanel to the inputed arguments
        PANEL_WIDTH = width;
        PANEL_HEIGHT = height;
        grid = battleshipGrid;
        hitMissPanel = hmp;
        
        //initialize number of hits and misses to 0 since no shot has been taken yet
        numHits = 0;
        numMisses = 0;
        
        //add action listener to buttons
        reset.addActionListener(new Reset());
        quit.addActionListener(new Quit());
        hint.addActionListener(new Hint());
        
        //add button to the frame
        add(hint);
        add(reset);
        add(quit);
        
        //start app by displaying rules dialog and option to exit the game without even starting
        int reply = JOptionPane.showConfirmDialog(null, message, "Game Rules",2);
        if(reply == JOptionPane.CANCEL_OPTION)
        	System.exit(0);
        else if (reply == JOptionPane.OK_OPTION)
        	sound.play("battleship", true);
        
        //set panel size to the width and height
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        
        //mouse listener to do the attacking
        addMouseListener(
            new MouseAdapter()
            {
                public void mouseClicked(MouseEvent e)
                {
                	
                    // attack the grid based on where the user clicked
                  	attackGrid(e.getX(), e.getY());
                    // update the graphics
                    repaint();
                   
                    // have all of the ships been destroyed?
                    if (grid.allDestroyed())
                    {	
                    	//shut down music when all ships are destroyed
                    	sound.shutdown();  	 
                    	                    	 
                    	//game is over, check if user want to restart or quit
                    	int reply1 = JOptionPane.showConfirmDialog(null, "You win!\n\"OK\" to restart \"Cancel\" to quit", "Game Over",2);
                        //if cancel then quit
                    	if(reply1 == JOptionPane.CANCEL_OPTION)
                        	System.exit(0);
                    	//if ok then restart game
                        else if(reply1 == JOptionPane.OK_OPTION){
                        	grid = new BattleshipGrid(grid.NUM_ROWS,grid.NUM_COLS);
                			numHits = 0;
                			numMisses = 0;
                			hitMissPanel.setStats(numHits, numMisses);
                			repaint();
                			hint.setEnabled(true);
                			clicked = false;
                        }
                        	
                    }
                }
            }
        );
    }
    
    //action listener for reset button, resets parameters to initial
    class Reset implements ActionListener{
    	@SuppressWarnings("static-access")
    	public void actionPerformed(ActionEvent e){
			Random rnd = new Random();
			grid = new BattleshipGrid(grid.NUM_ROWS,grid.NUM_COLS);
			//get random length for ships
	    	grid.AIRCRAFT_CARRIER_LENGTH = 2 +  rnd.nextInt(3);
	    	grid.BATTLESHIP_LENGTH = 2 +  rnd.nextInt(3);
	    	grid.DESTROYER_LENGTH = 2 +  rnd.nextInt(3);
	    	grid.SUBMARINE_LENGTH = 2 +  rnd.nextInt(3);
	    	grid.PATROL_BOAT_LENGTH = 2 +  rnd.nextInt(3);
			numHits = 0;
			numMisses = 0;
			hitMissPanel.setStats(numHits, numMisses);
			repaint();
			hint.setEnabled(true);
			clicked = false;
    		
		}
	}
    
    //Action Listener to quit app
    class Quit implements ActionListener{ 
		public void actionPerformed(ActionEvent e){
			System.exit(0);//exit if clicked
		}
	}
    //Action Listener to display hint
    class Hint implements ActionListener{ 
    	public void actionPerformed(ActionEvent e){	
    		clicked = true;//set boolean variable to true in order to paint the hint
    		repaint(); //update graphics
		}	
    }
    
    public void paintComponent(Graphics g)
    {
    	//background set to light gray color
        setBackground(Color.LIGHT_GRAY);
        super.paintComponent(g);
        //if statement to check if Hint button was clicked
        if(clicked == true){
    		drawHint(g); //draws the hint from the drawHint method below
    	}
        //draw lines and cells
        drawGridLines(g);
        drawGridCells(g);
 
    }
    //method to draw ovals only if there is a ship in the location
    private void drawHint(Graphics g){
    	g.setColor(Color.GREEN);
    	for(int row = 0; row < grid.NUM_ROWS; row++){
    		for(int col = 0; col < grid.NUM_COLS; col++){
    			if (grid.getCell(row, col)==1){
    				g.fillOval(60*col+5, 60*row+5, 50, 50);
    			}
    		}
    	}
    }
    
    //method to draw the lines
    private void drawGridLines(Graphics g)
    {
        g.setColor(Color.WHITE);
        // draw vertical grid lines
        for (int x = 0; x < PANEL_WIDTH; x += PANEL_WIDTH / grid.NUM_COLS) {
            g.drawLine(x, 0, x, PANEL_HEIGHT - 1);
        }
        // draw horizontal grid lines
        for (int y = 0; y < PANEL_HEIGHT; y += PANEL_HEIGHT / grid.NUM_ROWS){
            g.drawLine(0, y, PANEL_WIDTH - 1, y);
        }
    }
    
    //method to draw cells
    private void drawGridCells(Graphics g)
    {
        // iterate over the entire grid
        for (int row = 0; row < grid.NUM_ROWS; row++)
        {
            for (int col = 0; col < grid.NUM_COLS; col++)
            {	
            	//if the cells are hit and attacked draw red ovals
            	if(grid.getCell(row, col)==2 && grid.attack(row, col)==false){
            		g.setColor(Color.RED);
            		g.fillOval(60*col+5, 60* row+5, 50, 50);
            	}
            	//if the cells are missed or not attacked draw blue ovals
            	else if(grid.getCell(row, col)==3 && grid.attack(row, col)==true){
            		g.setColor(Color.BLUE);
            		g.fillOval(60*col+5, 60* row+5, 50, 50);
            	}
            	
            }
        }
    }
    
    //method to attack grid according to mouse location
    private void attackGrid(int mouseX, int mouseY)
    {
    	clicked = false;
    	repaint();
    	//division of integers gives only integers
    	int row = mouseY/60;
    	int col = mouseX/60;
    	
    	//attack specified row and column
    	grid.attack(row, col);
    	
    	//update number of misses and number of hits
		if(grid.attack(row, col) == true)
			numMisses++;
    	else if (grid.attack(row, col) == false)
    		numHits++;
		
		//update hitMissPanel
        hitMissPanel.setStats(numHits, numMisses);
    }
}
