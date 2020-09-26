package rs.miromaric.dotsandboxes.server.operations.impl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import rs.miromaric.dotsandboxes.common.domain.Game;
import rs.miromaric.dotsandboxes.common.request.RequestOperation;
import rs.miromaric.dotsandboxes.server.operations.GenericOperation;

/**
 *
 * @author miro
 */
public class FindScoresOperation extends GenericOperation<Game, List<Game>>{

    private List<Game> scores;
    
    @Override
    protected void validate(Game entity) throws Exception {
        
    }

    @Override
    protected void execute(Game score) throws Exception {
        System.out.println(score);
        this.scores =  databaseBroker.
                getAllRecord(score)
                .stream()
                .map(Game.class::cast)
                .filter(game -> Objects.equals(game.getPlayerId(), score.getPlayerId()))
                .filter(game -> Objects.equals(game.getDimension(), score.getDimension()))
                .collect(Collectors.toList());
        System.out.println("score:");
        this.scores.forEach(System.out::println);
    }

    @Override
    public List<Game> getResult() {
        return scores;
    }
    
    @Override
    public RequestOperation getSupportedOperation() {
        return RequestOperation.FIND_SCORES;
    }
    
}
