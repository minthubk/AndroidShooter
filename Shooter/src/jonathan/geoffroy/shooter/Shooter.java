package jonathan.geoffroy.shooter;

import jonathan.geoffroy.shooter.view.MainMenuScreen;
import jonathan.geoffroy.shooter.view.utils.App;

public class Shooter extends App {
	public final static String IMAGES = "data/img/";
	public static String FONTS = "data/fonts/";
	public static int MAINMENU = 0;
	@Override
	public void create() {
		super.create();
		screens.add(new MainMenuScreen());

		setScreen(MAINMENU);
	}
}