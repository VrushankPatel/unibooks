package com.omexy.unibooks.model;

import java.util.Date;

public class Expense {
    private String vendor;
    private String category;
    private double amount;
    private Date expenseDate;
    private String notes;

    public Expense(String vendor, String category, double amount, Date expenseDate, String notes) {
        this.vendor = vendor;
        this.category = category;
        this.amount = amount;
        this.expenseDate = expenseDate;
        this.notes = notes;
    }

    // Getters and Setters
    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(Date expenseDate) {
        this.expenseDate = expenseDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Expense{" +
               "vendor='" + vendor + '\'' +
               ", category='" + category + '\'' +
               ", amount=" + amount +
               ", expenseDate=" + expenseDate +
               ", notes='" + notes + '\'' +
               '}';
    }
} 