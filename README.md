# Cyborg Data API

### 🎯 API para Processamento de Arquivos de Funcionários e Geração de Relatórios

Este projeto é uma API desenvolvida em **Spring Boot** que processa arquivos de funcionários de empresas e gera relatórios estruturados em **JSON**. A API também permite acompanhar o status do processamento em tempo real.

---

## 🛠 Tecnologias Utilizadas

- **Java 17** + **Spring Boot**
- **Spring Web** (para construção da API REST)
- **Spring Async** (para processamento assíncrono)
- **Spring Data JPA** (para persistência de dados)
- **WebClient** (para chamadas HTTP externas)
- **H2database** (para persistência dos dados)

---

## 📌 Funcionalidades da API

### 1️⃣ Obter Arquivos Pendentes  
Consulta uma API externa para recuperar arquivos pendentes de processamento.

Na primeira etapa do sistema consultamos uma **API externa**, onde os dados retornados serão utilizados para próxima etapa.

**Endpoint:**  
```http
GET /api/pendente

[
  {
    "hash": "8223bb13-23d8-4831-ad8a-12359731064c",
    "company": "Valdez Comercial Ltda."
  }
]
```

---

### 2️⃣ Iniciar Processamento de um Arquivo
Após uma requisição bem-sucedida para a rota **/api/pendente**, o processamento dos dados é iniciado de forma assíncrona. Esses dados serão utilizados na geração do relatório, mas, para garantir transparência e acompanhamento, é importante sempre exibir o status do processamento dos arquivos.

Para isso, implementamos uma rota que permite consultar o status do processamento usando o hash obtido na etapa anterior. Isso possibilita visualizar o progresso individual de cada empresa durante o processamento.

**Endpoint:**  
```http
GET /api/lot/{hash}

{
  "company": "Valdez Comercial Ltda.",
  "runtime": "1 minutos",
  "processing": "100%",
  "status": "Completed"
}
```
Para facilitar o entendimento, vamos revisar os passos até agora. Inicialmente, realizamos uma requisição para uma **API externa**. Em seguida, o processamento dos arquivos e a leitura dos dados começam de forma **assíncrona**, 
com o download do arquivo **.csv**, no qual são capturados os dados dos **funcionários** das **empresas** listadas. Com essas informações em mãos, podemos agora gerar os **relatórios**.

---

### 3️⃣ Obter Relatório Final
Nesta etapa, exibiremos os **relatórios** gerados para as empresas listadas após a primeira requisição. Serão apresentados os **relatórios** de todas as empresas processadas.

É importante considerar que, devido ao grande volume de dados nos arquivos **.csv**, o processamento pode levar algum tempo. Caso a requisição para exibição dos relatórios seja feita antes da conclusão do processamento, a API deve retornar uma resposta **informando** que os relatórios **ainda estão sendo gerados**. Isso garante que o usuário esteja ciente do andamento do processo.

Se desejar acompanhar o status do processamento, basta utilizar a rota **/api/lot/{hash}**, passando o hash da empresa correspondente.

**Endpoint:**  
```http
GET /api/rel

{
    "empresas": [
        {
            "country": "brasil",
            "count_employee": 167,
            "size_file": "150.62 mb",
            "runtime": "15 minutos",
            "count_employee_city": {
                "city": "Porto Alegre",
                "state": "RS",
                "kids": 12,
                "young": 55,
                "adult": 100
            },
            "count_employee_state": {
                "state": "RS",
                "kids": 12,
                "young": 55,
                "adult": 100
            }
        }
    ]
}
```

---

### 4️⃣ Dificuldades e Considerações importantes
Primeiramente, **agradeço pela oportunidade de realizar o teste** 😄. Gostaria de destacar, de forma resumida, as principais dificuldades que encontrei durante o desenvolvimento.

Uma das maiores dificuldades foi a leitura dos arquivos **.csv**, principalmente devido ao seu tamanho e formatação, o que tornou o processamento mais complexo. 
Tive problemas ao percorrer as linhas da tabela para capturar os valores necessários para a geração dos relatórios. Por esse motivo, a rota **/api/rel** **não está funcionando**!!

Além disso, tive alguns problemas ao salvar os valores no banco de dados, então optei por armazená-los em **disco** para garantir que o download fosse realizado corretamente. 
Como não consegui concluir a geração dos **relatórios**, decidi marcar o status do processamento como **"Completed"** assim que o download do arquivo for finalizado com **sucesso**.

---

### 🏗️ Passos para Rodar
1️⃣ Clone o repositório:
```
git clone git@github.com:GiancarloPagliarini/cyborgdata.git
cd cyborgdata
```

2️⃣ Compile e execute o projeto via Maven:
```
mvn spring-boot:run
```
3️⃣ A API estará disponível em:
```
http://localhost:8080/api
```
