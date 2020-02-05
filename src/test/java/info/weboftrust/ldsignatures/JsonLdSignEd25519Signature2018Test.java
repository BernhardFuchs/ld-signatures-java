package info.weboftrust.ldsignatures;

import com.github.jsonldjava.utils.JsonUtils;
import info.weboftrust.ldsignatures.signer.Ed25519Signature2018LdSigner;
import info.weboftrust.ldsignatures.suites.SignatureSuites;
import info.weboftrust.ldsignatures.verifier.Ed25519Signature2018LdVerifier;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonLdSignEd25519Signature2018Test {

    @SuppressWarnings("unchecked")
    @Test
    public void testSignEd25519Signature2018() throws Exception {

        LinkedHashMap<String, Object> jsonLdObject = (LinkedHashMap<String, Object>) JsonUtils.fromInputStream(JsonLdSignEd25519Signature2018Test.class.getResourceAsStream("input.jsonld"));

        URI creator = URI.create("did:sov:WRfXPg8dantKVubE3HX8pw");
        String created = "2017-10-24T05:33:31Z";
        String domain = "example.com";
        String nonce = null;

        Ed25519Signature2018LdSigner signer = new Ed25519Signature2018LdSigner(TestUtil.testEd25519PrivateKey);
        signer.setCreator(creator);
        signer.setCreated(created);
        signer.setDomain(domain);
        signer.setNonce(nonce);
        LdSignature ldSignature = signer.sign(jsonLdObject);

        assertEquals(SignatureSuites.SIGNATURE_SUITE_ED25519SIGNATURE2018.getTerm(), ldSignature.getType());
        assertEquals(creator, ldSignature.getCreator());
        assertEquals(created, ldSignature.getCreated());
        assertEquals(domain, ldSignature.getDomain());
        assertEquals(nonce, ldSignature.getNonce());
        assertEquals("if8ooA+32YZc4SQBvIDDY9tgTatPoq4IZ8Kr+We1t38LR2RuURmaVu9D4shbi4VvND87PUqq5/0vsNFEGIIEDA==", ldSignature.getSignatureValue());

        Ed25519Signature2018LdVerifier verifier = new Ed25519Signature2018LdVerifier(TestUtil.testEd25519PublicKey);
        boolean verify = verifier.verify(jsonLdObject, ldSignature);
        assertTrue(verify);
    }
}
