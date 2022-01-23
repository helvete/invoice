package cz.helvete.invoice.config;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;

@Singleton
@Startup
public class AppConfig {

    private static Document doc;
    private static XPathFactory xPathfactory;
    private static XPath xpath;

    @PostConstruct
    public void load() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(AppConfig.class.getResourceAsStream("/config.xml"));
            xPathfactory = XPathFactory.newInstance();
            xpath = xPathfactory.newXPath();
        } catch (Exception e) {
            throw new IllegalArgumentException("Problem loading config.xml, application cannot start ");
        }
    }

    public String get(String parameter) {
        try {
            return xpath.compile("/config/" + parameter.replace(".", "/"))
                .evaluate(doc)
                .trim();
        } catch (XPathExpressionException e) {
            return null;
        }
    }
}
