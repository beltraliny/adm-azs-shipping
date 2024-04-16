# Desafio AZShip
## Sistema de gestão de fretes
### Descrição:
<p>A AZShip está desenvolvendo um novo produto, cujo objetivo é fazer a gestão das
informações de frete dos seus clientes. Essa gestão consiste basicamente em um CRUD das
informações do frete.</p>

<p>Porém essas informações não são uniformes entre todos os clientes da AZShip, cada cliente
tem seu conjunto de propriedades que precisa ser armazenado. Por exemplo, para alguns
clientes é importante armazenar Cubagem do frete mas não é importante armazenar o Peso.</p>

<p>Seu desafio é criar um sistema que realize a gestão das informações de frete. A aplicação
deve disponibilizar uma API REST ou GraphQL com as seguintes operações:</p>

- Cadastrar Frete
- Buscar Frete (a busca deve receber apenas um parâmetro que deve ser utilizado para
buscar em todas as propriedades do frete, e deve ser paginada)
- Atualizar dados do Frete
- Remover Frete

Os dados do frete devem ser armazenados em algum Banco de Dados, porém o banco pode
ser o de sua escolha.

____

### Neste projeto:
- Java 17
- Maven
- Lombok
- Spring
- JPA
- MySQL

____

### Exemplos:

### Cliente

<details><summary>POST</summary>
    <code>localhost:8080/api/customers</code>

    {
        "name" : "Jane Doe",
        "cpfCnpj": "11111111111",
        "phoneNumber": "41911111111",
        "email": "email@email.com",
        "address": {
            "street" : "Privet Drive",
            "number" : "4",
            "neighborhood" : "Little Whinging",
            "city" : "London",
            "state" : "Surrey",
            "country" : "United Kingdom",
            "complement" : "Under the stairs",
            "postalCode" : "WD3 4EG"
            }
    }

</details>
<br />
<details><summary>GET</summary>
    <code>localhost:8080/api/customers/a00abd1f-9704-408b-a67a-2b6f7e08c63a</code>
</details>
<br />
<details><summary>PUT</summary>
    <code>localhost:8080/api/customers/a00abd1f-9704-408b-a67a-2b6f7e08c63a</code>
    
    {
        "name" : "Harry Doe",
        "cpfCnpj": "11111111111",
        "phoneNumber": "41911111111",
        "email": "harry@email.com",
        "address": {
            "street" : "Privet Drive",
            "number" : "4",
            "neighborhood" : "Little Whinging",
            "city" : "London",
            "state" : "Surrey",
            "country" : "United Kingdom",
            "complement" : "Under the stairs",
            "postalCode" : "WD3 4EG"
            }
    }
</details>
<br />
<details><summary>DELETE</summary>
    <code>localhost:8080/api/customers/a00abd1f-9704-408b-a67a-2b6f7e08c63a</code>
</details>
<br />

### Frete


<details><summary>POST</summary>
    <code>localhost:8080/api/shipments</code>

    {
        "customerId": "a00abd1f-9704-408b-a67a-2b6f7e08c63a",
        "origin" : {
            "street" : "Rua A",
            "number" : "10",
            "neighborhood" : "Bairro A",
            "city" : "Cidade A",
            "state" : "SP",
            "country" : "Brasil",
            "complement" : "",
            "postalCode" : "08000000"
            },
        "destination" : {
            "street" : "Rua B",
            "number" : "20",
            "neighborhood" : "Bairro B",
            "city" : "Cidade B",
            "state" : "PR",
            "country" : "Brasil",
            "complement" : "",
            "postalCode" : "40000000"
            },
        "sendDate" : "2024-04-17",
        "estimatedDeliveryDate" : "",
        "type" : "SOLID",
        "weight" : "4.9",
        "length" : "",
        "width" : "",
        "height" : "",
        "declaredValue" : "126.06",
        "transportationType" : "ROAD"
    }

</details>
<br />
<details><summary>GET</summary>
    <code>localhost:8080/api/shipments/3844DB05F3494</code>
</details>
<br />
<details><summary>PUT</summary>
<code>localhost:8080/api/shipments/dfea0fed-cb00-4142-9664-f727beac3bf2</code>

    {
        "customerId": "a00abd1f-9704-408b-a67a-2b6f7e08c63a",
        "sendDate" : "2024-04-17",
        "estimatedDeliveryDate" : "2024-07-20",
        "type" : "SOLID",
        "weight" : "4.9",
        "length" : "",
        "width" : "",
        "height" : "",
        "declaredValue" : "126.06",
        "transportationType" : "ROAD"
    }
</details>
<br />
<details><summary>DELETE</summary>
    <code>localhost:8080/api/shipments/8daf5872-1862-4d75-ae09-30b699e26372</code>

    {
	    "customerId": "a00abd1f-9704-408b-a67a-2b6f7e08c63a"
    }
</details>
