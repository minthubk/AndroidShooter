package jonathan.geoffroy.shooter.view.actors;

import java.util.ArrayList;

import jonathan.geoffroy.shooter.Shooter;
import jonathan.geoffroy.shooter.model.Map;
import jonathan.geoffroy.shooter.model.characters.Coord2F;
import jonathan.geoffroy.shooter.view.screens.GameScreen;
import jonathan.geoffroy.shooter.view.utils.App;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class MapActor extends Actor {
	private static String BEGIN = Shooter.IMAGES + "MapActor/begin.png", END = Shooter.IMAGES + "MapActor/end.png",
			BULLET_BOX = Shooter.IMAGES + "MapActor/bulletBox.png", LIFE = Shooter.IMAGES + "MapActor/life.png",
			LEFT_GROUND = Shooter.IMAGES + "MapActor/leftGround.png", GROUND = Shooter.IMAGES + "MapActor/ground.png", RIGHT_GROUND = Shooter.IMAGES + "MapActor/rightGround.png";

	private Map map;
	private TextureRegion background;
	private float terrainWidth, terrainHeight;

	public MapActor(GameScreen gameScreen) {
		super();
		this.map = gameScreen.getMap();
		background = new TextureRegion(gameScreen.getBackground(), 800, 600);
		assert(map != null);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ArrayList<AssetDescriptor<Object>> getAssetDescriptors() {
		ArrayList<AssetDescriptor<Object>> result = new ArrayList<AssetDescriptor<Object>>();
		result.add(new AssetDescriptor(BEGIN, Texture.class));
		result.add(new AssetDescriptor(END, Texture.class));
		result.add(new AssetDescriptor(BULLET_BOX, Texture.class));
		result.add(new AssetDescriptor(LIFE, Texture.class));
		result.add(new AssetDescriptor(LEFT_GROUND, Texture.class));
		result.add(new AssetDescriptor(GROUND, Texture.class));
		result.add(new AssetDescriptor(RIGHT_GROUND, Texture.class));
		return result;
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		drawMap(batch, parentAlpha);
	}

	private void drawMap(SpriteBatch batch, float parentAlpha) {
		Coord2F coordPlayer = map.getPlayer().getPosition();

		int beginX = (int) (coordPlayer.x - getWidth() / 2);
		int endX = (int) (coordPlayer.x + getWidth() / 2) + 1;
		int beginY = (int) (coordPlayer.y - getHeight() / 2);
		int endY = (int) (coordPlayer.y + getHeight() / 2) + 1;
		int mapWidth = map.getWidth();
		int mapHeight = map.getHeight();

		Texture asset = null;
		float x, y;
		y = getY() + getHeight() - terrainHeight;

		for(int i = beginY; i < endY; i++) {
			if(i < 0 || i >= mapHeight) {
				continue;
			}

			x = getX() - terrainWidth;
			for(int j = beginX; j < endX; j++) {
				if(j < 0 || j >= mapWidth) {
					continue;
				}

				switch(map.getTerrain(j, i)) {
				case Map.NONE:
					asset = null;
					break;
				case Map.BEGIN:
					asset = (Texture) App.getAsset(BEGIN);
					break;
				case Map.END:
					asset = (Texture) App.getAsset(END);
					break;
				case Map.BULLET_BOX:
					asset = (Texture) App.getAsset(BULLET_BOX);
					break;
				case Map.LIFE:
					asset = (Texture) App.getAsset(LIFE);
					break;
				case Map.LEFT_GROUND:
					asset = (Texture) App.getAsset(LEFT_GROUND);
					break;
				case Map.GROUND:
					asset = (Texture) App.getAsset(GROUND);
					break;
				case Map.RIGHT_GROUND:
					asset = (Texture) App.getAsset(RIGHT_GROUND);
					break;
				}

				if(asset != null) {
					batch.draw(asset, x, y, terrainWidth, terrainHeight);
				}

				x += terrainWidth;
			}

			y -= terrainHeight;
		}
	}

	@Override
	public void setBounds(float x, float y, float width, float height) {
		super.setBounds(x, y, width, height);
		terrainWidth = width / Map.NB_TERRAINS_X;
		terrainHeight = height / Map.NB_TERRAINS_Y;
	}
}