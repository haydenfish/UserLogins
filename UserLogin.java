/**
 * Program to verify user credentials
 *
 * @author haydenfish
 * @version 12.08.2022
 */

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;

import java.util.Scanner;

import java.nio.charset.StandardCharsets;

import java.math.BigInteger;

public class UserLogin {
    /**
     * Compares user provided credentials to stored
     * data, returns true if credentials match
     * false otherwise
     *
     * @param user the given username
     * @param pass the given password
     * @return true if match, false else
     */
    public static boolean validCredentials(String user, String pass) {
        try {
            // read json config file as array of objects
            JSONParser jsonParser = new JSONParser();
            FileReader reader = new FileReader("src/UserCredentials.json");
            Object obj = jsonParser.parse(reader);
            JSONArray elementList = (JSONArray) obj;

            // check config file against user provided data
            for(int i = 0; i < elementList.size(); i++) {
                JSONObject configUser = (JSONObject) elementList.get(i);
                String configUserName = (String) configUser.get("username");
                String configPassword = (String) configUser.get("password_hash");

                if(user.equals(configUserName)) {
                    if(pass.equals(configPassword)) {
                        return true;
                    }
                }
            }
        } catch(ParseException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Calculate the message digest of an input
     *
     * @param input the String to be converted
     * @return array of type byte
     */
    public static byte[] getSHA(String input) throws NoSuchAlgorithmException {
        MessageDigest md  = MessageDigest.getInstance("SHA-256");

        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Converts message digest into hex value
     *
     * @param hash the String of bytes provided by getSHA method
     * @return hexString the hex representation of String
     */
    public static String toHexString(byte[] hash) {
        BigInteger num = new BigInteger(1, hash);

        StringBuilder hexString = new StringBuilder(num.toString(16));

        while(hexString.length() < 64) {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }

    /**
     * Prompt the user for input, check
     * if the entered information is correct
     *
     * @param args command line arguments
     */
    public static void main(String[] args) throws NoSuchAlgorithmException {
        // create object to store user provided
        // login information
        UserData user = new UserData();
        Scanner scan = new Scanner(System.in);

        // prompt user to enter their login credentials
        System.out.print("Please enter your username: ");
        user.setUsername(scan.nextLine());
        System.out.print("Please enter your password: ");
        user.setPassword(scan.nextLine());

        // hash the user password
        user.setPassword(toHexString(getSHA(user.getPassword())));

        // compare the user provided credentials
        // with the stored credentials
        if(validCredentials(user.getUsername(), user.getPassword())) {
            System.out.println("Your deepest, darkest secret");
        } else {
            System.out.println("Get lost");
        }
    }
}
