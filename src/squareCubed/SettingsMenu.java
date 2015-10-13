package squareCubed;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class SettingsMenu extends BasicGameState{
	
	private final int stateID = -4;
	
	private Color[] textColor = new Color[3];
	private Color goldColour = null;
	private TextField textField = null;
	
	//private boolean[] completedLevels = null;

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		goldColour = new Color(251, 227, 27);
		resetColor();
		textField = new TextField(gc, SquareCubed.getFont(), 327, 460, 50, 30);
		textField.setCursorVisible(false);
		textField.setBorderColor(goldColour);
		textField.setText("");
		//completedLevels = SettingsLoader.getCompletedLevels();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.setFont(SquareCubed.getFont());
		g.drawImage(SquareCubed.getBackgroundImage(), 0, 0);
		g.drawImage(SquareCubed.getMuteImage(), 665, 5);
		g.drawImage(SquareCubed.getBackButtonImage(), 0, 0);
		
		/*for (int i = 0; i < completedLevels.length; i++) {
			if(completedLevels[i] == true){
				g.drawString((i+1) + " true", 30, i*20);
			} else {
				g.drawString((i+1) + " false", 30, i*20);
			}
		}*/
		g.setColor(goldColour);
		g.drawString("Settings", 285, 50);
		g.drawString("Level Cheater", 240, 300);
		
		g.setColor(Color.white);
		g.drawString("Start Muted: ", 150, 130);
		g.drawString("Show FPS: ", 150, 200);
		g.drawString("Enter level number to unlock up", 60, 370);
		g.drawString("to that level:", 235, 400);
		textField.render(gc, g);
		g.drawString("Click      to reset your save file", 35, 570);
		
		g.setColor(textColor[0]);
		g.drawString(Boolean.toString(SettingsLoader.getStartMute()), 450, 130);
		g.setColor(textColor[1]);
		g.drawString(Boolean.toString(SettingsLoader.getShowFPS()), 450, 200);
		g.setColor(textColor[2]);
		g.drawString("here", 147, 570);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		resetColor();
		Input input = gc.getInput();
		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();
		boolean mousePressed = false;
		if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
			mousePressed = true;
		}
		if(mousePressed){
			if(mouseY >= 5 && mouseY <= 37 && mouseX >= 665 && mouseX <= 697){
				if(SettingsLoader.getVolume() == 0.0f){
					SettingsLoader.setMute(false);
				}else{
					SettingsLoader.setMute(true);
				}
			}
			if(mouseY >= 0 && mouseY <= 32 && mouseX >= 0 && mouseX <= 32){
				sbg.enterState(-1);
			}
		}
		if(mouseY >= 130 && mouseY <= 147){
			if(SettingsLoader.getStartMute()){
				if(mouseX >= 450 && mouseX <= 523){
					textColor[0] = goldColour;
					if(mousePressed){
						SettingsLoader.setStartMute(false);
					}
				}
			}else{
				if(mouseX >= 450 && mouseX <= 540){
					textColor[0] = goldColour;
					if(mousePressed){
						SettingsLoader.setStartMute(true);
					}
				}
			}
		}
		if(mouseY >= 200 && mouseY <= 217){
			if(SettingsLoader.getShowFPS()){
				if(mouseX >= 450 && mouseX <= 523){
					textColor[1] = goldColour;
					if(mousePressed){
						SettingsLoader.setShowFPS(false);
						gc.setShowFPS(false);
					}
				}
			}else{
				if(mouseX >= 450 && mouseX <= 540){
					textColor[1] = goldColour;
					if(mousePressed){
						SettingsLoader.setShowFPS(true);
						gc.setShowFPS(true);
					}
				}
			}
		}
		if(mouseY >= 570 && mouseY <= 587){
			if(mouseX >= 147 && mouseX <= 220){
				textColor[2] = goldColour;
				if(mousePressed){
					SettingsLoader.resetSave();
				}
			}
		}
		if(input.isKeyPressed(Input.KEY_ENTER)){
			if(!textField.getText().trim().equals("")){
				int textInput = Integer.parseInt(textField.getText().trim());
				if(textInput <= 20 && textInput >= 1){
					for(int a = 1; a < textInput; a++){
						SettingsLoader.setLevelComplete(a);
					}
				}
				textField.setText("");
			}
		}
	}
	
	private void resetColor(){
		for(int index = 0; index < textColor.length;index++){
			if(index == 2){
				textColor[index] = Color.gray;
			}else{
				textColor[index] = Color.white;
			}
		}
	}

	@Override
	public int getID() {
		return stateID;
	}

}
