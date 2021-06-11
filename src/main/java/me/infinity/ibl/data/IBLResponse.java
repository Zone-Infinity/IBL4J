package me.infinity.ibl.data;

/**
 * Response from IBL which contains message and response code
 */
public class IBLResponse {
    private final String message;
    private final boolean error;
    private final int code;

    /**
     * @param message The message got from ibl res.json
     * @param error   true if there's a error
     * @param code    The code got from ibl res.json
     */
    public IBLResponse(String message, boolean error, int code) {
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
     * @return true if there's a error
     */
    public boolean hasError() {
        return error;
    }

    /**
     * @return response code
     */
    public int getCode() {
        return code;
    }
}
