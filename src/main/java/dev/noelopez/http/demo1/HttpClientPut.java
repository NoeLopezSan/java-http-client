package dev.noelopez.http.demo1;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpClientPut extends HttpClientBase {
    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClientPut demo = new HttpClientPut();
        HttpClient client = demo.httpClient();

        String json = "{\"name\":\"Victor Martin\",\"email\":\"victor.martin@mail.com\",\"dateOfBirth\":\"1977-04-15\"}";
        HttpRequest request = demo.getHttpRequestPUT(4,json);

        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.printf("Status %s \n", response.statusCode());
        System.out.printf("Body %s \n", response.body());

//        var responseFuture = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
//        responseFuture
//                .thenApply(HttpResponse::body)
//                .thenApply(String::toUpperCase)
//                .thenAccept(System.out::println)
//                .join();
    }
}
