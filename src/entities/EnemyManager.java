package entities;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Levels.Level;
import gamestates.Playing;
import utilz.LoadSave;
import static utilz.Constants.EnemyConstants.*;


//Giống lvl manager quản lý việc hành động của enemy như do thám , tấn công....
//Sẽ thêm vào playing và chỉ cần update class này

public class EnemyManager {
	
	private Playing playing;
	private BufferedImage[][] crabbyArr;
	private ArrayList<Crabby> crabbies = new ArrayList<>(); //tạo ra nhiều kẻ địch 
	
	public EnemyManager(Playing playing) {
		this.playing = playing;
		loadEnemyImgs(); 
	}
	
	public void loadEnemies(Level level) {
		crabbies = level.getCrabs();
		
	}

	public void update(int[][] lvlData, Player player) {
		boolean isAnyActive = false;
		int check = 0;
		for(Crabby c : crabbies) {
			//check nếu chết r thì ko vẽ nữa
			if(c.isActive()) { 
			c.update(lvlData, player);
			isAnyActive = true;
			}
		}
		if(!isAnyActive)
			playing.setLevelCompleted(true);
	}
	
	public void draw(Graphics g, int xLvlOffset) {
		drawCrabs(g, xLvlOffset);
	}
	
	
	private void drawCrabs(Graphics g, int xLvlOffset) {
		for (Crabby c : crabbies) 
			if(c.isActive())
		{
			g.drawImage(crabbyArr[c.getEnemyState()][c.getAniIndex()], 
						(int) c.getHitBox().x - xLvlOffset - CRABBY_DRAWOFFSET_X + c.flipX(), 
						(int) c.getHitBox().y - CRABBY_DRAWOFFSET_Y, 
						CRABBY_WIDTH * c.flipW(), CRABBY_HEIGHT, null);
		}
	}

	public void checkEnemyHit(Rectangle2D.Float attackBox) {
		for (Crabby c : crabbies)
			if(c.isActive())
			if(attackBox.intersects(c.getHitBox())) { //trả về true nếu 2 hit box overlap nhau
				c.hurt(10);
				return ; //để player chỉ hit 1 enemy 1 time
			}
	}
	
	private void loadEnemyImgs() {
		crabbyArr = new BufferedImage[5][9]; 
		BufferedImage temp = LoadSave.getSpriteAtlas(LoadSave.CRABBY_SPRITE);
		for(int j = 0; j < crabbyArr.length; j++) {
			for(int i = 0; i < crabbyArr[0].length; i++) {
				crabbyArr[j][i] = temp.getSubimage(i * CRABBY_WIDTH_DEFAULT, j * CRABBY_HEIGHT_DEFAULT, CRABBY_WIDTH_DEFAULT, CRABBY_HEIGHT_DEFAULT);
			}
		}
		
	}

	public void resetAllEnemies() {
		for(Crabby c : crabbies)
			c.resetEnemy();
	}
	
}
