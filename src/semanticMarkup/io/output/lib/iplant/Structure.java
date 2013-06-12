//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.06.10 at 02:12:31 PM MST 
//


package semanticMarkup.io.output.lib.iplant;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import semanticMarkup.io.output.BooleanAdapter;


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
 *         &lt;element ref="{}character" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="alter_name" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="constraint" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="constraintid" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="geographical_constraint" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="in_bracket" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="in_brackets" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="parallelism_constraint" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="taxon_constraint" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="ontologyid" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="provenance" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="notes" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "character"
})
@XmlRootElement(name = "structure")
public class Structure {

    protected List<Character> character;
    @XmlAttribute(name = "alter_name")
    @XmlSchemaType(name = "anySimpleType")
    protected String alterName;
    @XmlAttribute(name = "constraint")
    @XmlSchemaType(name = "anySimpleType")
    protected String constraint;
    @XmlAttribute(name = "constraintid")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String constraintid;
    @XmlAttribute(name = "geographical_constraint")
    @XmlSchemaType(name = "anySimpleType")
    protected String geographicalConstraint;
    @XmlAttribute(name = "id", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String id;
    @XmlAttribute(name = "in_bracket")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    protected Boolean inBracket;
    @XmlAttribute(name = "in_brackets")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    protected Boolean inBrackets;
    @XmlAttribute(name = "name", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String name;
    @XmlAttribute(name = "parallelism_constraint")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String parallelismConstraint;
    @XmlAttribute(name = "taxon_constraint")
    @XmlSchemaType(name = "anySimpleType")
    protected String taxonConstraint;
    @XmlAttribute(name = "ontologyid")
    protected String ontologyid;
    @XmlAttribute(name = "provenance")
    protected String provenance;
    @XmlAttribute(name = "notes")
    protected String notes;

    /**
     * Gets the value of the character property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the character property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCharacter().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Character }
     * 
     * 
     */
    public List<Character> getCharacter() {
        if (character == null) {
            character = new ArrayList<Character>();
        }
        return this.character;
    }

    /**
     * Gets the value of the alterName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlterName() {
        return alterName;
    }

    /**
     * Sets the value of the alterName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlterName(String value) {
        this.alterName = value;
    }

    /**
     * Gets the value of the constraint property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConstraint() {
        return constraint;
    }

    /**
     * Sets the value of the constraint property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConstraint(String value) {
        this.constraint = value;
    }

    /**
     * Gets the value of the constraintid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConstraintid() {
        return constraintid;
    }

    /**
     * Sets the value of the constraintid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConstraintid(String value) {
        this.constraintid = value;
    }

    /**
     * Gets the value of the geographicalConstraint property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGeographicalConstraint() {
        return geographicalConstraint;
    }

    /**
     * Sets the value of the geographicalConstraint property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGeographicalConstraint(String value) {
        this.geographicalConstraint = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the inBracket property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isInBracket() {
        return inBracket;
    }

    /**
     * Sets the value of the inBracket property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setInBracket(Boolean value) {
        this.inBracket = value;
    }

    /**
     * Gets the value of the inBrackets property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isInBrackets() {
        return inBrackets;
    }

    /**
     * Sets the value of the inBrackets property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setInBrackets(Boolean value) {
        this.inBrackets = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the parallelismConstraint property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParallelismConstraint() {
        return parallelismConstraint;
    }

    /**
     * Sets the value of the parallelismConstraint property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParallelismConstraint(String value) {
        this.parallelismConstraint = value;
    }

    /**
     * Gets the value of the taxonConstraint property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaxonConstraint() {
        return taxonConstraint;
    }

    /**
     * Sets the value of the taxonConstraint property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaxonConstraint(String value) {
        this.taxonConstraint = value;
    }

    /**
     * Gets the value of the ontologyid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOntologyid() {
        return ontologyid;
    }

    /**
     * Sets the value of the ontologyid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOntologyid(String value) {
        this.ontologyid = value;
    }

    /**
     * Gets the value of the provenance property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProvenance() {
        return provenance;
    }

    /**
     * Sets the value of the provenance property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProvenance(String value) {
        this.provenance = value;
    }

    /**
     * Gets the value of the notes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Sets the value of the notes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNotes(String value) {
        this.notes = value;
    }

}
