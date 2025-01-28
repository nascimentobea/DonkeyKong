package objects;

import pt.iscte.poo.game.Collision;
import pt.iscte.poo.game.GameEngine;
import pt.iscte.poo.utils.Point2D;

public class Hammer extends GameInteractive implements Collision {

	public Hammer(Point2D position) {
		super(position);
	}

	@Override
	public String getName() {
		return "Hammer";
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