package objects;

import java.util.ArrayList;
import java.util.List;

import pt.iscte.poo.game.Collision;
import pt.iscte.poo.game.GameEngine;
import pt.iscte.poo.game.Room;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Bomb extends GameInteractive implements Collision {
	private int ticks;
	private boolean leftBomb;
	public static List<Bomb> bombs = new ArrayList<>();

	public Bomb(Point2D position) {
		super(position);
		this.ticks = ImageGUI.getInstance().getTicks();
		leftBomb = false;
	}

	public boolean getLeftBomb() {
		return this.leftBomb;
	}

	public void setLeftBomb(boolean a) {
		this.leftBomb = a;
	}

	@Override
	public boolean uniqueInteraction() {
		if (getLeftBomb() == false) // se não deixou bomba entao é porque está a apanhar
			return true;
		return false;
	}

	@Override
	public String getName() {
		return "Bomb";
	}

	@Override
	public void onCollision(JumpMan jumpMan) {
		if (getLeftBomb() == false) {
			jumpMan.setHasBomb(1);
			GameEngine.getInstance().sendMessage("JumpMan can explode!");
		}
	}

	public static void addBomb(Bomb bomb) {
		bombs.add(bomb);
	}

	public void removeBomb(Bomb bomb) {
		bombs.remove(bomb);
	}

	public boolean shouldExplode() {
		return hasCollision() || ImageGUI.getInstance().getTicks() >= this.ticks + 5;
	}

	private boolean hasCollision() {
		for (GameObjects obj : MovingObjects.listMovingObjects())
			if (!(obj instanceof Banana) && !obj.equals(this) && obj.getPosition().equals(this.getPosition()))
				return true; // colisão detectada
		return false;
	}

	public List<GameObjects> toExplode() {
		List<GameObjects> toExplode = new ArrayList<>();
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				Point2D targetPosition = position.plus(new Vector2D(i, j));
				for (GameObjects obj : Room.getImages())
					if (obj.getPosition().equals(targetPosition))
						if (obj instanceof Collision) {
							toExplode.add(obj);
						}
				if (JumpMan.getInstance(null).getPosition().equals(targetPosition))
					JumpMan.getInstance(null).lifeLost();
			}
		}
		toExplode.add(this);
		removeBomb(this);
		return toExplode;
	}

}