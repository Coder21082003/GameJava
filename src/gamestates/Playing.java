package gamestates;
//Chứa tất cả hành động của người chơi như lấy hình ảnh,update,chạy, nhảy .... trước đó ở class game đặt vào đây
//Những code ở class game để test mỗi playing sẽ move sang đây 

import java.awt.Color;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import Levels.LevelManager;
import entities.EnemyManager;
import entities.Player;
import main.Game;
import ui.GameOverOverlay;
import ui.LevelCompletedOverlay;
import ui.PauseOverlay;
import utilz.LoadSave;
import static utilz.Constants.Enviroment.*;


public class Playing extends State implements Statemethods {
	private Player player;
    private LevelManager levelManager;
    private EnemyManager enemyManager;
    private PauseOverlay pauseOverlay;
    private GameOverOverlay gameOverOverlay;
    private LevelCompletedOverlay levelCompletedOverlay;
    private boolean paused = false; //quyết định có paused game hay ko 
    
    private int xlvlOffset; //số px sẽ xóa hoặc thêm khi di chuyển 
    private int leftBorder = (int)(0.2 * Game.GAME_WIDTH);
    private int rightBorder = (int)(0.8 * Game.GAME_HEIGHT);
//    private int lvlTilesWide = LoadSave.GetLevelData()[0].length; //Lvl width
//    private int maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH; 
    private int maxlvlOffsetX; //max lvl offset can be
    
    private BufferedImage backgroundImg, bigCloud, smallCloud;
    private int[] smallCloudsPos;
    private Random rnd = new Random();
    private boolean gameOver = false;
    private boolean lvlCompleted;
    
    public Playing(Game game) {
		super(game);
		initClasses();
		backgroundImg = LoadSave.getSpriteAtlas(LoadSave.PLAYING_BG_IMG);
		bigCloud = LoadSave.getSpriteAtlas(LoadSave.BIG_CLOUDS);
		smallCloud = LoadSave.getSpriteAtlas(LoadSave.SMALL_CLOUDS);
		smallCloudsPos = new int[8]; //vẽ mây tại độ cao khác nhau ngẫu nhiên
		for(int i = 0; i < smallCloudsPos.length ; i++)
			smallCloudsPos[i] = (int)(95 * Game.SCALE) + rnd.nextInt((int)(100 * Game.SCALE));
    
		calcLvlOffset();
		loadStartLevel();
		
    }
    
    //Load lvl method
    
    public void loadNextLevel() {
    	resetAll();
		levelManager.loadNextLevel();
		//player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
    }
    
    private void loadStartLevel() {
		resetAll();
    	enemyManager.loadEnemies(levelManager.getCurrentLevel());
		player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
	}

	private void calcLvlOffset() {
		maxlvlOffsetX = levelManager.getCurrentLevel().getLvlOffset();
		
	}
	//end load lvl
	
	private void initClasses() {
    	levelManager = new LevelManager(game);
    	enemyManager = new EnemyManager(this);
    	
    	player = new Player(200,200,(int) (64*Game.SCALE), (int) (40*Game.SCALE), this);
    	player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
    	player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
    	
		pauseOverlay = new PauseOverlay(this);
		gameOverOverlay = new GameOverOverlay(this);
		levelCompletedOverlay = new LevelCompletedOverlay(this);
    }
    
    public Player getPlayer() {
    	return player;
    }

	public void windowFocusLost() {
		player.resetDirBooolean();
		
	}

	@Override
	public void update() {
		 // Check state để chỉ update 1 trạng thái của game -->Khi pause thì game sẽ freeze
		if(paused) {
			pauseOverlay.update(); // gọi tới music và sfx update
		}else if(lvlCompleted) {
			levelCompletedOverlay.update();
		}else if(!gameOver) {
			levelManager.update();
			player.update();
			enemyManager.update(levelManager.getCurrentLevel().getLevelData(),player);
			checkCloseToBoder(); // check de move lvl
		}
		
	}

