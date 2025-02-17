package com.omexy.unibooks.main;

import com.omexy.unibooks.ui.CustomerPanel;
import com.omexy.unibooks.ui.VendorPanel;
import com.omexy.unibooks.ui.InvoicePanel;
import com.omexy.unibooks.ui.ExpensePanel;
import com.omexy.unibooks.ui.ReportingPanel;
import com.omexy.unibooks.ui.TitleBarPanel;
import com.omexy.unibooks.ui.ThemeManager;
import com.omexy.unibooks.model.Invoice;
import com.omexy.unibooks.model.Expense;
import com.omexy.unibooks.eventhandler.CustomerListener;
import com.omexy.unibooks.eventhandler.VendorListener;


import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Date;

/**
 * Main application frame for Unibooks.
 * Manages the main window and tabbed panels.
 * 
 * @purpose Main application window.
 */
public class MainFrame extends JFrame {

    private JTabbedPane tabbedPane;
    private InvoicePanel invoicePanel;
    private ExpensePanel expensePanel;
    private CustomerPanel customerPanel;
    private VendorPanel vendorPanel;

    public MainFrame() {
        setTitle("Unibooks");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Apply default theme
        ThemeManager.applyTheme(ThemeManager.Theme.LIGHT);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        JMenuItem loadMenuItem = new JMenuItem("Load");

        saveMenuItem.addActionListener(e -> saveApplicationData());
        loadMenuItem.addActionListener(e -> loadApplicationData());

        fileMenu.add(saveMenuItem);
        fileMenu.add(loadMenuItem);
        menuBar.add(fileMenu);

        JMenu viewMenu = new JMenu("View");
        JToggleButton themeToggleButton = new JToggleButton("Dark Mode");
        themeToggleButton.addActionListener(e -> {
            if (themeToggleButton.isSelected()) {
                ThemeManager.applyTheme(ThemeManager.Theme.DARK);
            } else {
                ThemeManager.applyTheme(ThemeManager.Theme.LIGHT);
            }
        });
        viewMenu.add(themeToggleButton);
        menuBar.add(viewMenu);

        setJMenuBar(menuBar);

        tabbedPane = new JTabbedPane();

        JPanel welcomePanel = createWelcomePanel();
        tabbedPane.addTab("Welcome", welcomePanel);

        customerPanel = new CustomerPanel();
        vendorPanel = new VendorPanel();
        invoicePanel = new InvoicePanel();
        expensePanel = new ExpensePanel();
        ReportingPanel reportingPanel = new ReportingPanel(invoicePanel, expensePanel);

        tabbedPane.addTab("Customers", customerPanel);
        tabbedPane.addTab("Vendors", vendorPanel);
        tabbedPane.addTab("Invoices", invoicePanel);
        tabbedPane.addTab("Expenses", expensePanel);
        tabbedPane.addTab("Reporting", reportingPanel);

        customerPanel.addCustomerListener(invoicePanel);
        vendorPanel.addVendorListener(expensePanel);

        setLayout(new BorderLayout());
        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createWelcomePanel() {
        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        JLabel welcomeLabel = new JLabel("Welcome to Unibooks!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomePanel.add(welcomeLabel);
        return welcomePanel;
    }

    private void saveApplicationData() {
        try (PrintWriter writer = new PrintWriter("unibooks_data.txt")) {
            writer.println("Invoices:");
            for (Invoice invoice : invoicePanel.getInvoices()) {
                writer.println(invoice.getInvoiceNumber() + "," + invoice.getCustomer() + "," + invoice.getAmount() + "," + invoice.getInvoiceDate().getTime() + "," + invoice.getDescription());
            }
            writer.println("Expenses:");
            for (Expense expense : expensePanel.getExpenses()) {
                writer.println(expense.getVendor() + "," + expense.getCategory() + "," + expense.getAmount() + "," + expense.getExpenseDate().getTime() + "," + expense.getNotes());
            }
            JOptionPane.showMessageDialog(this, "Data saved to unibooks_data.txt", "Save Successful", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving data: " + ex.getMessage(), "Save Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadApplicationData() {
        try (BufferedReader reader = new BufferedReader(new FileReader("unibooks_data.txt"))) {
            invoicePanel.clearInvoices();
            expensePanel.clearExpenses();

            String line;
            String currentSection = "";

            while ((line = reader.readLine()) != null) {
                if (line.equals("Invoices:")) {
                    currentSection = "Invoices";
                    continue;
                } else if (line.equals("Expenses:")) {
                    currentSection = "Expenses";
                    continue;
                }

                if (currentSection.equals("Invoices") && !line.isEmpty()) {
                    String[] parts = line.split(",");
                    if (parts.length == 5) {
                        Invoice invoice = new Invoice(parts[0], parts[1], Double.parseDouble(parts[2]), new Date(Long.parseLong(parts[3])), parts[4]);
                        invoicePanel.addInvoice(invoice);
                    }
                } else if (currentSection.equals("Expenses") && !line.isEmpty()) {
                    String[] parts = line.split(",");
                    if (parts.length == 5) {
                        Expense expense = new Expense(parts[0], parts[1], Double.parseDouble(parts[2]), new Date(Long.parseLong(parts[3])), parts[4]);
                        expensePanel.addExpense(expense);
                    }
                }
            }
            JOptionPane.showMessageDialog(this, "Data loaded from unibooks_data.txt", "Load Successful", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "No data file found or error loading: " + ex.getMessage(), "Load Information", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException ex) {
            JOptionPane.showMessageDialog(this, "Data file corrupted or invalid format.", "Load Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
} 