
package inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import gamestates.Gamestate;
import main.GamePanel;

/**
 *
 * @author admin
 */
public class Mouseinputs implements MouseListener, MouseMotionListener{
    
    private GamePanel gamePanel;
    
    public Mouseinputs(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
    	switch(Gamestate.state) {
		case PLAYING:
			gamePanel.getGame().getPaying ().mouseClicked(e);
			break;
		default:
			break;
    	
    	}
    }

    @Override
    public void mousePressed(MouseEvent e) {
    	switch(Gamestate.state) {
		case MENU:
			gamePanel.getGame().getMenu().mousePressed(e);
			break;
		case PLAYING:
			gamePanel.getGame().getPaying ().mousePressed(e);
			break;
		default:
			break;
    	
    	}
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    	switch(Gamestate.state) {
		case MENU:
			gamePanel.getGame().getMenu().mouseReleased(e);
			break;
		case PLAYING:
			gamePanel.getGame().getPaying ().mouseReleased(e);
			break;
		default:
			break;
    	
    	}
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
      
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
    	switch(Gamestate.state) {
		case MENU:
			gamePanel.getGame().getMenu().mouseMoved(e);
			break;
		case PLAYING:
			gamePanel.getGame().getPaying ().mouseMoved(e);
			break;
		default:
			break;
    	
    	}
    	
    }

}
