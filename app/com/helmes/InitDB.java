package com.helmes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

public class InitDB {
    private static final String CREATE_SECTORS_SQL =
            "CREATE TABLE IF NOT EXISTS Category (            \n" +
            "  id BIGINT not null auto_increment primary key, \n" +
            "  parent_id BIGINT,                              \n" +
            "  name varchar(128)                              \n" +
            ")                                                \n";
    
    private static final ArrayList<Category> categoriesData = new ArrayList<Category>(
        Arrays.asList(
            new Category(1, null, "Manufacturing"),
            new Category(19, 1, "Construction materials"),
            new Category(18, 1, "Electronics and Optics"),

            new Category(6, 1, "Food and Beverage"),
            new Category(342, 6, "Bakery & confectionery products"),
            new Category(43, 6, "Beverages"),
            new Category(42, 6, "Fish & fish products"),
            new Category(40, 6, "Meat & meat products"),
            new Category(39, 6, "Milk & dairy products"),
            new Category(378, 6, "Sweets & snack food"),
            new Category(437, 6, "Other"),

            new Category(13, 1, "Furniture"),
            new Category(389, 13, "Bathroom/sauna"),
            new Category(385, 13, "Bedroom"),
            new Category(390, 13, "Children’s room"),
            new Category(98, 13, "Kitchen"),
            new Category(101, 13, "Living room"),
            new Category(392, 13, "Office"),
            new Category(394, 13, "Other (Furniture)"),
            new Category(341, 13, "Outdoor"),
            new Category(99, 13, "Project furniture"),

            new Category(12, 1, "Machinery"),
            new Category(94, 12, "Machinery components"),
            new Category(91, 12, "Machinery equipment/tools"),
            new Category(224, 12, "Manufacture of machinery"),
            
            new Category(97, 12, "Maritime"),
            new Category(271, 97, "Aluminium and steel workboats"),
            new Category(269, 97, "Boat/Yacht building"),
            new Category(230, 97, "Ship repair and conversion"),

            new Category(93, 12, "Metal structures"),
            new Category(508, 12, "Other"),            
            new Category(227, 12, "Repair and maintenance service"),

            new Category(11, 1, "Metalworking"),
            new Category(67, 11, "Construction of metal structures"),
            new Category(263, 11, "Houses and buildings"),
            new Category(267, 11, "Metal products"),

            new Category(542, 11, "Metal works"),
            new Category(75, 542, "CNC-machining"),
            new Category(62, 542, "Forgings, Fasteners"),
            new Category(69, 542, "Gas, Plasma, Laser cutting"),
            new Category(66, 542, "MIG, TIG, Aluminum welding"),

            new Category(9, 1, "Plastic and Rubber"),
            new Category(54, 9, "Packaging"),
            new Category(556, 9, "Plastic goods"),

            new Category(559, 9, "Plastic processing technology"),
            new Category(55, 559, "Blowing"),
            new Category(57, 559, "Moulding"),
            new Category(53, 559, "Plastics welding and processing"),

            new Category(560, 9, "Plastic profiles"),

            new Category(5, 1, "Printing"),
            new Category(148, 5, "Advertising"),
            new Category(150, 5, "Book/Periodicals printing"),
            new Category(145, 5, "Labelling and packaging printing"),

            new Category(7, 1, "Textile and Clothing"),
            new Category(44, 7, "Clothing"),
            new Category(45, 7, "Textile"),

            new Category(8, 1, "Wood"),
            new Category(337, 8, "Other (Wood)"),
            new Category(51, 8, "Wooden building materials"),
            new Category(47, 8, "Wooden houses"),

            new Category(3, null, "Other"),
            new Category(37, 3, "Creative industries"),
            new Category(29, 3, "Energy technology"),
            new Category(33, 3, "Environment"),

            new Category(2, null, "Service"),
            new Category(25, 2, "Business services"),
            new Category(35, 2, "Engineering"),

            new Category(28, 2, "Information Technology and Telecommunications"),
            new Category(581, 28, "Data processing, Web portals, E-marketing"),
            new Category(576, 28, "Programming, Consultancy"),
            new Category(121, 28, "Software, Hardware"),
            new Category(122, 28, "Telecommunications"),

            new Category(22, 2, "Tourism"),
            new Category(141, 2, "Translation services"),

            new Category(21, 2, "Transport and Logistics"),
            new Category(111, 21, "Air"),
            new Category(114, 21, "Rail"),
            new Category(112, 21, "Road"),
            new Category(113, 21, "Water")
        ));

    public static void createDB() {
        Connection dbConnection = null;
        Statement statement = null;
        try {
            dbConnection = ConnManager.getConnection();
            statement = dbConnection.createStatement();
            statement.execute(CREATE_SECTORS_SQL);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (dbConnection != null) {
                    dbConnection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void writeData() {
        Connection dbConnection = null;
        Statement statement = null;
        try {
            dbConnection = ConnManager.getConnection();
            for (Category c : categoriesData) {
                statement = dbConnection.createStatement();
                String insertDataSQL = 
                    "MERGE INTO Category (id, parent_id, name) " + 
                    "VALUES (" + c.getId() + "," +
                            "" + c.getParentId() + "," +
                            "'" + c.getName() + "')";
                statement.executeUpdate(insertDataSQL);
                System.out.println(c + " inserted.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (dbConnection != null) {
                    dbConnection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static ArrayList<String> readAllData() {
        Connection dbConnection = null;
        Statement statement = null;

        String readDataSQL = "SELECT * FROM Log";
        ArrayList<String> data = new ArrayList<>();

        try {
            dbConnection = ConnManager.getConnection();
            statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(readDataSQL);
            while (resultSet.next()) {
                data.add(String.format("id: %4d -- data: %s",
                                resultSet.getLong("id"),
                                resultSet.getString("data")));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (dbConnection != null) {
                    dbConnection.close();
                }
            }  catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return data;
    }
}
