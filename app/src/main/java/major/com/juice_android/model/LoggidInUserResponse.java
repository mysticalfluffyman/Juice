package major.com.juice_android.model;

public class LoggidInUserResponse
{
    private boolean error;
    private User user;

    public LoggidInUserResponse(boolean error, User user)
    {
        this.error = error;
        this.user = user;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
