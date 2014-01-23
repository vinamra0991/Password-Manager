/*
 * Password Manager (PassMan) v 1.0 developed by - Vinamra Misra MCA (3rd Sem)
 * Date of completion - 20th July 2012 .
 */
package passwordmanager;


import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
public class PasswordManager {

    /**
     * @param args the command line arguments
     */
    static Connection con;
    static  Statement st;
    public static void loadDriver()
    {
        try
		{
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver Loaded");
		}
		catch(ClassNotFoundException a)
		{
			System.out.println(a.getMessage());
		}
    }
    public static void connect()throws SQLException
    {
       con=DriverManager.getConnection("jdbc:mysql://localhost/PasswordManager","root","root");
	System.out.println("Connection Successful");
	st=con.createStatement();
    }
    public static String md5(String s)
    {
        MessageDigest md = null;
        String md5 = null;
        try {
            md=MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(PasswordManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            md.update(s.getBytes());
                md5= new BigInteger(md.digest()).toString(16);
                if(md5.length()%2!=0)
                    md5="0"+md5;
            return md5;
        }
    }
    public static String encrypt(String s)
    {
        char []l1= s.toCharArray();
        for(int i=0;i<l1.length;i++)
        {
           if(i%2==0)
               l1[i]+=1;
           else
               l1[i]-=1;
        }
        StringBuilder l2=new StringBuilder(String.valueOf(l1));
        StringBuilder enc = l2.reverse();
        return String.valueOf(enc);
    }
    public static String decrypt(String s)
    {
        StringBuilder l1=new StringBuilder(s);
        StringBuilder l2 = l1.reverse();
        char []dec=String.valueOf(l2).toCharArray();
        for(int i=0;i<dec.length;i++)
        {
            if(i%2==0)
                dec[i]-=1;
            else
                dec[i]+=1;
        }
        return String.valueOf(dec);
    }
    public static void main(String[] args) {
        // TODO code application logic here
        loadDriver();
        String str="CREATE  TABLE IF NOT EXISTS `PasswordManager`.`Details` (`username` VARCHAR(50) NOT NULL ,`password` VARCHAR(100) NULL ,`name` VARCHAR(50) NULL ,`dob` DATE NULL ,PRIMARY KEY (`username`) ,UNIQUE INDEX `userid_UNIQUE` (`username` ASC) )ENGINE = InnoDB;";
        try
        {
            connect();
            st.executeUpdate(str);
        }
        catch(SQLException se)
        {
            System.out.println("Database Error ...");
        }
        new Login().setVisible(true);
    }
}
