package dev.noelopez.http.demo2;

import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Base64;

public class HttpBasicAutheticationClient {

    public static void main(String[] args) throws IOException, InterruptedException {

        // Authenticator mechanism
        var client =  HttpClient.newBuilder()
                .connectTimeout(Duration.ofMillis(500))
                .authenticator(new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("user1234", "password5678".toCharArray());
                    }
                })
                .build();

        var request = HttpRequest
                .newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/customers/1"))
                .GET()
                .build();

        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.printf("Status %s \n", response.statusCode());
        System.out.printf("Body %s \n", response.body());

        // HTTP Header mechanism
        String credentials = "user1234:password5678";
        String headerValue = "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes());

        client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofMillis(500))
                .build();

        request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/customers/3"))
                .header("Authorization", headerValue)
                .GET()
                .build();

        var responseFuture = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        responseFuture
                .thenApply(HttpResponse::body)
                .thenApply(String::toUpperCase)
                .thenAccept(System.out::println)
                .join();
        }
}
