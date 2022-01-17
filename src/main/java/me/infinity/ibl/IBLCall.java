package me.infinity.ibl;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.function.Consumer;

@SuppressWarnings("ConstantConditions")
public class IBLCall {

    private static final OkHttpClient client = new OkHttpClient();
    private static final ObjectMapper mapper = new ObjectMapper();

    private IBLCall() {
    }

    public static <T> void fetch(Request request, Class<T> responseType, Consumer<ResponseT<T>> responseConsumer) {
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                // Something wrong in my codes ;-;
                throw new UncheckedIOException("Report to Zone_Infinity#0062 on discord.", e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try (ResponseBody body = response.body()) {
                    String string = body.string();
                    int code = response.code();
                    ResponseT<T> responseT = new ResponseT<>(mapper.readValue(string, responseType), code);

                    responseConsumer.accept(responseT);
                }
                response.close();
            }
        });
    }

    public static void shutdownClient() {
        client.connectionPool().evictAll();
        client.dispatcher().executorService().shutdown();
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
