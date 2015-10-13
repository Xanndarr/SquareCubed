package squareCubed;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Polygon;

public class Entity {
	
	public float xPos = -100;
	public float yPos = -100;
	
	private float xMoveSpeed = 0.0f;
	private float yMoveSpeed = 0.0f;
	
	//{startX, startY}{endX, endY}
	private float[][] moveCoords = null;
	
	public Image image = null;
	public Polygon collisionPolygon = null;
	
	private EntityType type = null;
	
	//non moving entity
	public Entity(EntityType type, float[] pos){
		xPos = pos[0];
		yPos = pos[1];
		init(type);
	}
	
	//moving entity
	public Entity(EntityType type, float moveSpeed, float[][] moveCoords){
		System.out.println("This is a moving entity.");
		this.moveCoords = moveCoords;
		xPos = moveCoords[0][0];
		yPos = moveCoords[0][1];
		//xMoveSpeed = (moveCoords[0][0] - moveCoords[0][1]) / 1000;
		//System.out.println(moveCoords[0][0] - moveCoords[0][1]);
		//yMoveSpeed = (moveCoords[1][0] - moveCoords[1][1]) / 1000;
		//System.out.println(moveCoords[1][0] - moveCoords[1][1]);
		xMoveSpeed = yMoveSpeed = moveSpeed;
		System.out.println(xPos + " : " + yPos + " : " + xMoveSpeed + " : " + yMoveSpeed);
		init(type);
	}
	
	private void init(EntityType type){
		System.out.println("INIT FUN");
		this.type = type;
		if(this.type == EntityType.Hostile){
			image = type.getRightImage();
		}else {
			image = type.getImage();
		}
		collisionPolygon = new Polygon(new float[]{
			this.xPos, this.yPos,
			this.xPos + type.getWidth(), this.yPos,
			this.xPos + type.getWidth(), this.yPos + type.getHeight(),
			this.xPos, this.yPos + type.getHeight(),
		});
	}
	
	public void handleMovement(int delta){
		if(moveCoords != null){
			xPos += xMoveSpeed * delta;
			yPos += yMoveSpeed * delta;
			if(xPos > moveCoords[0][1]){
				image = type.getLeftImage();
				xPos = moveCoords[0][1];
				xMoveSpeed = -xMoveSpeed;
				//System.out.print("1");
			}else if (xPos < moveCoords[0][0]) {
				image = type.getRightImage();
				xPos = moveCoords[0][0];
				xMoveSpeed = -xMoveSpeed;
				//System.out.print("2");
			}//else {
			//	image = type.getRightImage();
			//}
			//System.out.println(moveCoords[1][1] + " - " + moveCoords[1][0]);
			if(yPos > moveCoords[1][1]){
				yPos = moveCoords[1][1];
				yMoveSpeed = -yMoveSpeed;
				//System.out.print("3");
			}else if (yPos < moveCoords[1][0]) {
				yPos = moveCoords[1][0];
				yMoveSpeed = -yMoveSpeed;
				//System.out.print("4");
			}
			//System.out.println(type + " : " + xPos + " : " + yPos);
			collisionPolygon.setX(xPos);
			collisionPolygon.setY(yPos);
		}
	}
	
	public Polygon getPoly(){
		return collisionPolygon;
	}
	
	public EntityType getType(){
		return type;
	}

}