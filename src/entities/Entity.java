package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

//Để chứa các entity của người chơi , quái vật ....
public abstract class Entity {
	
	protected float x,y;
	protected int width, height;
	//hit box
	protected Rectangle2D.Float hitbox;
	
	
	public Entity (float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
	}
	
	protected void drawHitBox(Graphics g, int xLvlOffset) {
		//Để debug hitbox
		g.setColor(Color.PINK);
		g.drawRect((int)hitbox.x - xLvlOffset,(int) hitbox.y, (int)hitbox.width, (int)hitbox.height);
	}
	
	//Lấy hitbox
	protected void initHitBox(float x, float y, float width, float height) {
		hitbox = new Rectangle2D.Float(x, y, width, height);
	}
	
	//Lấy giá trị x y mới cho vào hit box
//	protected void updateHitBox() {
//		hitbox.x = (int) x;
//		hitbox.y = (int) y;
//	}
	public Rectangle2D.Float getHitBox() {
		return hitbox;
	}

}
