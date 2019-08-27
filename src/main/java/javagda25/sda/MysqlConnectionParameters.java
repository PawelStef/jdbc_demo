package javagda25.sda;

import lombok.Getter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
@Getter
public class MysqlConnectionParameters {
    private static final String PROPERTIES_FILE_NAME = "/jdbc.parameters";

    private Properties properties;

    private String dbHost ; //127.0.0.1
    private String dbPort ; //
    private String dbUserName ; //
    private String dbPassword ; //
    private String dbName ; //

    public MysqlConnectionParameters() throws IOException {
        loadConfigurationFrom(PROPERTIES_FILE_NAME);

        dbHost = loadParameter("jdbc.database.host");
        dbPort = loadParameter("jdbc.database.port");
        dbUserName = loadParameter("jdbc.username");
        dbPassword = loadParameter("jdbc.password");
        dbName = loadParameter("jdbc.database.name");
    }

    public Properties loadConfigurationFrom(String resourceName) throws IOException {
        properties = new Properties();

        InputStream propertiesStream = this.getClass().getResourceAsStream(resourceName);
        if (propertiesStream ==null){
            throw new FileNotFoundException("Cennot load file.");
        }
        properties.load(propertiesStream);
        return properties;
    }

    private String loadParameter(String propertyName){
        return properties.getProperty(propertyName);
    }

}
