
package objects;

import pt.iscte.poo.utils.Point2D;

public abstract class GameInteractive extends GameObjects {

	public GameInteractive(Point2D position) {
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