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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DefaultTaxTable complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DefaultTaxTable">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="tax-rules">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="default-tax-rule" type="{http://checkout.google.com/schema/2}DefaultTaxRule" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DefaultTaxTable", namespace = "http://checkout.google.com/schema/2", propOrder = {

})
public class DefaultTaxTable {

    @XmlElement(name = "tax-rules", namespace = "http://checkout.google.com/schema/2", required = true)
    protected DefaultTaxTable.TaxRules taxRules;

    /**
     * Gets the value of the taxRules property.
     * 
     * @return
     *     possible object is
     *     {@link DefaultTaxTable.TaxRules }
     *     
     */
    public DefaultTaxTable.TaxRules getTaxRules() {
        return taxRules;
    }

    /**
     * Sets the value of the taxRules property.
     * 
     * @param value
     *     allowed object is
     *     {@link DefaultTaxTable.TaxRules }
     *     
     */
    public void setTaxRules(DefaultTaxTable.TaxRules value) {
        this.taxRules = value;
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
     *         &lt;element name="default-tax-rule" type="{http://checkout.google.com/schema/2}DefaultTaxRule" maxOccurs="unbounded" minOccurs="0"/>
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
        "defaultTaxRule"
    })
    public static class TaxRules {

        @XmlElement(name = "default-tax-rule", namespace = "http://checkout.google.com/schema/2")
        protected List<DefaultTaxRule> defaultTaxRule;

        /**
         * Gets the value of the defaultTaxRule property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the defaultTaxRule property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDefaultTaxRule().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link DefaultTaxRule }
         * 
         * 
         */
        public List<DefaultTaxRule> getDefaultTaxRule() {
            if (defaultTaxRule == null) {
                defaultTaxRule = new ArrayList<DefaultTaxRule>();
            }
            return this.defaultTaxRule;
        }

    }

}
