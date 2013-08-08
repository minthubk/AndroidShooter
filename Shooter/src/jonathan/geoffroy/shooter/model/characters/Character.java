package jonathan.geoffroy.shooter.model.characters;


public class Character {
	public final static int X = 12, Y = 16;
	
	private Coord2F position;

	public Character(int terrainX, int terrainY) {
		position = new Coord2F();
	}
	
	public Coord2F getPosition() {
		return position;
	}

	public void setPosition(Coord2F position) {
		this.position = position;
	}
	
}
