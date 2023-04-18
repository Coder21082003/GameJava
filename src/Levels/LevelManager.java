package Levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gamestates.Gamestate;
import main.Game;
import utilz.LoadSave;

//Muốn vẽ level ta phải có bức ảnh đã chia chính xác thành ma trận cấp 2 để ta có thể lấy từng phần ra 
public class LevelManager {
	
	private Game game;
	private BufferedImage[] levelSprite;
	//Sửa lại khi thêm lvl
	private ArrayList<Level> levels;
	private int lvlIndex = 0;
	
	public LevelManager(Game game) {
		this.game = game;
		//levelSprite = LoadSave.getSpriteAtlas(LoadSave.LEVEL_ATLAS);
		importOutsideSprites();
		levels = new ArrayList<>();
		buildAllLevels();
	}
	
	public void loadNextLevel() {
		lvlIndex++;
		if(lvlIndex >= levels.size()) {
			lvlIndex = 0;
			System.out.println("Game completed");
			Gamestate.state = Gamestate.MENU;
		}
		
		Level newLevel = levels.get(lvlIndex);
		game.getPaying().getEnemyManager().loadEnemies(newLevel);
		game.getPaying().getPlayer().loadLvlData(newLevel.getLevelData());
		game.getPaying().setMaxLvlOffset(newLevel.getLvlOffset());
		
	}
	
	private void buildAllLevels() {
		BufferedImage[] allLevels = LoadSave.GetAllLevels();
		for(BufferedImage img : allLevels)
			levels.add(new Level(img));
		
	}

	private void importOutsideSprites() {
		BufferedImage img = LoadSave.getSpriteAtlas(LoadSave.LEVEL_ATLAS);
		
		//Vì bức ảnh dài 12 rộng 4 sprite
		levelSprite = new BufferedImage[48];
		for(int j = 0; j < 4; j++) {
			for(int i = 0; i < 12; i++) {
				int index = j*12 + i;
				levelSprite[index] = img.getSubimage(i * 32, j *32, 32, 32);
				//==>Ta tạo ra được 1 mảng các viên gạch để lát level
			}
		}
	} 	

	public void draw(Graphics g, int lvlOffset) {
		//Phải vẽ theo độ rộng của lvl vì lvl to hơn màn hình hiển thị (TILES_IN_WIDTH) //Chứ kp độ rộng game Game.GAME_WIDTH là to bằng map rồi 
		for(int j = 0; j < Game.TILES_IN_HEIGHT; j++) {
			for(int i = 0; i < levels.get(lvlIndex).getLevelData()[0].length; i++) { //thay vì Game TILES_IN_WIDTH
				int index = levels.get(lvlIndex).getSpriteIndex(i, j);
				//-->Lấy gạch để lát (Tự làm thì lên mạng để tạo màu cho red từ 0->số tiles chuẩn, muốn ko vẽ chỉ cần > số tiles thì set = 0 như trên)
				g.drawImage(levelSprite[index], Game.TILES_SIZE * i - lvlOffset, Game.TILES_SIZE * j, Game.TILES_SIZE, Game.TILES_SIZE, null);
			}
		}
	}
	
	public void update() {
		
	} 
	
	public Level getCurrentLevel() {
		return levels.get(lvlIndex);
	}
	
	public int getAmountOfLvevel() {
		return levels.size();
	}
	
}
