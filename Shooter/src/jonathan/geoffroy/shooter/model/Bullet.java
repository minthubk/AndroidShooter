package jonathan.geoffroy.shooter.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;

public class Bullet implements Poolable {
	Vector2 position;
	
	public Bullet() {
		position = new Vector2();
	}

	public void initialize(float x, float y) {
		position.x = x;
		position.y = y;
	}
	
	@Override
	public void reset() {}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}
}
