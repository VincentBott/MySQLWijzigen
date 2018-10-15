package org.betavzw.db;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Scanner;

public class EigenSpielerij {

    public static void main(String[] args) throws Exception {


        Scanner scanner = new Scanner(System.in);

        System.out.print("Geef tekst: ");

        String input = scanner.nextLine();


        String versleuteldeTekst = encrypt(input, "hallo");

        System.out.printf("De versleutelde tekst: %s", versleuteldeTekst);

        System.out.printf("Terug ontcijferd: %s", decrypt(versleuteldeTekst, "aaaaaaaa"));

    }



    private static String encrypt(String strClearText,String strKey) throws Exception{

        String strData="";

        try {

            SecretKeySpec skeyspec=new SecretKeySpec(strKey.getBytes(),"Blowfish");
            Cipher cipher=Cipher.getInstance("Blowfish");
            cipher.init(Cipher.ENCRYPT_MODE, skeyspec);

            byte[] encrypted=cipher.doFinal(strClearText.getBytes());

            strData=new String(encrypted);

        } catch (Exception e) {

            e.printStackTrace();
            throw new Exception(e);
        }

        return strData;
    }



    private static String decrypt(String strEncrypted,String strKey) throws Exception{

        String strData="";

        try {
            SecretKeySpec skeyspec=new SecretKeySpec(strKey.getBytes(),"Blowfish");
            Cipher cipher=Cipher.getInstance("Blowfish");
            cipher.init(Cipher.DECRYPT_MODE, skeyspec);
            byte[] decrypted=cipher.doFinal(strEncrypted.getBytes());
            strData=new String(decrypted);

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }

        return strData;
    }
}
