package utilz;

import main.Game;

public class Constants {
	
	//ENEMY
	public static class EnemyConstants {
		public static final int CRABBY = 0;
		
		public static final int	IDLE = 0;  // idle nghĩa là không làm gì cả
		public static final int RUNNING = 1;
		public static final int ATTACK = 2;
		public static final int HIT = 3;
		public static final int DEAD= 4;
		
		public static final int CRABBY_WIDTH_DEFAULT = 72;
		public static final int CRABBY_HEIGHT_DEFAULT = 32;
		
		public static final int CRABBY_WIDTH = (int) (CRABBY_WIDTH_DEFAULT * Game.SCALE);
		public static final int CRABBY_HEIGHT= (int) (CRABBY_HEIGHT_DEFAULT * Game.SCALE);
		
		//kc giữa size crab thật vs hitbox của nó
		public static final int CRABBY_DRAWOFFSET_X = (int) (26 * Game.SCALE);
		public static final int CRABBY_DRAWOFFSET_Y = (int) (9 * Game.SCALE);
		
		public static int getSpriteAmount(int enemy_type, int enemy_state) {
		
			switch(enemy_type) {
			case CRABBY:
				switch(enemy_state) {
				case IDLE:
					return 9;
				case RUNNING:
					return 6;
				case ATTACK:
					return 7;
				case HIT:
					return 4;
				case DEAD:
					return 5;
				}
			}
			return 0;	
		}
		//Enemy health
		public static int GetMaxHealth(int enemy_type) {
			switch (enemy_type) {
			case CRABBY: {
				return 10;
			}
			default:
				return 1;
			}
		}
		//Enemy damage
		public static int GetEnemyDmg(int enemy_type) {
			switch (enemy_type) {
			case CRABBY:
				return 15;
			default:
				return 0;
			}
		}
	}
	
	//Lấy width height của UI môi trường theo game scale
	public static class Enviroment{
		//big clouds
		public static final int BIG_CLOUD_WIDTH_DEFAULT = 448;
		public static final int BIG_CLOUD_HEIGTH_DEFAULT = 101;
		public static final int BIG_CLOUD_WIDTH = (int)(BIG_CLOUD_WIDTH_DEFAULT * Game.SCALE);
		public static final int BIG_CLOUD_HEIGHT = (int)(BIG_CLOUD_HEIGTH_DEFAULT * Game.SCALE);
	
		//small clouds
		public static final int SMALL_CLOUD_WIDTH_DEFAULT = 74;
		public static final int SMALL_CLOUD_HEIGTH_DEFAULT = 24;
		public static final int SMALL_CLOUD_WIDTH = (int)(SMALL_CLOUD_WIDTH_DEFAULT * Game.SCALE);
		public static final int SMALL_CLOUD_HEIGHT = (int)(SMALL_CLOUD_HEIGTH_DEFAULT * Game.SCALE);
	}
	
	
	
	//Tạo giá trị kích thước cho các button của UI
	public static class UI{
		public static class Buttons{
			public static final int B_WIDTH_DEFAULT = 140;
			public static final int B_HEIGHT_DEFAULT = 56;
			public static final int B_WIDTH = (int)(B_WIDTH_DEFAULT * Game.SCALE);
			public static final int B_HEIGHT = (int)(B_HEIGHT_DEFAULT * Game.SCALE);
		}
		
		
		public static class URMButtons{
			public static final int URM_DEFAULT_SIZE = 56;
			public static final int URM_SIZE = (int)(URM_DEFAULT_SIZE * Game.SCALE);
		}
		
		public static class PauseButtons{
			public static final int SOUND_SIZE_DEFAULT = 42;
			public static final int SOUND_SIZE = (int) (SOUND_SIZE_DEFAULT * Game.SCALE);
		}
	}
	
	public static class Directions{
		public static final int LEFT = 0;
		public static final int UP = 1;
		public static final int RIGHT = 2;
		public static final int DOWN = 3;
	}
	
	public static class PlayerConstants{
		public static final int	IDLE = 0;  // idle nghĩa là không làm gì cả
		public static final int RUNNING = 1;
		public static final int JUMP = 2;
		public static final int FALLING = 3;
		public static final int ATTACK = 4;
		public static final int	HIT = 5;
		public static final int DEAD = 6;

		
		public static int GetSrpiteAmount(int player_action) {
			
			switch(player_action) {
			//trả về số hoạt ảnh mà hành động đó sở hữu
			case IDLE:
				return 5;
			case RUNNING:
				return 6;
			case JUMP:
				return 3;
			case FALLING:
				return 1;
			case HIT:
				return 4;
			case ATTACK:
				return 3;
			case DEAD:
				return 8;
			default:
				return 1;
			}
			
		}
		
	}
}
