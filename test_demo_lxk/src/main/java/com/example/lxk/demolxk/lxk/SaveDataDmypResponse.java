
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
 *         &lt;element name="saveData_DmypResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "saveDataDmypResult"
})
@XmlRootElement(name = "saveData_DmypResponse")
public class SaveDataDmypResponse {

    @XmlElement(name = "saveData_DmypResult")
    protected String saveDataDmypResult;

    /**
     * Gets the value of the saveDataDmypResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSaveDataDmypResult() {
        return saveDataDmypResult;
    }

    /**
     * Sets the value of the saveDataDmypResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSaveDataDmypResult(String value) {
        this.saveDataDmypResult = value;
    }

}
