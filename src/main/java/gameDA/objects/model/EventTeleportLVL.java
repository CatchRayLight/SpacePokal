package gameDA.objects.model;

import gameDA.Game;
import gameDA.config.output.SpriteSheet;
import gameDA.gui.Gamestate;
import gameDA.gui.menus.submenus.DialogueMenu;
import gameDA.gui.menus.submenus.FlyConfirmationMenu;
import gameDA.objects.GameObject;
import gameDA.objects.LvLHandler;
import gameDA.objects.ObjectHandler;
import gameDA.objects.ObjectID;

import java.awt.*;
import java.awt.image.BufferedImage;

public class EventTeleportLVL extends GameObject {
    private final ObjectHandler objectHandler;
    private final BufferedImage img;

    private final LvLHandler lvLHandler;

    public EventTeleportLVL(int x, int y, ObjectID id, SpriteSheet spriteSheet, ObjectHandler objectHandler) {
        super(x, y, id, spriteSheet);
        this.objectHandler = objectHandler;
        img = spriteSheet.getImage(9,8,32,32);
        lvLHandler = new LvLHandler();
    }

    @Override
    public void update() {
        for (int i = 0; i < objectHandler.gameObjects.size() ; i++) {
            GameObject tempObject = objectHandler.gameObjects.get(i);
            if (tempObject.getId() == ObjectID.PLAYER) {
                if(getBounds().intersects(tempObject.getBounds())){
                    Game.getGame().setGamestate(Gamestate.INMENU);
                    //change lvl
                    //Triggers sample dialogue for now
                    if(((Player) tempObject).isOnPlanet()){
                        Game.getGame().getMenuHandler().setCurrentMenu(new FlyConfirmationMenu());
                        tempObject.setX(getBounds().x+37);
                    }
                    if(!((Player) tempObject).isOnPlanet()) {
                        String[][] sampleDialogue = new String[][]{{"Oh mein Gott, da ist einer der bösen Aliens","Jetzt kann ich mir ein Teil meines Pokales schnappen"}};
                        Game.getGame().getMenuHandler().setCurrentMenu(new DialogueMenu(sampleDialogue));
                        lvLHandler.nextLvL(objectHandler);
                    }
                    break;
                }
            }
        }
    }
    @Override
    public void render(Graphics g) {
        g.drawImage(img,x,y,null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x,y,32,32);
    }
    @Override
    public Rectangle getTopBounds(int offset) {
        return null;
    }

    @Override
    public Rectangle getRightBounds(int offset) {
        return null;
    }

    @Override
    public Rectangle getLeftBounds(int offset) {
        return null;
    }

    @Override
    public Rectangle getBotBounds(int offset) {
        return null;
    }
}
