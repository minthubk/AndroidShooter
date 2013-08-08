package jonathan.geoffroy.shooter.view.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;

import jonathan.geoffroy.shooter.Shooter;
import jonathan.geoffroy.shooter.model.Map;
import jonathan.geoffroy.shooter.model.characters.Character;
import jonathan.geoffroy.shooter.model.characters.Coord2F;
import jonathan.geoffroy.shooter.view.actors.CharacterActor;
import jonathan.geoffroy.shooter.view.actors.MapActor;
import jonathan.geoffroy.shooter.view.utils.App;
import jonathan.geoffroy.shooter.view.utils.StageScreen;

public abstract class GameScreen extends StageScreen {
	private static final String MUSIC = Shooter.SOUNDS + "Level/music.mp3";
	protected static final String BACKGROUNDS = Shooter.IMAGES+ "Game/backgrounds/";
	
	protected MapActor mapActor;
	protected CharacterActor characterActor;
	protected Map map;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ArrayList<AssetDescriptor<Object>> getAssetDescriptors() {
		ArrayList<AssetDescriptor<Object>> result = new ArrayList<AssetDescriptor<Object>>();
		result.add(new AssetDescriptor(MUSIC, Music.class));
		result.addAll(MapActor.getAssetDescriptors());
		result.addAll(CharacterActor.getAssetDescriptors());
		return result;
	}

	@Override
	protected void onEndLoaded() {
		mapActor = new MapActor(this);
		mapActor.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		stage.addActor(mapActor);

		Character player = map.getPlayer();
		Coord2F playerPos = player.getPosition();
		characterActor = new CharacterActor();
		characterActor.setBounds(
				Gdx.graphics.getWidth() / 2 - mapActor.getTerrainWidth() / 2,
				Gdx.graphics.getHeight() / 2 - mapActor.getTerrainHeight() / 2,
				mapActor.getTerrainWidth(), mapActor.getTerrainWidth());
		stage.addActor(characterActor);
		
		Music music = (Music) App.getAsset(MUSIC);
		music.play();
	}
	
	public abstract Texture getBackground();

	public Map getMap() {
		return map;
	}
}
