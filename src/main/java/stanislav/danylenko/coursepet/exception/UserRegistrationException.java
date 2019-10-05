package stanislav.danylenko.coursepet.exception;

import java.io.IOException;

public class UserRegistrationException extends IOException {
    public UserRegistrationException(String e) {
        super(e);
    }
}
