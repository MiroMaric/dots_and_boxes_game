package rs.miromaric.dotsandboxes.server.operations.impl;

import rs.miromaric.dotsandboxes.common.domain.Game;
import rs.miromaric.dotsandboxes.common.request.RequestOperation;
import rs.miromaric.dotsandboxes.server.operations.GenericOperation;

/**
 *
 * @author miro
 */
public class EndGameOperation extends GenericOperation<Game, Game> {

    private Game game;

    @Override
    protected void validate(Game entity) throws Exception {
        
    }

    @Override
    protected void execute(Game entity) throws Exception {
        Game endGame = (Game)entity;
        databaseBroker.updateRecord(endGame);
        this.game = databaseBroker.findRecord(endGame)
                .map(Game.class::cast)
                .orElseThrow(()-> new RuntimeException("Logic server error"));
    }
    
    @Override
    public Game getResult() {
        return game;
    }
    
    @Override
    public RequestOperation getSupportedOperation() {
        return RequestOperation.END_GAME;
    }

}
