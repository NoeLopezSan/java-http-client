package dev.noelopez.http.demo1;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.time.Duration;

public abstract class HttpClientBase {
    public static final String HTTP_CUSTOMERS_URI = "http://localhost:8080/api/v1/customers";

    public HttpClient httpClient() {
        return HttpClient
                .newBuilder()
                .connectTimeout(Duration.ofMillis(500))
                .build();
    }

    public HttpRequest httpRequestGET() {
        return HttpRequest
                .newBuilder()
                .uri(URI.create(HTTP_CUSTOMERS_URI))
                .header("Content-Type", "application/json")
                .GET()
                .build();
    }

    public HttpRequest getHttpRequestPOST(String json) {
        return HttpRequest
                .newBuilder()
                .uri(URI.create(HTTP_CUSTOMERS_URI))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
    }

    public HttpRequest getHttpRequestPUT(int customerId, String json) {
        return HttpRequest
                .newBuilder()
                .uri(URI.create(HTTP_CUSTOMERS_URI+"/"+customerId))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();
    }
    public HttpRequest httpRequestDELETE(int customerId) {
        return HttpRequest
                .newBuilder()
                .uri(URI.create(HTTP_CUSTOMERS_URI+"/"+customerId))
                .header("Content-Type", "application/json")
                .DELETE()
                .build();
    }
}
