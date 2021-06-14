package me.infinity.ibl.data;

/**
 * Response from IBL which contains message and response code
 */
public class IBLResponse {
    private String message;
    private boolean error;
    private int status;

    public IBLResponse() {
    }

    /**
     * @param message The message got from ibl res.json
     * @param error   true if there's a error
     * @param status  The status got from ibl res.json
     */
    public IBLResponse(String message, boolean error, int status) {
        this.message = message;
        this.status = status;
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
    public int getStatus() {
        return status;
    }
}
