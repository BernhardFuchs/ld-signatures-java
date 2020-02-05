package info.weboftrust.ldsignatures;

import com.github.jsonldjava.utils.JsonUtils;
import info.weboftrust.ldsignatures.verifier.Ed25519Signature2018LdVerifier;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonLdVerifyEd25519Signature2018Test {

    @SuppressWarnings("unchecked")
    @Test
    public void testVerify() throws Exception {

        LinkedHashMap<String, Object> jsonLdObject = (LinkedHashMap<String, Object>) JsonUtils.fromInputStream(JsonLdVerifyEd25519Signature2018Test.class.getResourceAsStream("signed.ed25519.jsonld"));

        Ed25519Signature2018LdVerifier verifier = new Ed25519Signature2018LdVerifier(TestUtil.testEd25519PublicKey);
        boolean verify = verifier.verify(jsonLdObject);

        assertTrue(verify);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testBadVerify() throws Exception {

        LinkedHashMap<String, Object> jsonLdObject = (LinkedHashMap<String, Object>) JsonUtils.fromInputStream(JsonLdVerifyEd25519Signature2018Test.class.getResourceAsStream("signed.ed25519.bad.jsonld"));

        Ed25519Signature2018LdVerifier verifier = new Ed25519Signature2018LdVerifier(TestUtil.testEd25519PublicKey);
        boolean verify = verifier.verify(jsonLdObject);

        assertFalse(verify);
    }
}
