package rs.miromaric.dotsandboxes.client.settings;

import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author miro
 */
public class AppSettings {
    
    private static AppSettings instance;
    private Properties settings;

    private AppSettings() throws IOException {
        loadProperties();
    }

    public synchronized static AppSettings getInstance() {
        try {
            if (instance == null) {
                instance = new AppSettings();
            }
            return instance;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadProperties() throws IOException {
        settings = new Properties();
        settings.load(AppSettings.class.getResourceAsStream("/settings.properties"));
    }

    public String getProperty(String key) {
        return settings.getProperty(key, "n/a");
    }
}
