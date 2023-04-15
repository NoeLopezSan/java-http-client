package dev.noelopez.http.demo3;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Base64;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpClientFileUpload {
    public static void main(String[] args) throws IOException, InterruptedException {
        var client = HttpClient
                .newBuilder()
                .connectTimeout(Duration.ofMillis(5000))
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        // upload with File Publisher
        Path file = Paths.get("C:/workspace/files/trees.jpg");
        var request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/documents"))
                .header("Content-Type", "image/jpg")
                .header("fileName", file.getFileName().toString())
                .header("Authorization", getBasicAuthenticationHeader("admin1234","password5678"))
                .POST(HttpRequest.BodyPublishers.ofFile(file))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.printf("Status %s \n", response.statusCode());
        System.out.printf("Headers %s \n", response.headers().firstValue("location"));

        // upload with ByteArray Publisher
        String fileName = "C:/workspace/files/java_tutorial.pdf";
        request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v2/documents"))
                .header("Content-Type", "image/png")
                .header("filename", fileName.substring(fileName.lastIndexOf("/")))
                .header("Authorization", getBasicAuthenticationHeader("admin1234","password5678"))
                .POST(HttpRequest.BodyPublishers.ofByteArray(new FileInputStream(fileName).readAllBytes()))
                .build();

        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.printf("Status %s \n", response.statusCode());
        System.out.printf("Headers %s \n", response.headers().firstValue("location"));
    }

    private static final String getBasicAuthenticationHeader(String username, String password) {
        String valueToEncode = username + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(valueToEncode.getBytes());
    }
}
