from flask import Flask, request 
import copy

app = Flask(__name__) 

smoothies = {
    "Classic": ["strawberry", "banana", "pineapple", "mango", "peach", "honey"],
    "Freezie":  ["blackberry", "blueberry", "black currant", "grape juice", "frozen yogurt"],
    "Greenie": ["green apple", "lime", "avocado", "spinach", "ice", "apple juice"],
    "Just Desserts":  ["banana", "ice cream", "chocolate", "peanut", "cherry"]
} #meu dicionário que serve como meu banco de dados

@app.route('/smoothies/<name>') #anotation que define a rota /smoothies/<nome do smoothie>
def get_smoothie(name): # função de recebe o nome do smoothie e retorna a lista de ingredientes
    if name not in smoothies.keys(): # se o nome recebido não está contido no meu banco de dados
        raise Exception("IllegalArgumentException") # estouro um erro chamado "IllegalArgumentException"
    smoothie = smoothies[name] # busco os ingredientes do smoothie no meu banco de dados
    ingredients_to_return = copy.deepcopy(smoothie) # uso a biblioteca copy para fazer um deep copy(cópia real em memória para não alterar o meu banco de dados)
    if 'dietary' in request.args: # se o query parameter dietary está presente
        forbidden_ingredients_string_list = request.args['dietary'] # busco a lista de ingredientes que eu quero retirar através do query parameter "dietary"
        forbidden_ingredients_list = forbidden_ingredients_string_list.split(',') # separo os itens da minha lista por vírgula
        for item in forbidden_ingredients_list: # itero a minha lista de ingredientes restritos
            if '-' not in item: # se o ingrediente restrito não contém "-"
                raise Exception("IllegalArgumentException") # estouro erro "IllegalArgumentException"
            else: # caso contrário
                try: # começo o bloco de try and catch
                    idx = ingredients_to_return.index(item[1:]) # busco o ingrediente restrino na minha lista de ingredientes. Se ele não existir, o erro será estourado e pego pelo catch. Começo a busca pelo ingrediente restrito a partir do segundo caracter, pois o primeiro é o "-".
                    ingredients_to_return.pop(idx) # restiro o ingrediente restrito da lista de ingredientes
                except: # caso o ingrediente restrito não seja encontrado e um erro seja estourado, o except receberá o erro sem dar erro para o usuário
                    continue # continuo a iteração sem erro para o usuário
    ingredients_to_return.sort() # ordeno a lista de ingredientes antes de retornar
    return ingredients_to_return # retorno a lista

@app.errorhandler(Exception) # anotation que lida com erros
def handle_error(e: Exception): # função que recebe o erro e retorna uma resposta tratada para o usuário
    if len(e.args) > 0: # se a exception tiver mensagem 
        return e.args[0], 400 # retorno a mensagem da exception com status code 400 (bad request)
    return 'IllegalArgumentException', 400 # caso contrário retorno a mensagem 'IllegalArgumentException' com status code 400 (bad request)
