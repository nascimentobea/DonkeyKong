package objects;

import pt.iscte.poo.game.Collision;
import pt.iscte.poo.game.GameEngine;
import pt.iscte.poo.game.Room;
import pt.iscte.poo.game.Updatable;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Bat extends MovingObjects implements Collision, Updatable {
	private Direction direction;

	public Bat(Point2D position) {
		super(position);
		this.direction = Direction.RIGHT;
	}

	@Override
	public String getName() {
		return "Bat";
	}

	@Override
	public void move(Direction d) {
		Point2D newPosition = position.plus(this.direction.asVector());
		Point2D below = position.plus(new Vector2D(0, 1));
		GameObjects object = GameEngine.getInstance().getCurrentRoom().getObject(below);
		
		if (object instanceof Stairs || object instanceof Bomb) {
			super.move(Direction.DOWN);
		} else if (!ImageGUI.getInstance().isWithinBounds(newPosition) || !Room.getObject(newPosition).isCrossable()){
			this.direction = changeDirection(this.direction);
		} else {
			super.move(this.direction);
		}
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