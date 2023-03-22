package API;

import java.io.*;
import java.net.*;
import java.sql.*;

import com.google.gson.Gson;

public class apiTest {

  // global variables to store user input
  private static String databaseName;
  private static String dbUrl;
  private static String dbUser;
  private static String dbPassword;

  // main method to run the application
  public static void main(String[] args) {
    // initialize database
    initializeDatabase();

    // show menu options to user
    showMenuOptions();
  }

  // method to initialize the database
  private static void initializeDatabase() {
    // prompt user for database name
    System.out.println("Enter the name of the database: ");
    databaseName = getInputString();

    // prompt user for database credentials
    System.out.println("Enter database URL: ");
    dbUrl = getInputString();
    System.out.println("Enter database username: ");
    dbUser = getInputString();
    System.out.println("Enter database password: ");
    dbPassword = getInputString();

    try {
      // connect to the database
      Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

      // create tables if they don't exist
      Statement stmt = conn.createStatement();
      stmt.executeUpdate("CREATE TABLE IF NOT EXISTS countries (id INT PRIMARY KEY, name VARCHAR(50), capital VARCHAR(50), region VARCHAR(50))");
      stmt.close();

      // close the database connection
      conn.close();
    } catch (SQLException e) {
      System.out.println("Error initializing database: " + e.getMessage());
    }
  }

  // method to show menu options to the user
  private static void showMenuOptions() {
    int choice = 0;
    while (choice != 6) {
      // display menu options to user
      System.out.println("Choose an option:");
      System.out.println("1. Fetch data from API and insert into database");
      System.out.println("2. Fetch data from database and display");
      System.out.println("3. Backup database");
      System.out.println("4. Remove tables from database");
      System.out.println("5. Search data");
      System.out.println("6. Exit");

      // get user input
      choice = Integer.parseInt(getInputString());

      switch (choice) {
        case 1:
          fetchDataFromAPI();
          break;
        case 2:
          fetchDataFromDatabase();
          break;
        case 3:
          backupDatabase();
          break;
        case 4:
          removeTables();
          break;
        case 5:
          searchData();
          break;
        case 6:
          System.out.println("Exiting...");
          break;
        default:
          System.out.println("Invalid option.");
          break;
      }
    }
  }

  // method to fetch data from the API and insert into the database
  private static void fetchDataFromAPI() {
    try {
      // create URL object
      URL url = new URL("https://restcountries.com/v3.1/all");

      // open connection to the URL
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("GET");
      conn.setRequestProperty("Accept", "application/json");

      // read the response from the API
      BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
      String output;
      StringBuilder response = new StringBuilder();
      while ((output = br.readLine()) != null) {
        response.append(output);
      }

   // parse the JSON data
      Gson gson = new Gson();
      objectTest[] cont = gson.fromJson(json.toString(), objectTest[].class);    
      for (int i = 0; i < jsonArray.length(); i++) {
     

          // retrieve the values from the JSON object
          String name = jsonObject.getString("name");
          int age = jsonObject.getInt("age");
          String city = jsonObject.getString("city");

          // create a new person object with the retrieved values
          Person person = new Person(name, age, city);

          // add the person object to a list
          personList.add(person);
      }

      // print the list of people
      for (Person person : personList) {
          System.out.println(person.getName() + " is " + person.getAge() + " years old and lives in " + person.getCity());
      }

