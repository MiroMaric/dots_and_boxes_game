package rs.miromaric.dotsandboxes.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import rs.miromaric.dotsandboxes.server.start.SocketCommunication;

/**
 *
 * @author miro
 */
@SpringBootApplication
public class SpringWebApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(SpringWebApplication.class, args);
        new Thread(() -> {
            try {
                new SocketCommunication().startServer();
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }).start();
    }
}
