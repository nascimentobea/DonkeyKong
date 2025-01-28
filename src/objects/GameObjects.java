package objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public abstract class GameObjects implements ImageTile {
	protected Point2D position;

	public GameObjects(Point2D position) {
		this.position = position;
	}

	public abstract String getName();

	public abstract int getLayer();

	public Point2D getPosition() {
		return position;
	}

	public abstract boolean isClimbable();

	protected abstract boolean isStepable();

	public boolean isCrossable() {
		return true;
	}
}