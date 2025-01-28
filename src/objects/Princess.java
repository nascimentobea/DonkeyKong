package objects;

import pt.iscte.poo.game.Collision;
import pt.iscte.poo.game.GameEngine;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Point2D;

public class Princess extends GameInteractive implements Collision {
	private boolean showedTheScore;

	public Princess(Point2D position) {
		super(position);
		showedTheScore = false;
	}

	private boolean getShowedTheScore() {
		return showedTheScore;
	}

	private void setShowedTheScore(boolean showedTheScore) {
		this.showedTheScore = showedTheScore;
	}

	@Override
	public String getName() {
		return "Princess";
	}

	@Override
	public boolean uniqueInteraction() {
		return false;
	}

	@Override
	public void onCollision(JumpMan jumpMan) {
		if (!getShowedTheScore()) {
			ImageGUI.getInstance().showMessage("You won!", "You rescued the Princess! Congratulations!");
			GameEngine.getInstance().gameFinished();
			setShowedTheScore(true);
		}
	}
}