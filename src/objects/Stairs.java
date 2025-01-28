package objects;

import pt.iscte.poo.utils.Point2D;

public class Stairs extends GameInanimate {
	
	public Stairs(Point2D position) {
		super(position);
	}

	@Override
	public String getName() {
		return "Stairs";
	}

	@Override
	public boolean isClimbable() {
		return true;
	}
	
	@Override
	public boolean isStepable() {
		return true;
	}
}