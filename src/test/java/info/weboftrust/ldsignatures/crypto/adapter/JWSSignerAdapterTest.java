package info.weboftrust.ldsignatures.crypto.adapter;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import info.weboftrust.ldsignatures.crypto.ByteSigner;
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
class JWSSignerAdapterTest {

    @Mock
    private ByteSigner byteSigner;
    private String anyAlgorithmName = "anyAlgorithm";
    private JWSAlgorithm anyAlgorithm = new JWSAlgorithm(anyAlgorithmName);
    private JWSHeader anyHeader = new JWSHeader(anyAlgorithm);

    private JWSSignerAdapter createTestInstance() {
        return new JWSSignerAdapter(byteSigner, anyAlgorithm);
    }

    @Test
    @DisplayName("When the signer throws a GeneralSecurityException it should be wrapped with it's message in a JOSEException")
    void given_signThrowsGeneralSecurityException_then_JOSEExceptionReThrown() throws GeneralSecurityException {
        String expectedErrorMessage = "errorMessage";
        GeneralSecurityException rootException = new GeneralSecurityException(expectedErrorMessage);
        doThrow(rootException).when(byteSigner).sign(new byte[]{}, anyAlgorithmName);

        JWSSignerAdapter adapter = createTestInstance();

        JOSEException exception = assertThrows(JOSEException.class,
                () -> adapter.sign(anyHeader, new byte[]{})
        );
        assertEquals(exception.getMessage(), expectedErrorMessage);
    }

    @Test
    void given_unexpectedAlgorithm_when_sign_then_JOSEExceptionThrown() {
        JWSAlgorithm unexpectedAlgorithm = new JWSAlgorithm("unexpectedAlgorithm");
        JWSAlgorithm expectedAlgorithm = new JWSAlgorithm("expectedAlgorithm");

        JWSHeader header = new JWSHeader(unexpectedAlgorithm);
        JWSSignerAdapter adapter = new JWSSignerAdapter(byteSigner, expectedAlgorithm);

        JOSEException exception = assertThrows(JOSEException.class,
                () -> adapter.sign(header, new byte[]{})
        );
        assertEquals(exception.getMessage(), "Unexpected algorithm: " + unexpectedAlgorithm);
    }

}