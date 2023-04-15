package dev.noelopez.http.demo1;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpClientPost extends HttpClientBase {
    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClientPost demo = new HttpClientPost();
        HttpClient client = demo.httpClient();

        String json = "{\"name\":\"Sonia Lamar\",\"email\":\"sonia.lamar@mail.com\",\"dateOfBirth\":\"1998-07-29\"}";
        HttpRequest request = demo.getHttpRequestPOST(json);

        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.printf("Status %s \n", response.statusCode());
        System.out.printf("Location %s \n", response.headers().map().get("location"));

        var responseFuture = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        responseFuture
                .thenApply(HttpResponse::body)
                .thenApply(String::toUpperCase)
                .thenAccept(System.out::println)
                .join();
    }
}
