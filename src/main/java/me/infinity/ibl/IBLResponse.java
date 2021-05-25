package me.infinity.ibl;

/**
 * Response from IBL which contains message and response code
 */
public class IBLResponse {
    private final String message;
    private final int code;
    private final boolean error;

    /**
     * @param message The message got from ibl res.json
     * @param code    The code got from ibl res.json
     * @param error   true if there's a error
     */
    public IBLResponse(String message, int code, boolean error) {
        this.message = message;
        this.code = code;
        this.error = error;
    }

    /**
     * @return response message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @return response code
     */
    public int getCode() {
        return code;
    }

    /**
     * @return true if there's a error
     */
    public boolean hasError() {
        return error;
    }
}
