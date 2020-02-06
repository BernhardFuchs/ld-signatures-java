package info.weboftrust.ldsignatures;

import info.weboftrust.ldsignatures.signer.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LdSignerTest {

    @Test
    public void given_validSignerName_then_correctClassIsInstantiated() {

        assertEquals(LdSigner.ldSignerForSignatureSuite("Ed25519Signature2018").getClass(), Ed25519Signature2018LdSigner.class);
        assertEquals(LdSigner.ldSignerForSignatureSuite("EcdsaKoblitzSignature2016").getClass(), EcdsaKoblitzSignature2016LdSigner.class);
        assertEquals(LdSigner.ldSignerForSignatureSuite("RsaSignature2018").getClass(), RsaSignature2018LdSigner.class);
        assertEquals(LdSigner.ldSignerForSignatureSuite("EcdsaSecp256k1Signature2019").getClass(), EcdsaSecp256k1Signature2019LdSigner.class);
    }
}
