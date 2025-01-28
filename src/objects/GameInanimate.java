package objects;

import pt.iscte.poo.utils.Point2D;

public abstract class GameInanimate extends GameObjects {

	public GameInanimate(Point2D position) {
		super(position);
	}

	@Override
	public boolean isClimbable() {
		return false;
	}

	@Override
	public int getLayer() {
		return 1;
	}
	
	@Override
	public boolean isStepable() {
		return false;
	}
}