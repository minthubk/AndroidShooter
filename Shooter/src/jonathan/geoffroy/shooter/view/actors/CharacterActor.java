package jonathan.geoffroy.shooter.view.actors;

import java.util.ArrayList;

import jonathan.geoffroy.shooter.Shooter;
import jonathan.geoffroy.shooter.view.utils.App;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class CharacterActor extends Actor {
	private static final float TIME_TO_CHANGE_SPRITE = 0.15f;
	private static final String IMAGES = Shooter.IMAGES + "characters/player/";
	private static final int LEFT = 0, RIGHT = 1, LEFT_1 = 2, LEFT_2 = 3, RIGHT_1 = 4, RIGHT_2 = 5, LEFT_SHOOT = 6, RIGHT_SHOOT = 7;
	public static final int MOVE_LEFT = 0, MOVE_RIGHT = 1, SHOOT = 2, STOP = 3;
	private int state;
	private ArrayList<Texture> textures;
	private Texture stateTexture;
	float timer;

	public CharacterActor() {
		textures = new ArrayList<Texture>();
		textures.add((Texture)App.getAsset(IMAGES + "left.png"));
		textures.add((Texture)App.getAsset(IMAGES + "right.png"));
		textures.add((Texture)App.getAsset(IMAGES + "left1.png"));
		textures.add((Texture)App.getAsset(IMAGES + "left2.png"));
		textures.add((Texture)App.getAsset(IMAGES + "right1.png"));
		textures.add((Texture)App.getAsset(IMAGES + "right2.png"));
		//		textures.add((Texture)App.getAsset("player/leftShoot.png"));
		//		textures.add((Texture)App.getAsset("player/rightShoot.png"));
		move(STOP);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ArrayList<AssetDescriptor<Object>> getAssetDescriptors() {
		ArrayList<AssetDescriptor<Object>> result = new ArrayList<AssetDescriptor<Object>>();
		result.add(new AssetDescriptor(IMAGES + "left.png", Texture.class));
		result.add(new AssetDescriptor(IMAGES + "right.png", Texture.class));
		result.add(new AssetDescriptor(IMAGES + "left1.png", Texture.class));
		result.add(new AssetDescriptor(IMAGES + "left2.png", Texture.class));
		result.add(new AssetDescriptor(IMAGES + "right1.png", Texture.class));
		result.add(new AssetDescriptor(IMAGES + "right2.png", Texture.class));
		return result;
	}

	/**
	 * Draw the Character correctly using his last movement
	 * @param typeMove the Character's last movement {MOVE_LEFT, MOVE_RIGHT, SHOOT}
	 */
	public void move(int typeMove) {
		switch(typeMove) {
		case MOVE_LEFT:
			switch(state) {
			case LEFT_1:
				if(timer > TIME_TO_CHANGE_SPRITE) {
					state = LEFT_2;
					timer = 0;
				}
				break;
			case LEFT_2:
				if(timer > TIME_TO_CHANGE_SPRITE) {
					state = LEFT_1;
					timer = 0;
				}
				break;
			default:
				state = LEFT_1;
				break;
			}
			break;
		case MOVE_RIGHT:
			switch(state) {
			case RIGHT_1:
				if(timer > TIME_TO_CHANGE_SPRITE) {
					state = RIGHT_2;
					timer = 0;
				}
			case RIGHT_2:
				if(timer > TIME_TO_CHANGE_SPRITE) {
					state = RIGHT_1;
					timer = 0;
				}
				break;
			default:
				state = RIGHT_1;
			}
			break;
		case STOP:
			switch(state) {
			case LEFT:
			case LEFT_1:
			case LEFT_2:
			case LEFT_SHOOT:
				state = LEFT;
				break;
			default:
				state = RIGHT;

			}
			break;
		default:
			System.out.println("ERROR: unrecognized movement: " + typeMove);
		}
		stateTexture = textures.get(state);
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		batch.draw(stateTexture, getX(), getY(), getWidth(), getHeight());
		if (state == LEFT || state == RIGHT) {
			timer = 0;
		}
		else {
			timer += Gdx.graphics.getDeltaTime();
		}
	}
}
