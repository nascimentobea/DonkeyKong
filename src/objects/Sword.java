package objects;

import pt.iscte.poo.game.Collision;
import pt.iscte.poo.game.GameEngine;
import pt.iscte.poo.utils.Point2D;

public class Sword extends GameInteractive implements Collision {
	public Sword(Point2D position) {
		super(position);
	}

	@Override
	public String getName() {
		return "Sword";
	}

	@Override
	public boolean uniqueInteraction() {
		return true;
	}

	@Override
	public void onCollision(JumpMan jumpMan) {
		jumpMan.setAttackCapacity(true, true);
		GameEngine.getInstance().sendMessage("Attack Capacity: " + jumpMan.getAttackCapacity());
	}
}