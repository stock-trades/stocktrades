package io.stocktrades.util;

import io.stocktrades.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;


    @Component
    @Slf4j
    @RequiredArgsConstructor
    public class HttpClient {

        private  final RestTemplate restTemplate;


        /**
         * Performs an HTTP GET request to the specified URL.
         *
         * @param url API URL to make request to
         * @param token JWT token to include to the request
         * @param type response type
         * @param <T> type of the response class
         * @return response result as a {@link ResponseEntity} of type <code>&lt;T&gt;</code>
         */
        public <T> ResponseEntity<T> get(
                @NonNull String url, String token, ParameterizedTypeReference<T> type) {
            log.debug("Calling {}", url);
            HttpEntity<T> request = createEmptyEntity(token);
            return restTemplate.exchange(url, HttpMethod.GET, request, type);
        }

        /**
         * Performs an HTTP GET request to the specified URL.
         *
         * @param url API URL to make request to
         * @param token JWT token to include to the request
         * @param customHeaders custom header
         * @param type response type
         * @param <T> type of the response class
         * @return response result as a {@link ResponseEntity} of type <code>&lt;T&gt;</code>
         */
        public <T> ResponseEntity<T> get(
                @NonNull String url,
                String token,
                @NonNull Map<String, String> customHeaders,
                ParameterizedTypeReference<T> type) {
            log.debug("Calling {}", url);
            HttpEntity<T> request = createEntity(null, customHeaders, token);
            return restTemplate.exchange(url, HttpMethod.GET, request, type);
        }

        /**
         * Performs an HTTP POST request to the specified URL.
         *
         * @param url API URL to make request to
         * @param token JWT token to include to the request
         * @param type response type
         * @param <T> type of the response class
         * @param <P> type of the request class
         * @return response result as a {@link ResponseEntity} of type <code>&lt;T&gt;</code>
         */
        public <T, P> ResponseEntity<T> post(
                @NonNull String url, String token, P entity, ParameterizedTypeReference<T> type) {
            HttpEntity<P> request = createEntityWithBody(entity, token);
            return restTemplate.exchange(url, HttpMethod.POST, request, type);
        }

        /**
         * Performs an HTTP POST request to the specified URL.
         *
         * @param url API URL to make request to
         * @param token JWT token to include to the request
         * @param customHeaders custom header
         * @param type response type
         * @param <T> type of the response class
         * @param <P> type of the request class
         * @return response result as a {@link ResponseEntity} of type <code>&lt;T&gt;</code>
         */
        public <T, P> ResponseEntity<T> post(
                @NonNull String url,
                String token,
                @NonNull Map<String, String> customHeaders,
                P entity,
                ParameterizedTypeReference<T> type) {
            HttpEntity<P> request = createEntity(entity, customHeaders, token);
            return restTemplate.exchange(url, HttpMethod.POST, request, type);
        }

        /**
         * Performs an HTTP POST request to the specified URL.
         *
         * @param url API URL to make request to
         * @param token JWT token to include to the request
         * @param type response type
         * @param <T> type of the response class
         * @param <P> type of the request class
         * @return response result as a {@link ResponseEntity} of type <code>&lt;T&gt;</code>
         */
        public <T, P> ResponseEntity<T> post(
                @NonNull String url, String token, ParameterizedTypeReference<T> type) {
            HttpEntity<P> request = createEmptyEntity(token);
            return restTemplate.exchange(url, HttpMethod.POST, request, type);
        }

        /**
         * Performs a HTTP POST request to the specified URL
         *
         * @param url API URL to make request to
         * @param requestEntity request body
         * @param responseType response type
         * @param <T> type of the response class
         * @return response result as a {@link ResponseEntity} of type <code>&lt;T&gt;</code>
         */
        public <T> ResponseEntity<T> post(
                @NonNull String url, HttpEntity<?> requestEntity, Class<T> responseType) {
            return restTemplate.exchange(url, HttpMethod.POST, requestEntity, responseType);
        }

        /**
         * Performs an HTTP POST request to the specified URL.
         *
         * @param url API URL to make request to
         * @param customHeaders custom header
         * @param type response type
         * @param <T> type of the response class
         * @param <P> type of the request class
         * @return response result as a {@link ResponseEntity} of type <code>&lt;T&gt;</code>
         */
        public <T, P> ResponseEntity<T> post(
                @NonNull String url,
                @NonNull Map<String, String> customHeaders,
                P entity,
                ParameterizedTypeReference<T> type) {
            HttpEntity<P> request = createEntity(entity, customHeaders);
            return restTemplate.exchange(url, HttpMethod.POST, request, type);
        }

        /**
         * Performs an HTTP PUT request to the specified URL.
         *
         * @param url API URL to make request to
         * @param token JWT token to include to the request
         * @param data request body
         * @param type response type
         * @param <T> type of the response class
         * @param <P> type of the request class
         * @return response result as a {@link ResponseEntity} of type <code>&lt;T&gt;</code>
         */
        public <T, P> ResponseEntity<T> put(
                @NonNull String url, String token, P data, ParameterizedTypeReference<T> type) {
            HttpEntity<P> request = createEntityWithBody(data, token);
            return restTemplate.exchange(url, HttpMethod.PUT, request, type);
        }

        /**
         * Performs an HTTP PUT request to the specified URL.
         *
         * @param url API URL to make request to
         * @param token JWT token to include to the request
         * @param customHeaders custom header
         * @param data request body
         * @param type response type
         * @param <T> type of the response class
         * @param <P> type of the request class
         * @return response result as a {@link ResponseEntity} of type <code>&lt;T&gt;</code>
         */
        public <T, P> ResponseEntity<T> put(
                @NonNull String url,
                String token,
                @NonNull Map<String, String> customHeaders,
                P data,
                ParameterizedTypeReference<T> type) {
            HttpEntity<P> request = createEntity(data, customHeaders, token);
            return restTemplate.exchange(url, HttpMethod.PUT, request, type);
        }

        /**
         * Performs an HTTP PATCH request to the specified URL.
         *
         * @param url API URL to make request to
         * @param token JWT token to include to the request
         * @param data request body
         * @param type response type
         * @param <T> type of the response class
         * @return response result as a {@link ResponseEntity} of type <code>&lt;T&gt;</code>
         */
        public <T, P> ResponseEntity<T> patch(
                @NonNull String url, String token, P data, ParameterizedTypeReference<T> type) {
            HttpEntity<P> request = createEntityWithBody(data, token);
            return restTemplate.exchange(url, HttpMethod.PATCH, request, type);
        }

        public <T, P> ResponseEntity<T> patch(
                @NonNull String url,
                String token,
                @NonNull Map<String, String> customHeaders,
                P data,
                ParameterizedTypeReference<T> type) {
            HttpEntity<P> request = createEntity(data, customHeaders, token);
            return restTemplate.exchange(url, HttpMethod.PATCH, request, type);
        }

        /**
         * Performs an HTTP DELETE request to the specified URL.
         *
         * @param url API URL to make request to
         * @param token JWT token to include to the request
         * @param type response type
         * @param <T> type of the response class
         * @return response result as a {@link ResponseEntity} of type <code>&lt;T&gt;</code>
         */
        public <T> ResponseEntity<T> delete(
                @NonNull String url, String token, ParameterizedTypeReference<T> type) {
            HttpEntity<T> request = createEmptyEntity(token);
            return restTemplate.exchange(url, HttpMethod.DELETE, request, type);
        }

        /**
         * Performs an HTTP DELETE request to the specified URL.
         *
         * @param url API URL to make request to
         * @param token JWT token to include to the request
         * @param customHeaders custom header
         * @param type response type
         * @param <T> type of the response class
         * @return response result as a {@link ResponseEntity} of type <code>&lt;T&gt;</code>
         */
        public <T> ResponseEntity<T> delete(
                @NonNull String url,
                String token,
                @NonNull Map<String, String> customHeaders,
                ParameterizedTypeReference<T> type) {
            HttpEntity<T> request = createEntity(null, customHeaders, token);
            return restTemplate.exchange(url, HttpMethod.DELETE, request, type);
        }

        /**
         * create empty entity
         *
         * @param token JWT token to include to the request
         * @param <T> type of the httpEntity body
         * @return {@link HttpEntity}
         */
        public <T> HttpEntity<T> createEmptyEntity(String token) {
            return createEntity(null, Map.of(), token);
        }

        /**
         * create entity with body
         *
         * @param <T> type of the httpEntity body
         * @param body body instance to be added in http entity
         * @param token JWT token to include to the request
         * @return HttpEntity
         */
        public <T> HttpEntity<T> createEntityWithBody(T body, String token) {
            return createEntity(body, Map.of(), token);
        }

        /**
         * create entity
         *
         * @param <T> type of the httpEntity body
         * @param body body instance to be added in http entity
         * @param customHeaders custom header to be added in the created http entitiy object
         * @param token JWT token to include to the request
         * @return {@link HttpEntity}
         */
        public <T> HttpEntity<T> createEntity(
                T body, @NonNull Map<String, String> customHeaders, String token) {
            var headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.setBearerAuth(token);
            customHeaders.forEach(headers::add);
            return new HttpEntity<>(body, headers);
        }

        public <T> HttpEntity<T> createEntity(
                T body, @NonNull Map<String, String> customHeaders) {
            var headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            customHeaders.forEach(headers::add);
            return new HttpEntity<>(body, headers);
        }
    }

