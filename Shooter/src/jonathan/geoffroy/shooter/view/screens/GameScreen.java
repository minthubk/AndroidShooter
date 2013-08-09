package jonathan.geoffroy.shooter.view.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

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
		joystick.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if(joystick.getKnobPercentX() >= 0) {
					characterActor.move(CharacterActor.MOVE_RIGHT);
				}
				else {
					characterActor.move(CharacterActor.MOVE_LEFT);
				}
			}
		});
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
		mapActor.gravity();
		super.draw(delta);
	}
}