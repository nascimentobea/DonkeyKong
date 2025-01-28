package objects;

import pt.iscte.poo.game.Collision;
import pt.iscte.poo.game.GameEngine;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Point2D;

public class Trap extends GameInteractive implements Collision {
	private TrapType type; // Estado da armadilha

	public Trap(Point2D position, TrapType initialType) {
		super(position);
		this.type = initialType;
	}

	@Override
	public String getName() {
		if (type == TrapType.HIDDEN) {
			return "Wall";
		} else {
			return "Trap";
		}
	}
 
	@Override
	public boolean uniqueInteraction() {
		return false;
	}

	@Override
	public void onCollision(JumpMan jumpMan) {
		if (type == TrapType.HIDDEN) { 
			ImageGUI.getInstance().removeImage(this);
			type = TrapType.ONDISPLAY;
			ImageGUI.getInstance().addImage(this);
		} else {
			jumpMan.lessHealth();
			GameEngine.getInstance().sendMessage("Health: " + jumpMan.getHealth());
		}
	}
}