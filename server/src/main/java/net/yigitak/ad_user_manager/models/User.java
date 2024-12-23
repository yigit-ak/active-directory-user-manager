package net.yigitak.ad_user_manager.models;

import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

import javax.naming.Name;

@Entry(objectClasses = {"top", "person", "organizationalPerson", "user"})
public class User {

    @Id
    private Name dn;

    @Attribute(name = "ou")
    private String organizationalUnit;

    @Attribute(name = "cn")
    private String commonName;

    @Attribute(name = "description")
    private String description;

    @Attribute(name = "sAMAccountName")
    private String samAccountName;

    @Attribute(name = "displayName")
    private String displayName;

    @Attribute(name = "givenName")
    private String givenName;

    @Attribute(name = "mail")
    private String email;

    @Attribute(name = "sn")
    private String surname;

    @Attribute(name = "telephoneNumber")
    private String telephoneNumber;

    @Attribute(name = "userPassword")
    private String userPassword;

    @Attribute(name = "userPrincipalName")
    private String userPrincipalName;

    // Getters and setters
    public Name getDn() {
        return dn;
    }

    public void setDn(Name dn) {
        this.dn = dn;
    }

    public String getOrganizationalUnit() {
        return organizationalUnit;
    }

    public void setOrganizationalUnit(String organizationalUnit) {
        this.organizationalUnit = organizationalUnit;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSamAccountName() {
        return samAccountName;
    }

    public void setSamAccountName(String samAccountName) {
        this.samAccountName = samAccountName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserPrincipalName() {
        return userPrincipalName;
    }

    public void setUserPrincipalName(String userPrincipalName) {
        this.userPrincipalName = userPrincipalName;
    }
}
