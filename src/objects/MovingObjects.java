package objects;

import java.util.ArrayList;
import java.util.List;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public abstract class MovingObjects extends GameObjects {

	public MovingObjects(Point2D position) {
		super(position);
	}

	@Override
	public boolean isClimbable() {
		return false;
	}

	@Override
	public int getLayer() {
		return 2;
	}

	@Override
	public boolean isStepable() {
		return false;
	}

	public void move(Direction direction) {
		Point2D newPosition = position.plus(direction.asVector());

		if (requiresCollisionCheck()) {
			for (GameObjects obj : Room.objects) {
				if (obj.getPosition().equals(newPosition) && !obj.isCrossable())
					return;
			}
		}

		position = newPosition;
	}

	protected Direction changeDirection(Direction direction) {
		if (direction == Direction.RIGHT) {
			return Direction.LEFT;
		} else {
			return Direction.RIGHT;
		}
	}

	public static List<GameObjects> listMovingObjects() {
		List<GameObjects> list = new ArrayList<>();
		for (GameObjects obj : Room.objects)
			if (obj instanceof MovingObjects)
				list.add(obj);
		return list;
	}

	protected boolean requiresCollisionCheck() {
		return true;
	}
}