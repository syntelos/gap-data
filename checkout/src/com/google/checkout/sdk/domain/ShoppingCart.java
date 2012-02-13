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
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ShoppingCart complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ShoppingCart">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="merchant-private-data" type="{http://checkout.google.com/schema/2}anyMultiple" minOccurs="0"/>
 *         &lt;element name="cart-expiration" type="{http://checkout.google.com/schema/2}CartExpiration" minOccurs="0"/>
 *         &lt;element name="items">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="item" type="{http://checkout.google.com/schema/2}Item" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="buyer-messages" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;choice maxOccurs="unbounded" minOccurs="0">
 *                     &lt;element name="gift-message" type="{http://checkout.google.com/schema/2}GiftMessage"/>
 *                     &lt;element name="include-gift-receipt" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                     &lt;element name="delivery-instructions" type="{http://checkout.google.com/schema/2}DeliveryInstructions"/>
 *                     &lt;element name="special-instructions" type="{http://checkout.google.com/schema/2}SpecialInstructions"/>
 *                     &lt;element name="special-requests" type="{http://checkout.google.com/schema/2}SpecialRequests"/>
 *                     &lt;element name="in-honor-of-message" type="{http://checkout.google.com/schema/2}InHonorOfMessage"/>
 *                     &lt;element name="in-tribute-of-message" type="{http://checkout.google.com/schema/2}InTributeOfMessage"/>
 *                     &lt;element name="in-memory-of-message" type="{http://checkout.google.com/schema/2}InMemoryOfMessage"/>
 *                     &lt;element name="buyer-note" type="{http://checkout.google.com/schema/2}BuyerNote"/>
 *                   &lt;/choice>
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
@XmlType(name = "ShoppingCart", namespace = "http://checkout.google.com/schema/2", propOrder = {

})
public class ShoppingCart {

    @XmlElement(name = "merchant-private-data", namespace = "http://checkout.google.com/schema/2")
    protected AnyMultiple merchantPrivateData;
    @XmlElement(name = "cart-expiration", namespace = "http://checkout.google.com/schema/2")
    protected CartExpiration cartExpiration;
    @XmlElement(namespace = "http://checkout.google.com/schema/2", required = true)
    protected ShoppingCart.Items items;
    @XmlElement(name = "buyer-messages", namespace = "http://checkout.google.com/schema/2")
    protected ShoppingCart.BuyerMessages buyerMessages;

    /**
     * Gets the value of the merchantPrivateData property.
     * 
     * @return
     *     possible object is
     *     {@link AnyMultiple }
     *     
     */
    public AnyMultiple getMerchantPrivateData() {
        return merchantPrivateData;
    }

    /**
     * Sets the value of the merchantPrivateData property.
     * 
     * @param value
     *     allowed object is
     *     {@link AnyMultiple }
     *     
     */
    public void setMerchantPrivateData(AnyMultiple value) {
        this.merchantPrivateData = value;
    }

    /**
     * Gets the value of the cartExpiration property.
     * 
     * @return
     *     possible object is
     *     {@link CartExpiration }
     *     
     */
    public CartExpiration getCartExpiration() {
        return cartExpiration;
    }

    /**
     * Sets the value of the cartExpiration property.
     * 
     * @param value
     *     allowed object is
     *     {@link CartExpiration }
     *     
     */
    public void setCartExpiration(CartExpiration value) {
        this.cartExpiration = value;
    }

    /**
     * Gets the value of the items property.
     * 
     * @return
     *     possible object is
     *     {@link ShoppingCart.Items }
     *     
     */
    public ShoppingCart.Items getItems() {
        return items;
    }

    /**
     * Sets the value of the items property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShoppingCart.Items }
     *     
     */
    public void setItems(ShoppingCart.Items value) {
        this.items = value;
    }

    /**
     * Gets the value of the buyerMessages property.
     * 
     * @return
     *     possible object is
     *     {@link ShoppingCart.BuyerMessages }
     *     
     */
    public ShoppingCart.BuyerMessages getBuyerMessages() {
        return buyerMessages;
    }

    /**
     * Sets the value of the buyerMessages property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShoppingCart.BuyerMessages }
     *     
     */
    public void setBuyerMessages(ShoppingCart.BuyerMessages value) {
        this.buyerMessages = value;
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
     *         &lt;choice maxOccurs="unbounded" minOccurs="0">
     *           &lt;element name="gift-message" type="{http://checkout.google.com/schema/2}GiftMessage"/>
     *           &lt;element name="include-gift-receipt" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *           &lt;element name="delivery-instructions" type="{http://checkout.google.com/schema/2}DeliveryInstructions"/>
     *           &lt;element name="special-instructions" type="{http://checkout.google.com/schema/2}SpecialInstructions"/>
     *           &lt;element name="special-requests" type="{http://checkout.google.com/schema/2}SpecialRequests"/>
     *           &lt;element name="in-honor-of-message" type="{http://checkout.google.com/schema/2}InHonorOfMessage"/>
     *           &lt;element name="in-tribute-of-message" type="{http://checkout.google.com/schema/2}InTributeOfMessage"/>
     *           &lt;element name="in-memory-of-message" type="{http://checkout.google.com/schema/2}InMemoryOfMessage"/>
     *           &lt;element name="buyer-note" type="{http://checkout.google.com/schema/2}BuyerNote"/>
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
        "allBuyerMessages"
    })
    public static class BuyerMessages {

        @XmlElements({
            @XmlElement(name = "in-honor-of-message", namespace = "http://checkout.google.com/schema/2", type = InHonorOfMessage.class),
            @XmlElement(name = "special-instructions", namespace = "http://checkout.google.com/schema/2", type = SpecialInstructions.class),
            @XmlElement(name = "gift-message", namespace = "http://checkout.google.com/schema/2", type = GiftMessage.class),
            @XmlElement(name = "in-memory-of-message", namespace = "http://checkout.google.com/schema/2", type = InMemoryOfMessage.class),
            @XmlElement(name = "include-gift-receipt", namespace = "http://checkout.google.com/schema/2", type = String.class),
            @XmlElement(name = "in-tribute-of-message", namespace = "http://checkout.google.com/schema/2", type = InTributeOfMessage.class),
            @XmlElement(name = "buyer-note", namespace = "http://checkout.google.com/schema/2", type = BuyerNote.class),
            @XmlElement(name = "special-requests", namespace = "http://checkout.google.com/schema/2", type = SpecialRequests.class),
            @XmlElement(name = "delivery-instructions", namespace = "http://checkout.google.com/schema/2", type = DeliveryInstructions.class)
        })
        protected List<Object> allBuyerMessages;

        /**
         * Gets the value of the allBuyerMessages property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the allBuyerMessages property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAllBuyerMessages().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link InHonorOfMessage }
         * {@link SpecialInstructions }
         * {@link GiftMessage }
         * {@link InMemoryOfMessage }
         * {@link String }
         * {@link InTributeOfMessage }
         * {@link BuyerNote }
         * {@link SpecialRequests }
         * {@link DeliveryInstructions }
         * 
         * 
         */
        public List<Object> getAllBuyerMessages() {
            if (allBuyerMessages == null) {
                allBuyerMessages = new ArrayList<Object>();
            }
            return this.allBuyerMessages;
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
     *       &lt;sequence>
     *         &lt;element name="item" type="{http://checkout.google.com/schema/2}Item" maxOccurs="unbounded" minOccurs="0"/>
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
        "item"
    })
    public static class Items {

        @XmlElement(namespace = "http://checkout.google.com/schema/2")
        protected List<Item> item;

        /**
         * Gets the value of the item property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the item property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getItem().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Item }
         * 
         * 
         */
        public List<Item> getItem() {
            if (item == null) {
                item = new ArrayList<Item>();
            }
            return this.item;
        }

    }

}
