/**
 * Archivo para la conexión de la ia, desde aquí mandaremos el prompt con lo que queremos enviar y que nos devuelva.
 * Autor: Joan Esteban Londoño Hernández
 * Versión: 1.0.0.0 - 13/06/2025
 */

package service;

import java.net.URI;
import java.util.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.*;
import util.Config;

public class LlmService {

    // URL de la API de OpenRouter
    private static final String API_URL = "https://openrouter.ai/api/v1/chat/completions";

    // Modelo y clave API cargados desde config.properties
    private static final String MODEL = Config.get("openrouter.model");
    private static final String API_KEY = Config.get("openrouter.api.key");

    private final HttpClient httpClient;
    private final Gson gson;

    // Constructor: inicializa el cliente HTTP y la librería Gson
    public LlmService() {
        httpClient = HttpClient.newHttpClient();
        gson = new Gson();
    }

    // Envía un prompt al modelo LLM y devuelve la respuesta generada
    public String enviarPrompt(String prompt) {
        // Verifica que la API key esté configurada
        if (API_KEY == null || API_KEY.isEmpty()) {
            return "Error: La API Key no está configurada (config.properties).";
        }

        try {
            // Construye el mensaje de entrada para el modelo
            Map<String, Object> mensaje = Map.of(
                "role", "user",
                "content", prompt
            );

            // Cuerpo completo de la solicitud (modelo + mensajes)
            Map<String, Object> cuerpo = Map.of(
                "model", MODEL,
                "messages", List.of(mensaje)
            );

            // Convierte el cuerpo a JSON usando Gson
            String json = gson.toJson(cuerpo);

            // Construye la solicitud HTTP POST
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Authorization", "Bearer " + API_KEY)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

            // Envía la solicitud y obtiene la respuesta como texto
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Si hay error, lo devuelve
            if (response.statusCode() != 200) {
                return "Error en la respuesta del modelo: " + response.body();
            }

            // Convierte la respuesta JSON y extrae el contenido generado
            JsonObject jsonResponse = gson.fromJson(response.body(), JsonObject.class);
            return jsonResponse
                    .getAsJsonArray("choices")
                    .get(0)
                    .getAsJsonObject()
                    .getAsJsonObject("message")
                    .get("content")
                    .getAsString();

        } catch (Exception e) {
            // Captura cualquier error durante el proceso
            return "Error al contactar con el modelo: " + e.getMessage();
        }
    }
}
