package info.weboftrust.ldsignatures;

import com.github.jsonldjava.utils.JsonUtils;
import info.weboftrust.ldsignatures.verifier.RsaSignature2018LdVerifier;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonLdVerifyRsaSignature2018Test {

    @SuppressWarnings("unchecked")
    @Test
    public void testVerify() throws Exception {

        LinkedHashMap<String, Object> jsonLdObject = (LinkedHashMap<String, Object>) JsonUtils.fromInputStream(JsonLdVerifyRsaSignature2018Test.class.getResourceAsStream("signed.rsa.jsonld"));

        RsaSignature2018LdVerifier verifier = new RsaSignature2018LdVerifier(TestUtil.testRSAPublicKey);
        boolean verify = verifier.verify(jsonLdObject);

        assertTrue(verify);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testBadVerify() throws Exception {

        LinkedHashMap<String, Object> jsonLdObject = (LinkedHashMap<String, Object>) JsonUtils.fromInputStream(JsonLdVerifyRsaSignature2018Test.class.getResourceAsStream("signed.rsa.bad.jsonld"));

        RsaSignature2018LdVerifier verifier = new RsaSignature2018LdVerifier(TestUtil.testRSAPublicKey);
        boolean verify = verifier.verify(jsonLdObject);

        assertFalse(verify);
    }
}
