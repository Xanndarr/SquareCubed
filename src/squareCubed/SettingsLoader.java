package squareCubed;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.newdawn.slick.Music;

public class SettingsLoader {
	
	private final String fileLocation = "res/global/settings.conf";
	private Scanner in = null;
	private static File file = null;
	private static FileWriter fw = null;
	
	private static boolean[] completedLevels = null;
	private static boolean mute = false;
	private static boolean startMute = false;
	private static boolean showFPS = false;
	private static Music track = null;
	
	public SettingsLoader(Music track){
		SettingsLoader.track = track;
		/*//in = new Scanner(fileLocation);
		
		file = new File(fileLocation);
		try {
			fr = new FileReader(file);
			fr.toString();
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");
		}
		
		//mute = in.nextBoolean();
		completedLevels = new boolean[20];
		for(int a = 0; a < completedLevels.length; a++){
			completedLevels[a] = in.nextBoolean(); 
		}*/
		
		file = new File(fileLocation);
		
		try {
			in = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		startMute = mute = Boolean.parseBoolean(in.nextLine());
		showFPS = Boolean.parseBoolean(in.nextLine());
		completedLevels = new boolean[21];
		
		completedLevels[0] = true;
		
		for (int i = 1; i < completedLevels.length; i++){
			if(in.nextLine().equals("y")){
				completedLevels[i] = true;
			} else {
				completedLevels[i]= false; 
			}
		}
		setMute(mute);
	}
	
	public static float getVolume(){
		return track.getVolume();
	}
	
	public static void setVolume(float volume){
		track.setVolume(volume);
	}
	
	public static boolean getMute(){
		return mute;
	}
	
	public static void setMute(boolean mute){
		if (mute) {
			setVolume(0.0f);
			return;
		}
		setVolume(1.0f);
	}
	
	public static void setStartMute(boolean mute){
		startMute = mute;
		save();
	}
	
	public static boolean getStartMute(){
		return startMute;
	}
	
	public static void setShowFPS(boolean show){
		showFPS = show;
		save();
	}
	
	public static boolean getShowFPS(){
		return showFPS;
	}
	
	public static boolean[] getCompletedLevels(){
		return completedLevels;
	}
	
	public static boolean isLevelComplete(int level){
		if(level <= 0){
			return true;
		}
		if(completedLevels[level] == true){
			return true;
		}
		return false;
	}
	
	public static void setLevelComplete(int level){
		completedLevels[level + 1] = true;
		System.out.println(level);
	}
	
	public static void save(){
		file.delete();
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Save File could not be created.");
		}
		if(file.canWrite()){
			try {
				fw = new FileWriter(file);
				fw.write(Boolean.toString(startMute) + "\n");
				fw.write(Boolean.toString(showFPS) + "\n");
				for(int level = 1; level < completedLevels.length; level++){
					if(completedLevels[level] == true){
						fw.write("y\n");
					}else{
						fw.write("n\n");
					}
					System.out.println(level);
				}
			} catch (IOException e) {
				System.out.println("Fail");
			}
			System.out.println("WIN");
		}
		try {
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void resetSave(){
		file.delete();
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Save File could not be created.");
		}
		if(file.canWrite()){
			try {
				fw = new FileWriter(file);
				fw.write(Boolean.toString(startMute) + "\n");
				fw.write(Boolean.toString(showFPS) + "\n");
				fw.write("y\n");
				for(int level = 1; level < completedLevels.length; level++){
					completedLevels[level] = false;
					fw.write("n\n");
				}
				completedLevels[1] = true;
			} catch (IOException e) {
				System.out.println("Fail");
			}
			System.out.println("WIN");
		}
		try {
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LevelsMenu.resetColour();
	}

}
