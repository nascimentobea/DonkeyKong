package objects;

import pt.iscte.poo.game.Collision;
import pt.iscte.poo.game.GameEngine;
import pt.iscte.poo.utils.Point2D;

public class GoodMeat extends GameInteractive implements Collision {
	private int ticks;

	public GoodMeat(Point2D position) {
		super(position);
		ticks = 0;
	}

	@Override
	public String getName() {
		return "GoodMeat";
	}

	@Override
	public boolean uniqueInteraction() {
		return true;
	}

	@Override
	public void onCollision(JumpMan jumpMan) {
		jumpMan.restoreHealth();
		GameEngine.getInstance().sendMessage("Health restored, total Healty: " + jumpMan.getHealth());
	}

	public boolean shouldChangeToBadMeat() {
		ticks++;
		return ticks >= 10;
	}
}