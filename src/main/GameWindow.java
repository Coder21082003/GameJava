/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main; 
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JFrame;
import javax.swing.JRootPane;
/**
 *
 * @author admin
 */

// lam giao dien cho game
public class GameWindow {
    private JFrame jframe;
    //panel la anh con frame la khung hinh --> phai cho anh vao khung hinh
    public GameWindow(GamePanel gamePanel) {
        
        jframe = new JFrame();
        
 
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Để thoát khi bấm x ở góc
        jframe.add(gamePanel);
        jframe.setResizable(false); // ko cho reszie khi ko vừa vs component
        jframe.pack(); // nói với jframe tạo ra size vừa với component mà ta có (ở đây chỉ có gamepanel)
        jframe.setLocationRelativeTo(null);
        jframe.setVisible(true);
        jframe.addWindowFocusListener(new WindowFocusListener() {
			
			@Override
			public void windowLostFocus(WindowEvent e) {
				gamePanel.getGame().windowFocusLost();
				
			}
			
			@Override
			public void windowGainedFocus(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
    }
}
