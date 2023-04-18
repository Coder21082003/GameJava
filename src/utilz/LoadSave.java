package utilz;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import entities.Crabby;
import main.Game;
import main.GamePanel;
import static utilz.Constants.EnemyConstants.CRABBY;

//Để load hoặc save 
// --> để lấy ảnh load gì đó hoặc call something
//Chỉ có static method

public class LoadSave {
	//Sprite = ảo , atlas = bản đồ
	public static final String PLAYER_ATLAS = "player_sprites.png";
	public static final String LEVEL_ATLAS = "outside_sprites.png";
//	public static final String LEVEL_ONE_DATA = "level_one_data.png";
//	public static final String LEVEL_ONE_DATA= "level_one_data_long.png"; //bigger lvl
	public static final String MENU_BUTTON = "button_atlas.png";
	public static final String MENU_BACKGROUND = "menu_background.png";
	public static final String PAUSE_BACKGROUND = "pause_menu.png";
	public static final String SOUND_BUTTON = "sound_button.png";
	public static final String URM_BUTTONS = "urm_buttons.png";
	public static final String MENU_BACKGROUND_IMG = "background_menu.png";
	public static final String PLAYING_BG_IMG = "playing_bg_img.png";
	public static final String BIG_CLOUDS = "big_clouds.png";
	public static final String SMALL_CLOUDS = "small_clouds.png"; 
	public static final String CRABBY_SPRITE = "crabby_sprite.png"; //sprite = quái vật 
	public static final String STATUS_BAR = "health_power_bar.png";
	public static final String COMPLETED_IMG = "completed_sprite.png";
	
	
	public static BufferedImage getSpriteAtlas(String fileName) {
		BufferedImage img = null;
		InputStream is =  LoadSave.class.getResourceAsStream("/" + fileName);
        try {
            img = ImageIO.read(is);
       } catch (IOException ex) {
            Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
        	try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        return img;
	}
	
	
	public static BufferedImage[] GetAllLevels() {
		URL url = LoadSave.class.getResource("/lvls"); //là location của folder chứa ảnh lvl
		File file = null;
		//URI là một chuỗi ký tự được sử dụng để nhận diện các tài nguyên
		try {
			file = new File(url.toURI()); //có đc folder chứa res inside
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		//listFile là go over tất cả file
		File[] files = file.listFiles();
		File[] filesSorted = new File[files.length]; //biến chứa file sau sort
		
		//Lấy tên file ra theo thứ tự tên từ thấp -> cao
		for (int i = 0; i < filesSorted.length; i++)
			for (int j = 0; j < files.length; j++) {
				if (files[j].getName().equals((i + 1) + ".png"))
					filesSorted[i] = files[j];

			}
		
		BufferedImage[] imgs = new BufferedImage[filesSorted.length];
		
		for (int i = 0; i < imgs.length; i++)
			try {
				imgs[i] = ImageIO.read(filesSorted[i]);
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		return imgs; //trả về mảng lvl
	}
	
	
}
