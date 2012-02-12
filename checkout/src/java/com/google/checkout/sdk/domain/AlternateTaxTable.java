//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.09.23 at 12:14:48 PM PDT 
//


package com.google.checkout.sdk.domain;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AlternateTaxTable complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AlternateTaxTable">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="alternate-tax-rules">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="alternate-tax-rule" type="{http://checkout.google.com/schema/2}AlternateTaxRule" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/all>
 *       &lt;attribute name="name" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;minLength value="0"/>
 *             &lt;maxLength value="255"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="standalone" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AlternateTaxTable", namespace = "http://checkout.google.com/schema/2", propOrder = {

})
public class AlternateTaxTable {

    @XmlElement(name = "alternate-tax-rules", namespace = "http://checkout.google.com/schema/2", required = true)
    protected AlternateTaxTable.AlternateTaxRules alternateTaxRules;
    @XmlAttribute(required = true)
    protected String name;
    @XmlAttribute
    protected Boolean standalone;

    /**
     * Gets the value of the alternateTaxRules property.
     * 
     * @return
     *     possible object is
     *     {@link AlternateTaxTable.AlternateTaxRules }
     *     
     */
    public AlternateTaxTable.AlternateTaxRules getAlternateTaxRules() {
        return alternateTaxRules;
    }

    /**
     * Sets the value of the alternateTaxRules property.
     * 
     * @param value
     *     allowed object is
     *     {@link AlternateTaxTable.AlternateTaxRules }
     *     
     */
    public void setAlternateTaxRules(AlternateTaxTable.AlternateTaxRules value) {
        this.alternateTaxRules = value;
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
     * Gets the value of the standalone property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isStandalone() {
        return standalone;
    }

    /**
     * Sets the value of the standalone property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setStandalone(Boolean value) {
        this.standalone = value;
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
     *       &lt;sequence>
     *         &lt;element name="alternate-tax-rule" type="{http://checkout.google.com/schema/2}AlternateTaxRule" maxOccurs="unbounded" minOccurs="0"/>
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
        "alternateTaxRule"
    })
    public static class AlternateTaxRules {

        @XmlElement(name = "alternate-tax-rule", namespace = "http://checkout.google.com/schema/2")
        protected List<AlternateTaxRule> alternateTaxRule;

        /**
         * Gets the value of the alternateTaxRule property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the alternateTaxRule property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAlternateTaxRule().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link AlternateTaxRule }
         * 
         * 
         */
        public List<AlternateTaxRule> getAlternateTaxRule() {
            if (alternateTaxRule == null) {
                alternateTaxRule = new ArrayList<AlternateTaxRule>();
            }
            return this.alternateTaxRule;
        }

    }

}
