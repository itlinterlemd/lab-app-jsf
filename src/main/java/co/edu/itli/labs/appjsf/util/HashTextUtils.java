package co.edu.itli.labs.appjsf.util;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
 
public class HashTextUtils {
 
    /**
     * @param args
     * @throws NoSuchAlgorithmException 
     */
    public static void main(String[] args) throws NoSuchAlgorithmException {
        System.out.println(sha256("1234"));
    }
     
   public static String sha256(String input) {
        MessageDigest mDigest;
        StringBuffer sb = new StringBuffer();
		try {
			mDigest = MessageDigest.getInstance("SHA-256");
		
        byte[] result = mDigest.digest(input.getBytes());
       
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
        
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
        return sb.toString();
    }
}