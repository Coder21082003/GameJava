package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

//Để tránh tạo thiếu method trong các class chung mục đích
public interface Statemethods {
	
	public void update();
	
	public void draw(Graphics g);
	
	public void mouseClicked(MouseEvent e);
	
	public void mousePressed(MouseEvent e);
	
	public void mouseReleased(MouseEvent e);
	
	public void mouseMoved(MouseEvent e);
	
	public void keyPressed(KeyEvent e);
	
	public void keyReleased(KeyEvent e);
	
}
