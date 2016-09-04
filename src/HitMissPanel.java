/*
 * CSC 221 - Assignment 3
 * Battleship Game
 * by Klaudio Vito
 * 10/07/2015
 */

//package assignment_3;
import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

/**
 * A panel that displays hit and miss statistics.
 */
@SuppressWarnings("serial")
public class HitMissPanel extends JPanel
{
    private static final int FONT_SIZE = 20;

    private JLabel totalShotsLabel;
    private JLabel hitsLabel;
    private JLabel missesLabel;
    private JLabel hitPercentageLabel;

    private int hits;
    private int misses;

    private DecimalFormat df = new DecimalFormat("0.00%");

    public HitMissPanel(int width, int height)
    {
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.DARK_GRAY);

        setLayout(new GridLayout(1, 0));

        // create a new label for the total number of shots
        totalShotsLabel = new JLabel("", JLabel.CENTER);
        totalShotsLabel.setFont(new Font("Arial", Font.PLAIN, FONT_SIZE));
        totalShotsLabel.setForeground(Color.WHITE);

        // create a new label for the total number of hits
        hitsLabel = new JLabel("", JLabel.CENTER);
        hitsLabel.setFont(new Font("Arial", Font.PLAIN, FONT_SIZE));
        hitsLabel.setForeground(Color.WHITE);

        // create a new label for the total number of misses
        missesLabel = new JLabel("", JLabel.CENTER);
        missesLabel.setFont(new Font("Arial", Font.PLAIN, FONT_SIZE));
        missesLabel.setForeground(Color.WHITE);

        // create a new label for the hit percentage
        hitPercentageLabel = new JLabel("", JLabel.CENTER);
        hitPercentageLabel.setFont(new Font("Arial", Font.PLAIN, FONT_SIZE));
        hitPercentageLabel.setForeground(Color.WHITE);

        // initialize stats
        setStats(0, 0);

        // add the labels to this panel
        add(totalShotsLabel);
        add(hitsLabel);
        add(missesLabel);
        add(hitPercentageLabel);
    }

    /**
     * Sets the number of hits and misses. This method
     * also updates the hit and miss statistics labels.
     *
     * @param    hits    the total number of hits
     * @param    misses  the total number of misses
     */
    public void setStats(int hits, int misses)
    {
        this.hits = hits;
        this.misses = misses;
        updateLabels();
    }

    /**
     * Updates the hit and miss statistics labels.
     */
    private void updateLabels()
    {
        totalShotsLabel.setText("Shots: " + (hits + misses));
        hitsLabel.setText("Hits: " + hits);
        missesLabel.setText("Misses: " + misses);
        
        double hitPercentage = 0.0;

        // compute hit percentage, taking into account divide by zero error
        if ((hits + misses) != 0)
        {
            hitPercentage = (double) hits / (hits + misses);
        }

        hitPercentageLabel.setText("Hit%: " + df.format(hitPercentage));
    }
}
