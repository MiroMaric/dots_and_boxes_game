package rs.miromaric.dotsandboxes.server.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rs.miromaric.dotsandboxes.common.domain.Move;
import rs.miromaric.dotsandboxes.server.operations.impl.SaveMoveOperation;
import rs.miromaric.dotsandboxes.server.settings.SettingsLoader;

/**
 *
 * @author miro
 */
@Service
public class DefaultDotsAndBoxesService implements DotsAndBoxesService {

    private final String GAME_URL_PROPERTY_NAME = "api.web.game.url";
    
    @Override
    public Integer isCloseMove(Move move) {
        System.out.println(move);
        String isCloseMoveUrl = SettingsLoader.getInstance().getProperty(GAME_URL_PROPERTY_NAME) + 
                                SettingsLoader.getInstance().getProperty("api.web.game.endpoint.isclosemove");
        return new RestTemplate().postForEntity(isCloseMoveUrl, move, Integer.class).getBody();
    }

    @Override
    public Move nextMove(Move move) throws Exception {
        System.out.println(move);
        String nextMoveUrl = SettingsLoader.getInstance().getProperty(GAME_URL_PROPERTY_NAME) + 
                                SettingsLoader.getInstance().getProperty("api.web.game.endpoint.nextmove");
        Move computerMove =  new RestTemplate().postForEntity(nextMoveUrl, move, Move.class).getBody();
        computerMove.setGameId(move.getGameId());
        SaveMoveOperation saveMoveOperation = new SaveMoveOperation();
        saveMoveOperation.templateExecute(computerMove);
        return saveMoveOperation.getResult();
    }
    
}
