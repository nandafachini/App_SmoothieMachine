package com.funcional.smoothie.inc.smoothiemachine.smoothie.machine.controllers;

import com.funcional.smoothie.inc.smoothiemachine.smoothie.machine.dtos.SmoothieDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController // anotation que diz que essa classe contém funções que escutam em algumas rotas
public class SmoothieController {

    @Autowired // pega o bin do repositório compartilhado
    private List<SmoothieDTO> smoothieDTOS;

    @GetMapping("/smoothies") // método GET na rota /smoothies que retorna uma lista de smoothiesDTO (que é o meu banco de dados)
    public Mono<List<SmoothieDTO>> listSmoothies() {
        return Mono.just(smoothieDTOS);
    } // uso o tipo mono para poder retornar a lista de uma maneira assíncrona usando o mono

    @GetMapping("/smoothies/{smoothieName}") // método GET na rota /smoothies/<nome do smoothie>
    public Mono<List<String>> getIngredients(
            @PathVariable("smoothieName") String smoothieName, // pega o valor da variável da smoothieName
            @RequestParam(value = "dietary", required = false) String dietaryListString // pega o valor do query parameter de nome dietary (que é opcional)
    ) {
        return Mono.fromCallable(() -> { // retorna um mono que tem como resultado o resultado da funcao
            ArrayList<String> ingredientsToReturn = new ArrayList<>(); // essa variável é a lista de ingredientes que irei retornar futuramente
            for (SmoothieDTO smoothieDTO : this.smoothieDTOS) { // estou iterando cada item do banco de dados
                if (smoothieDTO.getName().equals(smoothieName)) { // se o nome do smoothie que eu tenho no meu banco de dados é igual ao nome do smoothie que o usuário pediu
                    ingredientsToReturn.addAll(smoothieDTO.getIngredients()); // adiciono todos os ingredientes do smoothie do banco de dados na minha lista para retornar
                    break; // se eu já encontrei o smoothie, não preciso continuar a iteração, paro por aqui
                }
            }
            if (ingredientsToReturn.isEmpty()) { // se a minha lista de ingredientes continua vazia após olhar o banco de dados inteiro
                throw new IllegalArgumentException(); // significa que o usuário pediu um smoothie que eu não tenho no meu banco
            }
            if (dietaryListString != null) { // se o usuário colocou alguma restrição
                String[] dietaryIngredients = dietaryListString.split(","); // busco todas as restrições e crio uma lista separando por vírgula
                for (String dietaryIngredient : dietaryIngredients) { // itero a lista de ingredientes restritos
                    if (!dietaryIngredient.startsWith("-")) { // se o ingrediente restrito não começa com "-"
                        throw new IllegalArgumentException(); // estouro erro
                    } else { // caso contrário
                        int idx = ingredientsToReturn.indexOf(dietaryIngredient.substring(1)); // descrobindo o index do ingrediente restrito na lista de ingredientes que vou retornar para poder retirá-lo. Faço substring pois o nome do ingrediente restrito começa com "-"
                        if (idx > -1) { // ao buscar o index do ingrediente restrito na minha lista de ingredientes, se o resultado for -1, quer dizer que o ingrediente restrito não está na minha lista. Qualquer valor diferente disto, significa que preciso remove-lo da lista
                            ingredientsToReturn.remove(idx); // removo ingrediente restrito da lista
                        }
                    }
                }
            }
            return ingredientsToReturn.stream().sorted().collect(Collectors.toList()); // retorno a lista ordenada
        });
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST) // anotation que diz que o resultado da execução dessa função será um bad request
    @ExceptionHandler({IllegalArgumentException.class}) // anotation que diz que essa função é responsável por lidar com exceptions do tipo "IllegalArgumentException"
    public Mono<String> handleIllegalArgumentException(IllegalArgumentException e) { // recebo uma exception do tipo "IllegalArgumentException" e devo retornar um Mono do tipo String contendo uma resposta para o usuário
        return Mono.just("IllegalArgumentException"); // retorno a resposta para o usuário como sendo "IllegalArgumentException"
    }
}
