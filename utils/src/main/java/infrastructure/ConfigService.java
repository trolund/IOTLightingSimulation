package infrastructure;

import exceptions.ConfigException;

import java.util.*;
import java.io.*;

/**
 * @author Troels (s161791)
 */

public class ConfigService implements IConfigService {

    private Properties p;
    FileReader reader= null;

    public ConfigService() throws ConfigException {

        try {
            /*Properties p=new Properties();
            p.setProperty("rabbitmq.host","localhost");

            p.store(new FileWriter("global.properties"),"global config");*/
            reader = new FileReader("global.properties");
            p = new Properties();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ConfigException("Failed to load config : " + e.getMessage());
        }
    }

    public String getProp(String s) throws ConfigException {
        try {
            p.load(reader);
            return p.getProperty(s);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ConfigException("Failed to load prop: " + s + " from config : " + e.getMessage());
        }
    }
}
