package info.weboftrust.ldsignatures.util;

import info.weboftrust.ldsignatures.LdSignature;

import java.util.LinkedHashMap;

public class JSONLDUtil {

    public static LinkedHashMap<String, Object> removeSignatureFromJsonLdObject(LinkedHashMap<String, Object> jsonLdObject) {
        LinkedHashMap<String, Object> jsonLdObjectWithoutSignature = new LinkedHashMap<> (jsonLdObject);
        LdSignature.removeFromJsonLdObject(jsonLdObjectWithoutSignature);
        return jsonLdObjectWithoutSignature;
    }
}
