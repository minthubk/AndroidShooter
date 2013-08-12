package jonathan.geoffroy.shooter.view.actors;

import java.util.ArrayList;

import org.omg.CORBA.TIMEOUT;

import jonathan.geoffroy.shooter.Shooter;
import jonathan.geoffroy.shooter.model.Map;
import jonathan.geoffroy.shooter.model.characters.Coord2F;
import jonathan.geoffroy.shooter.model.characters.Character;
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
	public static final int MOVE_LEFT = 0, MOVE_RIGHT = 1, MOVE_JUMP = 2;
	private static final float TIME_TO_JUMP = 0.4f;

	private Map map;
	private TextureRegion background;
	private float terrainWidth, terrainHeight;
	private int beginTerrainX, beginTerrainY;
	private int endTerrainX, endTerrainY;
	private float beginX, beginY;
	private float gravityWeight;
	private float moveSpeed;
	private boolean jumping;
	private float timerJump;

	public MapActor(GameScreen gameScreen) {
		super();
		this.map = gameScreen.getMap();
		background = new TextureRegion(gameScreen.getBackground(), 800, 600);
		assert(map != null);
		computeCoords();
	}

	private void computeCoords() {
		Character player = map.getPlayer();
		Coord2F playerPos = player.getPosition();

		beginX = playerPos.x % terrainWidth;
		beginY = playerPos.y % terrainHeight;

		beginTerrainX = (int) ((playerPos.x / terrainWidth) - Map.NB_TERRAINS_X / 2);
		beginTerrainY = (int) (((playerPos.y - getHeight()) / terrainHeight) - Map.NB_TERRAINS_Y / 2);

		endTerrainX = (int) ((playerPos.x / terrainWidth) + Map.NB_TERRAINS_X / 2);
		if(beginX != 0) 
			endTerrainX ++;

		endTerrainY = (int) (((playerPos.y - Gdx.graphics.getHeight()) / terrainHeight) + Map.NB_TERRAINS_Y / 2);
		if(beginY != 0)
			endTerrainY ++;

		beginX =  - (playerPos.x % terrainWidth) ;
		beginY = Gdx.graphics.getHeight() +  playerPos.y % terrainHeight;
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		if(jumping) {
			jump(delta);
		}
		else {
			gravity();
		}
	}

	private void jump(float delta) {
		assert(jumping);
		Coord2F playerPos = map.getPlayer().getPosition();
		if(timerJump < TIME_TO_JUMP) {
			playerPos.y -= gravityWeight;
			timerJump += delta;
			computeCoords();
		}
		else {
			jumping = false;
			timerJump = 0.f;
		}
	}

	private void gravity() {
		Coord2F playerPos = map.getPlayer().getPosition();
		int terrainY = (int) ((playerPos.y - getHeight()) / terrainHeight) + 1;
		int terrainX = (int) (playerPos.x / terrainWidth);

		if(terrainY < 0) {
			playerPos.y += gravityWeight;
		}
		else {
			int terrainType = map.getTerrain(terrainX, terrainY);
			if(terrainType == Map.NONE || terrainType == Map.BEGIN) {
				playerPos.y += gravityWeight;
			}
		}

		computeCoords();
	}

	/**
	 * Move the player on the map
	 * @param moveType {MOVE_LEFT, MOVE_RIGHT}
	 * @return true if the player can move
	 */
	public boolean move(int moveType) {
		boolean haveMoved = false;
		Coord2F playerPos = map.getPlayer().getPosition();
		int terrainX, terrainY;
		int terrainType;
		switch(moveType) {
		case MOVE_LEFT:
			terrainX = (int) ( (playerPos.x - moveSpeed) / terrainWidth);
			terrainY = (int) ((playerPos.y - getHeight()) / terrainHeight);
			if(playerPos.x >= 0 && terrainX >= 0) {
				terrainType = map.getTerrain(terrainX, terrainY);
				if(terrainType == Map.NONE || terrainType == Map.BEGIN || terrainType == Map.BEGIN) {
					playerPos.x -= moveSpeed;
					computeCoords();
					haveMoved = true;
				}
			}
			break;

		case MOVE_RIGHT:
			terrainX = (int) ( (playerPos.x + terrainWidth / 2 + moveSpeed) / terrainWidth);
			if(terrainX < map.getWidth()) {
				terrainY = (int) ((playerPos.y - getHeight()) / terrainHeight);
				terrainType = map.getTerrain(terrainX, terrainY);
				if(terrainType == Map.NONE || terrainType == Map.BEGIN || terrainType == Map.BEGIN) {
					playerPos.x += moveSpeed;
					computeCoords();
					haveMoved = true;
				}
			}
			break;
		case MOVE_JUMP:
			terrainX = (int) ( (playerPos.x + terrainWidth / 2 + moveSpeed) / terrainWidth);
			terrainY = (int) ((playerPos.y - getHeight()) / terrainHeight) + 1;
			if(map.getTerrain(terrainX, terrainY) != Map.NONE) {
				jumping = true;
			}
			break;
		}

		return haveMoved;
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
		Texture asset = null;
		int mapHeight = map.getHeight();
		int mapWidth = map.getWidth();
		float x;
		float y = beginY;

		for(int i = beginTerrainY; i < endTerrainY; i++) {
			if(i >= 0 && i < mapHeight) {
				x = beginX;
				for(int j = beginTerrainX; j < endTerrainX; j++) {
					if(j >= 0 && j < mapWidth) {
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
					}
					x += terrainWidth;
				}
			}
			y -= terrainHeight;
		}
	}

	@Override
	public void setBounds(float x, float y, float width, float height) {
		super.setBounds(x, y, width, height);
		terrainWidth = width / Map.NB_TERRAINS_X;
		terrainHeight = height / Map.NB_TERRAINS_Y;
		computeCoords();
		beginX = getX();
		beginY = getY() + getHeight();
		gravityWeight = terrainHeight / 5;
		moveSpeed = terrainWidth / 3;
	}

	public float getTerrainWidth() {
		return terrainWidth;
	}

	public float getTerrainHeight() {
		return terrainHeight;
	}
}