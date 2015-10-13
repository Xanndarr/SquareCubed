package squareCubed;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class LevelsMenu extends BasicGameState{
	
	private final int stateID = -2;
	
	private static float[] levelCoordsX = new float[4];
	private static float[] levelCoordsY = new float[5];
	private static Color[] textColor = null;
	
	private Color goldColour = null;
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		goldColour = new Color(251, 227, 27);
		for(int x = 1; x <= levelCoordsX.length; x++){
			levelCoordsX[x-1] = x * 125;
		}
		for(int y = 1; y <= levelCoordsY.length; y++){
			levelCoordsY[y-1] = y * 110;
		}
		
		textColor = new Color[levelCoordsX.length*levelCoordsY.length];
		resetColour();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.setFont(SquareCubed.getFont());
		g.drawImage(SquareCubed.getBackgroundImage(), 0, 0);
		g.drawImage(SquareCubed.getMuteImage(), 665, 5);
		g.drawImage(SquareCubed.getBackButtonImage(), 0, 0);
		
		g.setColor(goldColour);
		g.drawString("Levels", 305, 50);
		g.setColor(Color.white);
		
		/*for(int x = 0; x < levelCoordsX.length; x++){
			for(int y = 0; y < levelCoordsY.length; y++){
				System.out.print(levelNum + " ");
				if(g.getColor() != Color.white){
					System.out.println(g.getColor());
				}
				g.setColor(textColor[x][y]);
				g.drawRoundRect(levelCoordsX[x], levelCoordsY[y], 90, 90, 20);
				if(levelNum < 10){
					g.drawString(Integer.toString(levelNum), levelCoordsX[x] + 37, levelCoordsY[y] + 37);
				}else{
					g.drawString(Integer.toString(levelNum), levelCoordsX[x] + 27, levelCoordsY[y] + 37);
				}
				levelNum += 4;
			}
			levelNum -= 19;
		}*/
		int levelNum = 1;
		for(int b = 0; b < levelCoordsY.length; b++){
			for(int a = 0; a < levelCoordsX.length; a++){
				g.setColor(textColor[levelNum-1]);
				g.drawRoundRect(levelCoordsX[a], levelCoordsY[b], 90, 90, 20);
				if(levelNum < 10){
					g.drawString(Integer.toString(levelNum), levelCoordsX[a] + 37, levelCoordsY[b] + 37);
				}else{
					g.drawString(Integer.toString(levelNum), levelCoordsX[a] + 27, levelCoordsY[b] + 37);
				}
				levelNum++;
			}
		}
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();
		boolean mousePress = false;
		if(input.isKeyPressed(Input.KEY_ESCAPE)){
			sbg.enterState(-1);
		}
		if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
			mousePress = true;
		}
		if(mousePress){
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
		int levelNum = 1;
		for(int a = 0; a < levelCoordsX.length; a++){
			for(int b = 0; b < levelCoordsY.length; b++){
				if (SettingsLoader.isLevelComplete(levelNum)) {
					if(mouseX >= levelCoordsX[a] && mouseX <= levelCoordsX[a] + 90 && mouseY >= levelCoordsY[b] && mouseY <= levelCoordsY[b] + 90){					
						if(mousePress){
							//if(SettingsLoader.isLevelCompleted(levelNum) || SettingsLoader.isLevelCompleted(levelNum - 1)){
								sbg.enterState(levelNum);
							//}
						}
						textColor[levelNum-1] = goldColour;
					}else{
						textColor[levelNum-1] = Color.white;
					}
				}
				levelNum += 4;
			}
			levelNum -= 19;
		}
	}
	
	public static void resetColour(){
		int levelNum = 1;
		for(int a = 0; a < levelCoordsX.length; a++){
			for(int b = 0; b < levelCoordsY.length; b++){
				if(SettingsLoader.isLevelComplete(levelNum)){
					System.out.println("Wharrgarbl");
					System.out.println(levelNum);
					textColor[levelNum-1] = Color.white;
				} else {
					textColor[levelNum-1] = Color.gray;
				}
				System.out.println(levelNum + " : " + textColor[levelNum-1]);
				levelNum++;
			}
		}
	}

	@Override
	public int getID() {
		return stateID;
	}

}
