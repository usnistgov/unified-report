//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.06.08 at 04:13:08 PM EDT 
//


package gov.nist.healthcare.unified.validation.message.hl7.v2.context;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import gov.nist.healthcare.unified.message.MessageElement;
import gov.nist.healthcare.unified.validation.AssertionResultConstants;
import gov.nist.healthcare.unified.validation.Value;


/**
 * <p>Java class for DataValueLocationItemV2 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DataValueLocationItemV2">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Location">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{http://www.nist.gov/healthcare/message}MessageElement">
 *                 &lt;attribute name="CheckAll" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *               &lt;/extension>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Value" type="{http://www.nist.gov/healthcare/validation}Value" maxOccurs="unbounded"/>
 *         &lt;element name="Comment" type="{http://www.nist.gov/healthcare/validation}CommentType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="AssertionResult" type="{http://www.nist.gov/healthcare/validation}AssertionResultConstants" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DataValueLocationItemV2", propOrder = {
    "location",
    "value",
    "comment"
})
public class DataValueLocationItemV2 {

    @XmlElement(name = "Location", required = true)
    protected DataValueLocationItemV2 .Location location;
    @XmlElement(name = "Value", required = true)
    protected List<Value> value;
    @XmlElement(name = "Comment")
    protected String comment;
    @XmlAttribute(name = "AssertionResult")
    protected AssertionResultConstants assertionResult;

    /**
     * Gets the value of the location property.
     * 
     * @return
     *     possible object is
     *     {@link DataValueLocationItemV2 .Location }
     *     
     */
    public DataValueLocationItemV2 .Location getLocation() {
        return location;
    }

    /**
     * Sets the value of the location property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataValueLocationItemV2 .Location }
     *     
     */
    public void setLocation(DataValueLocationItemV2 .Location value) {
        this.location = value;
    }

    /**
     * Gets the value of the value property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the value property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getValue().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Value }
     * 
     * 
     */
    public List<Value> getValue() {
        if (value == null) {
            value = new ArrayList<Value>();
        }
        return this.value;
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
     * Gets the value of the assertionResult property.
     * 
     * @return
     *     possible object is
     *     {@link AssertionResultConstants }
     *     
     */
    public AssertionResultConstants getAssertionResult() {
        return assertionResult;
    }

    /**
     * Sets the value of the assertionResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link AssertionResultConstants }
     *     
     */
    public void setAssertionResult(AssertionResultConstants value) {
        this.assertionResult = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;extension base="{http://www.nist.gov/healthcare/message}MessageElement">
     *       &lt;attribute name="CheckAll" type="{http://www.w3.org/2001/XMLSchema}boolean" />
     *     &lt;/extension>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class Location
        extends MessageElement
    {

        @XmlAttribute(name = "CheckAll")
        protected Boolean checkAll;

        /**
         * Gets the value of the checkAll property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isCheckAll() {
            return checkAll;
        }

        /**
         * Sets the value of the checkAll property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setCheckAll(Boolean value) {
            this.checkAll = value;
        }

    }

}
