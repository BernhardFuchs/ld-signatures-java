package info.weboftrust.ldsignatures;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class TestUtil {

    static final RSAPrivateKey testRSAPrivateKey;
    static final RSAPublicKey testRSAPublicKey;
    static final byte[] testEd25519PrivateKey;
    static final byte[] testEd25519PublicKey;

    static {

        try {

            String pem = read(TestUtil.class.getResourceAsStream("publickey.pem"));
            pem = pem.replace("-----BEGIN PUBLIC KEY-----", "").replace("\n", "");
            pem = pem.replace("-----END PUBLIC KEY-----", "");

            byte[] encoded = Base64.decodeBase64(pem);

            X509EncodedKeySpec spec = new X509EncodedKeySpec(encoded);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            testRSAPublicKey = (RSAPublicKey) keyFactory.generatePublic(spec);
        } catch (Exception ex) {

            throw new RuntimeException(ex.getMessage(), ex);
        }

        try {

            String pem = read(TestUtil.class.getResourceAsStream("privatekey.pem"));
            pem = pem.replace("-----BEGIN PRIVATE KEY-----", "").replace("\n", "");
            pem = pem.replace("-----END PRIVATE KEY-----", "");

            byte[] encoded = Base64.decodeBase64(pem);

            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(encoded);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            testRSAPrivateKey = (RSAPrivateKey) keyFactory.generatePrivate(spec);
        } catch (Exception ex) {

            throw new RuntimeException(ex.getMessage(), ex);
        }

        try {

            testEd25519PrivateKey = Hex.decodeHex("984b589e121040156838303f107e13150be4a80fc5088ccba0b0bdc9b1d89090de8777a28f8da1a74e7a13090ed974d879bf692d001cddee16e4cc9f84b60580".toCharArray());
            testEd25519PublicKey = Hex.decodeHex("de8777a28f8da1a74e7a13090ed974d879bf692d001cddee16e4cc9f84b60580".toCharArray());
        } catch (Exception ex) {

            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    private static final String TEST_FILES_PATH = "src/test/resources/info/weboftrust/ldsignatures/";

    private static String read(InputStream inputStream) throws Exception {

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuffer buffer = new StringBuffer();

        String line;
        while ((line = reader.readLine()) != null) buffer.append(line + "\n");

        return buffer.toString();
    }

    public static InputStream parseFileToInputStream(String filename) throws FileNotFoundException {
        File initialFile = new File(TEST_FILES_PATH + filename);
        return new FileInputStream(initialFile);
    }

    public static String parseFileToString(String filename) throws IOException {
        return new String(Files.readAllBytes(Paths.get(TEST_FILES_PATH + filename)));
    }
}
