package ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;

import utilz.LoadSave;
import static utilz.Constants.UI.PauseButtons.*;
import static utilz.Constants.UI.URMButtons.*;
public class PauseOverlay {

	private BufferedImage backgroundImg;
	private int bgX, bgY, bgW, bgH;
	private SoundButton musicButton, sfxButton;
	private UrmButton menuB, replayB, unpauseB;
	private Playing playing; //tạo ra object để truy cập vào và đổi state ở unpause Button

	
	
	public PauseOverlay(Playing playing) {
		this.playing = playing;
		loadBackground();
		createSoundButtons();
		createUrmButtons();
	}
	
	private void createUrmButtons() {
		int menuX = (int) ( 313 * Game.SCALE);
		int replayX = (int) (387 * Game.SCALE);
		int unpauseX = (int) ( 462 * Game.SCALE);
		int bY = (int) ( 310 * Game.SCALE); //vì chung 1 tọa độ Y
		
		menuB = new UrmButton(menuX, bY, URM_SIZE, URM_SIZE, 2); //vì menuB là hàng 3
		replayB = new UrmButton(replayX, bY, URM_SIZE, URM_SIZE, 1);
		unpauseB = new UrmButton(unpauseX, bY, URM_SIZE, URM_SIZE, 0);
	}

	private void createSoundButtons() {
		int soundX = (int)(450* Game.SCALE);
		int musicY = (int)(146* Game.SCALE);
		int sfxY = (int)(192 * Game.SCALE);
		musicButton = new SoundButton(soundX, musicY, SOUND_SIZE, SOUND_SIZE);
		sfxButton = new SoundButton(soundX, sfxY, SOUND_SIZE, SOUND_SIZE);
	}

	private void loadBackground() {
		backgroundImg = LoadSave.getSpriteAtlas(LoadSave.PAUSE_BACKGROUND);
		bgW = (int)(backgroundImg.getWidth() * Game.SCALE);
		bgH = (int)(backgroundImg.getHeight() * Game.SCALE);
		bgX = Game.GAME_WIDTH / 2 - bgW /2 ; //Để căn giữa
		bgY = (int)(30 * Game.SCALE);
		
	}

	public void update() {
		
		musicButton.update();
		sfxButton.update();
		//urm buttons
		menuB.update();
		replayB.update();
		unpauseB.update();
		
	}
	
	public void draw(Graphics g) {
		//Background
		g.drawImage(backgroundImg, bgX, bgY, bgW, bgH, null);
		
		//Sound button
		musicButton.draw(g);
		sfxButton.draw(g);
		
		//Urm buttons
		menuB.draw(g);
		replayB.draw(g);
		unpauseB.draw(g);
	}
	
	public void mouseDragged(MouseEvent e) {
		
	}
	
	public void mousePressed(MouseEvent e) {
		if(isIn(e,musicButton))
			musicButton.setMousePressed(true);
		else if(isIn(e, sfxButton))
			sfxButton.setMousePressed(true);
		else if(isIn(e, menuB))
			menuB.setMousePressed(true);
		else if(isIn(e, replayB))
			replayB.setMousePressed(true);
		else if(isIn(e, unpauseB))
			unpauseB.setMousePressed(true);
	}

	public void mouseReleased(MouseEvent e) {
		if(isIn(e,musicButton)) {
			if(musicButton.isMousePressed()) {
				musicButton.setMuted(!musicButton.isMuted( )); //!musicButton.isMuted() sẽ trả về giá trị ngược lại vd đang mute thì sẽ thành ko mute bởi dấu !
			}
		}
		else if(isIn(e, sfxButton)) {
			if(sfxButton.isMousePressed()) {
				sfxButton.setMuted(!sfxButton.isMuted()); //Tương tự trên
			}
		}
		else if(isIn(e, menuB)) {
			if(menuB.isMousePressed()) {
				Gamestate.state = Gamestate.MENU;
				playing.unpauseGame();
			}
		}
		else if(isIn(e, replayB)) {
			if(replayB.isMousePressed()) {
				playing.resetAll();
				playing.unpauseGame();
			}
		}
		else if(isIn(e, unpauseB)) {
			if(unpauseB.isMousePressed()) {
				playing.unpauseGame();
			}
		}
		
		
		musicButton.resetBools();
		sfxButton.resetBools();
		menuB.resetBols();
		replayB.resetBols();
		unpauseB.resetBols();
	}

	public void mouseMoved(MouseEvent e) {
		//reset
		musicButton.setMouseOver(false);
		sfxButton.setMouseOver(false);
		menuB.setMouseOver(false);
		replayB.setMouseOver(false);
		unpauseB.setMouseOver(false);
		
		//check
		if(isIn(e,musicButton))
			musicButton.setMouseOver(true);
		else if(isIn(e, sfxButton))
			sfxButton.setMouseOver(true);
		else if(isIn(e, menuB))
			menuB.setMouseOver(true);
		else if(isIn(e, replayB))
			replayB.setMouseOver(true);
		else if(isIn(e, unpauseB))
			unpauseB.setMouseOver(true);
	}
	
	//Check xem chuột có ở trong trc và sau khi click ko  
	private boolean isIn(MouseEvent e, PauseButton b) {
		 return b.getBounds().contains(e.getX(), e.getY());
	}
	
	

	
}
