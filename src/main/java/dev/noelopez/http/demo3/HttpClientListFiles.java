package dev.noelopez.http.demo3;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Base64;

public class HttpClientListFiles {
    public static void main(String[] args) throws IOException, InterruptedException {
        var client = HttpClient
                .newBuilder()
                .connectTimeout(Duration.ofMillis(5000))
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/documents"))
                .header("Authorization", getBasicAuthenticationHeader("admin1234","password5678"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.printf("Response %s \n", response.body());
    }

    private static final String getBasicAuthenticationHeader(String username, String password) {
        String valueToEncode = username + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(valueToEncode.getBytes());
    }
}
