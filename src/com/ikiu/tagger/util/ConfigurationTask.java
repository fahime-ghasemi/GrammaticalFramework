package com.ikiu.tagger.util;

import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;

import org.w3c.dom.Document;

import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by fahime on 9/10/15.
 */
public class ConfigurationTask {
    private String configurationFilePath;
    private String corePath;
    private String workspacePath;
    private static ConfigurationTask mInstance;
    private ConfigurationChangeListener listener;

    public interface ConfigurationChangeListener {
        public void onCoreChangeListener();

        public void onWorkspaceChangeListener();
    }

    public void writeLog(String log) {
        try {

            File file = new File("");

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(log);
            bw.close();

            System.out.println("Done");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ConfigurationTask() {
        String path = new File("").getAbsolutePath() + File.separatorChar + "resources" + File.separatorChar + "configuration.xml";
        this.configurationFilePath = path;
//        writeLog(configurationFilePath);
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser pullParser = factory.newPullParser();
            pullParser.setInput(new FileReader(configurationFilePath));
            int eventType = pullParser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG && pullParser.getName().equals("workspace")) {
                    this.workspacePath = pullParser.nextText();
                }
                if (eventType == XmlPullParser.START_TAG && pullParser.getName().equals("core")) {
                    this.corePath = pullParser.nextText();
                }
                eventType = pullParser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }
    }

    public static synchronized ConfigurationTask getInstance() {
        if (mInstance == null)
            mInstance = new ConfigurationTask();
        return mInstance;
    }

    public void setListener(ConfigurationChangeListener listener) {
        this.listener = listener;
    }

    public void setWorkspace(String workspacePath) {
        if (this.workspacePath != null && this.workspacePath.equals(workspacePath))
            return;

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            Document document = documentBuilder.parse(configurationFilePath);
            Node node = document.getElementsByTagName("workspace").item(0);
            node.setTextContent(workspacePath);
            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(configurationFilePath));
            transformer.transform(source, result);

            this.workspacePath = workspacePath;
            if (this.listener != null)
                this.listener.onWorkspaceChangeListener();

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {

        } catch (IOException e) {

        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    public String getWorkspace() {
        return this.workspacePath;
    }

    public String getCore() {
        return this.corePath;
    }

    public void setCore(String corePath) {
        if (this.corePath != null && this.corePath.equals(corePath))
            return;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            Document document = documentBuilder.parse(configurationFilePath);
            Node node = document.getElementsByTagName("core").item(0);
            node.setTextContent(corePath);
            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(configurationFilePath));
            transformer.transform(source, result);

            this.corePath = corePath;
            if (this.listener != null)
                this.listener.onCoreChangeListener();

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {

        } catch (IOException e) {

        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
