package pt.iscte.poo.game;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import objects.*;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.utils.Direction;

public class GameEngine implements Observer {

    private static GameEngine INSTANCE;

    private Room currentRoom;
    private int lastTickProcessed;
    public List<Room> rooms;

    private GameEngine() {
        rooms = new ArrayList<>();
        this.lastTickProcessed = 0;
    }

    public static GameEngine getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GameEngine();
        }
        return INSTANCE;
    }

    public int getTicks() {
        return lastTickProcessed;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    @Override
    public void update(Observed source) {
        if (ImageGUI.getInstance().wasKeyPressed()) {
            int k = ImageGUI.getInstance().keyPressed();
            if (Direction.isDirection(k))
                currentRoom.getJumpMan().move(Direction.directionFor(k));
            if (k == 66) // Tecla B
                currentRoom.getJumpMan().releaseBomb();
        }

        int t = ImageGUI.getInstance().getTicks();
        while (lastTickProcessed < t) {
            processTick();
            currentRoom.getJumpMan().gravity();
        }

        currentRoom.checkCollisions();

        if (currentRoom.getIsComplete()) {
            try {
                currentRoom.nextRoom();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ImageGUI.getInstance().update();
    }

    private void processTick() {
        List<GameObjects> toRemove = new ArrayList<>();
        List<GameObjects> toAdd = new ArrayList<>();
        List<Bomb> bombsCopy = new ArrayList<>(Bomb.bombs);

        currentRoom.moveObjects(0.2, currentRoom);
        currentRoom.spawnBatPeriodically(0.1);

        for (GameObjects obj : Room.objects) {
            if (obj instanceof Bomb) {
                for (Bomb bomb : bombsCopy) {
                    if (bomb.shouldExplode()) {
                        toRemove.addAll(bomb.toExplode());
                    }
                }
            }

            if (obj instanceof GoodMeat) {
                GoodMeat goodMeat = (GoodMeat) obj;
                if (goodMeat.shouldChangeToBadMeat()) {
                    toRemove.add(goodMeat);
                    toAdd.add(new BadMeat(goodMeat.getPosition()));
                }
            }
        }

        Room.objects.removeAll(toRemove);
        toRemove.forEach(ImageGUI.getInstance()::removeImage);

        Room.objects.addAll(toAdd);
        toAdd.forEach(ImageGUI.getInstance()::addImage);

        lastTickProcessed++;
    }

    public void restartGame() {
        currentRoom = new Room();
        ImageGUI.getInstance().clearImages();

        currentRoom.loadRoom(new File("rooms/room" + currentRoom.getNumber() + ".txt"));
        ImageGUI.getInstance().addImages(currentRoom.objects);
        ImageGUI.getInstance().update();
    }

    public void start() {
        this.currentRoom = new Room();
        String roomNumber = Integer.toString(currentRoom.getNumber());
        loadElements("rooms/room" + roomNumber + ".txt");
        sendImagesToGUI();
    }

    public void sendImagesToGUI() {
        ImageGUI.getInstance().clearImages();
        for (GameObjects obj : Room.getImages()) {
            ImageGUI.getInstance().addImage(obj);
        }

        ImageGUI.getInstance().removeImage(currentRoom.getJumpMan());
        ImageGUI.getInstance().addImage(currentRoom.getJumpMan());
        ImageGUI.getInstance().update();
    }

    public void loadElements(String f) {
        currentRoom.loadRoom(new File(f));
    }

    public void gameFinished() {
        HighScores.getInstance().updateAndShowScoresBoard();
        ImageGUI.getInstance().dispose();
    }

    public void sendMessage(String message) {
        ImageGUI.getInstance().setStatusMessage(message);
    }
}
