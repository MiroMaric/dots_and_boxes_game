package rs.miromaric.dotsandboxes.server.service;

import rs.miromaric.dotsandboxes.common.domain.Move;

/**
 *
 * @author miro
 */
public interface DotsAndBoxesService {
    
    Integer isCloseMove(Move move);
    Move nextMove(Move move) throws Exception;
}
