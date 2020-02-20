package info.weboftrust.ldsignatures.crypto.adapter;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.util.Base64URL;
import info.weboftrust.ldsignatures.crypto.ByteVerifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.GeneralSecurityException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
public class JWSVerifierAdapterTest {

    @Mock
    private ByteVerifier byteVerifier;
    @Mock
    private Base64URL signature;


    private JWSVerifierAdapter createTestInstance(JWSAlgorithm algorithm) {
        return new JWSVerifierAdapter(byteVerifier, algorithm);
    }

    @Test
    @DisplayName("When the verifier throws a GeneralSecurityException it should be wrapped with it's message in a JOSEException")
    void given_verifyThrowsGeneralSecurityException_then_JOSEExceptionReThrown() throws GeneralSecurityException {
        String anyAlgorithmName = "anyAlgorithm";
        JWSAlgorithm anyAlgorithm = new JWSAlgorithm(anyAlgorithmName);
        JWSHeader anyHeader = new JWSHeader(anyAlgorithm);

        String expectedErrorMessage = "errorMessage";
        GeneralSecurityException rootException = new GeneralSecurityException(expectedErrorMessage);

        doThrow(rootException).when(byteVerifier).verify(new byte[]{}, null, anyAlgorithmName);

        JWSVerifierAdapter adapter = createTestInstance(anyAlgorithm);
        JOSEException exception = assertThrows(JOSEException.class,
                () -> adapter.verify(anyHeader, new byte[]{}, signature)
        );
        assertEquals(exception.getMessage(), expectedErrorMessage);
    }

    @Test
    void given_unexpectedAlgorithm_when_verify_then_JOSEExceptionThrown() {
        JWSAlgorithm unexpectedAlgorithm = new JWSAlgorithm("unexpectedAlgorithm");
        JWSAlgorithm expectedAlgorithm = new JWSAlgorithm("expectedAlgorithm");

        JWSHeader header = new JWSHeader(unexpectedAlgorithm);
        JWSVerifierAdapter adapter = createTestInstance(expectedAlgorithm);

        JOSEException exception = assertThrows(JOSEException.class,
                () -> adapter.verify(header, new byte[]{}, signature)
        );
        assertEquals(exception.getMessage(), "Unexpected algorithm: " + unexpectedAlgorithm);
    }
}
