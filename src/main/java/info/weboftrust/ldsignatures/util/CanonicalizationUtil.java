package info.weboftrust.ldsignatures.util;

import com.github.jsonldjava.core.JsonLdConsts;
import com.github.jsonldjava.core.JsonLdError;
import com.github.jsonldjava.core.JsonLdOptions;
import com.github.jsonldjava.core.JsonLdProcessor;
import info.weboftrust.ldsignatures.LdSignature;

import java.util.LinkedHashMap;

public class CanonicalizationUtil {

	private CanonicalizationUtil() {

	}

	public static String buildCanonicalizedDocumentForInputJsonLd(Object inputJsonLdObject) throws JsonLdError {
		LdSignature.removeFromJsonLdObject((LinkedHashMap<String, Object>) inputJsonLdObject);
		return createCanonicalizedDocument(inputJsonLdObject);
	}

	public static String buildCanonicalizedDocumentForSignedJsonLd(Object signedJsonLdObject) throws JsonLdError {
		return createCanonicalizedDocument(signedJsonLdObject);
	}

	private static String createCanonicalizedDocument(Object jsonLdObject) {
		JsonLdOptions options = new JsonLdOptions();
		options.format = JsonLdConsts.APPLICATION_NQUADS;
		return (String) JsonLdProcessor.normalize(jsonLdObject, options);
	}

	@Deprecated()
	public static String buildCanonicalizedDocument(Object jsonLdObject) throws JsonLdError {
		return createCanonicalizedDocument(jsonLdObject);
	}
}
