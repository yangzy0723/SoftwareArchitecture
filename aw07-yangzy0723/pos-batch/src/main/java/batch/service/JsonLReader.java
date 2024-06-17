package batch.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.batch.item.ItemReader;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class JsonLReader<T> implements ItemReader<T> {

    public static int lineNum = 0;
    private final BufferedReader reader;
    private final ObjectMapper objectMapper;
    private final Class<T> targetType;


    public JsonLReader(ClassPathResource classPathResource, Class<T> targetType) {
        try {
            this.reader = new BufferedReader(new FileReader(classPathResource.getFile()));
            this.objectMapper = new ObjectMapper();
            this.targetType = targetType;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public T read() throws Exception {
        String line = reader.readLine();
        if (line == null) {
            return null; // EOF reached
        }
        try {
            return objectMapper.readValue(line, targetType);
        } catch (IOException e) {
            System.out.println("Error reading JSONL line: " + line);
            return null;
        }
    }
}