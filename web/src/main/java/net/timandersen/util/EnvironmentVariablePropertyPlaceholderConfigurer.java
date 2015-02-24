package net.timandersen.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EnvironmentVariablePropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

    private static Pattern pattern = Pattern.compile("\\$\\{(?:ENV|env)\\.(.+?)\\}");
    private Logger logger = LoggerFactory.getLogger(EnvironmentVariablePropertyPlaceholderConfigurer.class);

    @Override
    protected String resolveSystemProperty(String key) {
        String value = super.resolveSystemProperty(key);
        if (value == null) {
            value = manipulatePropertyValue(key);
        }
        String resolvedValue = (value == null) ? null : manipulatePropertyValue(value);
        logger.info("RESOLVED PROPERTY [" + key + " -> " + value + "]");
        return resolvedValue;
    }

    private String manipulatePropertyValue(String value) {
        return manipulatePropertyValue(null, value);
    }

    private String manipulatePropertyValue(String key, String value) {
        String newValue = value.trim();
        boolean changed = true;
        StringBuffer buffer = new StringBuffer(newValue);
        while (changed) {
            changed = false;
            Matcher matcher = pattern.matcher(buffer.toString());
            buffer.setLength(0);
            if (matcher.find(0)) {
                String environmentVariableName = matcher.group(1);
                String environmentVariable = System.getenv(environmentVariableName);
                verifyEnvironmentVariableExists(environmentVariable, environmentVariableName, key, value);
                matcher.appendReplacement(buffer, environmentVariable);
                matcher.appendTail(buffer);
                changed = true;
            } else {
                matcher.appendTail(buffer);
            }
        }
        return buffer.toString();
    }

    private void verifyEnvironmentVariableExists(String environmentVariable, String environmentVariableName, String key, String value) {
        if (environmentVariable == null) {
            String errorMessge = "property file references missing environment variable: " + environmentVariableName +
                    " for key: " + key + " and value: " + value;
            throw new RuntimeException(errorMessge);
        }
    }

    public void initialize() {
        setSystemPropertiesMode(SYSTEM_PROPERTIES_MODE_OVERRIDE);
        Map<String, String> propertiesFileMap = new HashMap<String, String>();
        propertiesFileMap.put(serverEnv(), "dev.properties");
        propertiesFileMap.put("TEST", "test.properties");
        propertiesFileMap.put("PROD", "prod.properties");
        String serverEnv = serverEnv();
        String propertiesFile = propertiesFileMap.get(serverEnv);
        String propertiesFileLocation = "config/" + propertiesFile;
        Properties properties = new Properties();
        InputStream resource = getClass().getClassLoader().getResourceAsStream(propertiesFileLocation);

        try {
            properties.load(resource);
            for (Object objectKey : properties.keySet()) {
                String key = (String) objectKey;
                String value = manipulatePropertyValue(key, (String) properties.get(key));
                properties.setProperty(key, value);
                setSystemProperty(key, value);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        logger.info("Loading properties [" + propertiesFile + "] for [" + serverEnv + "] environment");

        setLocation(new ClassPathResource(propertiesFileLocation));

    }

    private void setSystemProperty(String key, String value) {
        System.setProperty(key, value);
    }

    private String serverEnv() {
        String env = System.getProperty("SERVER_ENVIRONMENT");
        return env == null ? "DEV" : env;
    }

}
