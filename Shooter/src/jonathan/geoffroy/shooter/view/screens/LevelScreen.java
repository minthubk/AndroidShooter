package jonathan.geoffroy.shooter.view.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;

import jonathan.geoffroy.shooter.model.Map;
import jonathan.geoffroy.shooter.view.utils.App;

public class LevelScreen extends GameScreen {
	private int level;

	public LevelScreen() {
		super();

		level = 1;
		loadMap();
	}

	/**
	 * Load the map, using level number
	 * Kill the game if the map can't be load
	 */
	private void loadMap() {
		try {
			map = Map.load(level);
		} catch (Exception e) {
			e.printStackTrace();
			Gdx.app.exit();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ArrayList<AssetDescriptor<Object>> getAssetDescriptors() {
		ArrayList<AssetDescriptor<Object>> result = super.getAssetDescriptors();
		result.add(new AssetDescriptor(BACKGROUNDS + level + ".jpg", Texture.class));
		return result;
	}

	@Override
	public Texture getBackground() {
		return (Texture) App.getAsset(BACKGROUNDS + level + ".jpg");
	}
}
