/*******************************************************************************
 *                                                                              
 *  Copyright FUJITSU LIMITED 2015                                             
 *                                                                              
 *                                                                              
 *******************************************************************************/

package baseService;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Read config-settings. In case a folder local exists, then read properties
 * from this location.
 * 
 */
public class PropertiesReader {

    private Properties testProperties;

    private List<String> propFiles = new ArrayList<String>();

    public PropertiesReader() {
        testProperties = new Properties();

        propFiles.add(System.getProperty("user.dir")
                + "/test.properties");
    }
    
    public static void main(String args[]){
    	System.out.println(System.getProperty("user.dir"));
    }

    public Properties load() throws Exception {
        for (String file : propFiles) {
            if (fileExists(file)) {
                load(file, testProperties);
            } else {
                System.out.println(" ***** WARNING: File" + file
                        + " not found!");
            }
//            replacePlaceholders();
        }

        return testProperties;
    }

    private boolean fileExists(String path) {
        File f = new File(path);
        return f.exists();
    }

    private void load(String configFile, Properties properties) {
        try (FileInputStream is = new FileInputStream(configFile);) {
            properties.load(is);
        } catch (Exception e) {
            throw new RuntimeException("Config path:" + configFile, e);
        }
    }

    /**
     * Sets the environment variable values in the properties, where required.
     * 
     * @param props
     *            The properties to set the values in.
     * @return The properties with the changed settings.
     */
    @SuppressWarnings("unused")
	private void replacePlaceholders() {
        Properties result = new Properties();
        for (Object keyValue : testProperties.keySet()) {
            String key = (String) keyValue;
            String value = testProperties.getProperty(key);
            Pattern pattern = Pattern.compile("[$]\\{[\\w.]+\\}");
            Matcher matcher = pattern.matcher(value);
            if (matcher.find()) {
                String match = matcher.toMatchResult().group();
                match = match.substring(2);
                match = match.substring(0, match.length() - 1);
                if (match.startsWith("env.")) {
                    String envValue = System.getenv(match.substring(4));
                    if (envValue != null) {
                        value = matcher.replaceAll(envValue);
                    }
                }
            }
            result.setProperty(key, value);
        }
        testProperties = result;
    }

}