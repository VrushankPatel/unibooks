package com.omexy.unibooks.model;

import java.util.Date;

public class Invoice {
    private String invoiceNumber;
    private String customer;
    private double amount;
    private Date invoiceDate;
    private String description;

    public Invoice(String invoiceNumber, String customer, double amount, Date invoiceDate, String description) {
        this.invoiceNumber = invoiceNumber;
        this.customer = customer;
        this.amount = amount;
        this.invoiceDate = invoiceDate;
        this.description = description;
    }

    // Getters and Setters
    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Invoice{" +
               "invoiceNumber='" + invoiceNumber + '\'' +
               ", customer='" + customer + '\'' +
               ", amount=" + amount +
               ", invoiceDate=" + invoiceDate +
               ", description='" + description + '\'' +
               '}';
    }
} 