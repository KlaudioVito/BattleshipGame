/*
 * CSC 221 - Assignment 3
 * Battleship Game
 * by Klaudio Vito
 * 10/07/2015
 */

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class Battleship extends JApplet
{
    private static final int NUM_ROWS = 10;
    private static final int NUM_COLS = 10;
    private  final int PANEL_WIDTH = 601;
    private static final int PANEL_HEIGHT = 601;

    private BattleshipPanel battleshipPanel;
    private HitMissPanel hitMissPanel;

    public Battleship()
    {
        hitMissPanel = new HitMissPanel(PANEL_WIDTH, 50);
        battleshipPanel = new BattleshipPanel(PANEL_WIDTH, PANEL_HEIGHT,
            new BattleshipGrid(NUM_ROWS, NUM_COLS), hitMissPanel);
    }

    public void init()
    {
        try
        {
            SwingUtilities.invokeAndWait(
                new Runnable()
                {
                    public void run()
                    {
                        getContentPane().setLayout(new BorderLayout());
                        add(battleshipPanel, BorderLayout.CENTER);
                        add(hitMissPanel, BorderLayout.PAGE_END);
                    }
                }
            );
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args)
    {
        Battleship applet = new Battleship();
        applet.init();

        JFrame frame = new JFrame("Battleship!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(applet);
        frame.pack();
        frame.setVisible(true);
        applet.start();
    }
}