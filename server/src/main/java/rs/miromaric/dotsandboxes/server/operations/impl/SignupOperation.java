package rs.miromaric.dotsandboxes.server.operations.impl;

import rs.miromaric.dotsandboxes.common.domain.Player;
import rs.miromaric.dotsandboxes.common.request.RequestOperation;
import rs.miromaric.dotsandboxes.server.operations.GenericOperation;

/**
 *
 * @author miro
 */
public class SignupOperation extends GenericOperation<Player, Player> {

    @Override
    protected void validate(Player player) throws Exception {
        if(player.getNickname() == null || player.getNickname().isEmpty()) {
            throw new Exception("Nickname is required");
        }
        if(player.getPassword()== null || player.getPassword().isEmpty()) {
            throw new Exception("Password is required");
        }
    }

    @Override
    protected void execute(Player player) throws Exception {
        boolean exists = databaseBroker.findRecord(new Player(player.getNickname()))
                .map(Player.class::cast)
                .isPresent();
        if(!exists) {
            databaseBroker.insertRecord(player);
        } else {
           throw new RuntimeException("Nickname already exists");
        }
    }
    
    @Override
    public Player getResult() {
        return null;
    }
    
    @Override
    public RequestOperation getSupportedOperation() {
        return RequestOperation.SIGNUP;
    }
    
}
