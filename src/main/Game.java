package main;

import java.awt.Graphics;
import gamestates.Gamestate;
import gamestates.Menu;
import gamestates.Playing;
import utilz.LoadSave;

/**
 this is the main class for entire game ( handlers, player, levels, enemies);
  */
//Witre code   
public class Game implements Runnable{
    
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;

    private Playing playing;
    private Menu menu;
    
    //Xác định độ to của màn hình game tiles nghĩa là gạch lát nhà
    public final static int TILES_DEFAULT_SIZE = 32 ; // là 1 khối vuông 32 px
    public final static float SCALE = 1.5f; 
    public final static int TILES_IN_WIDTH =26;
    public final static int TILES_IN_HEIGHT = 14;
    public final static int TILES_SIZE = (int)(TILES_DEFAULT_SIZE * SCALE);
    public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;
    
    public Game() {
        initClasses();
        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.setFocusable(true);
        gamePanel.requestFocus();
        startGameLoop();
    }

    private void initClasses() {
    	menu = new Menu(this);
    	playing = new Playing(this);
    }

	private void startGameLoop(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    
    public void update() {
    	switch(Gamestate.state) {
		case MENU:
			menu.update();
			break;
		case PLAYING:
			playing.update();
			break;
		case OPTIONS:
		case QUIT:
		default:
			//Thoát chương trình
			System.exit(0);
			break;
    	}
    	
    }
    
    public void render(Graphics g) {
    	switch(Gamestate.state) {
		case MENU:
			menu.draw(g);
			break;
		case PLAYING:
			playing.draw(g);
			break;
		default:
			break;
    	}

    }
    
    @Override
    public void run() {
        //Giới hạn thời gian làm mới khung hình và tính toán FPS
        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;
//		long lastFrame = System.nanoTime(); //--> tra ve gia tri long
//		long now = System.nanoTime();
		
		long previousTime = System.nanoTime();

		int frames = 0;
		int updates = 0;
		long lastCheck = System.currentTimeMillis();

		//Part để tạo game loop 2.0
		double deltaU = 0;
		double deltaF = 0; 
		
		
		while (true) {

//			now = System.nanoTime();
			long currentTime = System.nanoTime();

			//Sử dụng ntn sẽ sẽ ko hụt đi vài frame và bị tụt fps
			deltaU += (currentTime - previousTime) / timePerUpdate;
			deltaF += (currentTime - previousTime) / timePerFrame;
			previousTime = currentTime;
			
			if(deltaU >= 1) {
				update();
				updates++;
				deltaU--;
			}
			
			if(deltaF >= 1) {
				gamePanel.repaint();
				frames++;
				deltaF--;
			}
			
//			if (now - lastFrame >= timePerFrame) {
//				gamePanel.repaint();
//				lastFrame = now;
//				frames++;
//			}

			if (System.currentTimeMillis() - lastCheck >= 1000) {
				lastCheck = System.currentTimeMillis();
				System.out.println("FPS: " + frames + " |UPS: "+ updates);
				frames = 0;
				updates = 0;
			}
		}   
    }
    
	public void windowFocusLost() {
		if(Gamestate.state == Gamestate.PLAYING) // Không quan tâm nếu đang ở menu
			playing.getPlayer().resetDirBooolean(); // nếu đang chơi mà lost focus thì resetDir
	}
	
	public Menu getMenu() {
		return menu;
	}
	public Playing getPaying() {
		return playing;
	}
}
