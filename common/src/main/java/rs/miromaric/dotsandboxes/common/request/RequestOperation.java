package rs.miromaric.dotsandboxes.common.request;

import java.io.Serializable;

/**
 *
 * @author miro
 */
public enum RequestOperation implements Serializable{
    
    LOGIN,
    SIGNUP,
    START_GAME,
    IS_CLOSE_MOVE,
    SAVE_MOVE,
    END_GAME,
    FIND_SCORES
    
}
