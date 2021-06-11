# #2 Authentication with JWT

<img src="https://miro.medium.com/max/856/1*O68LbDvD5Dcsnez73M7v4Q.png" alt="linguagem" width="80px"/>

### Problemática

criar um API que rode na porta 3333 que tenha um C.R.U.D. de Usuário e que para acessar os R.U.D. o usuário deve ter um token fornecido por uma autenticação feita com o JWT.

**Recursos**
- `HTTP/1.1 POST /users`

<img src="./screenshots/screen1.png" />

- `HTTP/1.1 POST /token`

<img src="./screenshots/screen2.png" />

- `HTTP/1.1 GET /users`

<img src="./screenshots/screen3.png" />

- `HTTP/1.1 PUT /users`

<img src="./screenshots/screen4.png" />

- `HTTP/1.1 DELETE /users`

<img src="./screenshots/screen5.png" />


### Starting

- **Instalando dependências**

  Para instalar as dependências basta rodar o seguinte comando:
  ```shellscript
  ./mvnw package
  ```

- **Inicializando Aplicação**

  Para inicializar a aplicação basta rodar o seguinte comando:
  ```shellscript
  ./mvnw spring-boot:run
  ```


- **Testando aplicação**

  Para inicializar os tests basta rodar o seguinte comando no terminal:
  ```shellscript
  ./mvnw test
  ```
