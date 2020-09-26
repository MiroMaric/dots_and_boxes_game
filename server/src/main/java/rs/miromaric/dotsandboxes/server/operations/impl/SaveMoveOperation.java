package rs.miromaric.dotsandboxes.server.operations.impl;

import rs.miromaric.dotsandboxes.common.domain.Move;
import rs.miromaric.dotsandboxes.common.request.RequestOperation;
import rs.miromaric.dotsandboxes.server.operations.GenericOperation;

/**
 *
 * @author miro
 */
public class SaveMoveOperation extends GenericOperation<Move, Move>{

    private Move move;
    
    @Override
    protected void validate(Move entity) throws Exception {
        
    }

    @Override
    protected void execute(Move newMove) throws Exception {
        newMove.setMoveOrder(-1); //todo
        Long id = databaseBroker.insertRecord(newMove);
        move = databaseBroker.findRecord(new Move(id))
                .map(Move.class::cast)
                .orElseThrow(() -> new RuntimeException("Logic server error"));
    }
    
    @Override
    public Move getResult() {
        return move;
    }
    
    @Override
    public RequestOperation getSupportedOperation() {
        return RequestOperation.SAVE_MOVE;
    }
    
}
