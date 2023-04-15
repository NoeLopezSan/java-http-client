package dev.noelopez.http.demo1;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpClientDelete extends HttpClientBase {
    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClientDelete demo = new HttpClientDelete();
        HttpClient client = demo.httpClient();
        HttpRequest request = demo.httpRequestDELETE(2);

        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.printf("Status %s \n", response.statusCode());
        System.out.printf("Response %s \n", response.body());

//        var responseFuture = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
//        responseFuture
//                .thenApply(HttpResponse::body)
//                .thenApply(String::toUpperCase)
//                .thenAccept(System.out::println)
//                .join();
    }
}
