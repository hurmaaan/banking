```
 /$$$$$$$                      /$$       /$$                                                               
| $$__  $$                    | $$      |__/                                                               
| $$  \ $$  /$$$$$$  /$$$$$$$ | $$   /$$ /$$ /$$$$$$$   /$$$$$$                                            
| $$$$$$$  |____  $$| $$__  $$| $$  /$$/| $$| $$__  $$ /$$__  $$                                           
| $$__  $$  /$$$$$$$| $$  \ $$| $$$$$$/ | $$| $$  \ $$| $$  \ $$                                           
| $$  \ $$ /$$__  $$| $$  | $$| $$_  $$ | $$| $$  | $$| $$  | $$                                           
| $$$$$$$/|  $$$$$$$| $$  | $$| $$ \  $$| $$| $$  | $$|  $$$$$$$                                           
|_______/  \_______/|__/  |__/|__/  \__/|__/|__/  |__/ \____  $$                                           
                                                       /$$  \ $$                                           
                                                      |  $$$$$$/                                           
                                                       \______/                                            
 /$$      /$$                                                                                       /$$    
| $$$    /$$$                                                                                      | $$    
| $$$$  /$$$$  /$$$$$$  /$$$$$$$   /$$$$$$   /$$$$$$   /$$$$$$  /$$$$$$/$$$$   /$$$$$$  /$$$$$$$  /$$$$$$  
| $$ $$/$$ $$ |____  $$| $$__  $$ |____  $$ /$$__  $$ /$$__  $$| $$_  $$_  $$ /$$__  $$| $$__  $$|_  $$_/  
| $$  $$$| $$  /$$$$$$$| $$  \ $$  /$$$$$$$| $$  \ $$| $$$$$$$$| $$ \ $$ \ $$| $$$$$$$$| $$  \ $$  | $$    
| $$\  $ | $$ /$$__  $$| $$  | $$ /$$__  $$| $$  | $$| $$_____/| $$ | $$ | $$| $$_____/| $$  | $$  | $$ /$$
| $$ \/  | $$|  $$$$$$$| $$  | $$|  $$$$$$$|  $$$$$$$|  $$$$$$$| $$ | $$ | $$|  $$$$$$$| $$  | $$  |  $$$$/
|__/     |__/ \_______/|__/  |__/ \_______/ \____  $$ \_______/|__/ |__/ |__/ \_______/|__/  |__/   \___/  
                                            /$$  \ $$                                                      
                                           |  $$$$$$/                                                      
                                            \______/                                                       
  /$$$$$$                        /$$                                                                       
 /$$__  $$                      | $$                                                                       
| $$  \__/ /$$   /$$  /$$$$$$$ /$$$$$$    /$$$$$$  /$$$$$$/$$$$                                            
|  $$$$$$ | $$  | $$ /$$_____/|_  $$_/   /$$__  $$| $$_  $$_  $$                                           
 \____  $$| $$  | $$|  $$$$$$   | $$    | $$$$$$$$| $$ \ $$ \ $$                                           
 /$$  \ $$| $$  | $$ \____  $$  | $$ /$$| $$_____/| $$ | $$ | $$                                           
|  $$$$$$/|  $$$$$$$ /$$$$$$$/  |  $$$$/|  $$$$$$$| $$ | $$ | $$                                           
 \______/  \____  $$|_______/    \___/   \_______/|__/ |__/ |__/                                           
           /$$  | $$                                                                                       
          |  $$$$$$/                                                                                       
           \______/                                                                                        






```

# Banking Management System

## Overview

The Banking Management System is a simple command-line application designed to streamline banking operations. It allows users to create accounts, deposit and withdraw funds, apply for loans, and check account balances. The application supports three distinct roles: Manager, User, and Employee, each with specific functionalities to enhance the banking experience.

## Roles

- **Manager**: 
  - Can add and remove employees.

- **Client**: 
  - Can deposit and withdraw funds, check account balances, and apply for loans.
  - Limited to managing their own account.

- **Employee**: 
  - Register new users.
  - Opening/closing accounts.
  - approving/rejecting loan applications

## Features

- **User Account Management**: Create and manage customer accounts with essential details.
- **Transactions**: Perform deposits and withdrawals with real-time balance updates.
- **Account Inquiry**: Check account balance and transaction history.
- **Loan Management**: Users can apply for loans and manage repayments.
- **Role-Based Access**: Different functionalities based on user roles (Manager, User, Employee).


## Loan Functionality

- **Apply for a Loan**: Users can apply for various loan types (e.g., personal, home, auto) with specified amounts and terms.
- **Loan Approval Process**: Employees can review and approve or deny loan applications.
- **Repayment Management**: Users can manage repayments through the application.

## Technologies Used

- **Java**: The primary programming language used for the application.
- **Object-Oriented Programming**: Utilizes OOP principles for better code organization and reusability.

## Usage

# Running the app

## Prerequisites

1. **Java Installed**: Ensure you have Java installed on your machine. You can check this by opening a terminal (Command Prompt on Windows or Terminal on macOS) and typing:

   java -version


   If Java is installed, you will see the version number. If not, download and install the latest version from t(https://www.java.com/en/download/).

## Running the JAR File

### On Windows

1. **Open Command Prompt**:
   - Press `Win + R`, type `cmd`, and hit `Enter`.

2. **Navigate to the Directory**:
   Use the `cd` command to navigate to the release folder

3. **Run the JAR File**:
   Use the following command to run the JAR file:

   ```bash
   java -jar BankingManagementSystem.jar
   ```

### On macOS

1. **Open Terminal**:
   - You can find Terminal in `Applications > Utilities`, or search for it using Spotlight (`Cmd + Space`).

2. **Navigate to the Directory**:
   Use the `cd` command to navigate to the release folder=.


3. **Run the JAR File**:
   Use the following command to run the JAR file:

   ```bash
   java -jar BankingManagementSystem.jar
   ```


