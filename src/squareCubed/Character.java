package squareCubed;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;

public class Character {
	
	public float xPos = 100;
	public float yPos = 100;
	
	private final float xMoveSpeed = 0.4f;
	public float yMoveSpeed = 1.0f;
	private final float gravity = 0.003f;
	
	private boolean isJumping = false;
	
	public Image image = null;
	private Image rightImage = null;
	private Image leftImage = null;
	public Polygon collisionPolygon = null;
	
	//0 is for up and down
	//1 is for left and right
	//0 is for nothing
	//1 is up and right
	//2 is down and left
	private int[] direction = {0,0};
	
	public Character(float xPos, float yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
		try {
			rightImage = new Image("res/global/mobs/characterImageRight.png");
			leftImage = new Image("res/global/mobs/characterImageLeft.png");
			image = rightImage;
		} catch (SlickException e) {
			e.printStackTrace();
		}
		collisionPolygon = new Polygon(new float[]{
			this.xPos, this.yPos,
			this.xPos + image.getWidth(), this.yPos,
			this.xPos + image.getWidth(), this.yPos + image.getHeight(),
			this.xPos, this.yPos + image.getHeight(),
		});
	}
	
	public void handleMovement(Input input, int delta, Rectangle[][] collisionsArray, ArrayList<Entity> entities){
		Polygon tempPoly = collisionPolygon;
		tempPoly.setY(tempPoly.getY() - 1);
		float changeX = xMoveSpeed * delta;
		if(input.isKeyDown(Input.KEY_RIGHT)){
			tempPoly.setX(xPos + changeX);
			direction[1] = 1;
			if(!hasCollided(tempPoly, collisionsArray)){
				xPos += changeX;
				collisionPolygon.setX(tempPoly.getX());
				tempPoly.setX(xPos - changeX);
			}
		}else if (input.isKeyDown(Input.KEY_LEFT)){
			tempPoly.setX(xPos - changeX);
			direction[1] = 2;
			if(!hasCollided(tempPoly, collisionsArray)){
				xPos -= changeX;
				collisionPolygon.setX(tempPoly.getX());
				tempPoly.setX(xPos + changeX);
			}
		}else{
			direction[1] = 0;
		}
		if (input.isKeyPressed(Input.KEY_SPACE) && isJumping == false){
			if(!isJumping){
				yMoveSpeed = -8.0f;
			}
			while(this.xPos == 10.0f){
				if(this.isJumping == false){
					tempPoly.copy();
				}
			}
			isJumping = true;
		}
		if(yMoveSpeed < 0){ //i.e. moving upwards
			direction[0] = 1;
		}else if(yMoveSpeed > 0){ //i.e. moving downwards
			direction[0] = 2;
		}else if(yMoveSpeed == 0){ //stationary
			direction[0] = 0;
		}
		tempPoly.setY(yPos + yMoveSpeed);
		if(direction[1] == 1){
			tempPoly.setX(tempPoly.getX() - changeX - 1);
			image = rightImage;
		}else if(direction[1] == 2){
			tempPoly.setX(tempPoly.getX() + changeX + 1);
			image = leftImage;
		}
		if(!hasCollided(tempPoly, collisionsArray)){
			yMoveSpeed += gravity * (delta*10);
			yPos += yMoveSpeed;
			collisionPolygon.setY(tempPoly.getY());
			if(isJumping == false){
				isJumping = true;
			}
		}else{
			yMoveSpeed = 0.0f;
			if(direction[0] == 2){
				isJumping = false;
			}
		}
	}
	
	private boolean hasCollided(Polygon playerPolygon, Rectangle[][] collisionsArray){
		for(int yRect = 0; yRect < collisionsArray.length; yRect++){
			for(int xRect = 0; xRect < collisionsArray.length; xRect++){
				if(collisionsArray[xRect][yRect] != null && playerPolygon.intersects(collisionsArray[xRect][yRect])){
					//System.out.println(collisionsArray[xRect][yRect] + " : " + xRect + ", " + yRect + " = null" + " : " + direction[0] + ":" + direction[1]);
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean nonHostileEntityCollision(Polygon playerPolygon, ArrayList<Entity> entities){
		for(Entity e : entities){
			if(!((e.getType() == EntityType.Hostile) || e.getType() == EntityType.Spike)){
				if(playerPolygon.intersects(e.getPoly())){
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean hostileEntityCollision(Polygon playerPolygon, ArrayList<Entity> entities){
		for(Entity e : entities){
			if((e.getType() == EntityType.Hostile) || e.getType() == EntityType.Spike){
				if(playerPolygon.intersects(e.getPoly())){
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean checkInteract(ArrayList<Entity> entities){
		if(nonHostileEntityCollision(collisionPolygon, entities)){
			System.out.println("Entity Collision!");
			return true;
		}
		return false;
	}
	
	public boolean checkDeath(ArrayList<Entity> entities){
		if(hostileEntityCollision(collisionPolygon, entities)){
			System.out.println("DEAD!");
			return true;
		}
		return false;
	}

}