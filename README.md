# 🏆 API - Otimização de Tabelas de Campeonato com Algoritmos Genéticos

### 🚀 Sobre o Projeto
Esta API utiliza **algoritmos genéticos** para otimizar a montagem de **tabelas de campeonatos de futebol**, reduzindo a distância percorrida pelas equipes e minimizando custos operacionais.

---

## 📖 Descrição
A API permite:
✅ Gerar tabelas otimizadas para campeonatos com base em critérios logísticos  
✅ Minimizar deslocamentos das equipes ao longo do torneio  
✅ Aplicar técnicas de **algoritmos genéticos** para encontrar soluções eficientes

---

## ✅ Pré-requisitos
Antes de rodar a API, certifique-se de ter instalado:

- [Java 17+](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
- [Maven](https://maven.apache.org/download.cgi)
- [Docker](https://www.docker.com/)
- [Swagger](https://swagger.io/)

---

## 🚀 Como executar
1️⃣ **Clone o repositório**
```sh
git clone https://github.com/seu-usuario/api-tabelas-geneticas.git
cd api-tabelas-geneticas
```

2️⃣ **Acessar a pasta do projeto**
```sh
cd C:/[pasta-do-projeto]/estagio
```

### 🐳 **Com Docker**
Se desejar rodar a API dentro de um **container Docker**, siga estes passos:

2️⃣ **Compilando JAR**
```sh
mvn clean package
```
3️⃣ **Iniciar Container**
```sh
docker image build -t estagio .
docker container run --rm -p 8080:8080 estagio
```
### 💻 **Sem Docker (Modo Local)**
Caso prefira rodar a API diretamente em sua máquina:

2️⃣ **Compile e execute a aplicação**
```sh
java -jar .\estagio-1.0.0.jar
```

### 🐳 **Acessar a interface**
1️⃣ **Acesse os endpoints via Swagger UI**
```
http://localhost:8080/swagger-ui.html
```

## 🔗 Endpoints da API
A API expõe endpoints para otimização das tabelas:

| Método | Endpoint      | Descrição |
|--------|---------------|-----------|
| `POST` | `/tournament` | Cria uma tabela personalizada conforme parâmetros do usuário |
---

## 📊 Algoritmo Utilizado
Esta API aplica **algoritmos genéticos** para encontrar tabelas de campeonatos otimizadas. O fluxo do algoritmo inclui:
1. **Representação cromossômica** para modelar os jogos.
2. **Função de avaliação**, que mede as distâncias percorridas.
3. **Seleção dos indivíduos**, utilizando o método de roleta.
4. **Cruzamento** e **mutação** para gerar novas soluções.
5. **Otimização iterativa**, garantindo melhores resultados ao longo das gerações.

---

## 🛠 Tecnologias Utilizadas
Este projeto foi desenvolvido utilizando:
- **Java 17**
- **Spring Boot**
- **Docker**
- **Swagger OpenAPI**
- **Maven**

---

## 👨‍💻 Autor
- **Pablo Fernando Lopes Costa** - [GitHub](https://github.com/pablopes)

---

## 📝 Licença
Este projeto está licenciado sob **Creative Commons BY 4.0**  
🔗 [Link da licença](https://creativecommons.org/licenses/by/4.0/)

---
