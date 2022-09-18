package com.funcional.smoothie.inc.smoothiemachine.smoothie.machine.configuration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.funcional.smoothie.inc.smoothiemachine.smoothie.machine.dtos.SmoothieDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Configuration // anotation que diz que essa classe é uma classe de configuração e portanto deverá ser executada antes da api iniciar
public class DatabaseConfiguration {

    @Autowired // busca o objeto do repositório compartilhado para poder usar
    private ObjectMapper mapper; // sua função é converter string para objeto e objeto para string

    @Bean // esse anotation diz que o resultado dessa função será armazenado no repositório compartilhado
    public List<SmoothieDTO> getDatabase() {
        try {
            String location = "src/main/resources/smoothies.json"; // defino meu caminho
            Path path = Paths.get(location); // pego meu caminho
            List<String> lines = Files.readAllLines(path); // leio todas as linhas
            String jsonString = String.join("", lines); // pego todas as linhas do arquivo e coloco tudo junto em uma só string
            return mapper.readValue(jsonString, new TypeReference<>(){}); // transformo a string em uma lista de smoothieDTO
        } catch (Exception e) { // se eu não conseguir ler o arquivo, eu estouro uma exception na inicialização da api
            throw new RuntimeException("Unable to read smoothies.json string"); // meu erro
        }
    }
}
