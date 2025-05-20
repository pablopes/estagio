# 📌 Como Utilizar o Swagger para Enviar uma Requisição

Esta seção explica como utilizar o **Swagger UI** da aplicação para enviar uma requisição `POST /tournaments`, que recebe arquivos CSV via `multipart/form-data`.

---

## ✅ Acessando o Swagger UI

1. Certifique-se de que a aplicação está rodando.
2. Acesse o Swagger UI pelo navegador:
```
http://localhost:8080/swagger-ui.html
```


---

## 🔗 Endpoint Disponível

| Método | Endpoint | Descrição |
|--------|---------|-----------|
| `POST` | `/tournament` | Envia arquivos CSV para criação de um torneio |

---

## 📂 Enviando a Requisição

1. No **Swagger UI**, localize o endpoint `POST /tournaments`.
2. Clique em **"Try it out"** para habilitar a edição dos parâmetros.
3. Na seção de **Body**, selecione o tipo `multipart/form-data`.
4. Adicione os arquivos CSV nos campos corretos:
- `teamsFile` → Arquivo contendo as equipes (`.csv`).
- `matrizFile` → Arquivo contendo a matriz (`.csv`).
5. Após adicionar os arquivos, clique em **"Execute"** para enviar a requisição.

---

## 🛠 Exemplo de Requisição via `curl`

Caso prefira testar sem o Swagger UI, você pode enviar a requisição via **cURL**:

```sh
curl -X POST "http://localhost:8080/tournaments" \
-H "Content-Type: multipart/form-data" \
-F "teamsFile=@teams.csv" \
-F "matrizFile=@matriz.csv"
