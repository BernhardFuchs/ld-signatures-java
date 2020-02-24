package info.weboftrust.ldsignatures.util;

import com.github.jsonldjava.utils.JsonUtils;
import info.weboftrust.ldsignatures.TestUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CanonicalizationUtilTest {

    @SuppressWarnings("unchecked")
    @Test
    public void testCanonicalizationInput() throws Exception {

        LinkedHashMap<String, Object> jsonLdObject = (LinkedHashMap<String, Object>) JsonUtils.fromInputStream(TestUtil.parseFileToInputStream("input.jsonld"));
        String canonicalizedDocument = TestUtil.parseFileToString("input.canonicalized");

        Assertions.assertEquals(CanonicalizationUtil.buildCanonicalizedDocumentForInputJsonLd(jsonLdObject), canonicalizedDocument);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testCanonicalizationSigned() throws Exception {

        LinkedHashMap<String, Object> jsonLdObject = (LinkedHashMap<String, Object>) JsonUtils.fromInputStream(TestUtil.parseFileToInputStream("signed.rsa.jsonld"));
        String canonicalizedDocument = TestUtil.parseFileToString("signed.rsa.canonicalized");

        assertEquals(CanonicalizationUtil.buildCanonicalizedDocumentForSignedJsonLd(jsonLdObject), canonicalizedDocument);
    }
}
