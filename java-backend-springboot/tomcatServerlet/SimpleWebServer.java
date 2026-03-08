package tomcatServerlet;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;

/**
 * 1: Create a simple Java Web Server
 * A simple Java Web Server using the built-in HttpServer with various handlers.
 */
public class SimpleWebServer {

    public static void main(String[] args) throws IOException {
        int port = 8080;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        System.out.println("Starting Simple Java Web Server on port " + port);

        // Creates contexts for different test paths
        server.createContext("/", new RootHandler());
        server.createContext("/header", new HeaderHandler());
        server.createContext("/get", new GetHandler());
        server.createContext("/post", new PostHandler());

        // Default executor
        server.setExecutor(null);
        server.start();

        System.out.println("Server is running. Press Ctrl+C to stop.");
    }

    /**
     * RootHandler as inner class to process HTTP requests to the root path
     */
    static class RootHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = "<html><body>" +
                    "<h1>Welcome to Simple Java Web Server!</h1>" +
                    "<p>Try these endpoints:</p>" +
                    "<ul>" +
                    "<li><a href='/header'>/header</a> - to get Header Parameters</li>" +
                    "<li><a href='/get?name=Shaurya&age=20'>/get?name=Shaurya&age=20</a> - to get Query Parameters</li>"
                    +
                    "</ul>" +
                    "<p>Or try sending a POST request to <b>/post</b> to see body parameters!</p>" +
                    "</body></html>";
            sendResponse(exchange, response);
        }
    }

    /**
     * HeaderHandler to process HTTP headers
     */
    static class HeaderHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            Headers requestHeaders = exchange.getRequestHeaders();
            StringBuilder response = new StringBuilder("<html><body><h1>Request Headers</h1><ul>");

            for (String key : requestHeaders.keySet()) {
                response.append("<li><b>").append(key).append("</b>: ")
                        .append(requestHeaders.getFirst(key)).append("</li>");
            }
            response.append("</ul></body></html>");
            sendResponse(exchange, response.toString());
        }
    }

    /**
     * GetHandler to parse query parameters
     */
    static class GetHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String query = exchange.getRequestURI().getQuery();
            String response = "<html><body><h1>Get Request Query Parameters</h1>" +
                    "<p>Query: " + (query != null ? query : "No parameters provided") + "</p></body></html>";
            sendResponse(exchange, response);
        }
    }

    /**
     * PostHandler (Push Handler) to parse body parameters
     */
    static class PostHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                InputStream is = exchange.getRequestBody();
                String body = new String(is.readAllBytes());
                String response = "<html><body><h1>Post Request Body Parameters</h1>" +
                        "<p>Body: " + body + "</p></body></html>";
                sendResponse(exchange, response);
            } else {
                sendResponse(exchange, "<html><body><h1>Error: Please use POST method</h1></body></html>");
            }
        }
    }

    /**
     * Helper method to send HTTP response
     */
    private static void sendResponse(HttpExchange exchange, String response) throws IOException {
        exchange.sendResponseHeaders(200, response.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}
