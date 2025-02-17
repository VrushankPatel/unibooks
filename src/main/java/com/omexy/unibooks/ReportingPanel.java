package com.omexy.unibooks;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ReportingPanel extends JPanel {

    private InvoicePanel invoicePanel;
    private ExpensePanel expensePanel;
    private JTable invoiceReportTable;
    private JTable expenseReportTable;
    private DefaultTableModel invoiceTableModel;
    private DefaultTableModel expenseTableModel;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

    public ReportingPanel(InvoicePanel invoicePanel, ExpensePanel expensePanel) {
        this.invoicePanel = invoicePanel;
        this.expensePanel = expensePanel;

        setLayout(new BorderLayout());

        JPanel summaryPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel reportDescriptionLabel = new JLabel("Financial Reports");
        reportDescriptionLabel.setFont(new Font("Arial", Font.BOLD, 18));
        JButton generateReportButton = new JButton("Generate Summary Report");
        summaryPanel.add(reportDescriptionLabel);
        summaryPanel.add(generateReportButton);

        // Invoice Report Table Setup
        String[] invoiceColumnNames = {"Invoice No.", "Customer", "Amount", "Date", "Description"};
        invoiceTableModel = new DefaultTableModel(invoiceColumnNames, 0);
        invoiceReportTable = new JTable(invoiceTableModel);
        JScrollPane invoiceScrollPane = new JScrollPane(invoiceReportTable);
        invoiceScrollPane.setBorder(BorderFactory.createTitledBorder("Invoices"));

        // Expense Report Table Setup
        String[] expenseColumnNames = {"Vendor", "Category", "Amount", "Date", "Notes"};
        expenseTableModel = new DefaultTableModel(expenseColumnNames, 0);
        expenseReportTable = new JTable(expenseTableModel);
        JScrollPane expenseScrollPane = new JScrollPane(expenseReportTable);
        expenseScrollPane.setBorder(BorderFactory.createTitledBorder("Expenses"));

        generateReportButton.addActionListener(e -> {
            generateSummaryReport();
            updateReportTables();
        });

        JPanel tablesPanel = new JPanel(new GridLayout(2, 1));
        tablesPanel.add(invoiceScrollPane);
        tablesPanel.add(expenseScrollPane);

        add(summaryPanel, BorderLayout.NORTH);
        add(tablesPanel, BorderLayout.CENTER);

        updateReportTables();
    }

    private void generateSummaryReport() {
        List<Invoice> invoices = invoicePanel.getInvoices();
        List<Expense> expenses = expensePanel.getExpenses();

        double totalInvoiced = 0;
        for (Invoice invoice : invoices) {
            totalInvoiced += invoice.getAmount();
        }

        double totalExpenses = 0;
        for (Expense expense : expenses) {
            totalExpenses += expense.getAmount();
        }

        double netProfit = totalInvoiced - totalExpenses;

        JOptionPane.showMessageDialog(this,
                "Total Invoiced: $" + String.format("%.2f", totalInvoiced) +
                "\nTotal Expenses: $" + String.format("%.2f", totalExpenses) +
                "\nNet Profit/Loss: $" + String.format("%.2f", netProfit),
                "Financial Summary", JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateReportTables() {
        invoiceTableModel.setRowCount(0);
        expenseTableModel.setRowCount(0);

        for (Invoice invoice : invoicePanel.getInvoices()) {
            Object[] rowData = {invoice.getInvoiceNumber(), invoice.getCustomer(), invoice.getAmount(), dateFormat.format(invoice.getInvoiceDate()), invoice.getDescription()};
            invoiceTableModel.addRow(rowData);
        }

        for (Expense expense : expensePanel.getExpenses()) {
            Object[] rowData = {expense.getVendor(), expense.getCategory(), expense.getAmount(), dateFormat.format(expense.getExpenseDate()), expense.getNotes()};
            expenseTableModel.addRow(rowData);
        }
    }
} 