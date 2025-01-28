package pt.iscte.poo.game;

import objects.JumpMan;

public interface Collision {
	void onCollision(JumpMan jumpMan);
	
	boolean uniqueInteraction();
}