package gamestates;

import java.awt.event.MouseEvent;

import main.Game;
import ui.MenuButton;

public class State {
	//Super class cho tất cả gamestate
	
	protected Game game;
	
	public State(Game game) {
		this.game = game;
	}
	//Vì tất cả state đều có button nên tạo 1 hàm chung để check xem có hover chuột vào ko 
	public boolean isIn(MouseEvent e, MenuButton mb) {
		//Trả về true nếu chuột đang ở trong bounds của button
		return mb.getBounds().contains(e.getX(), e.getY());
	}
	
	public Game getGame() {
		return game;
	}
	
}
