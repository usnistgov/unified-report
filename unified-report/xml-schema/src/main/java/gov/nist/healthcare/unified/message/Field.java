//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.06.08 at 04:13:08 PM EDT 
//


package gov.nist.healthcare.unified.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Field complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Field">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Component" type="{http://www.nist.gov/healthcare/message}Component" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="Position" use="required" type="{http://www.nist.gov/healthcare/message}Position" />
 *       &lt;attribute name="InstanceNumber" type="{http://www.nist.gov/healthcare/message}InstanceNumber" />
 *       &lt;attribute name="AnyInstanceNumber" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Field", propOrder = {
    "component"
})
public class Field {

    @XmlElement(name = "Component")
    protected Component component;
    @XmlAttribute(name = "Position", required = true)
    protected int position;
    @XmlAttribute(name = "InstanceNumber")
    protected Integer instanceNumber;
    @XmlAttribute(name = "AnyInstanceNumber")
    protected Boolean anyInstanceNumber;

    /**
     * Gets the value of the component property.
     * 
     * @return
     *     possible object is
     *     {@link Component }
     *     
     */
    public Component getComponent() {
        return component;
    }

    /**
     * Sets the value of the component property.
     * 
     * @param value
     *     allowed object is
     *     {@link Component }
     *     
     */
    public void setComponent(Component value) {
        this.component = value;
    }

    /**
     * Gets the value of the position property.
     * 
     */
    public int getPosition() {
        return position;
    }

    /**
     * Sets the value of the position property.
     * 
     */
    public void setPosition(int value) {
        this.position = value;
    }

    /**
     * Gets the value of the instanceNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getInstanceNumber() {
        return instanceNumber;
    }

    /**
     * Sets the value of the instanceNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setInstanceNumber(Integer value) {
        this.instanceNumber = value;
    }

    /**
     * Gets the value of the anyInstanceNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAnyInstanceNumber() {
        return anyInstanceNumber;
    }

    /**
     * Sets the value of the anyInstanceNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAnyInstanceNumber(Boolean value) {
        this.anyInstanceNumber = value;
    }

}
