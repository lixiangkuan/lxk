
package com.example.lxk.demolxk.lxk;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="saveData_jzghResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "saveDataJzghResult"
})
@XmlRootElement(name = "saveData_jzghResponse")
public class SaveDataJzghResponse {

    @XmlElement(name = "saveData_jzghResult")
    protected String saveDataJzghResult;

    /**
     * Gets the value of the saveDataJzghResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSaveDataJzghResult() {
        return saveDataJzghResult;
    }

    /**
     * Sets the value of the saveDataJzghResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSaveDataJzghResult(String value) {
        this.saveDataJzghResult = value;
    }

}
