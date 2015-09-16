//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.06.08 at 04:13:08 PM EDT 
//


package gov.nist.healthcare.unified.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MessageElement complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MessageElement">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice minOccurs="0">
 *         &lt;element name="SegmentGroup" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{http://www.nist.gov/healthcare/message}SegmentGroup">
 *               &lt;/extension>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Segment" type="{http://www.nist.gov/healthcare/message}Segment" minOccurs="0"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MessageElement", propOrder = {
    "segmentGroup",
    "segment"
})
@XmlSeeAlso({
    gov.nist.healthcare.unified.validation.message.hl7.v2.context.DataValueLocationItemV2 .Location.class
})
public class MessageElement {

    @XmlElement(name = "SegmentGroup")
    protected MessageElement.SegmentGroup segmentGroup;
    @XmlElement(name = "Segment")
    protected Segment segment;

    /**
     * Gets the value of the segmentGroup property.
     * 
     * @return
     *     possible object is
     *     {@link MessageElement.SegmentGroup }
     *     
     */
    public MessageElement.SegmentGroup getSegmentGroup() {
        return segmentGroup;
    }

    /**
     * Sets the value of the segmentGroup property.
     * 
     * @param value
     *     allowed object is
     *     {@link MessageElement.SegmentGroup }
     *     
     */
    public void setSegmentGroup(MessageElement.SegmentGroup value) {
        this.segmentGroup = value;
    }

    /**
     * Gets the value of the segment property.
     * 
     * @return
     *     possible object is
     *     {@link Segment }
     *     
     */
    public Segment getSegment() {
        return segment;
    }

    /**
     * Sets the value of the segment property.
     * 
     * @param value
     *     allowed object is
     *     {@link Segment }
     *     
     */
    public void setSegment(Segment value) {
        this.segment = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;extension base="{http://www.nist.gov/healthcare/message}SegmentGroup">
     *     &lt;/extension>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class SegmentGroup
        extends gov.nist.healthcare.unified.message.SegmentGroup
    {


    }

}
