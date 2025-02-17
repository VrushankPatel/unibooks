# Unibooks

Unibooks is a comprehensive accounting application designed to manage customers, vendors, invoices, and expenses efficiently. It provides a user-friendly interface for small to medium-sized businesses to keep track of their financial transactions.

## Features

- **Customer Management**: Add, edit, and manage customer information.
- **Vendor Management**: Add, edit, and manage vendor information.
- **Invoice Management**: Create and manage invoices for customers.
- **Expense Tracking**: Record and track business expenses.
- **Reporting**: Generate reports for invoices and expenses.
- **Theme Support**: Switch between light and dark themes.

## Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/yourusername/unibooks.git
   cd unibooks
   ```

2. **Build the project**:
   ```bash
   mvn clean install
   ```

## Running the Application

### Using Maven

```bash
mvn exec:java -Dexec.mainClass="com.omexy.unibooks.MainFrame"
```

### Using Scripts

#### Bash (run.sh)

```bash
#!/bin/bash
mvn exec:java -Dexec.mainClass="com.omexy.unibooks.MainFrame"
```

#### Batch (run.bat)

```bat
@echo off
mvn exec:java -Dexec.mainClass="com.omexy.unibooks.MainFrame"
```

## Country Codes

Unibooks supports phone number entry with country codes. The application includes a comprehensive list of country codes sourced from [CountryCode.org](https://countrycode.org/).

## License

This project is licensed under the MIT License. 