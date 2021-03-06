package squareCubed;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class WinScreen extends BasicGameState{
	
	private final int stateID = -6;

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawImage(SquareCubed.getBackgroundImage(), 0, 0);
		g.drawImage(SquareCubed.getMuteImage(), 665, 5);
		g.drawImage(new Image("res/menu/winContent.png"), 0, 0);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();
		if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
			if(mouseY >= 5 && mouseY <= 37 && mouseX >= 665 && mouseX <= 697){
				if(SettingsLoader.getVolume() == 0.0f){
					SettingsLoader.setMute(false);
				}else{
					SettingsLoader.setMute(true); 
				}
			}
		}
		if(input.isKeyPressed(Input.KEY_ENTER)){
			sbg.enterState(-1);
		}
	}

	@Override
	public int getID() {
		return stateID;
	}

}
