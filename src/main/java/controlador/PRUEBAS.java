package controlador;

import service.LlmService;

public class PRUEBAS {

	public static void main(String[] args) {
		
		System.out.println("API Key detectada: " + System.getenv("OPENROUTER_API_KEY"));

		LlmService llm = new LlmService();
		String respuesta = llm.enviarPrompt("Dame una descripción breve del cuaderno de light yagami.");
		System.out.println(respuesta);

	}

}
