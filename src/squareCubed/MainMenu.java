package squareCubed;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MainMenu extends BasicGameState{
	
	private final int stateID = -1;
	
	private Image titleImage = null;
	
	private Color[] textColor = new Color[4];
	private Color goldColour = null;
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		titleImage = new Image("res/menu/titleImage.png");
		goldColour = new Color(251, 227, 27);
		resetColor();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.setFont(SquareCubed.getFont());
		g.drawImage(SquareCubed.getBackgroundImage(), 0, 0);
		g.drawImage(SquareCubed.getMuteImage(), 665, 5);
		g.drawImage(titleImage, 0, 50);
		
		g.setColor(textColor[0]);
		g.drawString("Play", 80, 520);
		g.setColor(textColor[1]);
		g.drawString("Help", 215, 520);
		g.setColor(textColor[2]);
		g.drawString("Settings", 335, 520);
		g.setColor(textColor[3]);
		g.drawString("About", 530, 520);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();
		if(input.isKeyPressed(Input.KEY_ESCAPE)){
			gc.exit();
		}
		//Mute Button
		if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
			if(mouseY >= 5 && mouseY <= 37 && mouseX >= 665 && mouseX <= 697){
				if(SettingsLoader.getVolume() == 0.0f){
					SettingsLoader.setMute(false);
				}else{
					SettingsLoader.setMute(true); 
				}
			}
		}
		//Select option on main menu
		if(mouseY <= 537 && mouseY >= 520){
			if(mouseX >= 80 && mouseX <= 152){
				textColor[0] = goldColour;
				if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
					sbg.enterState(-2);
				}
			}else if (mouseX >= 215 && mouseX <= 287) {
				textColor[1] = goldColour;
				if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
					sbg.enterState(-3);
				}
			}else if (mouseX >= 335 && mouseX <= 482) {
				textColor[2] = goldColour;
				if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
					sbg.enterState(-4);
				}
			}else if (mouseX >= 530 && mouseX <= 620) {
				textColor[3] = goldColour;
				if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
					sbg.enterState(-5);
				}
			}else{
				resetColor();
			}
		}else{
			resetColor();
		}
	}
	
	private void resetColor(){
		for(int index = 0; index < textColor.length;index++){
			textColor[index] = Color.white;
		}
	}

	@Override
	public int getID() {
		return stateID;
	}

}
