package major.com.juice_android.model;

import java.util.ArrayList;

public class InsertRatingResponse
{
    private boolean error;
    private String message;

    public InsertRatingResponse(boolean error, String message)
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
