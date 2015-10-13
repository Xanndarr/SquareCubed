package squareCubed;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

public class LevelClass extends BasicGameState{
	
	private int stateID = 0;
	
	private String baseLocation = "res/maps/";
	
	private TiledMap levelMap = null;
	private Image backgroundImage = null;
	private Rectangle[][] collisionsRectangles = null;
	
	private Character character = null;
	private ArrayList<Entity> entities = null;
	
	private int startX = 100, startY = 100;
	private boolean stopCharacterMove = false;
	private boolean completed = false;
	
	private ExplosionSystem explosionSystem = null;
		
	public LevelClass(int levelID) {
		stateID = levelID;
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		levelMap = new TiledMap(baseLocation + stateID + "/" + "map" + stateID + ".tmx", true);
		backgroundImage = new Image(baseLocation + stateID + "/" + "background" + stateID + ".png");
		
		entities = new ArrayList<Entity>(0);
		collisionsRectangles = new Rectangle[levelMap.getHeight()][levelMap.getWidth()];
		
		for(int xTile = 0; xTile < levelMap.getWidth(); xTile++){
			for(int yTile = 0; yTile < levelMap.getHeight(); yTile++){
				if(levelMap.getTileProperty(levelMap.getTileId(xTile, yTile, levelMap.getLayerIndex("walls")), "solid", "false") != "false"){
					collisionsRectangles[xTile][yTile] = new Rectangle(xTile*levelMap.getTileWidth(), yTile*levelMap.getTileHeight(), levelMap.getTileWidth(), levelMap.getTileHeight());
				}else if(levelMap.getTileProperty(levelMap.getTileId(xTile, yTile, levelMap.getLayerIndex("entities")), "spike", "false") != "false"){
					entities.add(new Entity(EntityType.Spike, new float[]{
						xTile * levelMap.getTileWidth(),
						yTile * levelMap.getTileHeight(),
					}));
				}else if(levelMap.getTileProperty(levelMap.getTileId(xTile, yTile, levelMap.getLayerIndex("entities")), "key", "false") != "false"){
					entities.add(new Entity(EntityType.Key, new float[]{
						xTile * levelMap.getTileWidth(),
						yTile * levelMap.getTileHeight(),
					}));
				}				
				System.out.println(xTile + " : " + yTile);
			}
		}
		System.out.println("Object Count: " + levelMap.getObjectCount(0));
		System.out.println("Object X: " + levelMap.getObjectX(0, 0));
		System.out.println("Object Y: " + levelMap.getObjectY(0, 0));
		System.out.println(levelMap.getObjectX(0, 0) + levelMap.getObjectWidth(0, 0));
		for(int mobNum = 0; mobNum < levelMap.getObjectCount(0); mobNum++){
			System.out.println("HI " + levelMap.getObjectProperty(0, mobNum, "spawn", "derp"));
			//FIX THIS~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
			if(levelMap.getObjectProperty(0, mobNum, "spawn", "derp").equals("true")){
				System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
				System.out.println(levelMap.getObjectX(0, mobNum) + " : " + levelMap.getObjectY(0, mobNum));
				startX = levelMap.getObjectX(0, mobNum);
				startY = levelMap.getObjectY(0, mobNum);
			}else{
				entities.add(new Entity(EntityType.Hostile, Float.parseFloat(levelMap.getObjectProperty(0, mobNum, "speed", "5.0")) ,new float[][]{
					{levelMap.getObjectX(0, mobNum), ((levelMap.getObjectX(0, mobNum) + levelMap.getObjectWidth(0, mobNum)) - 32)},
					{levelMap.getObjectY(0, mobNum), ((levelMap.getObjectY(0, mobNum) + levelMap.getObjectHeight(0, mobNum)) - 32)},
				}));
			}
			System.out.println("ADDED HOSTILE ENTITY: " + mobNum + " :MOVE SPEED: " + Float.parseFloat(levelMap.getObjectProperty(0, mobNum, "speed", "50.0")));
		}
		character = new Character(startX, startY);
		explosionSystem = new ExplosionSystem();
		for(int b = 0; b < (collisionsRectangles.length); b++){
			for(int a = 0; a < (collisionsRectangles.length); a++){
				if(collisionsRectangles[a][b] != null){
					System.out.print("x ");
				}else {
					System.out.print("o ");
				}
			}
			System.out.println();
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.setColor(Color.white);
		g.drawImage(backgroundImage, 0, 0);
		g.drawString(Integer.toString(stateID), 100, 100);
		g.setColor(Color.red);
		g.setFont(SquareCubed.getFont());
		levelMap.render(0, 0);
		g.drawImage(SquareCubed.getMuteImage(), 665, 5);
		g.drawImage(character.image, character.xPos, character.yPos);
		for(Entity e : entities){
			if(e.getType() == EntityType.Hostile){
				g.drawImage(e.image, e.xPos, e.yPos);
			}
		}
		explosionSystem.getEffectSystem().render();
		if(stopCharacterMove){
			if(completed){
				g.drawString("Press Enter to return", 150, 300);
				g.drawString("to Level Menu", 225, 340);
			}else{
				g.drawString("Press R to respawn", 190, 300);
			}
		}
		//g.drawRect(keyEntity.getPoly().getX(), keyEntity.getPoly().getY(), EntityType.Key.getWidth(), EntityType.Key.getHeight());
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();
		//Mute
		if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
			if(mouseY >= 5 && mouseY <= 37 && mouseX >= 665 && mouseX <= 697){
				if(SettingsLoader.getVolume() == 0.0f){
					SettingsLoader.setMute(false);
				}else{
					SettingsLoader.setMute(true);
				}
			}
		}
		if(input.isKeyPressed(Input.KEY_ESCAPE) || (input.isKeyPressed(Input.KEY_ENTER) && completed)){
			respawn();
			if(completed && getID() == 20){
				completed = false;
				sbg.enterState(-6);
			}else{
				completed = false;
				sbg.enterState(-2);
			}
		}
		if(!stopCharacterMove){
			character.handleMovement(input, delta, collisionsRectangles, entities);
			if(character.checkDeath(entities)){
				System.out.println("DEATDGBSKGBSDFI");
				character.image = new Image("res/global/mobs/characterImageBlank.png");
				character.yMoveSpeed = 0.0f;
				stopCharacterMove = true;
				explosionSystem.addExplosion(character.xPos, character.yPos);
			}
			if(character.checkInteract(entities)){
				System.out.println("bACJKSA");
				stopCharacterMove = true;
				completed = true;
				SettingsLoader.setLevelComplete(this.getID());
				SettingsLoader.save();
			}
			
			for(Entity e : entities){
				e.handleMovement(delta);
			}
		}
		if(input.isKeyPressed(Input.KEY_R) && !completed){
			respawn();
		}
		explosionSystem.getEffectSystem().update(delta);
	}
	
	private void respawn() throws SlickException{
		stopCharacterMove = false;
		character.image = new Image("res/global/mobs/characterImageRight.png");
		character.xPos = startX;
		character.yPos = startY;
		character.collisionPolygon.setX(startX);
		character.collisionPolygon.setY(startY);
		character.yMoveSpeed = 0.0f;
	}

	@Override
	public int getID() {
		if(stateID == 0){
			return 0;
		}else{
			return stateID;
		}
	}
	
	public TiledMap getMap(){
		return levelMap;
	}

}
