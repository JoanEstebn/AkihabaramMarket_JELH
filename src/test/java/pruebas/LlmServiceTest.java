package pruebas;

import org.junit.jupiter.api.Test;

import service.LlmService;

import static org.junit.jupiter.api.Assertions.*;

public class LlmServiceTest {

    @Test
    public void testGenerarRespuestaSimple() {
        LlmService servicio = new LlmService();
        String prompt = "Explica qué es un producto otaku.";
        String respuesta = servicio.enviarPrompt(prompt);
        assertNotNull(respuesta);
        assertFalse(respuesta.isBlank());
    }
}
