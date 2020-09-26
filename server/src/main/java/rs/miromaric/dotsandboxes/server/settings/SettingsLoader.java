package rs.miromaric.dotsandboxes.server.settings;

import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author miro
 */
public class SettingsLoader {

    private static SettingsLoader instance;
    private Properties databaseProperties;

    private SettingsLoader() throws IOException {
        loadProperties();
    }

    public synchronized static SettingsLoader getInstance() {
        try {
            if (instance == null) {
                instance = new SettingsLoader();
            }
            return instance;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadProperties() throws IOException {
        databaseProperties = new Properties();
        databaseProperties.load(SettingsLoader.class.getResourceAsStream("/settings.properties"));
    }

    public String getProperty(String key) {
        return databaseProperties.getProperty(key, "n/a");
    }
}
