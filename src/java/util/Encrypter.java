/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author rjuarezja
 */
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import java.security.Key;
import javax.crypto.KeyGenerator;
import sun.misc.CharacterDecoder;
import sun.misc.CharacterEncoder;

public class Encrypter {

    private static String algorithm = "DESede";
    private static Key key = null;
    private static Cipher cipherEnc = null;
    private static Cipher cipherDec = null;
    private static final String SEP = "|||";
    private static CharacterEncoder encoder;
    private static CharacterDecoder decoder;

    public static void setUp() throws Exception {
//        try {
//Generar nuevo key
            key = KeyGenerator.getInstance(algorithm).generateKey();
            FileOutputStream fos = new FileOutputStream("encrypter.key");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(key);
            
            fos.close();
            oos.close();
//            System.out.println("new File(encrypter.key) = " + new File("encrypter.key").getAbsolutePath());
            FileInputStream fis = new FileInputStream("encrypter.key");
            ObjectInputStream ois = new ObjectInputStream(fis);
            key = (Key) ois.readObject();
            fis.close();
            ois.close();
            
//             byte[] keyAsBytes = "123456789012345678901234567890".getBytes("US-ASCII");
//            DESedeKeySpec keySpec = new DESedeKeySpec(keyAsBytes);
//            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
//            SecretKey key = keyFactory.generateSecret(keySpec);
            //cipher = Cipher.getInstance(encryptionScheme);

            
//            KeyGenerator.getInstance("asdfasd").init(new AlgorithmParameters().)
//            AlgorithmParameters s = AlgorithmParameters.getInstance("dasdfasf");
//            s.
//            Charset.availableCharsets().get("asdfas").
            cipherEnc = Cipher.getInstance(algorithm);
            cipherEnc.init(Cipher.ENCRYPT_MODE, key);
            cipherDec = Cipher.getInstance(algorithm);
            cipherDec.init(Cipher.DECRYPT_MODE, key);
            
            encoder = new sun.misc.UCEncoder();
            decoder = new sun.misc.UCDecoder();
            
//        } catch (Exception ex) {
//            Logger.getLogger(WebListener.class.getName()).log(Level.SEVERE, "------------------- NO SE PUDO INICIALIZAR ENCRYPTER ----------- ", ex);
//        }
    }

    public static void main(String[] args)
            throws Exception {
//        Provider[] ps =Security.getProviders();
//        for (int i = 0; i < ps.length; i++) {
//            System.out.println("exp = " + ps[i]);
//            for (Provider.Service x : ps[i].getServices()) {
//                System.out.println("x = " +x.getType()+" - "+ x.getAlgorithm());
//            }
//        
//        }
        setUp();
        for (int i = 0; i < 6; i++) {
            String input = "JUJR861102TB3";
            //String input = ""+i;
            System.out.println("Entered  : " + input);
            String encoded = encrypt(input);
            System.out.println("encrypted: " + encoded);
            System.out.println("Recovered: " + decrypt(encoded));
            Thread.sleep(123);
        }
    }

    public static synchronized String encrypt(String input) throws Exception {
        //cipherEnc.init(Cipher.ENCRYPT_MODE, key);
        int milis = (int) (System.currentTimeMillis() % 10000);
        byte[] inputBytes = String.valueOf(milis).concat(SEP).concat(input).getBytes();
        StringBuilder sb = new StringBuilder(encoder.encodeBuffer(cipherEnc.doFinal(inputBytes)));
        return sb.deleteCharAt(0).toString();
    }

    public static synchronized String encryptId(String id) throws Exception {
        return encrypt(id.concat(SEP).concat(String.valueOf(System.currentTimeMillis())));
    }

    public static synchronized String decrypt(String encoded) throws Exception {
        //cipherEnc.init(Cipher.DECRYPT_MODE, key);
        byte[] recoveredBytes = null;
        try {
            String sb = new StringBuilder(encoded).insert(0, '*').toString();
            recoveredBytes = cipherDec.doFinal(decoder.decodeBuffer(sb));
        } catch (IOException ex) {
            Logger.getLogger(Encrypter.class.getName()).log(Level.SEVERE, null, ex);
        }
        String recovered =
                new String(recoveredBytes);
        int index = recovered.indexOf(SEP);
        recovered = recovered.substring(index + SEP.length());
        return recovered;
    }

    public static synchronized String decryptId(String encodedId) throws Exception {
        String decoded = decrypt(encodedId);
        return decoded.substring(0, decoded.lastIndexOf(SEP));
    }
}
