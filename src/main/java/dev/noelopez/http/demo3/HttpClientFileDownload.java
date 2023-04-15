package dev.noelopez.http.demo3;

import java.io.IOException;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.*;
import java.time.Duration;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpClientFileDownload {
    public static void main(String[] args) throws IOException, InterruptedException {
        var client = HttpClient
                .newBuilder()
                .connectTimeout(Duration.ofMillis(5000))
                    .authenticator(new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication("admin1234", "password5678".toCharArray());
                        }
                    })
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        // download file
        var request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/documents/2"))
                .GET()
                .build();

        // Byte Array
        HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());
        String headerContentDisposition = (String)response.headers().firstValue("content-disposition").get();
        String fileName = "C:/workspace/files/"+ getFileName(headerContentDisposition);
        Files.write(Paths.get(fileName),response.body(), StandardOpenOption.CREATE);

        // Path
        HttpResponse<Path> responsePath = client.send(request, HttpResponse.BodyHandlers.ofFile(Path.of("C:/workspace/files/DownloadedFile.jpg")));

        // Input Stream
        HttpResponse<InputStream> responseIS = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
        headerContentDisposition = (String)responseIS.headers().firstValue("content-disposition").get();
        fileName = "C:/workspace/files/"+ getFileName(headerContentDisposition);

        Files.copy(responseIS.body(), Paths.get(fileName), StandardCopyOption.REPLACE_EXISTING);
    }

    private static String getFileName(String contentDisposition) {
        Objects.requireNonNull(contentDisposition);
        String fileName = "";
        Pattern p = Pattern.compile(".+filename=\"(.+?)\".*");

        if(contentDisposition != null) {
            Matcher m = p.matcher(contentDisposition);
            if(m.find()) {
                fileName = m.group(1);
            }
        }
        System.out.println("fileName = " + fileName);
        return fileName;
    }
}
