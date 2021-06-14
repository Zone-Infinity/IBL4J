import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonIgnoreProperties(ignoreUnknown = true)
class Something {
    private String message;
    private boolean error;

    private Something() {
    }

    public String getMessage() {
        return message;
    }

    public boolean hasError() {
        return error;
    }
}

public class JsonToClassTest {
    public static void main(String[] args) throws JsonProcessingException {
        String json = "{\"message\":\"Hello\",\"error\":false}";
        ObjectMapper mapper = new ObjectMapper();
        final Something something = mapper.readValue(json, Something.class);
        System.out.println(something.getMessage());
        System.out.println(something.hasError());
    }
}
