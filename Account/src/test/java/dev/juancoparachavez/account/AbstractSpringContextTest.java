package dev.juancoparachavez.account;

import dev.juancoparachavez.account.util.JSONUtil;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@ExtendWith(SpringExtension.class)
public abstract class AbstractSpringContextTest {

    @Configuration
    @ComponentScan(basePackages = "dev.juancoparachavez")
    static class Config {
    }

    protected static String readJSON(String pathJson) throws URISyntaxException, IOException {
        return new String(Files.readAllBytes(Paths.get(AbstractSpringContextTest.class.getResource(pathJson).toURI())));
    }

    protected <T> T convertTo(String pathJson, Class<T> type) throws IOException, URISyntaxException {
        return JSONUtil.deserialize(readJSON(pathJson), type);
    }

    protected String getJsonFromPath(String pathJson) throws Exception {
        return new String(Files.readAllBytes(Paths
                .get(getClass().getResource(pathJson).toURI())));
    }

    protected <T> T convert(String pathJson, Class<T> type) throws Exception {

        return JSONUtil.deserialize(getJsonFromPath(pathJson), type);
    }

    protected <T> List<T> convertList(String pathJson, Class<T> type) throws Exception {

        return JSONUtil.deserializeList(getJsonFromPath(pathJson), type);
    }

    protected <T> List<T> convertToList(String pathJson, Class<T> type) throws IOException, URISyntaxException {
        return JSONUtil.deserializeList(readJSON(pathJson), type);
    }
}
