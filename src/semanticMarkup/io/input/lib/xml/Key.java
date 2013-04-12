//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.04.09 at 03:48:51 PM MST 
//


package semanticMarkup.io.input.lib.xml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}key_heading" minOccurs="0"/>
 *         &lt;element ref="{}key_author" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;choice maxOccurs="unbounded" minOccurs="0">
 *           &lt;element ref="{}key_discussion"/>
 *           &lt;element ref="{}key_head"/>
 *           &lt;element ref="{}key_statement"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "keyHeading",
    "keyAuthor",
    "keyDiscussionOrKeyHeadOrKeyStatement"
})
@XmlRootElement(name = "key")
public class Key {

    @XmlElement(name = "key_heading")
    protected String keyHeading;
    @XmlElement(name = "key_author")
    protected List<String> keyAuthor;
    @XmlElementRefs({
        @XmlElementRef(name = "key_statement", type = KeyStatement.class, required = false),
        @XmlElementRef(name = "key_head", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "key_discussion", type = JAXBElement.class, required = false)
    })
    protected List<Object> keyDiscussionOrKeyHeadOrKeyStatement;

    /**
     * Gets the value of the keyHeading property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKeyHeading() {
        return keyHeading;
    }

    /**
     * Sets the value of the keyHeading property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKeyHeading(String value) {
        this.keyHeading = value;
    }

    /**
     * Gets the value of the keyAuthor property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the keyAuthor property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getKeyAuthor().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getKeyAuthor() {
        if (keyAuthor == null) {
            keyAuthor = new ArrayList<String>();
        }
        return this.keyAuthor;
    }

    /**
     * Gets the value of the keyDiscussionOrKeyHeadOrKeyStatement property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the keyDiscussionOrKeyHeadOrKeyStatement property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getKeyDiscussionOrKeyHeadOrKeyStatement().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link KeyStatement }
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * 
     */
    public List<Object> getKeyDiscussionOrKeyHeadOrKeyStatement() {
        if (keyDiscussionOrKeyHeadOrKeyStatement == null) {
            keyDiscussionOrKeyHeadOrKeyStatement = new ArrayList<Object>();
        }
        return this.keyDiscussionOrKeyHeadOrKeyStatement;
    }

}