package info.weboftrust.ldsignatures.signer;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.util.Base64URL;
import info.weboftrust.ldsignatures.crypto.ByteSigner;
import info.weboftrust.ldsignatures.crypto.adapter.JWSSignerAdapter;
import info.weboftrust.ldsignatures.crypto.impl.secp256k1_ES256K_PrivateKeySigner;
import info.weboftrust.ldsignatures.suites.EcdsaSecp256k1Signature2019SignatureSuite;
import info.weboftrust.ldsignatures.suites.SignatureSuites;
import info.weboftrust.ldsignatures.util.JWSUtil;
import org.bitcoinj.core.ECKey;

import java.security.GeneralSecurityException;
import java.util.Collections;

public class EcdsaSecp256k1Signature2019LdSigner extends LdSigner<EcdsaSecp256k1Signature2019SignatureSuite> {

	public EcdsaSecp256k1Signature2019LdSigner(ByteSigner signer) {

		super(SignatureSuites.SIGNATURE_SUITE_ECDSASECP256L1SIGNATURE2019, signer);
	}

	public EcdsaSecp256k1Signature2019LdSigner(ECKey privateKey) {

		this(new secp256k1_ES256K_PrivateKeySigner(privateKey));
	}

	public EcdsaSecp256k1Signature2019LdSigner() {

		this((ByteSigner) null);
	}

	public static String sign(byte[] signingInput, ByteSigner signer) throws GeneralSecurityException {

		// build the JWS and sign

		String jws;

		try {

			JWSHeader jwsHeader = new JWSHeader.Builder(JWSAlgorithm.ES256K).base64URLEncodePayload(false).criticalParams(Collections.singleton("b64")).build();
			byte[] jwsSigningInput = JWSUtil.getJwsSigningInput(jwsHeader, signingInput);

			JWSSigner jwsSigner = new JWSSignerAdapter(signer, JWSAlgorithm.ES256K);
			Base64URL signature = jwsSigner.sign(jwsHeader, jwsSigningInput);
			jws = JWSUtil.serializeDetachedJws(jwsHeader, signature);
		} catch (JOSEException ex) {

			throw new GeneralSecurityException("JOSE signing problem: " + ex.getMessage(), ex);
		}

		// done

		return jws;
	}

	@Override
	public String sign(byte[] signingInput) throws GeneralSecurityException {

		return sign(signingInput, this.getSigner());
	}
}
