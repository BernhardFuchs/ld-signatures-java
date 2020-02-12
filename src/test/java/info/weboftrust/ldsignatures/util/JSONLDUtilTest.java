package info.weboftrust.ldsignatures.util;

import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

class JSONLDUtilTest {

    @Test
    void given_jsonLdObjectWithSignature_when_removeSignatureFromJsonLdObject_then_returnJsonLdObjectWithoutSignature() {
        LinkedHashMap<String, Object> givenJsonLdObject = new LinkedHashMap<>();
        givenJsonLdObject.put("proof", new Object());

        LinkedHashMap<String, Object> returnedJsonLdObject = JSONLDUtil.removeSignatureFromJsonLdObject(givenJsonLdObject);

        LinkedHashMap<String, Object> expectedJsonLdObject = new LinkedHashMap<>();
        assertEquals(expectedJsonLdObject, returnedJsonLdObject);
    }
}