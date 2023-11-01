package com.school_management_system;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class School_Management_System {

    public static void main(String[] args) {
        String dbUrl = "jdbc:mysql://localhost:3306/school_management_system";
        String dbUser = "root";
        String dbPassword = "g00dluck";
        Scanner scanner = new Scanner(System.in);

        try {
            Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

            createSchoolTable(connection);

            while (true) {
                System.out.println("School Management System Menu:");
                System.out.println("1. Insert School Data");
                System.out.println("2. Delete School Data");
                System.out.println("3. Update School Data");
                System.out.println("4. View School Data");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); 

                switch (choice) {
                    case 1:
                        insertSchoolData(connection, scanner);
                        break;
                    case 2:
                        deleteSchoolData(connection, scanner);
                        break;
                    case 3:
                        updateSchoolData(connection, scanner);
                        break;
                    case 4:
                        viewSchoolData(connection);
                        break;
                    case 5:
                        connection.close();
                        System.out.println("Exiting the School Management System.");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createSchoolTable(Connection connection) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS school_manage (" +
                "school_id INT(10) PRIMARY KEY," +
                "school_name VARCHAR(255) NOT NULL," +
                "school_address VARCHAR(255) NOT NULL," +
                "school_overall_grade VARCHAR(10)," +
                "school_pin_code VARCHAR(10) NOT NULL" +
                ")";
        PreparedStatement statement = connection.prepareStatement(createTableSQL);
        statement.execute();
    }

    private static void insertSchoolData(Connection connection, Scanner scanner) {
        try {
            System.out.println("Enter school details:");
            System.out.print("School ID: ");
            int schoolId = scanner.nextInt();
            scanner.nextLine();
            System.out.print("School Name: ");
            String schoolName = scanner.nextLine();
            System.out.print("School Address: ");
            String schoolAddress = scanner.nextLine();
            System.out.print("School Overall Grade: ");
            String schoolOverallGrade = scanner.nextLine();
            System.out.print("School Pin Code: ");
            String schoolPinCode = scanner.nextLine();

            String insertSQL = "INSERT INTO school_manage (school_id, school_name, school_address, school_overall_grade, school_pin_code) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(insertSQL);
            statement.setInt(1, schoolId);
            statement.setString(2, schoolName);
            statement.setString(3, schoolAddress);
            statement.setString(4, schoolOverallGrade);
            statement.setString(5, schoolPinCode);
            statement.execute();
            System.out.println("School added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteSchoolData(Connection connection, Scanner scanner) {
        try {
            System.out.print("Enter the School ID to delete: ");
            int schoolId = scanner.nextInt();
            String deleteSQL = "DELETE FROM school_manage WHERE school_id = ?";
            PreparedStatement statement = connection.prepareStatement(deleteSQL);
            statement.setInt(1, schoolId);
            int deletedRows = statement.executeUpdate();
            if (deletedRows > 0) {
                System.out.println("School data with ID " + schoolId + " deleted successfully.");
            } else {
                System.out.println("No data found with School ID " + schoolId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateSchoolData(Connection connection, Scanner scanner) {
        try {
            System.out.print("Enter the School ID to update: ");
            int schoolId = scanner.nextInt();
            scanner.nextLine();
            System.out.print("New School Name: ");
            String newSchoolName = scanner.nextLine();
            System.out.print("New School Address: ");
            String newSchoolAddress = scanner.nextLine();
            System.out.print("New School Overall Grade: ");
            String newSchoolOverallGrade = scanner.nextLine();
            System.out.print("New School Pin Code: ");
            String newSchoolPinCode = scanner.nextLine();

            String updateSQL = "UPDATE school_manage SET school_name = ?, school_address = ?, school_overall_grade = ?, school_pin_code = ? WHERE school_id = ?";
            PreparedStatement statement = connection.prepareStatement(updateSQL);
            statement.setString(1, newSchoolName);
            statement.setString(2, newSchoolAddress);
            statement.setString(3, newSchoolOverallGrade);
            statement.setString(4, newSchoolPinCode);
            statement.setInt(5, schoolId);
            int updatedRows = statement.executeUpdate();
            if (updatedRows > 0) {
                System.out.println("School data with ID " + schoolId + " updated successfully.");
            } else {
                System.out.println("No data found with School ID " + schoolId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewSchoolData(Connection connection) {
        try {
            String selectSQL = "SELECT * FROM school_manage";
            PreparedStatement statement = connection.prepareStatement(selectSQL);
            ResultSet resultSet = statement.executeQuery();

            System.out.println("School Data:");
            System.out.println("+------+----------------------+----------------------+--------------+----------------+");
            System.out.printf("| %-4s | %-20s | %-20s | %-10s | %-14s|\n",
                    "ID", "School Name", "School Address", "Overall Grade", "Pin Code");
            System.out.println("+------+----------------------+----------------------+--------------+----------------+");

            while (resultSet.next()) {
                int schoolId = resultSet.getInt("school_id");
                String schoolName = resultSet.getString("school_name");
                String schoolAddress = resultSet.getString("school_address");
                String schoolOverallGrade = resultSet.getString("school_overall_grade");
                String schoolPinCode = resultSet.getString("school_pin_code");

                System.out.printf("| %-4s | %-20s | %-20s | %-10s    | %-14s|\n",
                        schoolId, schoolName, schoolAddress, schoolOverallGrade, schoolPinCode);
            }

            System.out.println("+------+----------------------+----------------------+--------------+----------------+");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

