package pt.iscte.poo.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import objects.*;
import pt.iscte.poo.utils.*;
import pt.iscte.poo.gui.*;

public class Room {
	protected Point2D heroStartingPosition = new Point2D(6, 8);
	public static List<GameObjects> objects;
	private boolean isComplete = false;
	private int roomNumber;

	public Room() {
		this.roomNumber = 0;
		objects = new ArrayList<>();
	}

	public int getNumber() {
		return roomNumber;
	}

	public JumpMan getJumpMan() { // Facilitar a escrita do código
		return JumpMan.getInstance(heroStartingPosition);
	}

	public boolean getIsComplete() {
		return isComplete;
	}

	public void setIsComplete(boolean isComplete) {
		this.isComplete = isComplete;
	}

	public static List<GameObjects> getImages() {
		return objects;
	}
	public void addObject(GameObjects obj) {
		objects.add(obj);
		ImageGUI.getInstance().addImage(obj);
	}

	public void removeObject(GameObjects obj) {
		objects.remove(obj);
		ImageGUI.getInstance().removeImage(obj);
	}
	public void removeAll() {
		ImageGUI.getInstance().removeImages(objects);
		objects.clear();
	}

	public static GameObjects getObject(Point2D position) {
		GameObjects object = null;
		for (GameObjects c : objects) {
			if (c.getPosition().equals(position))
				object = c;
		}
		return object;
	}

	public void loadRoom(File f) {
		getJumpMan().setPosition(heroStartingPosition);
		objects.add(getJumpMan());
		try {
			Scanner s = new Scanner(f);
			int lineNum = 0;
			while (s.hasNextLine()) {
				String line = s.nextLine();
				if (line.startsWith("#"))
					continue;
				if (line.isEmpty()) {
					GameEngine.getInstance().sendMessage("Line missing. Aborting the game!");
					ImageGUI.getInstance().dispose();
					return;
				}
				for (int i = 0; i < line.length(); i++) {
					Point2D position = new Point2D(i, lineNum);
					char object = line.charAt(i);
					addObject(new Floor(position)); // Adicionar sempre um piso por padrão
					switch (object) {
					case 'G':
						addObject(new DonkeyKong(position));
						break;
					case '0':
						addObject(new DoorClosed(position));
						break;
					case 'W':
						addObject(new Wall(position));
						break;
					case 'S':
						addObject(new Stairs(position));
						break;
					case 't':
						addObject(new Trap(position, TrapType.ONDISPLAY));
						break;
					case 'T':
						addObject(new Trap(position, TrapType.HIDDEN));
						break;
					case 's':
						addObject(new Sword(position));
						break;
					case 'b':
						addObject(new Bomb(position));
						break;
					case 'P':
						addObject(new Princess(position));
						break;
					case 'H':
						addObject(new Hammer(position));
						break;
					case 'm':
						addObject(new GoodMeat(position));
						break;
					default:
						System.err.println("Unknown character '" + object + "' at line " + lineNum + " and column " + i
								+ ". Defaulting to Floor.");
						ImageGUI.getInstance().setStatusMessage("Missing or Unknown character: " + object
								+ ", at line: " + lineNum + ", column: " + i + ". Defaulting to Floor.");
					}
				}
				lineNum++;
			}

			s.close();
		} catch (FileNotFoundException e) { 
			loadRoom(new File(ImageGUI.getInstance().askUser("File name: ")));
		}
	}

	public void nextRoom() throws IOException {
		this.roomNumber++;

		setIsComplete(false);

		ImageGUI.getInstance().removeImages(objects);
		objects.clear();
		GameEngine.getInstance().loadElements("rooms/room" + roomNumber + ".txt");
		GameEngine.getInstance().sendImagesToGUI();
	}

	public void checkCollisions() {
		List<GameObjects> toRemove = new ArrayList<>(); 

		objects.stream().filter(obj -> obj instanceof Collision) 
				.filter(obj -> obj.getPosition().equals(getJumpMan().getPosition()))
				.forEach(obj -> {
					((Collision) obj).onCollision(getJumpMan());
					if (((Collision) obj).uniqueInteraction()) {
						toRemove.add(obj);
					}
				});

		for (GameObjects obj : toRemove) {
			removeObject(obj);
		}
	}

	public void moveObjects(double dificulty, Room room) {
		for (GameObjects obj : MovingObjects.listMovingObjects()) {
			if (obj instanceof Updatable)
				((Updatable) obj).action(dificulty, room);
		}
	}

	public void spawnBatPeriodically(double difficulty) {
		if (Math.random() < difficulty) {
			addObject(new Bat(new Point2D(new Random().nextInt(11), 0)));
		}
	}
}
