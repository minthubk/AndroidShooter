package jonathan.geoffroy.shooter.model;

import java.util.ArrayList;

import jonathan.geoffroy.shooter.Shooter;
import jonathan.geoffroy.shooter.model.characters.Character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.utils.Pool;

public class Map {
	public final static int NONE = 0, LEFT_GROUND = 1, GROUND = 2, RIGHT_GROUND = 3, LIFE = 4, BULLET_BOX = 5, BEGIN = 6, END = 7;
	private final static int NONE_COLOR = -1, GROUND_COLOR = 255, LIFE_COLOR = -16776961, BULLET_BOX_COLOR = 65535, BEGIN_COLOR = 16711935, END_COLOR = -65281;
	public final static int NB_TERRAINS_X = 16, NB_TERRAINS_Y = 12;

	private int[][] terrains;
	private Character player;
	private ArrayList<Bullet> bullets;
	private Pool<Bullet> bulletPool;

	public static Map load(int mapNumber) throws Exception {
		Map loadedMap = new Map();
		loadedMap.setBullets(new ArrayList<Bullet>());
		loadedMap.bulletPool = new Pool<Bullet>() {
			@Override
			protected Bullet newObject() {
				return new Bullet();
			}
		};
		
		FileHandle file = Gdx.files.internal(Shooter.MAPS + mapNumber + ".png");
		Pixmap img = new Pixmap(file);
		int width = img.getWidth();
		int height = img.getHeight();
		loadedMap.terrains = new int[height][width];
		int pixel;

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				pixel = img.getPixel(j, i);
				switch(pixel) {
				case NONE_COLOR:
					loadedMap.terrains[i][j] = NONE;
					break;
				case GROUND_COLOR:
					if(j == 0 || img.getPixel(j - 1, i) != GROUND_COLOR) {
						loadedMap.terrains[i][j] = LEFT_GROUND;
					}
					else if(j == height - 1 || img.getPixel(j + 1, i) != GROUND_COLOR) {
						loadedMap.terrains[i][j] = RIGHT_GROUND;
					}
					else {
						loadedMap.terrains[i][j] = GROUND;
					}
					break;
				case LIFE_COLOR:
					loadedMap.terrains[i][j] = LIFE;
					break;
				case BULLET_BOX_COLOR:
					loadedMap.terrains[i][j] = BULLET_BOX;
					break;
				case BEGIN_COLOR:
					loadedMap.terrains[i][j] = BEGIN;
					loadedMap.setPlayer(new Character(j, i));
					break;
				case END_COLOR:
					loadedMap.terrains[i][j] = END;
					break;
				default:
					throw new Exception("this terrain doesn't exist (x: " + j + ", y: + " + i + ")");
				}
			}
		}

		return loadedMap;
	}

	public int[][] getTerrains() {
		return terrains;
	}

	public void setTerrains(int[][] terrains) {
		this.terrains = terrains;
	}

	public Character getPlayer() {
		return player;
	}

	public void setPlayer(Character player) {
		this.player = player;
	}

	public int getWidth() {
		return terrains[0].length;
	}

	public int getHeight() {
		return terrains.length;
	}

	public int getTerrain(int x, int y) {
		if(y >= 0 && y < terrains.length && x >= 0 && x < terrains[y].length)
			return terrains[y][x];
		return NONE;
	}

	public ArrayList<Bullet> getBullets() {
		return bullets;
	}

	public void setBullets(ArrayList<Bullet> bullets) {
		this.bullets = bullets;
	}
	
	public Bullet obtainBullet() {
		Bullet bullet = bulletPool.obtain();
		bullets.add(bullet);
		return bullet;
	}
	
	public void freeBullet(int index) {
		Bullet bullet = bullets.remove(index);
		bulletPool.free(bullet);
	}
	
	public void freeBullet(Bullet bullet) {
		bulletPool.free(bullet);
		bullets.remove(bullet);
	}
}
