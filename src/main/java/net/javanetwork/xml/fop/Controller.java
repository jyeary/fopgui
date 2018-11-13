/*
 * Controller.java
 *
 * Created on May 7, 2006, 7:16 PM
 */
package net.javanetwork.xml.fop;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;

/**
 *
 * @author John Yeary <jyeary@javanetwork.net>
 * @author John Yeary <jyeary@bluelotussoftware.com>
 * @version 1.1
 */
public class Controller {

    private FopFactory fopFactory;
    private Fop fop;
    private FOUserAgent foUserAgent;
    private static final Logger LOGGER = Logger.getLogger("net.javanetwork.xml.fop.Controller");

    /**
     * Creates a new instance of Controller
     */
    public Controller() {
        LOGGER.setLevel(Level.INFO);
        fopFactory = FopFactory.newInstance();
        foUserAgent = fopFactory.newFOUserAgent();
    }

    public Controller(Level loggingLevel) {
        this();
        LOGGER.setLevel(loggingLevel);
    }

    /**
     * Holds value of property xmlFile.
     */
    private File xmlFile;

    /**
     * Getter for property xmlFile.
     *
     * @return Value of property xmlFile.
     */
    public File getXmlFile() {
        return this.xmlFile;
    }

    /**
     * Setter for property xmlFile.
     *
     * @param xmlFile New value of property xmlFile.
     */
    public void setXmlFile(File xmlFile) {
        this.xmlFile = xmlFile;
    }

    /**
     * Holds value of property xsltFile.
     */
    private File xsltFile;

    /**
     * Getter for property xslFile.
     *
     * @return Value of property xslFile.
     */
    public File getXsltFile() {
        return this.xsltFile;
    }

    /**
     * Setter for property xslFile.
     *
     * @param xsltFile New value of property xslFile.
     */
    public void setXsltFile(File xsltFile) {
        this.xsltFile = xsltFile;
    }

    /**
     * Holds value of property foFile.
     */
    private File foFile;

    /**
     * Getter for property foFile.
     *
     * @return Value of property foFile.
     */
    public File getFoFile() {
        return this.foFile;
    }

    /**
     * Setter for property foFile.
     *
     * @param foFile New value of property foFile.
     */
    public void setFoFile(File foFile) {
        this.foFile = foFile;
    }

    public void viewFO() {
        try {
            TransformerFactory factory = TransformerFactory.newInstance();
            fop = fopFactory.newFop(MimeConstants.MIME_FOP_AWT_PREVIEW, foUserAgent);
            Transformer transformer = factory.newTransformer(new StreamSource(xsltFile));
            Source src = new StreamSource(xmlFile);
            Result res = new SAXResult(fop.getDefaultHandler());
            transformer.transform(src, res);
        } catch (FOPException e) {
            LOGGER.log(Level.SEVERE, "The Controller failed during init()", e);
            System.exit(1);
        } catch (TransformerConfigurationException e) {
            LOGGER.log(Level.SEVERE, "The transformer failed to instantiate", e);
            System.exit(1);
        } catch (TransformerException e) {
            LOGGER.log(Level.SEVERE, "The transformer failed to perform the transformation", e);
            System.exit(1);
        }
    }

    public void convertToPDF() throws IOException, FOPException, TransformerException {
        String filename = xmlFile.getName().split("\\.")[0] + ".pdf";
        try (OutputStream out = new FileOutputStream(filename)) {
            fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(xsltFile));
            Result res = new SAXResult(fop.getDefaultHandler());
            transformer.transform(new StreamSource(xmlFile), res);
        }
    }

}
