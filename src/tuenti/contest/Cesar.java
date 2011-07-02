package tuenti.contest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import sun.misc.BASE64Decoder;

public class Cesar {

	private static Logger log = Logger.getLogger(Cesar.class); 
	/**
	 * @param args
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 * @throws NoSuchProviderException 
	 */
	public static void main(String[] args) throws IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException {
		Cesar main = new Cesar();
		main.process();
		//System.out.println(main.magic(null));
	}
	private void process() throws IOException {
		//Scanner in = new Scanner(System.in);
		System.out.println(-111 * 187);
	}
	
	private String magic(String text) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException {
		String encText64;
		if (text == null) {
			encText64 = "C+F4IWP2y77sfnUHRa2Xziqh401dCy6R4m+rb7bWVYQiTkg27MyLqYF0CGjr/R9Q5VKA7p3KY3KG8OKdHJVcdNj+6waDZMHnm050jDYI/tfJapW72QSQ5YelAI0SKX06TIEaJfRQIB9QJhvZ6r1li2bPFDbmNIThHRoYFHBDCT9W6mudWy95sTUhOHHgfAxi0zyVLUPhoyGtaxtX/kxrkV6SG6e2ofFUidhS6ldD+LHaLQmj4xPgx/zwhke51YwlRgr2Se7IK3R1bokC6RbWaR1S2YiAMLNWwDd05NrK2mNjQlieAevO/5vDTegbEGTkER82JTN+DdwfQ2Io5wZjO/Z9SeGJ6NctG/RL51SEfZMuKWPgvy/nL3C4pX47llHTZdSOBUnpBBWcvG4g2Gx/SXx8++BhWjWjLbd0/+/gqwg=";
		} else {
			encText64 = text;
		}
		BASE64Decoder b64dec = new BASE64Decoder();
        byte[] encText = b64dec.decodeBuffer(encText64);
        FileOutputStream fos = new FileOutputStream("../data/cesar.bin");
        fos.write( encText);
        fos.flush();
        fos.close();
        Security.addProvider (new BouncyCastleProvider ());
		Cipher dcipher = Cipher.getInstance("DES/ECB/PKCS7Padding","BC");
		byte[] key1 = new byte[8]; 
		key1[0]	 = 't';
		key1[1]	 = 'u';
		key1[2]	 = 'e';
		key1[3]	 = 'n';
		key1[4]	 = 't';
		key1[5]	 = 'i';
		key1[6]	 = 0;
		key1[7]	 = 0;
		SecretKeySpec skeySpec = new SecretKeySpec(key1, "DES");
		dcipher.init(Cipher.DECRYPT_MODE, skeySpec);
		byte[] decrypt1 = dcipher.doFinal(encText);
        byte[] decrypt2 = new byte[decrypt1.length];
        byte[] decrypt3 = new byte[decrypt1.length];
        fos = new FileOutputStream("../data/cesar1.bin");
        fos.write( decrypt1);
        fos.flush();
        fos.close();
        for (int i = 0; i < decrypt1.length; i++) {
        	int newVal = decrypt1[i];
        	newVal = ~newVal;
        	decrypt2[i] = (byte)newVal; 
        }
        String key2 = "tuenti";
        fos = new FileOutputStream("../data/cesar2.bin");
        fos.write( decrypt2);
        fos.flush();
        fos.close();

        int keyPointer = 0;
        int keyPointerAdd = 0;      
        for (int i = 0; i < decrypt1.length; i++) {
        	int newVal = decrypt2[i];
        	keyPointerAdd = newVal;
        	newVal = newVal ^ key2.charAt(keyPointer);
        	decrypt3[i] = (byte)newVal;
        	keyPointer = (keyPointerAdd + keyPointer)%key2.length();
        }
        fos = new FileOutputStream("../data/cesar3.bin");
        fos.write( decrypt3);
        fos.flush();
        fos.close();
        for (int i = 0; i < 128; i++) {
        	System.out.println(applyCaesar2(new String(decrypt3),i));
        }
        applyCaesar(" ", 115);
        return "";
	}
	
	public String applyCaesar(String text, int shift)
	{
	    char[] chars = text.toCharArray();
	    for (int i=0; i < text.length(); i++)
	    {
	        char c = chars[i];
	        if (c >= 32 && c <= 127)
	        {
	            // Change base to make life easier, and use an
	            // int explicitly to avoid worrying... cast later
	            int x = c - 32;
	            x = (x + shift) % 127;
	            chars[i] = (char) (x + 32);
	        }
	    }
	    return new String(chars);
	}
	public String applyCaesar2(String text, int shift)
	{
	    char[] chars = text.toCharArray();
	    for (int i=0; i < text.length(); i++)
	    {
	    	char c = chars[i];
            // Change base to make life easier, and use an
            // int explicitly to avoid worrying... cast later
            int x = c;
            x = (x + shift) % 128;
            if (x < 0) 
              x += 128; //java modulo can lead to negative values!
            chars[i] = (char) (x);
	    }
	    return new String(chars);
	}
}
