package Levels;
//Chứa dữ liệu về level

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import static utilz.HelpMethods.GetLevelData;
import static utilz.HelpMethods.GetCrabs;
import static utilz.HelpMethods.GetPlayerSpawn;

import entities.Crabby;
import main.Game;
import utilz.LoadSave;

public class Level {
	
	private BufferedImage img;
	private int[][] lvlData;
	private ArrayList<Crabby> crabs;
    private int lvlTilesWide;//Lvl width
    private int maxTilesOffset;
    private int maxlvlOffsetX; //max lvl offset can be
    private Point playerSpawn;
	
	public Level(BufferedImage img) {
		this.img = img;
		createLevelData();
		createEnemies();
		calcLvlOffsets();
		calcPlayerSpawn();
		
	}
	
	
	private void calcPlayerSpawn() {
		playerSpawn = GetPlayerSpawn(img);
	}


	private void calcLvlOffsets() {
		lvlTilesWide = img.getWidth();
		maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
		maxlvlOffsetX = Game.TILES_SIZE * maxTilesOffset; 
	}


	private void createEnemies() {
		crabs = GetCrabs(img);
		
	}


	private void createLevelData() {
		lvlData = GetLevelData(img);
	}


	public int getSpriteIndex(int x, int y) {
		return lvlData[y][x];
	}
	
	public int[][] getLevelData(){
		return lvlData;
	}
	
	public int getLvlOffset() {
		return maxlvlOffsetX;
	}
	
	public ArrayList<Crabby> getCrabs(){
		return crabs;
	}
	
	public Point getPlayerSpawn() {
		return playerSpawn;
	}
}
