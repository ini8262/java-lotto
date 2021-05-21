package lotto.ui.message;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;

public class Message {
    private static final String UTF8 = "UTF-8";
    private Properties properties;

    public Message(String resource) {
        properties = new Properties();

        try {
            InputStream src = getClass().getClassLoader().getResourceAsStream(resource);
            properties.load(new InputStreamReader(src, Charset.forName(UTF8)));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public String get(String key) {
        return properties.getProperty(key);
    }

    public String get(String key, Object... param) {
        return String.format(get(key), param);
    }
}
