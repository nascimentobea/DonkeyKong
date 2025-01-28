package objects;

import pt.iscte.poo.utils.Point2D;

public class Wall extends GameInanimate {

	public Wall(Point2D initialPosition) {
		super(initialPosition);
	}

	@Override
	public String getName() {
		return "Wall";
	}

	@Override
	public int getLayer() {
		return 3;
	}

	@Override
	public boolean isStepable() {
		return true;
	}

	@Override
	public boolean isCrossable() {
		return false;
	}
}