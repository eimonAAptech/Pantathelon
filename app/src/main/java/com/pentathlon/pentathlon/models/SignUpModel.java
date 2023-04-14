package com.pentathlon.pentathlon.models;

import com.google.gson.annotations.SerializedName;

public class SignUpModel {
    @SerializedName("customer")
    private Customer customer;
    @SerializedName("password")
    private String password;

    public SignUpModel(Customer customer, String password) {
        this.customer = customer;
        this.password = password;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getPassword() {
        return password;
    }

    public static class Customer {
        @SerializedName("email")
        private String email;
        @SerializedName("firstname")
        private String firstName;
        @SerializedName("lastname")
        private String lastName;
        @SerializedName("extension_attributes")
        private ExtensionAttributes extensionAttributes;

        public Customer(String email, String firstName, String lastName, ExtensionAttributes extensionAttributes) {
            this.email = email;
            this.firstName = firstName;
            this.lastName = lastName;
            this.extensionAttributes = extensionAttributes;
        }

        public String getEmail() {
            return email;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public ExtensionAttributes getExtensionAttributes() {
            return extensionAttributes;
        }
    }

    public static class ExtensionAttributes {
        @SerializedName("mobile")
        private String mobile;

        public ExtensionAttributes(String mobile) {
            this.mobile = mobile;
        }

        public String getMobile() {
            return mobile;
        }
    }
}

