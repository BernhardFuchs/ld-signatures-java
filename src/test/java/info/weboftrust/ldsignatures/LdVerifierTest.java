package info.weboftrust.ldsignatures;

import info.weboftrust.ldsignatures.suites.SignatureSuite;
import info.weboftrust.ldsignatures.verifier.*;
import org.junit.jupiter.api.Test;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;

import java.security.GeneralSecurityException;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LdVerifierTest {

    @SuppressWarnings("rawtypes")
    @Test
    public void given_unexpectedSignature_when_verify_then_GeneralSecurityExceptionThrown() throws GeneralSecurityException {
        String expectedType = "expectedType";
        String unexpectedType = "unexpectedType";

        LdSignature signature = prepareTestData(unexpectedType);
        LdVerifier ldVerifierMock = createMocksAndStubs(signature, expectedType);

        GeneralSecurityException exception = assertThrows(GeneralSecurityException.class, () ->
                ldVerifierMock.verify(new LinkedHashMap<>(), signature));
        assertEquals(exception.getMessage(), "Unexpected signature type: " + unexpectedType + " is not " + expectedType);
    }

    private LdSignature prepareTestData(String unexpectedType) {
        LdSignature signature = new LdSignature();
        signature.setType(unexpectedType);
        return signature;
    }

    @SuppressWarnings("rawtypes")
    private LdVerifier createMocksAndStubs(LdSignature signature, String expectedType) throws GeneralSecurityException {
        SignatureSuite signatureSuiteMock = PowerMockito.mock(SignatureSuite.class);
        //noinspection ResultOfMethodCallIgnored
        PowerMockito.doReturn(expectedType).when(signatureSuiteMock).getTerm();

        LdVerifier ldVerifierMock = PowerMockito.mock(LdVerifier.class);
        PowerMockito.doCallRealMethod()
                .when(ldVerifierMock)
                .verify(new LinkedHashMap<>(), signature);

        PowerMockito.doReturn(signatureSuiteMock).when(ldVerifierMock).getSignatureSuite();
        Whitebox.setInternalState(ldVerifierMock, "signatureSuite", signatureSuiteMock);
        return ldVerifierMock;
    }

    @Test
    public void given_validSignatureName_when_ldVerifierForSignatureSuite_then_correctClassInstantiated() {
        assertEquals(LdVerifier.ldVerifierForSignatureSuite("Ed25519Signature2018").getClass(), Ed25519Signature2018LdVerifier.class);
        assertEquals(LdVerifier.ldVerifierForSignatureSuite("EcdsaKoblitzSignature2016").getClass(), EcdsaKoblitzSignature2016LdVerifier.class);
        assertEquals(LdVerifier.ldVerifierForSignatureSuite("RsaSignature2018").getClass(), RsaSignature2018LdVerifier.class);
        assertEquals(LdVerifier.ldVerifierForSignatureSuite("EcdsaSecp256k1Signature2019").getClass(), EcdsaSecp256k1Signature2019LdVerifier.class);
    }

    @Test
    public void given_invalidSignatureName_when_ldVerifierForSignatureSuite_then_IllegalArgumentExceptionThrown() {
        assertThrows(IllegalArgumentException.class, () ->
                LdVerifier.ldVerifierForSignatureSuite("NotExistingSignature")
        );
    }
}
