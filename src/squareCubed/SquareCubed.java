package squareCubed;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.StateBasedGame;

public class SquareCubed extends StateBasedGame{
			
	private final static String GAMENAME = "Square Cubed";
	private final static int WIDTH = 704;
	private final static int HEIGHT = WIDTH;
	private final static int NUMLEVELS = 2;
	
	private static UnicodeFont pixelFont = null;
	private static Image backgroundImage = null;
	private static Image muteImage = null;
	private static Image notMuteImage = null;
	private static Image backButtonImage = null;
	
	private final String[] iconRefs = {"res/global/icon32.png", "res/global/icon16.png"};
	
	private static Music track = null;
	
	public static void main(String[] args) {
		try {
			AppGameContainer app = new AppGameContainer(new SquareCubed(GAMENAME));
			app.setDisplayMode(WIDTH, HEIGHT, false);
			app.start();
		} catch (SlickException e) {
			System.out.println("Game failed to init!");
			e.printStackTrace();
		}
	}

	public SquareCubed(String name) {
		super(name);
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		initGameProperties(gc);
		addState(new MainMenu());		//-1
		addState(new LevelsMenu());		//-2
		addState(new HelpMenu());		//-3
		addState(new SettingsMenu());	//-4
		addState(new AboutMenu());		//-5
		addState(new WinScreen());		//-6
		for(int stateNum = 1; stateNum <= 20; stateNum++){
			addState(new LevelClass(stateNum));
		}
		this.enterState(-1);
	}
	
	@SuppressWarnings("unchecked")
	private void initGameProperties(GameContainer gc) throws SlickException{
		pixelFont = new UnicodeFont("res/global/pixelFont.ttf", 24, false, false);
		pixelFont.getEffects().add(new ColorEffect(java.awt.Color.white));
		pixelFont.addAsciiGlyphs();
		pixelFont.loadGlyphs();
		
		backgroundImage = new Image("res/global/mainBackground.png");
		muteImage = new Image("res/global/muteImage.png");
		notMuteImage = new Image("res/global/notMuteImage.png");
		backButtonImage = new Image("res/global/backButtonImage.png");
		
		track = new Music("res/global/music/DST-1990.ogg");
		track.play();
		
		new SettingsLoader(track);
		
		gc.setShowFPS(SettingsLoader.getShowFPS());
		gc.setTargetFrameRate(120);
		gc.setIcons(iconRefs);
	}
	
	public static int getNumLevels(){
		return NUMLEVELS;
	}
	
	public static Image getBackgroundImage(){
		return backgroundImage;
	}
	
	public static Image getMuteImage(){
		if(SettingsLoader.getVolume() == 0.0f){
			return muteImage;
		}else{
			return notMuteImage;
		}
	}
	
	public static Image getBackButtonImage(){
		return backButtonImage;
	}
	
	public static UnicodeFont getFont(){
		return pixelFont;
	}

}
