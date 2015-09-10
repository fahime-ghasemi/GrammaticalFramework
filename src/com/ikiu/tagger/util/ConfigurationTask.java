package com.ikiu.tagger.util;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by fahime on 9/10/15.
 */
public class ConfigurationTask {
    public static final String configurationFilePath ="src/com/ikiu/tagger/configuration.xml";
    public  String getWorkspace()
    {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser pullParser = factory.newPullParser();
            pullParser.setInput(new FileReader(configurationFilePath));
            int eventType = pullParser.getEventType();
            while (eventType!= XmlPullParser.END_DOCUMENT)
            {
                if(eventType == XmlPullParser.START_TAG && pullParser.getName().equals("workspace"))
                {
                    String s= pullParser.nextText();
                    return s;
                }
                 eventType = pullParser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        catch (FileNotFoundException e)
        {

        }
        catch (IOException e)
        {

        }

        return null;
    }
}
