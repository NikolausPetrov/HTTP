package ru.netology;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Request {
    private final String method;
    private final String path;
    private final Map<String, String> queryParams;

    public Request(String requestLine) {
        String[] parts = requestLine.split(" ");
        this.method = parts[0];
        String fullPath = parts[1];
        int queryIndex = fullPath.indexOf('?');
        if (queryIndex != -1) {
            this.path = fullPath.substring(0, queryIndex);
            String queryString = fullPath.substring(queryIndex + 1);
            List<NameValuePair> params = URLEncodedUtils.parse(queryString, StandardCharsets.UTF_8);
            this.queryParams = params.stream()
                    .collect(Collectors.toMap(NameValuePair::getName, NameValuePair::getValue));
        } else {
            this.path = fullPath;
            this.queryParams = Map.of();
        }
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getQueryParam(String name) {
        return queryParams.get(name);
    }

    public Map<String, String> getQueryParams() {
        return queryParams;
    }
}