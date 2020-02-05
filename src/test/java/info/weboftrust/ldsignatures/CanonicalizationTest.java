package info.weboftrust.ldsignatures;

import com.github.jsonldjava.utils.JsonUtils;
import info.weboftrust.ldsignatures.util.CanonicalizationUtil;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CanonicalizationTest {

    @SuppressWarnings("unchecked")
    @Test
    public void testCanonicalizationInput() throws Exception {

        LinkedHashMap<String, Object> jsonLdObject = (LinkedHashMap<String, Object>) JsonUtils.fromInputStream(CanonicalizationTest.class.getResourceAsStream("input.jsonld"));
        String canonicalizedDocument = TestUtil.read(CanonicalizationTest.class.getResourceAsStream("input.canonicalized"));

        assertEquals(CanonicalizationUtil.buildCanonicalizedDocument(jsonLdObject), canonicalizedDocument);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testCanonicalizationSigned() throws Exception {

        LinkedHashMap<String, Object> jsonLdObject = (LinkedHashMap<String, Object>) JsonUtils.fromInputStream(CanonicalizationTest.class.getResourceAsStream("signed.rsa.jsonld"));
        String canonicalizedDocument = TestUtil.read(CanonicalizationTest.class.getResourceAsStream("signed.rsa.canonicalized"));

        assertEquals(CanonicalizationUtil.buildCanonicalizedDocument(jsonLdObject), canonicalizedDocument);
    }
}
