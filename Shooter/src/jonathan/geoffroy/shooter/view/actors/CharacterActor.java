package jonathan.geoffroy.shooter.view.actors;

import java.util.ArrayList;

import jonathan.geoffroy.shooter.Shooter;
import jonathan.geoffroy.shooter.view.utils.App;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class CharacterActor extends Actor {
	private static final String IMAGES = Shooter.IMAGES + "characters/player/";
	private static final int LEFT = 0, RIGHT = 1, MOVE_LEFT_1 = 2, MOVE_LEFT_2 = 3, MOVE_RIGHT_1 = 4, MOVE_RIGHT_2 = 5, LEFT_SHOOT = 6, RIGHT_SHOOT = 7;
	public static final int MOVE_LEFT = 0, MOVE_RIGHT = 1, SHOOT = 2;
	private int state;
	private Texture texture;

	public CharacterActor() {
		move(MOVE_LEFT);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ArrayList<AssetDescriptor<Object>> getAssetDescriptors() {
		ArrayList<AssetDescriptor<Object>> result = new ArrayList<AssetDescriptor<Object>>();
		result.add(new AssetDescriptor(IMAGES + "left.png", Texture.class));
		result.add(new AssetDescriptor(IMAGES + "right.png", Texture.class));
		return result;
	}

	/**
	 * Draw the Character correctly using his last movement
	 * @param typeMove the Character's last movement {MOVE_LEFT, MOVE_RIGHT, SHOOT}
	 */
	public void move(int typeMove) {
		String asset = null;
		switch(typeMove) {
		case MOVE_LEFT:
			state = LEFT;
			asset = "left";
			break;
		case MOVE_RIGHT:
			state = RIGHT;
			asset = "right";
			break;
		default:
			System.out.println("ERROR: unrecognized movement");
		}
		texture = (Texture) App.getAsset(IMAGES + asset + ".png");
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		batch.draw(texture, getX(), getY(), getWidth(), getHeight());
	}


}
