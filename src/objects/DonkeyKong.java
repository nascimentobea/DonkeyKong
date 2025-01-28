package objects;

import pt.iscte.poo.game.Collision;
import pt.iscte.poo.game.GameEngine;
import pt.iscte.poo.game.Room;
import pt.iscte.poo.game.Updatable;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.*;

public class DonkeyKong extends MovingObjects implements Collision, Updatable {
	private Direction direction;
	private boolean wasKilled;// para quando fizer restart do nivel apagar, ou nao, o DonkeyKong

	public DonkeyKong(Point2D position) {
		super(position);
		this.direction = Direction.RIGHT;
		this.wasKilled = false;
	}

	public void setWasKilled(boolean killed) {
		wasKilled = killed;
	}

	@Override
	public String getName() {
		return "DonkeyKong";
	}

	@Override
	public void move(Direction d) {
		Point2D newPosition = position.plus(this.direction.asVector());
		for (GameObjects obj : Room.getImages()) {
			if ((obj.getPosition().equals(newPosition) && !obj.isCrossable())
					|| !ImageGUI.getInstance().isWithinBounds(newPosition)) {
				this.direction = changeDirection(this.direction);
			}
		}
		if (!ImageGUI.getInstance().isWithinBounds(newPosition)) {
			this.direction = changeDirection(this.direction);
		} else {
			super.move(this.direction);
		}
	}

	@Override
	public void onCollision(JumpMan jumpMan) {
		if (jumpMan.getAttackCapacity() >= 20) {
			setWasKilled(true);
			GameEngine.getInstance().sendMessage("DonkeyKong killed!!");
			jumpMan.setAttackCapacity(false, false);
		} else {
			jumpMan.lifeLost();
		}
	}

	@Override
	public boolean uniqueInteraction() {
		return wasKilled;
	}

	@Override
	public void action(double dificulty, Room room) {
		move(Direction.random());
		attackWithBananas(dificulty, room);
	}

	private void attackWithBananas(double dificulty, Room room) {
		if (Math.random() < dificulty)
			room.addObject(new Banana(position));
	}
}