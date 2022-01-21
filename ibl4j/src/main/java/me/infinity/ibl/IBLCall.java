package me.infinity.ibl;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.infinity.ibl.data.IBLResponse;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.function.Consumer;

@SuppressWarnings("ConstantConditions")
public class IBLCall {

    private static final OkHttpClient client = new OkHttpClient();
    private static final ObjectMapper mapper = new ObjectMapper();

    private IBLCall() {
    }

    /**
     * Fetch data from a rest api
     *
     * @param request      the {@link Request}
     * @param responseType the data class of the response, example: {@link me.infinity.ibl.data.entities.IBLBot} and {@link me.infinity.ibl.data.IBLResponse}
     * @param afterTask the task with the data class, you want to do after the posting is done
     * @param <T> the data class of the response
     */
    public static <T> void fetch(Request request, Class<T> responseType, Consumer<ResponseT<T>> afterTask, Consumer<IOException> errorCallback) {
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                errorCallback.accept(e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try (ResponseBody body = response.body()) {
                    String string = body.string();
                    int code = response.code();
                    ResponseT<T> responseT = new ResponseT<>(mapper.readValue(string, responseType), code);

                    afterTask.accept(responseT);
                }
                response.close();
            }
        });
    }

    /**
     * Shuts down the okhttp client
     */
    public static void shutdownClient() {
        client.connectionPool().evictAll();
        client.dispatcher().executorService().shutdown();
    }

    /**
     * A response data class
     *
     * @param <T> the type of response, example: {@link me.infinity.ibl.data.entities.IBLBot} and {@link me.infinity.ibl.data.entities.IBLUser}
     */
    public static class ResponseT<T> {
        private final T response;
        private final int code;

        public ResponseT(T response, int code) {
            this.response = response;
            this.code = code;
        }

        /**
         * @return the data class of the response
         */
        public T getResponse() {
            return response;
        }

        /**
         * @return the response code
         */
        public int getCode() {
            return code;
        }
    }
}
