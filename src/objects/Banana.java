package objects;

import pt.iscte.poo.game.Collision;
import pt.iscte.poo.game.GameEngine;
import pt.iscte.poo.game.Room;
import pt.iscte.poo.game.Updatable;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Banana extends MovingObjects implements Collision, Updatable {

	public Banana(Point2D position) {
		super(position);
	}

	@Override
	public void move(Direction d) {
		Point2D newPosition = position.plus(Direction.DOWN.asVector());
		super.move(Direction.DOWN);
		if (!ImageGUI.getInstance().isWithinBounds(newPosition))
			GameEngine.getInstance().getCurrentRoom().removeObject(this);
		position = newPosition;
	}

	@Override
	public boolean requiresCollisionCheck() {
		return false;
	}

	@Override
	public String getName() {
		return "Banana";
	}

	@Override
	public boolean isStepable() {
		return false;
	}

	@Override
	public int getLayer() {
		return 3;
	}

	@Override
	public void onCollision(JumpMan jumpMan) {
		jumpMan.lessHealth();
		GameEngine.getInstance().sendMessage("Health: " + jumpMan.getHealth());
	}

	@Override
	public boolean uniqueInteraction() {
		return true;
	}

	@Override
	public void action(double dificulty, Room room) {
		move(Direction.random());
	}
}