package utils;

import org.encryptor4j.factory.KeyFactory;
import org.encryptor4j.Encryptor;
import java.security.Key;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class Cypher {

    private static InputStream getInputStream(String path, String mode, Encryptor encryptor){
        if(mode.equals("ENCODE")){
            try {
                return new FileInputStream(path);
            }catch (Exception e){}
        }
        else if(mode.equals("DECODE")){
            try {
                return encryptor.wrapInputStream(new FileInputStream(path));
            }catch (Exception e){}
        }
        return null;
    }

    private static OutputStream getOutputStream(String path, String mode, Encryptor encryptor){
        if(mode.equals("ENCODE")){
            try {
                return encryptor.wrapOutputStream(new FileOutputStream(path+"er"));
            }catch(Exception e){}
        }
        else if(mode.equals("DECODE")){
            try{
                return new FileOutputStream(path.substring(0,path.length()-2));
            }catch(Exception e){}
        }

        return null;

    }

    private static void operate(String path, Encryptor encryptor, String mode){
        InputStream is = null;
        OutputStream os = null;
        try {

            is = getInputStream(path, mode, encryptor);
            os = getOutputStream(path, mode, encryptor);

            byte[] buffer = new byte[4096];
            int nRead;
            while((nRead = is.read(buffer)) != -1) {
                try {
                    os.write(buffer, 0, nRead);
                }catch(Exception e){}
            }
            try {
                os.flush();
            }catch(Exception e){}
        }catch(Exception e){}
        finally {
            if(is != null) {
                try {
                    is.close();
                }catch(Exception e){}
            }
            if(os != null) {
                try {
                    os.close();
                }
                catch(Exception e){}
            }
        }
    }


    public static void encipher(String path, String password){

        Key secretKey = KeyFactory.AES.keyFromPassword(password.toCharArray());
        Encryptor encryptor = new Encryptor(secretKey, "AES/CTR/NoPadding", 16);

        try{
            operate(path, encryptor, "ENCODE");
        }catch(Exception e){
        }
    }

    public static void decipher(String path, String password){

        Key secretKey = KeyFactory.AES.keyFromPassword(password.toCharArray());
        Encryptor encryptor = new Encryptor(secretKey, "AES/CTR/NoPadding", 16);

        try{
            operate(path, encryptor, "DECODE");
        }catch(Exception e){
        }
    }
}
