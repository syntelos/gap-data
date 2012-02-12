//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.09.23 at 12:14:48 PM PDT 
//


package com.google.checkout.sdk.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MerchantCalculationCallback complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MerchantCalculationCallback">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="shopping-cart" type="{http://checkout.google.com/schema/2}ShoppingCart"/>
 *         &lt;element name="buyer-id" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="buyer-language" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="calculate" type="{http://checkout.google.com/schema/2}Calculate"/>
 *       &lt;/all>
 *       &lt;attribute name="serial-number" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MerchantCalculationCallback", namespace = "http://checkout.google.com/schema/2", propOrder = {

})
public class MerchantCalculationCallback {

    @XmlElement(name = "shopping-cart", namespace = "http://checkout.google.com/schema/2", required = true)
    protected ShoppingCart shoppingCart;
    @XmlElement(name = "buyer-id", namespace = "http://checkout.google.com/schema/2")
    protected Long buyerId;
    @XmlElement(name = "buyer-language", namespace = "http://checkout.google.com/schema/2", required = true)
    protected String buyerLanguage;
    @XmlElement(namespace = "http://checkout.google.com/schema/2", required = true)
    protected Calculate calculate;
    @XmlAttribute(name = "serial-number", required = true)
    protected String serialNumber;

    /**
     * Gets the value of the shoppingCart property.
     * 
     * @return
     *     possible object is
     *     {@link ShoppingCart }
     *     
     */
    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    /**
     * Sets the value of the shoppingCart property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShoppingCart }
     *     
     */
    public void setShoppingCart(ShoppingCart value) {
        this.shoppingCart = value;
    }

    /**
     * Gets the value of the buyerId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getBuyerId() {
        return buyerId;
    }

    /**
     * Sets the value of the buyerId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setBuyerId(Long value) {
        this.buyerId = value;
    }

    /**
     * Gets the value of the buyerLanguage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBuyerLanguage() {
        return buyerLanguage;
    }

    /**
     * Sets the value of the buyerLanguage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBuyerLanguage(String value) {
        this.buyerLanguage = value;
    }

    /**
     * Gets the value of the calculate property.
     * 
     * @return
     *     possible object is
     *     {@link Calculate }
     *     
     */
    public Calculate getCalculate() {
        return calculate;
    }

    /**
     * Sets the value of the calculate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Calculate }
     *     
     */
    public void setCalculate(Calculate value) {
        this.calculate = value;
    }

    /**
     * Gets the value of the serialNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * Sets the value of the serialNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSerialNumber(String value) {
        this.serialNumber = value;
    }


    public javax.xml.bind.JAXBElement<MerchantCalculationCallback> toJAXB() {
      return com.google.checkout.sdk.util.Utils.objectFactory().createMerchantCalculationCallback(this);
    }

    @Override
    public String toString() {
      return com.google.checkout.sdk.util.Utils.toXML(toJAXB());
    }
}
