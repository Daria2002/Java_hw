package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

/**
 * This interface represents tool
 * @author Daria Matković
 *
 */
interface Tool {
	/**
	 * This method is called when mouse is pressed 
	 * @param e mouse event
	 */
	public void mousePressed(MouseEvent e);
	
	/**
	 * This method is called when mouse is released 
	 * @param e mouse event
	 */
	public void mouseReleased(MouseEvent e);
	
	/**
	 * This method is called when mouse is clicked 
	 * @param e mouse event
	 */
	public void mouseClicked(MouseEvent e);
	
	/**
	 * This method is called when mouse is moved 
	 * @param e mouse event
	 */
	public void mouseMoved(MouseEvent e);
	
	/**
	 * This method is called when mouse is dragged 
	 * @param e mouse event
	 */
	public void mouseDragged(MouseEvent e);
	
	/**
	 * Paint method
	 * @param g2d graphics2d
	 */
	public void paint(Graphics2D g2d);
}