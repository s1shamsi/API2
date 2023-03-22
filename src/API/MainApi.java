package API;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.google.gson.Gson;

public class MainApi {
	public static void main(String[] args) {

		 String url = "jdbc:sqlserver://localhost:1433;" +
	             "databaseName=Api;" +
	             "encrypt=true;" +
	             "trustServerCertificate=true";
      

       Scanner sc = new Scanner(System.in);
  
       String username = "";
       String sPassword = "";
       char[] cPassword;


       JTextField usernameField = new JTextField(50);
       JPasswordField passwordField = new JPasswordField(20);

       JPanel loginPanel = new JPanel();
       loginPanel.add(new JLabel("Username:"));
       loginPanel.add(usernameField);
       loginPanel.add(Box.createHorizontalStrut(15)); 
       loginPanel.add(new JLabel("Password:"));
       loginPanel.add(passwordField);

         int result = JOptionPane.showConfirmDialog(null, loginPanel, 
                  "Please Enter Username and Password", JOptionPane.OK_CANCEL_OPTION);

         if (result == JOptionPane.OK_OPTION) {
            username = usernameField.getText();
            cPassword = passwordField.getPassword();

            //password = passwordField.getText();

            for(int c = 0; c < cPassword.length; c++)
                sPassword = sPassword + cPassword[c];
   

           String apiUrl = "https://restcountries.com/v3.1/all";

           try {
           	
               URL url1 = new URL(apiUrl);
               
               HttpURLConnection conn1 = (HttpURLConnection) url1.openConnection();
               
               conn1.setRequestMethod("GET");
               conn1.setRequestProperty("Accept", "application/json");

               if (conn1.getResponseCode() != 200) {
                   throw new RuntimeException("HTTP error code : " + conn1.getResponseCode());
               }

               BufferedReader br = new BufferedReader(new InputStreamReader((conn1.getInputStream())));
               String output;
               StringBuilder json = new StringBuilder();

               while ((output = br.readLine()) != null) {
                   json.append(output);
               }

               Connection conn = DriverManager.getConnection(url, username, sPassword);
               Gson gson = new Gson();
               MyObject[] cont = gson.fromJson(json.toString(), MyObject[].class);               
               String insertSql = "INSERT INTO countries (name, officialName, nativeCommonName, nativeOfficialName, cca2, ccn3, cca3, cioc, independent, status, unMember, currencyName, currencySymbol, iddRoot, capital, region, subregion, translations, latlng, landlocked, area, flag, population, gini, fifa, car,  startOfWeek, capitalInfo, postalCode) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
          
               PreparedStatement statement = conn.prepareStatement(insertSql);
               for (MyObject Country : cont) {
            	   statement.setString(1, Country.getName());
            	    statement.setString(2, Country.getOfficialName());
            	    statement.setString(3, Country.getNativeCommonName());
            	    statement.setString(4, Country.getNativeOfficialName());
            	    statement.setString(5, Country.getCca2());
            	    statement.setString(6, Country.getCcn3());
            	    statement.setString(7, Country.getCca3());
            	    statement.setString(8, Country.getCioc());
            	    statement.setBoolean(9, Country.isIndependent());
            	    statement.setString(10, Country.getStatus());
            	    statement.setBoolean(11, Country.isUnMember());
            	    statement.setString(12, Country.getCurrencyName());
            	    statement.setString(13, Country.getCurrencySymbol());
            	    statement.setString(14, Country.getIddRoot());
            	    statement.setString(15, Country.getCapital());
            	
            	    statement.setString(17, Country.getRegion());
            	    statement.setString(18, Country.getSubregion());

            	   
            	    statement.setBoolean(22, Country.isLandlocked());
            	    
            	    statement.setDouble(24, Country.getArea());
            	   
            	    statement.setString(26, Country.getFlag());
            	   
            	    statement.setFloat(28, Country.getPopulation());
            	    statement.setDouble(29, Country.getGini());
            	    statement.setString(30, Country.getFifa());
            	    statement.setString(31, Country.getCar());
            	   
            	    statement.setString(36, Country.getStartOfWeek());
        
            	    statement.setString(38, Country.getPostalCode());

            	    statement.addBatch();
                   System.out.println("Name: " + Country.getName());
                   System.out.println("Official Name: " + Country.getOfficialName());
                   System.out.println("Native Common Name: " + Country.getNativeCommonName());
                   System.out.println("Native Official Name: " + Country.getNativeOfficialName());
                   System.out.println("CCA2: " + Country.getCca2());
                   System.out.println("CCN3: " + Country.getCcn3());
                   System.out.println("CCA3: " + Country.getCca3());
                   System.out.println("CIOC: " + Country.getCioc());
                   System.out.println("Independent: " + Country.isIndependent());
                   System.out.println("Status: " + Country.getStatus());
                   System.out.println("UN Member: " + Country.isUnMember());
                   System.out.println("Currency Name: " + Country.getCurrencyName());
                   System.out.println("Currency Symbol: " + Country.getCurrencySymbol());
                   System.out.println("IDD Root: " + Country.getIddRoot());
                   System.out.println("Capital: " + Country.getCapital());
                   System.out.println("Alt Spellings: " + Country.getAltSpellings());
                   System.out.println("Region: " + Country.getRegion());
                   System.out.println("Subregion: " + Country.getSubregion());
                   System.out.println("Languages: " + Country.getLanguages());
                   System.out.println("Translations: " + Country.getTranslations());
                   System.out.println("Latitude and Longitude: " + Country.getLatlng());
                   System.out.println("Landlocked: " + Country.isLandlocked());
                   System.out.println("Borders: " + Country.getBorders());
                   System.out.println("Area: " + Country.getArea());
                   System.out.println("Demonyms: " + Country.getDemonyms());
                   System.out.println("Flag: " + Country.getFlag());
                   System.out.println("Maps: " + Country.getMaps());
                   System.out.println("Population: " + Country.getPopulation());
                   System.out.println("Gini: " + Country.getGini());

                   System.out.println();
               }
                   statement.executeBatch();
                   statement.close();
                   conn.close();
                   conn1.disconnect();

           } catch (Exception e) {
               e.printStackTrace();
           }
           }
   }
}
