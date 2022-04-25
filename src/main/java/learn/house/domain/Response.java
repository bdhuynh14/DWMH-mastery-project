package learn.house.domain;

import java.util.ArrayList;
import java.util.List;

public class Response {

    private ArrayList<String> messages = new ArrayList<>();

    public boolean isSuccess() {
        return messages.size() == 0;
    }

    public List<String> getErrorMessages() {
        return new ArrayList<>(messages);
    }

    public void addErrorMessage(String message) {
        messages.add(message);
    }

    public void addErrorMessages(List<String> messages) {
        messages.forEach(m -> this.messages.add(m));
    }
}