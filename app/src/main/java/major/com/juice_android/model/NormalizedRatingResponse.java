package major.com.juice_android.model;

public class NormalizedRatingResponse {
    private boolean error;
    private String message;

    public NormalizedRatingResponse(boolean error, String message)
    {
        this.error = error;
        this.message = message;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
