package jonathan.geoffroy.shooter.view.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;

import jonathan.geoffroy.shooter.Shooter;
import jonathan.geoffroy.shooter.model.Map;
import jonathan.geoffroy.shooter.view.actors.CharacterActor;
import jonathan.geoffroy.shooter.view.actors.JoystickActor;
import jonathan.geoffroy.shooter.view.actors.MapActor;
import jonathan.geoffroy.shooter.view.utils.App;
import jonathan.geoffroy.shooter.view.utils.StageScreen;

public abstract class GameScreen extends StageScreen {
	private static final String MUSIC = Shooter.SOUNDS + "Level/music.mp3";
	protected static final String BACKGROUNDS = Shooter.IMAGES+ "Game/backgrounds/";

	protected MapActor mapActor;
	protected CharacterActor characterActor;
	protected JoystickActor joystick;
	protected Map map;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ArrayList<AssetDescriptor<Object>> getAssetDescriptors() {
		ArrayList<AssetDescriptor<Object>> result = new ArrayList<AssetDescriptor<Object>>();
		result.add(new AssetDescriptor(MUSIC, Music.class));
		result.addAll(MapActor.getAssetDescriptors());
		result.addAll(CharacterActor.getAssetDescriptors());
		result.addAll(JoystickActor.getAssetDescriptors());
		return result;
	}

	@Override
	protected void onEndLoaded() {
		mapActor = new MapActor(this);
		mapActor.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		stage.addActor(mapActor);

		characterActor = new CharacterActor();
		characterActor.setBounds(
				Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 2,
				mapActor.getTerrainWidth(), mapActor.getTerrainWidth());
		stage.addActor(characterActor);

		JoystickActor.initialize();
		joystick = new JoystickActor();
		float joystickSize = Math.max(Gdx.graphics.getWidth(), Gdx.graphics.getWidth()) / 10;
		joystick.setBounds(0, 0, joystickSize, joystickSize);
		stage.addActor(joystick);

		Gdx.input.setInputProcessor(stage);
		Music music = (Music) App.getAsset(MUSIC);
		music.play();
	}

	public abstract Texture getBackground();

	public Map getMap() {
		return map;
	}

	@Override
	public void draw(float delta) {
		// joystick handling
		float joyX = joystick.getKnobPercentX();
		if(joyX > 0) {
			if(mapActor.move(MapActor.MOVE_RIGHT))
				characterActor.move(CharacterActor.MOVE_RIGHT);
		}
		else if (joyX < 0) {
			if(mapActor.move(MapActor.MOVE_LEFT))
				characterActor.move(CharacterActor.MOVE_LEFT);
		}
		else {
			characterActor.move(CharacterActor.STOP);
		}
		
		float joyY = joystick.getKnobPercentY();
		if(joyY > 0.8f) {
			mapActor.move(MapActor.MOVE_JUMP);
		}
		
		super.draw(delta);
	}
}
