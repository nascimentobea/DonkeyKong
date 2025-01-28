package objects;

import pt.iscte.poo.game.Collision;
import pt.iscte.poo.game.GameEngine;
import pt.iscte.poo.utils.Point2D;

public class BadMeat extends GameInteractive implements Collision {

	public BadMeat(Point2D position) {
		super(position);
	}

	@Override
	public String getName() {
		return "BadMeat";
	}

	@Override
	public boolean uniqueInteraction() {
		return true;
	}

	@Override
	public void onCollision(JumpMan jumpMan) {
		jumpMan.lessHealth();
		GameEngine.getInstance().sendMessage("Health loss! total Healty: " + jumpMan.getHealth());
	}

}
