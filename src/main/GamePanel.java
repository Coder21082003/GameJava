/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
Lưu ý quan trọng của phần này 
1.Gameloop
*fps counter để đếm có bao nhiêu frame mỗi giây
    --> sử dụng repaint() ko sẽ là một gameloop tệ nên ta phải sử dụng một gameloop khác mà ta có thể kiểm soát fps và số khung hình đc update mỗi giây

*Thread() và Runnable()
    thread là luồng của chương trình chia nhỏ ra cho phép chạy mượt mà
    runnable là method truyền vào thread để biết code nào cần chạy trong thread và gọi thread.start() để chạy

2.Set size cho game 
tạo hàm setPanelSize và sử dụng setMin, max, prefer.... với tham số truyền vào là một object dimension
*/
package main;

import inputs.Keyboardinputs;
import inputs.Mouseinputs;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

//Import phải có static
import static utilz.Constants.Directions.*;
import static utilz.Constants.PlayerConstants.*;
import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;
/**
 *
 * @author admin
 */
public class GamePanel extends JPanel{
	
    private Game game;
    private Mouseinputs mouseInputs;

    
    public GamePanel(Game game){
        mouseInputs = new Mouseinputs(this);
        this.game = game;
        
        setPanelSize();
        addKeyListener(new Keyboardinputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
        
    }
    
    
    public void updateGame() {
        //1 s FPS = 120 nên để aniSpeed = 15 thì 1 s sẽ có 8 lần vẽ lại hình

    }
    
    
    // paintCOmponent de jPanel goi va truyen vao Graphics de ve
    @Override
    public void paintComponent(Graphics g){
        //Goi vao ham ve cua JPanel truoc de no ve cua mk trc xong den luot Graphics duoc ve (va ham nay con chuan bi cong tac ve de ko bi loi diem anh )
        super.paintComponent(g);
        game.render(g);
//        subImg = img.getSubimage(1*64, 8*40, 64, 40); 
//        
//        //Hàm vẽ ảnh drawImage(anh, x,y, witdh, high) và hàm getSubimage (ví trí x , y, độ rộng, độ dài ) -->chinh do rong dai để ảnh to nhỏ tùy ý
//        g.drawImage(animations[playerAction][aniIndex], (int)xDelta, (int)yDelta, 256, 160, null);  
        
    }  


	private void setPanelSize() {
    //    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        Dimension size = new Dimension(GAME_WIDTH,GAME_HEIGHT);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }
	
	public Game getGame() {
		return game;
	}
}
