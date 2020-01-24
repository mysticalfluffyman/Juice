package major.com.juice_android;


import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.io.UnsupportedEncodingException;

public class AESencryption {

    public static String Decrypt( String output,String password) throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException {
        SecretKeySpec key=generateKey(password);
        Cipher c= Cipher.getInstance("AES");
        c.init(Cipher.DECRYPT_MODE,key);
        byte[] decrypvalue= android.util.Base64.decode(output, android.util.Base64.DEFAULT);
        byte[] decvalue=c.doFinal(decrypvalue);
        String Decryptedvalue = new String(decvalue);
        return Decryptedvalue;
    }

    public static String Encrypt(String text,String password) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        SecretKeySpec key=generateKey(password);
        Log.e("enc", "Encrypt: "+key);
        Cipher c= Cipher.getInstance("AES");
        c.init(Cipher.ENCRYPT_MODE,key);
        byte[] encvalue =c.doFinal(text.getBytes());
        String Encryptedvalue= android.util.Base64.encodeToString(encvalue, android.util.Base64.DEFAULT);
        return Encryptedvalue;
    }

    private static SecretKeySpec generateKey(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
        final MessageDigest digest= MessageDigest.getInstance("SHA-256");
        byte[] bytes=password.getBytes("UTF-8");
        digest.update(bytes,0,bytes.length);
        byte[] key =digest.digest();
        SecretKeySpec secretKeySpec= new SecretKeySpec(key,"AES");
        return  secretKeySpec;
    }


}

