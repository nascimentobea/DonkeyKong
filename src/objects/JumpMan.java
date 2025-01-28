package objects;

import pt.iscte.poo.game.GameEngine;
import pt.iscte.poo.game.Room;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class JumpMan extends MovingObjects {
	private static JumpMan INSTANCE;
	private boolean putBomb;
	private Point2D initialPosition;
	private int attackCapacity;
	private static final int TOTALHEALTH = 100;
	private int health;
	private static final int TOTALLIFES = 3;
	private int lifes;
	private int hasBomb;

	private JumpMan(Point2D initialPosition) {
		super(initialPosition);
		this.initialPosition = initialPosition;
		this.attackCapacity = 0;
		this.health = TOTALHEALTH;
		this.lifes = TOTALLIFES;
		this.putBomb = false;
		this.hasBomb = 0;
	}

	public static JumpMan getInstance(Point2D position) {
		if (INSTANCE == null) {
			INSTANCE = new JumpMan(position);
		}
		return INSTANCE;
	}

	public boolean getPutBomb() {
		return this.putBomb;
	}

	private void setPutBomb(boolean bomb) {
		this.putBomb = bomb;
	}

	public void setPosition(Point2D pos) {
		this.position = pos;
	}

	public int getHealth() {
		return this.health;
	}

	public int getAttackCapacity() {
		return this.attackCapacity;
	}

	public int getLifes() {
		return this.lifes;
	}

	public int getHasBomb() {
		return hasBomb;
	}

	public void setHasBomb(int bomb) {
		hasBomb = hasBomb + bomb;
	}

	@Override
	public String getName() {
		return "JumpMan";
	}

	@Override
	public void move(Direction d) {
		Point2D newPosition = position.plus(d.asVector());

		if (!ImageGUI.getInstance().isWithinBounds(newPosition))
			return;

		for (GameObjects obj : Room.getImages()) {
			if (obj.getPosition().equals(newPosition) && !obj.isCrossable())
				return;
		}

		position = newPosition;

	}

	public void gravity() {
		Point2D below = position.plus(new Vector2D(0, 1));

		for (GameObjects obj : Room.getImages())
			if (obj.getPosition().equals(below))
				if (((GameObjects) obj).isStepable())
					return;

		move(Direction.DOWN);

	}

	public void restoreHealth() {
		this.health = TOTALHEALTH;
	}

	public void lessHealth() {
		if (this.health > 0) {
			this.health -= 20;
			ImageGUI.getInstance().setStatusMessage("Health: " + getHealth());
		} else {
			lifeLost();
		}
	}

	public void setAttackCapacity(boolean capacity, boolean showMessage) {
		if (capacity) {
			this.attackCapacity += 20;
		} else
			this.attackCapacity -= 20;
		if (showMessage)
			GameEngine.getInstance().sendMessage("Attack Capacity: " + getAttackCapacity());
	}

	public void resetJumpMan() {
		this.lifes = TOTALLIFES;
		this.attackCapacity = 0;
		this.health = TOTALHEALTH;
		this.hasBomb = 0;
	}

	public void lifeLost() {
		this.lifes -= 1;
		GameEngine.getInstance().sendMessage("Remaining lifes: " + this.lifes);
		if (this.lifes <= 0) {
			GameEngine.getInstance().sendMessage("Game Over!");
			resetJumpMan();
			GameEngine.getInstance().restartGame();
		}
		this.setPosition(initialPosition);
		this.health = TOTALHEALTH;
	}

	public void releaseBomb() {
		if (hasBomb > 0) {
			hasBomb--;
			Bomb bomb = new Bomb(position);
			setPutBomb(true);
			GameEngine.getInstance().getCurrentRoom().addObject(bomb);
			Bomb.addBomb(bomb);
			bomb.setLeftBomb(true);
		}

	}

}