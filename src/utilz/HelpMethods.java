package utilz;

import main.Game;

import static utilz.Constants.EnemyConstants.CRABBY;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.Crabby;


public class HelpMethods {
	
	//Kiểm tra tọa độ nếu ko hợp lệ (fasle) thì sẽ ko cho di chuyển vào
	public static boolean CanMoveHere(float x, float y, float width, float height, int[][] lvlData) {
		
		if(!IsSolid(x,y,lvlData)) { // check góc trên trái
			if(!IsSolid(x + width, y + height, lvlData)) { // check góc dưới phải
				if(!IsSolid(x + width,  y, lvlData)) { // check góc trên phải
					if(!IsSolid(x, y + height, lvlData)) { // check góc dưới trái
						return true;
					}
				}
			}
		}
		return false;
	}
	
	
	//Check xem có phải là 1 ô trên màn hình ko
	private static boolean IsSolid(float x, float y, int[][] lvlData) {
		int maxWidth = lvlData[0].length * Game.TILES_SIZE; //Lấy độ rộng thật sự của lvl
		
		//Check xem có ở trong màn hình ko trc
		 if(x < 0 || x >= maxWidth) {
			 return true;
		 }
		 if(y < 0 || y >= Game.GAME_HEIGHT) {
			 return true;
		 }
		 
		 //Tìm xem đang ở đâu trên màn hình
		 float xIndex = x / Game.TILES_SIZE;
		 float yIndex = y / Game.TILES_SIZE;
		 
		 return IsTileSolid((int) xIndex, (int) yIndex, lvlData);	
	}
	
	//check tile có phải vật thể ko
	public static boolean IsTileSolid(int xTile, int yTile, int[][] lvlData) {
		int value = lvlData[yTile][xTile];
		 //ô 11 trong hình là ô có thể di chuyển ( vì trong ảnh nó ko có màu gì )
		 if(value >= 48 || value < 0 || value != 11) // Chỉ có ô có giá trị bằng 11 được dy chuyển và ko thuộc level thì ko thể dy chuyển đc
			 return true; //-->là các ô ko di chuyển đc nên là vật thể 
		 return false;
	}
	
	public static float GetEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
		int currentTile = (int)(hitbox.x / Game.TILES_SIZE);
		if( xSpeed >0 ) {
			//right
			int tileXPos = currentTile * Game.TILES_SIZE;
			int xOffset = (int)(Game.TILES_SIZE - hitbox.width);
			return tileXPos + xOffset -1; // -1 để chạm vào nó chứ ko bay trên nó
		}else {
			//left
			return currentTile * Game.TILES_SIZE;
		}
	}
	
	//Giống bên trên check xem có bị mắc trần hoặc đang ở trên sàn
	public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed) {
		int currentTile = (int)(hitbox.y / Game.TILES_SIZE);
		if(airSpeed > 0) {
			//falling - touching floor
			int tileYPos = currentTile * Game.TILES_SIZE;
			int yOffset = (int)(Game.TILES_SIZE - hitbox.height);
			return tileYPos + yOffset -1;
		}else {
			//jumping
			return currentTile * Game.TILES_SIZE;
		}
		
		
	}
	
	//Check xem khi đi nếu ko ở trên floor sẽ rơi xuống
	public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int[][] lvlData) {
		// Check the pixel below bottomleft and bottomright
		if (!IsSolid(hitbox.x, hitbox.y + hitbox.height + 1, lvlData))
			if (!IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData))
				return false;

		return true;
	}
	
	
	public static boolean IsFloor(Rectangle2D.Float hitbox, float xSpped, int[][] lvlData) {
		if(xSpped > 0) //check ô tiếp có phải mặt đất theo độ rộng (để khi đi sang phải sẽ chuẩn)
			return IsSolid(hitbox.x +hitbox.width, hitbox.y + hitbox.height + 1, lvlData);
		else //check ô tiếp có phải mặt đất (vì tọa độ đặt ở đầu nên sẽ check đc bên trái chuẩn)
			return IsSolid(hitbox.x + xSpped, hitbox.y + hitbox.height + 1, lvlData);
	}
	
	//Check vật thể giữa 2 điểm nên có thể sd lại --> ko đặt ở enemy mà đặt đây
	
	public static boolean IsAllTileWalkable(int xStart, int xEnd, int y, int[][] lvlData) {
		//ta sẽ chỉ check các tile giữa xStart và xEnd
		for (int i = 0; i < xEnd - xStart; i++) {
			if (IsTileSolid(xStart + i, y, lvlData)) { //nếu có vật thể thì fasle
				return false;
			}
			if (!IsTileSolid(xStart + i, y + 1, lvlData)) { //dưới nó ko có vật thể --> vực --> false
				return false;
			}
		}
		return true;
	}	
	
	public static  boolean IsSightClear(int[][] lvlData, Rectangle2D.Float firstHitbox, Rectangle2D.Float secondHitbox, int yTile) {
		int firstXTile = (int) (firstHitbox.x /Game.TILES_SIZE);
		int secondXTile = (int) (secondHitbox.x /Game.TILES_SIZE);
		if(firstXTile > secondXTile) {
			return IsAllTileWalkable(firstXTile, secondXTile, yTile, lvlData);
		}else { //nếu second lớn hơn --> ngc lại
			return IsAllTileWalkable(secondXTile, firstXTile, yTile, lvlData);
		}
	}
	
	
	public static int[][] GetLevelData(BufferedImage img){
//		int[][] lvlData = new int[Game.TILES_IN_HEIGHT][Game.TILES_IN_WIDTH]; //vẽ theo width & height nên đây là visible size
		int[][] lvlData = new int[img.getHeight()][img.getWidth()]; 
		
		for(int j = 0; j < img.getHeight(); j++) {
			for(int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getRed();
				if(value >= 48) {
					value = 0;
				}
				lvlData[j][i] = value; 
			}
		}
		return lvlData;
	}
	
	public static ArrayList<Crabby> GetCrabs(BufferedImage img){
		ArrayList<Crabby> list = new ArrayList<>();
		for(int j = 0; j < img.getHeight(); j++) {
			for(int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getGreen();
				if(value == CRABBY) { //vẽ cua tại điểm có green = 0 (CRABBY)
					list.add(new Crabby(i * Game.TILES_SIZE, j * Game.TILES_SIZE));
				}
			}
		}
		return list;
	}
	
	public static Point GetPlayerSpawn(BufferedImage img) {
		for (int j = 0; j < img.getHeight(); j++)
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getGreen();
				if (value == 100)
					return new Point(i * Game.TILES_SIZE, j * Game.TILES_SIZE);
			}
		return new Point(1 * Game.TILES_SIZE, 1 * Game.TILES_SIZE);
	}
	
}
























