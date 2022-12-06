package com.exalt.estate.dto;

import java.util.List;

/**
 * The type OwnerDTO is the owner data transfer object.
 * It is the object that's used between service layer and controller layer.
 */
public class OwnerDTO {

    private String userName;
    private String firstName;
    private String lastName;
    private Long balance;
    private List<PropertyDTO> properties;
    /**
     * Instantiates a new Owner dto.
     */
    public OwnerDTO() {
    }

    public OwnerDTO(String userName, String firstName, String lastName, Long balance) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.balance = balance;
    }
    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUserName() {
        return userName;
    }
    /**
     * Sets username.
     *
     * @param userName the username
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }
    /**
     * Sets first name.
     *
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }
    /**
     * Sets last name.
     *
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    /**
     * Gets balance.
     *
     * @return the balance
     */
    public Long getBalance() {
        return balance;
    }

    /**
     * Sets balance.
     *
     * @param balance the balance
     */
    public void setBalance(Long balance) {
        this.balance = balance;
    }
    /**
     * Gets owned properties.
     *
     * @return the owned properties
     */
    public List<PropertyDTO> getProperties() {
        return properties;
    }
    /**
     * Set owned properties.
     * This attribute is used for presenting the properties of the owner on rest calls.
     *
     * @param properties the properties
     */
    public void setProperties(List<PropertyDTO> properties) {
        this.properties = properties;
    }
}

