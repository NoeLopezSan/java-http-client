package dev.noelopez.http.demo1;

import java.io.IOException;
import java.net.http.HttpResponse;

public class HttpClientGet extends HttpClientBase {
    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClientGet demo = new HttpClientGet();
        var client =  demo.httpClient();
        var request = demo.httpRequestGET();

        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.printf("Status %s \n", response.statusCode());
        System.out.printf("Body %s \n", response.body());

        var responseFuture = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        responseFuture
                .thenApply(HttpResponse::body)
                .thenApply(String::toUpperCase)
                .thenAccept(System.out::println)
                .join();
    }
}
