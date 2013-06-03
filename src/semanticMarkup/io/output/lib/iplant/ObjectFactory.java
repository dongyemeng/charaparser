//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.05.31 at 10:25:38 AM MST 
//


package semanticMarkup.io.output.lib.iplant;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the semanticMarkup.io.output.lib.iplant package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _TaxonName_QNAME = new QName("", "taxon_name");
    private final static QName _Text_QNAME = new QName("", "text");
    private final static QName _Discussion_QNAME = new QName("", "discussion");
    private final static QName _Source_QNAME = new QName("", "source");
    private final static QName _OtherInfo_QNAME = new QName("", "other_info");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: semanticMarkup.io.output.lib.iplant
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Structure }
     * 
     */
    public Structure createStructure() {
        return new Structure();
    }

    /**
     * Create an instance of {@link Character }
     * 
     */
    public Character createCharacter() {
        return new Character();
    }

    /**
     * Create an instance of {@link DescriptionStatement }
     * 
     */
    public DescriptionStatement createDescriptionStatement() {
        return new DescriptionStatement();
    }

    /**
     * Create an instance of {@link Relation }
     * 
     */
    public Relation createRelation() {
        return new Relation();
    }

    /**
     * Create an instance of {@link Description }
     * 
     */
    public Description createDescription() {
        return new Description();
    }

    /**
     * Create an instance of {@link Treatment }
     * 
     */
    public Treatment createTreatment() {
        return new Treatment();
    }

    /**
     * Create an instance of {@link Meta }
     * 
     */
    public Meta createMeta() {
        return new Meta();
    }

    /**
     * Create an instance of {@link TaxonIdentification }
     * 
     */
    public TaxonIdentification createTaxonIdentification() {
        return new TaxonIdentification();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "taxon_name")
    public JAXBElement<String> createTaxonName(String value) {
        return new JAXBElement<String>(_TaxonName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "text")
    public JAXBElement<String> createText(String value) {
        return new JAXBElement<String>(_Text_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "discussion")
    public JAXBElement<String> createDiscussion(String value) {
        return new JAXBElement<String>(_Discussion_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "source")
    public JAXBElement<String> createSource(String value) {
        return new JAXBElement<String>(_Source_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "other_info")
    public JAXBElement<String> createOtherInfo(String value) {
        return new JAXBElement<String>(_OtherInfo_QNAME, String.class, null, value);
    }

}