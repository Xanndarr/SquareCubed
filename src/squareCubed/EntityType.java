package squareCubed;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public enum EntityType {
	
	Hostile(1),
	Key(2),
	Spike(3);
	
	private int type = 0;
	private float width = 0.0f;
	private float height = 0.0f;
	
	private Image nullImage = null;
	private Image entityImage = null;
	private Image rightImage = null;
	private Image leftImage = null;
	
	private EntityType(int type){
		try {
			nullImage = new Image("res/global/mobs/Null.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
		this.type = type;
		width = height = 32.0f;
		switch (type) {
		case 1:
			try {
				rightImage = new Image("res/global/mobs/HostileRight.png");
				leftImage = new Image("res/global/mobs/HostileLeft.png");
			} catch (SlickException e) {
				e.printStackTrace();
			}
			break;
		case 2:
			try {
				entityImage = new Image("res/global/mobs/Key.png");
			} catch (SlickException e) {
				e.printStackTrace();
			}
			break;
		case 3:
			try {
				entityImage = new Image("res/global/mobs/Spike.png");
			} catch (SlickException e) {
				e.printStackTrace();
			}
		default:
			entityImage = nullImage;
			break;
		}
	}
	
	public int getType(){
		return type;
	}
	
	public float getWidth(){
		return width;
	}
	
	public float getHeight(){
		return height;
	}
	
	public Image getImage(){
		return entityImage;
	}
	
	public Image getRightImage(){
		if(rightImage != null){
			return rightImage;
		}else {
			return nullImage;
		}
	}
	
	public Image getLeftImage(){
		if(leftImage != null){
			return leftImage;
		}else {
			return nullImage;
		}
	}

}
