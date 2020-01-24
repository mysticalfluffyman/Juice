package major.com.juice_android.model;

import java.util.ArrayList;

public class DisplayRatingResponse
{
    private boolean error;
    private String message;
    private Record record;

    public DisplayRatingResponse(boolean error, String message, Record record)
    {
        this.error = error;
        this.message = message;
        this.record = record;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public Record getRecord()
    {
        return record;
    }
}
