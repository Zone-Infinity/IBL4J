package me.infinity.ibl;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.io.UncheckedIOException;

public class IBLCall {

    private static final OkHttpClient client = new OkHttpClient();
    private static final ObjectMapper mapper = new ObjectMapper();

    private IBLCall() {
    }

    public static <T> ResponseT<T> fetch(Request request, Class<T> responseType) {
        try (Response response = client.newCall(request).execute()) {
            return new ResponseT<>(
                    mapper.readValue(response.body().string(), responseType),
                    response.code()
            );
        } catch (IOException e) {
            // Something wrong in my codes ;-;
            throw new UncheckedIOException("Report to Zone_Infinity#0062 on discord.", e);
        }
    }


    public static class ResponseT<T> {
        private final T response;
        private final int code;

        public ResponseT(T response, int code) {
            this.response = response;
            this.code = code;
        }

        public T getResponse() {
            return response;
        }

        public int getCode() {
            return code;
        }
    }
}
