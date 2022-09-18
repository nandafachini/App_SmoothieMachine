package com.funcional.smoothie.inc.smoothiemachine.smoothie.machine.dtos;

import java.util.List;
// esta classe é responsável por representar o dado do banco de dados

public class SmoothieDTO {
    private String name;
    private List<String> ingredients;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
