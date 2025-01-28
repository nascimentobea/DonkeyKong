package objects;

import pt.iscte.poo.game.Collision;
import pt.iscte.poo.game.GameEngine;
import pt.iscte.poo.utils.Point2D;

public class DoorClosed extends GameInanimate implements Collision {

	public DoorClosed(Point2D position) {
		super(position);
	}

	@Override
	public String getName() {
		return "DoorClosed";
	}

	@Override
	public boolean isStepable() {
		return true;
	}

	@Override
	public boolean uniqueInteraction() {
		return true;
	}

	@Override
	public void onCollision(JumpMan jumpMan) {
		GameEngine gameEngine = GameEngine.getInstance();
		gameEngine.getCurrentRoom().setIsComplete(true);
	}

}