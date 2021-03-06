//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.11.18 at 01:42:48 PM EET 
//


package https.www_ncbi_nlm_nih_gov.snp.docsum;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
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
 *         &lt;element name="Method">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence minOccurs="0">
 *                   &lt;element name="Exception" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                 &lt;/sequence>
 *                 &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="Id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Taxonomy">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                 &lt;attribute name="organism" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Strains" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="Comment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Citation" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="handle" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="batch" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="batchId" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="batchType">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="snpassay"/>
 *             &lt;enumeration value="validation"/>
 *             &lt;enumeration value="doublehit"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="molType">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="genomic"/>
 *             &lt;enumeration value="cDNA"/>
 *             &lt;enumeration value="mito"/>
 *             &lt;enumeration value="chloro"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="sampleSize" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="population" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="linkoutUrl" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "method",
    "taxonomy",
    "strains",
    "comment",
    "citation"
})
@XmlRootElement(name = "Assay")
public class Assay {

    @XmlElement(name = "Method", required = true)
    protected Assay.Method method;
    @XmlElement(name = "Taxonomy", required = true)
    protected Assay.Taxonomy taxonomy;
    @XmlElement(name = "Strains")
    protected List<String> strains;
    @XmlElement(name = "Comment")
    protected String comment;
    @XmlElement(name = "Citation")
    protected List<String> citation;
    @XmlAttribute(name = "handle")
    protected String handle;
    @XmlAttribute(name = "batch")
    protected String batch;
    @XmlAttribute(name = "batchId")
    protected Integer batchId;
    @XmlAttribute(name = "batchType")
    protected String batchType;
    @XmlAttribute(name = "molType")
    protected String molType;
    @XmlAttribute(name = "sampleSize")
    protected Integer sampleSize;
    @XmlAttribute(name = "population")
    protected String population;
    @XmlAttribute(name = "linkoutUrl")
    protected String linkoutUrl;

    /**
     * Gets the value of the method property.
     * 
     * @return
     *     possible object is
     *     {@link Assay.Method }
     *     
     */
    public Assay.Method getMethod() {
        return method;
    }

    /**
     * Sets the value of the method property.
     * 
     * @param value
     *     allowed object is
     *     {@link Assay.Method }
     *     
     */
    public void setMethod(Assay.Method value) {
        this.method = value;
    }

    /**
     * Gets the value of the taxonomy property.
     * 
     * @return
     *     possible object is
     *     {@link Assay.Taxonomy }
     *     
     */
    public Assay.Taxonomy getTaxonomy() {
        return taxonomy;
    }

    /**
     * Sets the value of the taxonomy property.
     * 
     * @param value
     *     allowed object is
     *     {@link Assay.Taxonomy }
     *     
     */
    public void setTaxonomy(Assay.Taxonomy value) {
        this.taxonomy = value;
    }

    /**
     * Gets the value of the strains property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the strains property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStrains().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getStrains() {
        if (strains == null) {
            strains = new ArrayList<String>();
        }
        return this.strains;
    }

    /**
     * Gets the value of the comment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the value of the comment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComment(String value) {
        this.comment = value;
    }

    /**
     * Gets the value of the citation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the citation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCitation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getCitation() {
        if (citation == null) {
            citation = new ArrayList<String>();
        }
        return this.citation;
    }

    /**
     * Gets the value of the handle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHandle() {
        return handle;
    }

    /**
     * Sets the value of the handle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHandle(String value) {
        this.handle = value;
    }

    /**
     * Gets the value of the batch property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBatch() {
        return batch;
    }

    /**
     * Sets the value of the batch property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBatch(String value) {
        this.batch = value;
    }

    /**
     * Gets the value of the batchId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getBatchId() {
        return batchId;
    }

    /**
     * Sets the value of the batchId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setBatchId(Integer value) {
        this.batchId = value;
    }

    /**
     * Gets the value of the batchType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBatchType() {
        return batchType;
    }

    /**
     * Sets the value of the batchType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBatchType(String value) {
        this.batchType = value;
    }

    /**
     * Gets the value of the molType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMolType() {
        return molType;
    }

    /**
     * Sets the value of the molType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMolType(String value) {
        this.molType = value;
    }

    /**
     * Gets the value of the sampleSize property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSampleSize() {
        return sampleSize;
    }

    /**
     * Sets the value of the sampleSize property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSampleSize(Integer value) {
        this.sampleSize = value;
    }

    /**
     * Gets the value of the population property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPopulation() {
        return population;
    }

    /**
     * Sets the value of the population property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPopulation(String value) {
        this.population = value;
    }

    /**
     * Gets the value of the linkoutUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLinkoutUrl() {
        return linkoutUrl;
    }

    /**
     * Sets the value of the linkoutUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLinkoutUrl(String value) {
        this.linkoutUrl = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence minOccurs="0">
     *         &lt;element name="Exception" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *       &lt;/sequence>
     *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="Id" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "exception"
    })
    public static class Method {

        @XmlElement(name = "Exception")
        protected String exception;
        @XmlAttribute(name = "name")
        protected String name;
        @XmlAttribute(name = "Id")
        protected String id;

        /**
         * Gets the value of the exception property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getException() {
            return exception;
        }

        /**
         * Sets the value of the exception property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setException(String value) {
            this.exception = value;
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

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
     *       &lt;attribute name="organism" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class Taxonomy {

        @XmlAttribute(name = "id", required = true)
        protected int id;
        @XmlAttribute(name = "organism")
        protected String organism;

        /**
         * Gets the value of the id property.
         * 
         */
        public int getId() {
            return id;
        }

        /**
         * Sets the value of the id property.
         * 
         */
        public void setId(int value) {
            this.id = value;
        }

        /**
         * Gets the value of the organism property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getOrganism() {
            return organism;
        }

        /**
         * Sets the value of the organism property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setOrganism(String value) {
            this.organism = value;
        }

    }

}
