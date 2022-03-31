import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class LineBasedJsonReader {

    private ObjectMapper objectMapper;

    public LineBasedJsonReader(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Parses a provided input in a streamed way. Converts each line in it
     * (which is supposed to be a JSON) to a specified POJO class
     * and performs an action provided as a Java 8 Consumer.
     *
     * @param stream     lined JSON input
     * @param entryClass POJO class to convert JSON to
     * @param consumer   action to perform on each entry
     * @return number of rows read
     */
    public int parseAsStream(final InputStream stream, final Class entryClass, final Consumer<? super Object> consumer) {
        long start = System.currentTimeMillis();


            try (Stream<String> lines = new BufferedReader(new InputStreamReader(stream)).lines()) {
            lines
                    .map(line -> {
                        try {
                            return objectMapper.readerFor(entryClass).readValue(line);
                        } catch (IOException e) {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .forEach(consumer);
        }
        return 0;
    }
}
