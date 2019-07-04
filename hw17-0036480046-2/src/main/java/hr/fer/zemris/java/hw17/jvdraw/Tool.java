package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

interface Tool {
	public void mousePressed(MouseEvent e);
	public void mouseReleased(MouseEvent e);
	public void mouseClicked(MouseEvent e);
	public void mouseMoved(MouseEvent e);
	public void mouseDragged(MouseEvent e);
	public void paint(Graphics2D g2d);
}