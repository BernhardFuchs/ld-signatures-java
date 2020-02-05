package info.weboftrust.ldsignatures;

import info.weboftrust.ldsignatures.verifier.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LdVerifierTest {

    @Test
    public void given_validSignatureName_then_correctClassInstantiated() {
        assertEquals(LdVerifier.ldVerifierForSignatureSuite("Ed25519Signature2018").getClass(), Ed25519Signature2018LdVerifier.class);
        assertEquals(LdVerifier.ldVerifierForSignatureSuite("EcdsaKoblitzSignature2016").getClass(), EcdsaKoblitzSignature2016LdVerifier.class);
        assertEquals(LdVerifier.ldVerifierForSignatureSuite("RsaSignature2018").getClass(), RsaSignature2018LdVerifier.class);
        assertEquals(LdVerifier.ldVerifierForSignatureSuite("EcdsaSecp256k1Signature2019").getClass(), EcdsaSecp256k1Signature2019LdVerifier.class);
    }

    @Test
    public void given_invalidSignatureName_then_IllegalArgumentExceptionThrown() {
        assertThrows(IllegalArgumentException.class, () ->
                LdVerifier.ldVerifierForSignatureSuite("NotExistingSignature")
        );
    }
}
