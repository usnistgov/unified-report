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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MessageInstanceSpecificValuesV2 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MessageInstanceSpecificValuesV2">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="DataValueLocationItem" type="{http://www.nist.gov/healthcare/validation/message/hl7/v2/context}DataValueLocationItemV2"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MessageInstanceSpecificValuesV2", propOrder = {
    "dataValueLocationItem"
})
public class MessageInstanceSpecificValuesV2 {

    @XmlElement(name = "DataValueLocationItem")
    protected List<DataValueLocationItemV2> dataValueLocationItem;

    /**
     * Gets the value of the dataValueLocationItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dataValueLocationItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDataValueLocationItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DataValueLocationItemV2 }
     * 
     * 
     */
    public List<DataValueLocationItemV2> getDataValueLocationItem() {
        if (dataValueLocationItem == null) {
            dataValueLocationItem = new ArrayList<DataValueLocationItemV2>();
        }
        return this.dataValueLocationItem;
    }

}
