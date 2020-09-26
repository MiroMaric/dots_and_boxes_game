package rs.miromaric.dotsandboxes.client.game;

import rs.miromaric.dotsandboxes.common.domain.Player;

/**
 *
 * @author miro
 */
public class Session {
    
    private Player player;
    private static Session instance;

    public Session() {
    }

    public synchronized static Session getInstance() {
        if(instance == null)
            instance = new Session();
        return instance;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

}