	private void checkCloseToBoder() {
		int playerX = (int) player.getHitBox().x; //get player X
		int diff = playerX - xlvlOffset; // để kiểm tra tọa độ người chơi
		//Ví dụ nếu player = 85px, offset = 0 --> diff = 85 và size có 100px thì 85 > 80 
		if(diff > rightBorder)
			xlvlOffset += diff -rightBorder; //-->Như trên thì offset = 85-80 += 5
		else if(diff < leftBorder)
			xlvlOffset += diff - leftBorder;
		
		//Đảm bảo lvl offset ko vượt quá kích cỡ cửa map
		if(xlvlOffset > maxlvlOffsetX)
			xlvlOffset = maxlvlOffsetX;
		else if(xlvlOffset < 0)
			xlvlOffset = 0;
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(backgroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
		
		drawClouds(g);
		
		levelManager.draw(g, xlvlOffset);
		player.render(g, xlvlOffset);
		enemyManager.draw(g, xlvlOffset);
		
		//Check xem có paused ko mới vẽ paused overlay
		if(paused) {
			g.setColor(new Color(0,0,0,150));
			g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
			pauseOverlay.draw(g);

		}else if(gameOver)
			gameOverOverlay.draw(g);
		else if(lvlCompleted == true)
			levelCompletedOverlay.draw(g);
	}

	private void drawClouds(Graphics g) {
		
		for(int i = 0; i < 3; i++)
		g.drawImage(bigCloud, 0 + i * BIG_CLOUD_WIDTH - (int) (xlvlOffset * 0.3), (int)(204 * Game.SCALE), BIG_CLOUD_WIDTH, BIG_CLOUD_HEIGHT, null);
		
		for(int i = 0; i < smallCloudsPos.length; i++)
		g.drawImage(smallCloud, SMALL_CLOUD_WIDTH * 4 * i - (int) (xlvlOffset * 0.7) , smallCloudsPos[i], SMALL_CLOUD_WIDTH, SMALL_CLOUD_HEIGHT, null);
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(!gameOver)
    	//BUTTON1 là chuột trái 2 là giữa 3 là chuột phải
    	if(e.getButton() == MouseEvent.BUTTON1) {
    		player.setAttacking(true);
    	}	
	}
	
	public void mouseDragged(MouseEvent e) {
		if(!gameOver)
			if(paused)
				pauseOverlay.mouseDragged(e);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if(!gameOver) {
			if(paused)
				pauseOverlay.mousePressed(e);
			else if(lvlCompleted)
				levelCompletedOverlay.mousePressed(e);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(!gameOver) {
			if(paused)
				pauseOverlay.mouseReleased(e);
			else if(lvlCompleted)
				levelCompletedOverlay.mouseReleased(e);

		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(!gameOver) {
			if(paused)
				pauseOverlay.mouseMoved(e);
			else if(lvlCompleted)
				levelCompletedOverlay.mouseMoved(e);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(gameOver)
			gameOverOverlay.keyPressed(e);
		//Lay gia tri key duoc nhan
        switch(e.getKeyCode()){
            //KeyEvent.VK_X --> X la gia tri muon lay
        // *vi trong java truc toa do 0,0 duoc dat goc tren cung ben trai man hinh
        case KeyEvent.VK_A:
    		player.setLeft(true);
    		break;
    	case KeyEvent.VK_D:
    		player.setRight(true);
    		break;
    	case KeyEvent.VK_SPACE:
    		player.setJump(true);
    		break;
    	case KeyEvent.VK_ESCAPE:
    		paused = !paused;
    		break;
    	}
         
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(!gameOver)
		switch (e.getKeyCode()) {
		case KeyEvent.VK_A:
    		player.setLeft(false);
    		break;
    	case KeyEvent.VK_D:
    		player.setRight(false);
    		break;
    	case KeyEvent.VK_SPACE:
    		player.setJump(false);
    		break;
    	}
	}
    
	public void unpauseGame() {
		paused = false;
	}

	public void resetAll() {
		//Reset player, enemy, level,...
		gameOver = false;
		paused = false;
		lvlCompleted = false;
		player.resetAll();
		enemyManager.resetAllEnemies();
	}
	
	
	
	public void setLevelCompleted(boolean levelCompleted) {
		this.lvlCompleted = levelCompleted;
	}
	
	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}
	
	public void checkEnemyHit(Rectangle2D.Float attackBox) {
		enemyManager.checkEnemyHit(attackBox);
	}
	
	public EnemyManager getEnemyManager() {
		return enemyManager;
	}
	
	public void setMaxLvlOffset(int lvlOffset) {
		this.maxlvlOffsetX = lvlOffset;
	}
}
