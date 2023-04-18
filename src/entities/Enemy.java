package entities;
import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.*;

import java.awt.geom.Rectangle2D;

import static utilz.Constants.Directions.*;
import main.Game;


//Class chung cho enemy nên để abstract--> ở đây ta sẽ có 2 class enemy riêng extend class enemy
public abstract class Enemy extends Entity {
	protected int aniIndex, enemyState, enemyType;
	protected int aniTick, aniSpeed = 25;
	protected boolean firstUpdate =true;
	protected boolean inAir; //để enemy spawn ở trên không sẽ rơi xuống đất
	protected float fallSpeed;
	protected float gravity = 0.04f* Game.SCALE;
	protected float walkSpeed = 0.35f * Game.SCALE;
	protected int walkDir = LEFT;
	protected int tileY;
	protected float attackDistance = Game.TILES_SIZE;
	protected int maxHealth;
	protected int currentHealth;
	protected boolean active =true;
	protected boolean attackChecked;
	
	public Enemy(float x, float y, int width, int height, int enemyType) {
		super(x, y, width, height);
		this.enemyType = enemyType;
		initHitBox(x, y, width, height); //lấy hitbox
		maxHealth = GetMaxHealth(enemyType);
		currentHealth = maxHealth;
	}
	//Các phương thức dùng chung trong update move
	protected void firstUpdateCheck(int[][] lvlData) {
		if(!IsEntityOnFloor(hitbox, lvlData))  //check trên mặt đất ko như player
			inAir = true;
		firstUpdate = false; //sau khi check xong thì bỏ qua firstupdate
	}
	
	protected void updateInAir(int[][] lvlData) {
		if(CanMoveHere(hitbox.x, hitbox.y + fallSpeed, hitbox.width, hitbox.height, lvlData)) {
			hitbox.y += fallSpeed;
			fallSpeed += gravity;
		}else {
			inAir = false;
			hitbox.y =  GetEntityYPosUnderRoofOrAboveFloor(hitbox, fallSpeed);
			tileY = (int)(hitbox.y / Game.TILES_SIZE);
		}
	}
	
	protected void move(int[][] lvlData) {
		float xSpeed =  0; //reset
		
		if(walkDir == LEFT)
			xSpeed = -walkSpeed;
		else
			xSpeed = walkSpeed;
		
		//Kiểm tra vị trí di chuyển tới tiếp theo
		if(CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData))
			if(IsFloor(hitbox, xSpeed, lvlData)) {
				hitbox.x += xSpeed;
				return; //hợp lệ thì return 
			}
		//nếu ko hợp lệ thì quay đầu đi về hướng ngược lại
		changeWalkDir();
	}
	
	//end update move
	
	//Đổi hướng di chuyển về cùng phái người chơi
	protected void turnTowardsPlayer(Player player) {
		if(player.hitbox.x > hitbox.x)
			walkDir = RIGHT;
		else
			walkDir = LEFT;
	}
	
	//Check title y của player--> nếu cùng hàng ms check x xem có trong tầm ko, còn tile của enemy ko đổi nên đặt biến luôn để ko cần update
	protected boolean  canSeePlayer(int[][] lvlData, Player player) {
		int playerTileY = (int) (player.getHitBox().y / Game.TILES_SIZE); //Lấy player xem ở tile mấy
		System.out.println(playerTileY + "\t" + tileY);
		if(playerTileY == tileY) { 
			if(isPlayerInRange(player)) {
				if (IsSightClear(lvlData, hitbox, player.hitbox, tileY)) //Check sight ví dụ trong range nhưng có cục đá --> ko nhìn thấy player nữa
					return true;
			}
		}
		
		return false;
	}
	
	//Check trong tầm nhìn & tấn công
	protected boolean isPlayerInRange(Player player) {
		//Vì hiệu của playerX vs enemeyX có thể âm --> sd abs
		int absValue = (int) Math.abs(player.hitbox.x - hitbox.x);
		return absValue <= attackDistance *5; //ở đây tầm nhìn của quái gấp 5 tầm tấn công và return true nếu đúng
	}
	
	
	protected boolean isPlayerCloseForAttack(Player player) {
		int absValue = (int) Math.abs(player.hitbox.x - hitbox.x);
		return absValue <= attackDistance; //ở đây tầm nhìn của quái gấp 5 tầm tấn công và return true nếu đúng
	}
	
	//Giống player mỗi khi đổi trạng thái ta reset aniTick để có hoạt ảnh mượt không bị bắt đầu từ giữa animation 
	protected void newState(int enemyState) {
		this.enemyState = enemyState;
		aniTick = 0;
		aniIndex = 0;
	}
	
	public void hurt(int amount) {
		currentHealth -= amount;
		if(currentHealth <= 0)
			newState(DEAD);
		else
			newState(HIT);
	}
	
	protected void checkPlayerHit(Rectangle2D.Float attackBox, Player player) {
		if(attackBox.intersects(player.hitbox))
			player.changeHealth(-GetEnemyDmg(enemyType));
		attackChecked = true;
	}
	
	protected void updateAnimationTick() { //same player class
		aniTick ++;
		if(aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if(aniIndex >= getSpriteAmount(enemyType, enemyState)) {
				aniIndex = 0;
				if(enemyState == ATTACK)
					enemyState = IDLE; //Sau khi tấn công thì dừng 1 nhịp
				else if(enemyState == HIT)
					enemyState = IDLE;
				else if(enemyState == DEAD)
					active = false;
			
			}
		}
	}
	

	protected void changeWalkDir() {
		if(walkDir == LEFT)
			walkDir = RIGHT;
		else 
			walkDir = LEFT;
		
	}

	public int getAniIndex() {
		return aniIndex;
	}
	
	public int getEnemyState() {
		return enemyState;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public void resetEnemy() {
		hitbox.x = x;
		hitbox.y = y;
		firstUpdate = true;
		currentHealth = maxHealth;
		newState(IDLE);
		active = true;
		fallSpeed = 0;
	}
	
}



















