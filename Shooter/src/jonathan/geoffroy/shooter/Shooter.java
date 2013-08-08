package jonathan.geoffroy.shooter;

import jonathan.geoffroy.shooter.view.screens.LevelScreen;
import jonathan.geoffroy.shooter.view.screens.MainMenuScreen;
import jonathan.geoffroy.shooter.view.utils.App;

public class Shooter extends App {
	public final static String IMAGES = "data/img/";
	public static final String MAPS = "data/maps/";
	public static String FONTS = "data/fonts/";
	public static int MAINMENU = 0, LEVEL = 1;
	
	@Override
	public void create() {
		super.create();
		screens.add(new MainMenuScreen());
		screens.add(new LevelScreen());
		
		setScreen(MAINMENU);
	}
}