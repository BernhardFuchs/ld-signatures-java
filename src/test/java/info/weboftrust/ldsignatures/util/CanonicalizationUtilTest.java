package info.weboftrust.ldsignatures.util;

import com.github.jsonldjava.utils.JsonUtils;
import info.weboftrust.ldsignatures.TestUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CanonicalizationUtilTest {

    @SuppressWarnings("unchecked")
    @Test
    public void testCanonicalizationInput() throws Exception {

        LinkedHashMap<String, Object> jsonLdObject = (LinkedHashMap<String, Object>) JsonUtils.fromInputStream(TestUtil.parseFileToInputStream("input.jsonld"));
        String canonicalizedDocument = TestUtil.parseFileToString("input.canonicalized");

        Assertions.assertEquals(CanonicalizationUtil.buildDocumentForInputJsonLd(jsonLdObject), canonicalizedDocument);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testCanonicalizationSigned() throws Exception {

        LinkedHashMap<String, Object> jsonLdObject = (LinkedHashMap<String, Object>) JsonUtils.fromInputStream(TestUtil.parseFileToInputStream("signed.rsa.jsonld"));
        String canonicalizedDocument = TestUtil.parseFileToString("signed.rsa.canonicalized");

        assertEquals(CanonicalizationUtil.buildDocumentForSignedJsonLd(jsonLdObject), canonicalizedDocument);
    }

    @SuppressWarnings("unchecked")
    @Test
    @Disabled("Throws com.github.jsonldjava.core.JsonLdError: invalid term definition: 1.1 with the same" +
            " input as CanonicalizationTest.testCanonicalization() in verifiable-credentials-java module")
    void testCanonicalization() throws Exception {

        LinkedHashMap<String, Object> jsonLdObject = (LinkedHashMap<String, Object>) JsonUtils.fromInputStream(TestUtil.parseFileToInputStream("verifiable-credential.ldp.good.jsonld"));
        String canonicalizedDocument = TestUtil.parseFileToString("verifiable-credential.canonicalized.test");

        assertEquals(CanonicalizationUtil.buildDocumentForSignedJsonLd(jsonLdObject), canonicalizedDocument);
    }
}
