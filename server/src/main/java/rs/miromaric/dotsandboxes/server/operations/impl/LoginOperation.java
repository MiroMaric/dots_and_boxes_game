package rs.miromaric.dotsandboxes.server.operations.impl;

import rs.miromaric.dotsandboxes.common.domain.Player;
import rs.miromaric.dotsandboxes.common.request.RequestOperation;
import rs.miromaric.dotsandboxes.server.operations.GenericOperation;

/**
 *
 * @author miro
 */
public class LoginOperation extends GenericOperation<Player, Player>{

    private Player player;
    
    @Override
    protected void validate(Player entity) throws Exception {
        if(entity.getNickname() == null || entity.getPassword() == null) {
            throw new RuntimeException("nickname and password is required");
        }
    }

    @Override
    protected void execute(Player entity) throws Exception {
        player = databaseBroker.findRecord(entity)
                .map(Player.class::cast)
                .orElseThrow(() -> new RuntimeException("Bad nickname"));
        if(!player.getPassword().equals(entity.getPassword())) {
            throw new RuntimeException("Bad password");
        }
    }

    @Override
    public Player getResult() {
        return player;
    }
    
    @Override
    public RequestOperation getSupportedOperation() {
        return RequestOperation.LOGIN;
    }
    
}
