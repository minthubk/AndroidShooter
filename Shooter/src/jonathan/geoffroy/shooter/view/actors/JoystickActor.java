package jonathan.geoffroy.shooter.view.actors;

import java.util.ArrayList;

import jonathan.geoffroy.shooter.Shooter;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class JoystickActor extends Touchpad {
	private final static String BACKGROUND = Shooter.IMAGES + "/JoystickActor/background.png", KNOB = Shooter.IMAGES + "/JoystickActor/knob.png";
	private static TouchpadStyle touchpadStyle;
	private static Skin touchpadSkin;

	public JoystickActor() {
		super(10, touchpadStyle);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ArrayList<AssetDescriptor<Object>> getAssetDescriptors() {
		ArrayList<AssetDescriptor<Object>> result = new ArrayList<AssetDescriptor<Object>>();
		result.add(new AssetDescriptor(BACKGROUND, Texture.class));
		result.add(new AssetDescriptor(KNOB, Texture.class));
		return result;
	}

	public static void initialize() {
		touchpadStyle = new TouchpadStyle();
		Texture backgroundText = (Texture) Shooter.getAsset(BACKGROUND);
		Texture knobText = (Texture) Shooter.getAsset(KNOB);

		touchpadSkin = new Skin();
		touchpadSkin.add("background", backgroundText);
		touchpadSkin.add("knob", knobText);
		touchpadStyle = new TouchpadStyle();
		Drawable touchBackground = touchpadSkin.getDrawable("background");
		Drawable touchKnob = touchpadSkin.getDrawable("knob");

		touchpadStyle.background = touchBackground;
		touchpadStyle.knob = touchKnob;
	}
}
