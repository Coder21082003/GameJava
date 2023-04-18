package entities;

import static utilz.Constants.EnemyConstants.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import static utilz.Constants.Directions.*;
import main.Game;

public class Crabby extends Enemy {

	// AttackBox 
	private Rectangle2D.Float attackBox;
	private int attackBoxOffsetX;

	
	public Crabby(float x, float y) {
		super(x, y, CRABBY_WIDTH, CRABBY_HEIGHT, CRABBY);
		initHitBox(x, y,(int)(22 * Game.SCALE),(int) (19 * Game.SCALE)); //true size hitbox
		initAttackBox();
	}
	
	private void initAttackBox() {
		attackBox = new Rectangle2D.Float(x,y, (int)(82* Game.SCALE), (int) (19* Game.SCALE));
		attackBoxOffsetX = (int) (Game.SCALE *30);
	}

	public void update(int[][] lvlData, Player player) {
		updateBehaviour(lvlData,player);
		updateAnimationTick();
		updateAttackBox();
	}
	
	private void updateAttackBox() {
		attackBox.x = hitbox.x - attackBoxOffsetX;
		attackBox.y = hitbox.y;
	}
	
	private void updateBehaviour(int[][] lvlData, Player player) { //behaviour hành vi
		//check đầu tiên
		if(firstUpdate) {
			firstUpdateCheck(lvlData);
		}
		
		
		if(inAir){ //nếu first update inAir true
			updateInAir(lvlData);
		}else {
			switch (enemyState) {
			case IDLE: //bắt đầu với idle sau đó running	
				enemyState = RUNNING;
				break;
			
			case RUNNING:
				if(canSeePlayer(lvlData, player)) {
					turnTowardsPlayer(player);
				if(isPlayerCloseForAttack(player))
					newState(ATTACK);
				}
				move(lvlData);
				break;
				
			case ATTACK:
				if(aniIndex == 0)
					attackChecked = false; //reset attack check
				
				if(aniIndex == 3 && !attackChecked) //chỉ gây sát thg khi ở hoạt ảnh cuối 
					checkPlayerHit(attackBox, player);
				break;
			
			case HIT:
				
				break;
			}
		}
	}

	
	public int flipX() {
		if(walkDir == RIGHT)
			return width; 
		else
			return 0;
	}

	public int flipW() {
		if(walkDir == RIGHT)
			return -1;
		else
			return 1;
	}


	
	
	
	
	
	
	
}
