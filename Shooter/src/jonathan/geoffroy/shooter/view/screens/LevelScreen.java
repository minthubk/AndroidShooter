package jonathan.geoffroy.shooter.view.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;

import jonathan.geoffroy.shooter.model.Map;
import jonathan.geoffroy.shooter.view.actors.MapActor;
import jonathan.geoffroy.shooter.view.utils.StageScreen;

public class LevelScreen extends StageScreen {
	private int level;
	private Map map;
	private MapActor mapActor;
	
	public LevelScreen() {
		super();
		
		level = 1;
		try {
			map = Map.load(level);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public ArrayList<AssetDescriptor<Object>> getAssetDescriptors() {
		ArrayList<AssetDescriptor<Object>> result = new ArrayList<AssetDescriptor<Object>>();
		result.addAll(MapActor.getAssetDescriptors());
		return result;
	}

	@Override
	protected void onEndLoaded() {
		mapActor = new MapActor(map);
		mapActor.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		stage.addActor(mapActor);
	}
}
