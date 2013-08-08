package jonathan.geoffroy.shooter.model.characters;

import jonathan.geoffroy.shooter.model.Map;

import com.badlogic.gdx.Gdx;


public class Character {
	private Coord2F position;

	public Character(int terrainX, int terrainY) {
		float terrainWidth = Gdx.graphics.getWidth() / Map.NB_TERRAINS_X;
		float terrainHeight = Gdx.graphics.getHeight() / Map.NB_TERRAINS_Y;
		position = new Coord2F(
				(terrainX) * terrainWidth,
				(terrainY) * terrainHeight + Gdx.graphics.getHeight()
				);
	}
	
	public Coord2F getPosition() {
		return position;
	}

	public void setPosition(Coord2F position) {
		this.position = position;
	}
	
}
