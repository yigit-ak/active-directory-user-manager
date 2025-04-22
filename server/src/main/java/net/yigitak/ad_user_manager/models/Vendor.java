package net.yigitak.ad_user_manager.models;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.ldap.odm.annotations.DnAttribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

import javax.naming.Name;

@Entry(base = "OU=VPNUSERS,DC=yigit,DC=local", objectClasses = {"organizationalUnit"})
public final class Vendor {
    @Id
    private Name dn;  // The Distinguished Name (DN)

    @DnAttribute(value = "OU", index = 0)
    private String ou; // Organizational Unit name (Vendor Name)
}
