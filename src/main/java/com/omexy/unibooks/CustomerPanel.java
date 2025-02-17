package com.omexy.unibooks;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Panel for managing customer information.
 * Allows adding, displaying, and validating customer details.
 * 
 * @author VrushankPatel
 * @purpose Manage customer data in the application.
 */
public class CustomerPanel extends JPanel {

    private JTextField nameField;
    private JTextField emailField;
    private JComboBox<String> countryCodeComboBox;
    private JTextField phoneField;
    private JTextArea addressField;
    private JTable customerTable;
    private DefaultTableModel tableModel;
    private List<Customer> customers = new ArrayList<>();
    private List<CustomerListener> listeners = new ArrayList<>();
    private int customerCounter = 1;

    public CustomerPanel() {
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField(15);
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(15);
        JLabel phoneLabel = new JLabel("Phone:");
        
        String[] countryCodes = {
            "+93", "+355", "+213", "+1-684", "+376", "+244", "+1-264", "+672", "+1-268", "+54", "+374", "+297", "+61", "+43", "+994",
            "+1-242", "+973", "+880", "+1-246", "+375", "+32", "+501", "+229", "+1-441", "+975", "+591", "+387", "+267", "+55", "+246",
            "+1-284", "+673", "+359", "+226", "+257", "+855", "+237", "+1", "+238", "+1-345", "+236", "+235", "+56", "+86", "+61", "+61",
            "+57", "+269", "+682", "+506", "+385", "+53", "+599", "+357", "+420", "+243", "+45", "+253", "+1-767", "+1-809", "+670", "+593",
            "+20", "+503", "+240", "+291", "+372", "+251", "+500", "+298", "+679", "+358", "+33", "+689", "+241", "+220", "+995", "+49",
            "+233", "+350", "+30", "+299", "+1-473", "+1-671", "+502", "+44-1481", "+224", "+245", "+592", "+509", "+504", "+852", "+36",
            "+354", "+91", "+62", "+98", "+964", "+353", "+44-1624", "+972", "+39", "+225", "+1-876", "+81", "+44-1534", "+962", "+7",
            "+254", "+686", "+383", "+965", "+996", "+856", "+371", "+961", "+266", "+231", "+218", "+423", "+370", "+352", "+853", "+389",
            "+261", "+265", "+60", "+960", "+223", "+356", "+692", "+222", "+230", "+262", "+52", "+691", "+373", "+377", "+976", "+382",
            "+1-664", "+212", "+258", "+95", "+264", "+674", "+977", "+31", "+599", "+687", "+64", "+505", "+227", "+234", "+683", "+850",
            "+1-670", "+47", "+968", "+92", "+680", "+970", "+507", "+675", "+595", "+51", "+63", "+64", "+48", "+351", "+1-787", "+974",
            "+242", "+262", "+40", "+7", "+250", "+590", "+290", "+1-869", "+1-758", "+590", "+508", "+1-784", "+685", "+378", "+239",
            "+966", "+221", "+381", "+248", "+232", "+65", "+1-721", "+421", "+386", "+677", "+252", "+27", "+82", "+211", "+34", "+94",
            "+249", "+597", "+47", "+268", "+46", "+41", "+963", "+886", "+992", "+255", "+66", "+228", "+690", "+676", "+1-868", "+216",
            "+90", "+993", "+1-649", "+688", "+1-340", "+256", "+380", "+971", "+44", "+1", "+598", "+998", "+678", "+379", "+58", "+84",
            "+681", "+212", "+967", "+260", "+263"
        };
        
        countryCodeComboBox = new JComboBox<>(countryCodes);
        phoneField = new JTextField(12);
        JLabel addressLabel = new JLabel("Address:");
        addressField = new JTextArea(3, 20);
        JScrollPane addressScrollPane = new JScrollPane(addressField);
        JButton createCustomerButton = new JButton("Create Customer");

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(nameLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(emailLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(phoneLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(countryCodeComboBox, gbc);
        gbc.gridx = 2;
        inputPanel.add(phoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(addressLabel, gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        inputPanel.add(addressScrollPane, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        inputPanel.add(createCustomerButton, gbc);

        String[] columnNames = {"Customer ID", "Name", "Email", "Phone", "Address"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0;
            }
        };
        customerTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(customerTable);

        createCustomerButton.addActionListener(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String countryCode = (String) countryCodeComboBox.getSelectedItem();
            String phone = phoneField.getText();
            String address = addressField.getText();

            if (name.trim().isEmpty() || email.trim().isEmpty() || phone.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!isValidEmail(email)) {
                JOptionPane.showMessageDialog(this, "Invalid email format.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!isValidPhone(countryCode, phone)) {
                JOptionPane.showMessageDialog(this, "Invalid phone number format.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String customerId = generateCustomerId();
            Customer newCustomer = new Customer(customerId, name, email, countryCode + phone, address);
            customers.add(newCustomer);
            addCustomerToTable(newCustomer);
            clearInputFields();
            notifyListeners();
        });

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Generates a new customer ID in the format UNIC000000000001.
     * 
     * @return A formatted customer ID.
     */
    private String generateCustomerId() {
        return String.format("UNIC%015d", customerCounter++);
    }

    /**
     * Validates the email format.
     * 
     * @param email The email to validate.
     * @return True if the email is valid, false otherwise.
     */
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return Pattern.matches(emailRegex, email);
    }

    /**
     * Validates the phone number based on the country code.
     * 
     * @param countryCode The country code.
     * @param phone The phone number.
     * @return True if the phone number is valid, false otherwise.
     */
    private boolean isValidPhone(String countryCode, String phone) {
        if (countryCode.equals("+91")) {
            return phone.matches("\\d{10}");
        }
        return true;
    }

    /**
     * Adds a customer to the table.
     * 
     * @param customer The customer to add.
     */
    private void addCustomerToTable(Customer customer) {
        Object[] rowData = {customer.getCustomerId(), customer.getName(), customer.getEmail(), customer.getPhone(), customer.getAddress()};
        tableModel.addRow(rowData);
    }

    /**
     * Clears the input fields.
     */
    private void clearInputFields() {
        nameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        addressField.setText("");
    }

    /**
     * Gets the list of customers.
     * 
     * @return The list of customers.
     */
    public List<Customer> getCustomers() {
        return customers;
    }

    /**
     * Gets the list of customer names with IDs.
     * 
     * @return The list of customer names with IDs.
     */
    public List<String> getCustomerNames() {
        List<String> names = new ArrayList<>();
        for (Customer customer : customers) {
            names.add(customer.getName() + " - " + customer.getCustomerId());
        }
        return names;
    }

    /**
     * Adds a customer listener.
     * 
     * @param listener The listener to add.
     */
    public void addCustomerListener(CustomerListener listener) {
        listeners.add(listener);
    }

    /**
     * Notifies listeners of customer updates.
     */
    private void notifyListeners() {
        for (CustomerListener listener : listeners) {
            listener.customersUpdated(getCustomerNames());
        }
    }
} 