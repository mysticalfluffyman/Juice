package major.com.juice_android.model;

import com.google.gson.annotations.SerializedName;

public class DefaultResponse
{
    private boolean error;
    private String message;

    public DefaultResponse(boolean error, String message)
    {
        this.error = error;
        this.message = message;
    }

    public boolean isError()
    {
        return error;
    }
    public String getMessage()
    {
        return message;
    }
}
