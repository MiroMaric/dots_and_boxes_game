package rs.miromaric.dotsandboxes.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.miromaric.dotsandboxes.common.domain.Move;
import rs.miromaric.dotsandboxes.server.service.DotsAndBoxesService;


/**
 *
 * @author miro
 */
@RestController
@RequestMapping("api")
public class DotsAndBoxesController {
    
    private final Logger log = LoggerFactory.getLogger(DotsAndBoxesController.class);
    @Autowired
    DotsAndBoxesService service;
    
    @PostMapping("isCloseMove")
    public Integer isCloseMove(@RequestBody Move move) {
        log.debug(move.toString());
        return service.isCloseMove(move);
    }
    
    @PostMapping("nextMove")
    public Move nextMove(@RequestBody Move move) throws Exception {
        log.debug(move.toString());
        return service.nextMove(move);
    }
    
    @ExceptionHandler({ Exception.class })
    public void handleException(Exception exception) {
        log.error("",exception);
    }
    
}
