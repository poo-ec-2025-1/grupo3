# grupo3
Repositório do grupo 3

## Membros 
* Daniel - @danielufg - Backend
* Eduardo - @Eddclement - Frontend
* João - @joao-araujo22 - Frontend
* José - @jjoseeee - Backend
* Miguel - @Miguel-MC-png - Backend

## Seção 1 - Introdução

### Justificativa
A crescente demanda por serviços de lavanderia exige soluções eficientes para otimizar o tempo de atendimento e a organização dos processos internos. Muitas lavanderias ainda utilizam métodos manuais ou sistemas pouco integrados, o que pode gerar atrasos, confusões e insatisfação dos clientes.

### Descrição do problema
Atualmente, a gestão do tempo em lavanderias é um desafio, principalmente no controle do status das roupas, prazos de entrega e organização das tarefas diárias. A falta de um sistema automatizado pode acarretar em perdas de produtividade, esquecimentos e dificuldade no acompanhamento dos pedidos.

### Motivação
A proposta deste projeto é desenvolver um sistema de gerenciamento de tempo para lavanderias, utilizando os princípios da programação orientada a objetos. O objetivo é facilitar o controle dos processos, melhorar a comunicação interna e garantir maior eficiência no atendimento ao cliente. A motivação principal é proporcionar uma solução tecnológica acessível, que contribua para a modernização e o crescimento do setor.

## Seção 2 - Plano

### Objetivo geral 
Desenvolver um sistema de gerenciamento de tempo para lavanderia, utilizando programação orientada a objetos, com foco em otimizar os processos internos, melhorar a eficiência operacional e garantir a entrega dos serviços dentro dos prazos estabelecidos

### Objetivos específicos
* Mapear e organizar os processos produtivos da lavanderia, identificando etapas críticas e possíveis gargalos.

* Automatizar o controle de entrada, processamento e saída das roupas, permitindo o acompanhamento em tempo real do status de cada pedido.

* Reduzir desperdícios de tempo e recursos, promovendo a melhor utilização dos equipamentos e da equipe.

* Propor e implementar melhorias nos fluxos de trabalho, visando aumentar a produtividade e a satisfação dos clientes.

* Disponibilizar relatórios gerenciais que auxiliem na tomada de decisão e no planejamento das atividades da lavanderia.

* Integrar funcionalidades que facilitem a comunicação entre funcionários e clientes, como notificações de status e previsão de entrega

## Seção 3 - Divisão de tarefas:

### Tarefas (issues)
O projeto foi dividido em tarefas menores para facilitar o desenvolvimento e garantir o acompanhamento do progresso. Entre as principais tarefas estão:

* Levantamento dos requisitos e definição das funcionalidades do sistema

* Modelagem das classes e estrutura do banco de dados

* Implementação das funcionalidades principais (cadastro de clientes, controle de pedidos, gestão de tempo, notificações)

* Criação da interface do usuário

* Testes e validação do sistema

* Documentação do projeto

### Atribuição de tarefa
A distribuição das tarefas foi realizada conforme as habilidades e interesses de cada membro do grupo, promovendo o engajamento e o desenvolvimento individual.

### Responsabilidades 
Eduardo: coordenação do grupo, acompanhamento do progresso, integração dos módulos e revisão final

Eduardo: levantamento de requisitos, documentação geral, testes e validação

José: modelagem das classes e banco de dados

Miguel: implementação das funcionalidades principais

Daniel: desenvolvimento da interface do usuário

João: desenvolvimento da interface do usuário

### Prazos
Cada tarefa foi associada a um prazo específico, definido em conjunto para garantir o cumprimento do cronograma geral do projeto. O acompanhamento dos prazos foi realizado por meio de reuniões semanais e atualizações regulares das tarefas, seguindo boas práticas de organização e divisão do trabalho em projetos de programação.

## Seção 4 - Modelagem inicial: 

### Diagrama de classes 
Diagrama de Classes Completo

![Diagrama de Classe](Imagens/Etapa%202/Diagrama_Classes_Atualizado.png)

---------------
Diagrama separado em partes para facilitar a vizualização

![Diagrama de Classe](Imagens/Etapa%202/Diagrama_Classes_Atualizado_Parte1.png)
![Diagrama de Classe](Imagens/Etapa%202/Diagrama_Classes_Atualizado_Parte2.png)

```java
@startuml
' Configurações para layout vertical e melhor legibilidade
skinparam monochrome true
skinparam classAttributeIconSize 0
skinparam linetype ortho
left to right direction

' Pacote para Interfaces
package "Interfaces" {
  interface Autenticavel {
    + autenticar(senha: String): boolean
  }
}

' Pacote para Entidades/Domínio
package "Dominio" {
  class Usuario {
    - id: int
    - nome: String
    - saldo: double
    - email: String
    - login: String
    + autenticar(senha: String): boolean
    + atualizarDados(novoLogin: String, novoEmail: String): boolean
    + encerrarSessao(): void
    + getId(): int
    + getNome(): String
  }
  class Cliente {
    - preferencias: String
    + selecionarHorario(data: Date, aparelho: Aparelho): void
    + getPreferencias(): String
  }
  class Administrador {
    - permissoes: String[]
    + gerenciarUsuarios(): void
    + getPermissoes(): String[]
  }
  class Aparelho {
    - id: int
    - tipo: String
    - status: String
    + verificarDisponibilidade(): boolean
    + getId(): int
    + getTipo(): String
  }
  class MaquinaDeLavar {
    - capacidade: double
    - ciclos: int
    + lavar(): void
    + getCapacidade(): double
  }
  class Secadora {
    - temperaturaMaxima: int
    - duracao: int
    + secar(): void
    + getTemperaturaMaxima(): int
  }
  class Reserva {
    - id: int
    - data: Date
    - status: String
    - aparelhoId: int
    - usuarioId: int
    + calcularValorTotal(aparelho: Aparelho): double
    + cancelar(): boolean
    + criarReserva(usuario: Usuario, aparelho: Aparelho, data: Date): boolean
    + getStatus(): String
  }
  class Intervalo {
    - inicio: Date
    - fim: Date
    + isDisponivel(): boolean
    + getInicio(): Date
    + getFim(): Date
  }
  class IntervaloDeUso {
    - ocupado: boolean
    - aparelho: Aparelho
    - intervalo: Intervalo
    + verificarDisponibilidade(data: Date): boolean
    + reservar(): void
    + liberar(): void
  }
  class DiaDaReserva {
    - data: Date
    - reservas: List<Reserva>
    + adicionarReserva(reserva: Reserva): void
    + getReservas(): List<Reserva>
  }
  class IntervaloHorario {
    - horaInicio: Time
    - horaFim: Time
    + isDentroHorario(data: Date): boolean
  }
  class HorariosFixos {
    - horarios: List<IntervaloHorario>
    + obterHorariosLivres(): List<IntervaloHorario>
    + adicionarHorario(horario: IntervaloHorario): void
  }

  Usuario <|-- Cliente
  Usuario <|-- Administrador
  Aparelho <|-- MaquinaDeLavar
  Aparelho <|-- Secadora
  IntervaloDeUso --> Aparelho
  IntervaloDeUso --> Intervalo
  HorariosFixos --> IntervaloHorario
  Reserva --> Usuario
  Reserva --> IntervaloDeUso
  Cliente ..|> Autenticavel
  Administrador ..|> Autenticavel
}

' Pacote para Repositórios
package "Repositorios" {
  class UsuarioRepository {
    + salvarUsuario(usuario: Usuario): boolean
    + verificarCredenciais(login: String, senha: String): Usuario
    + atualizarSaldo(usuario: Usuario, valor: double): boolean
    + atualizarDados(usuario: Usuario, novoLogin: String, novoEmail: String): boolean
    + findById(id: int): Usuario
  }
  class ReservaRepository {
    + salvarReserva(reserva: Reserva): boolean
    + obterReservasPorUsuario(idUsuario: int): List<Reserva>
    + atualizarStatus(idReserva: int, status: String): boolean
    + findById(id: int): Reserva
  }
  class AparelhoRepository {
    + obterAparelhosDisponiveis(): List<Aparelho>
    + findById(id: int): Aparelho
    + salvarAparelho(aparelho: Aparelho): boolean
  }
  class DiaDaReservaRepository {
    + salvarDiaDaReserva(dia: DiaDaReserva): boolean
    + findByData(data: Date): DiaDaReserva
  }
  class IntervaloDeUsoRepository {
    + salvarIntervalo(uso: IntervaloDeUso): boolean
    + findByAparelho(aparelhoId: int): IntervaloDeUso
  }

  UsuarioRepository --> Dominio::Usuario
  ReservaRepository --> Dominio::Reserva
  AparelhoRepository --> Dominio::Aparelho
  DiaDaReservaRepository --> Dominio::DiaDaReserva
  IntervaloDeUsoRepository --> Dominio::IntervaloDeUso
}

' Pacote para Gerenciamento
package "Gerenciamento" {
  class Database {
    - conexao: Connection
    + conectar(): Connection
    + desconectar(): void
  }
  class DatabaseManager {
    + atualizarDatabase(objeto: Object): boolean
    + consultarDatabase(id: int): Object
    + salvarDados(objeto: Object): boolean
  }
  class Agenda {
    + selecionarAparelhoEData(aparelho: Aparelho, data: Date): boolean
    + verificarDisponibilidade(data: Date): boolean
    + liberarIntervalo(idReserva: int): boolean
    + calcularPesoRoupas(): double
  }
  class Caixa {
    + processarPagamento(valor: double): boolean
    + verificarSaldo(usuario: Usuario): double
  }

  Agenda --> Dominio::Aparelho
  Agenda --> Repositorios::ReservaRepository
  Agenda --> Dominio::Reserva
  Agenda --> Repositorios::DiaDaReservaRepository
  Agenda --> Dominio::DiaDaReserva
  Agenda --> Dominio::Intervalo
  Agenda --> Dominio::IntervaloDeUso
  Agenda --> Repositorios::IntervaloDeUsoRepository
  Agenda --> Dominio::IntervaloHorario
  Agenda --> Repositorios::UsuarioRepository
  Caixa --> Dominio::Aparelho
  Caixa --> Repositorios::AparelhoRepository
  Caixa --> Dominio::Usuario
  Caixa --> Gerenciamento::Database
  Caixa --> Interfaces::Autenticavel

  DatabaseManager --> Dominio::Aparelho
  DatabaseManager --> Dominio::Usuario
  DatabaseManager --> Dominio::IntervaloDeUso
  DatabaseManager --> Dominio::DiaDaReserva
  DatabaseManager --> Repositorios::DiaDaReservaRepository

  Repositorios::UsuarioRepository --> Gerenciamento::Database
  Repositorios::UsuarioRepository --> Gerenciamento::DatabaseManager
  Repositorios::ReservaRepository --> Gerenciamento::Database
  Repositorios::ReservaRepository --> Gerenciamento::DatabaseManager
  Repositorios::AparelhoRepository --> Gerenciamento::Database
  Repositorios::AparelhoRepository --> Gerenciamento::DatabaseManager
  Repositorios::IntervaloDeUsoRepository --> Gerenciamento::Database
  Repositorios::IntervaloDeUsoRepository --> Gerenciamento::DatabaseManager
  Repositorios::DiaDaReservaRepository --> Gerenciamento::Database
  Repositorios::DiaDaReservaRepository --> Gerenciamento::DatabaseManager
}

' Pacote para Testes
package "Testes" {
  class CrudTest {
    + testarCrud(): void
    + testarReserva(): void
    + testarUsuario(): void
  }

  CrudTest --> Repositorios::IntervaloDeUsoRepository
  CrudTest --> Dominio::IntervaloDeUso
  CrudTest --> Repositorios::DiaDaReservaRepository
  CrudTest --> Dominio::DiaDaReserva
  CrudTest --> Dominio::Reserva
  CrudTest --> Repositorios::ReservaRepository
  CrudTest --> Gerenciamento::Database
  CrudTest --> Dominio::Aparelho
  CrudTest --> Repositorios::AparelhoRepository
  CrudTest --> Dominio::Usuario
  CrudTest --> Repositorios::UsuarioRepository
}
@enduml
````
---------------
### Diagramas de sequência
Diagrama de Sequência - Cadastro Usuário

![Diagrama de Sequência Cadastro Usuário](Imagens/Etapa%202/Diagrama_Sequência_Cadastro_Usuario.png)
````
@startuml

actor Cliente

Cliente -> PainelDeLogin: cadastrarUsuario(dados)
PainelDeLogin -> UsuarioRepository: validarEcriarUsuario(dados)
UsuarioRepository -> DatabaseManager: salvarUsuario(dados)
DatabaseManager --> UsuarioRepository: sucesso
UsuarioRepository --> PainelDeLogin: usuarioCriado
PainelDeLogin --> Cliente: Cadastro realizado com sucesso, redirecionando para login

@enduml
````
---------------
Diagrama de Sequência - Fazer Login

![Diagrama de Sequência Fazer Login](Imagens/Etapa%202/Diagrama_Sequência_Fazer_Login.png)
````
@startuml

actor Usuario

Usuario -> PainelDeLogin: fazerLogin(credenciais)
PainelDeLogin -> Autenticavel: autenticar(credenciais)
Autenticavel -> UsuarioRepository: verificarCredenciais(credenciais)
UsuarioRepository -> DatabaseManager: consultarDatabase()
DatabaseManager --> UsuarioRepository: dadosUsuario
UsuarioRepository --> Autenticavel: autenticado = true
Autenticavel --> PainelDeLogin: loginSucesso
PainelDeLogin -> PainelDoUsuario: abrirPainel()
PainelDoUsuario --> PainelDeLogin: painelAberto
PainelDeLogin --> Usuario: Login realizado com sucesso

@enduml
````
---------------
Diagrama de Sequência - Alterar Cadastro

![Diagrama de Sequência Alterar Cadastro](Imagens/Etapa%202/Diagrama_Sequência_Alterar_Cadastro.png)
````
@startuml

actor Usuario

Usuario -> PainelDoUsuario: alterarCadastro(novoLogin, novoEmail)
PainelDoUsuario -> UsuarioRepository: atualizarDados(usuario, novoLogin, novoEmail)
UsuarioRepository -> DatabaseManager: atualizarDatabase()
DatabaseManager --> UsuarioRepository: sucesso
UsuarioRepository --> PainelDoUsuario: dadosAtualizados
PainelDoUsuario --> Usuario: Cadastro alterado com sucesso

@enduml
````
---------------
Diagrama de Sequência - Adicionar Saldo

![Diagrama de Sequência Adicionar Saldo](Imagens/Etapa%202/Diagrama_Sequência_Adicionar_Saldo.png)
````
@startuml

actor Usuario

Usuario -> PainelDoUsuario: adicionarSaldo(valor)
PainelDoUsuario -> Caixa: processarPagamento(valor)
Caixa --> PainelDoUsuario: pagamentoConfirmado
PainelDoUsuario -> UsuarioRepository: atualizarSaldo(usuario, valor)
UsuarioRepository -> DatabaseManager: atualizarDatabase()
DatabaseManager --> UsuarioRepository: sucesso
UsuarioRepository --> PainelDoUsuario: saldoAtualizado
PainelDoUsuario --> Usuario: Saldo adicionado com sucesso

@enduml
````
---------------
Diagrama de Sequência - Fazer Reserva

![Diagrama de Sequência Fazer Reserva](Imagens/Etapa%202/Diagrama_Sequência_Fazer_Reserva.png)
````
@startuml

actor Usuario

Usuario -> Agenda: selecionarAparelhoEData(aparelho, data)
Agenda -> IntervaloDeUso: verificarDisponibilidade(data)
IntervaloDeUso --> Agenda: disponivel = true
Agenda -> Reserva: calcularValorTotal(aparelho)
Reserva --> Agenda: valorTotal
Agenda -> Reserva: criarReserva(usuario, aparelho, data, valorTotal)
Reserva -> ReservaRepository: salvarReserva(reserva)
ReservaRepository -> DatabaseManager: atualizarDatabase()
DatabaseManager --> ReservaRepository: sucesso
ReservaRepository --> Reserva: reservaSalva
Reserva --> Agenda: reservaConfirmada
Agenda --> Usuario: Reserva realizada com sucesso

@enduml
````
---------------
Diagrama de Sequência - Mostrar Reservas

![Diagrama de Sequência Mostrar Reservas](Imagens/Etapa%202/Diagrama_Sequência_Mostrar_Reservas.png)
````
@startuml

actor Usuario

Usuario -> PainelDoUsuario: mostrarReservas()
PainelDoUsuario -> ReservaRepository: obterReservasPorUsuario(idUsuario)
ReservaRepository -> DatabaseManager: consultarDatabase(idUsuario)
DatabaseManager --> ReservaRepository: listaReservas
ReservaRepository --> PainelDoUsuario: listaReservas
PainelDoUsuario -> MinhasReservas: exibirReservas(listaReservas)
MinhasReservas --> PainelDoUsuario: reservasExibidas
PainelDoUsuario --> Usuario: Reservas exibidas com sucesso

@enduml
````

---------------
Diagrama de Sequência - Cancelar Reserva

![Diagrama de Sequência Cancelar Reserva](Imagens/Etapa%202/Diagrama_Sequência_Cancelar_Reserva.png)
````
@startuml

actor Usuario

Usuario -> PainelDoUsuario: cancelarReserva(idReserva)
PainelDoUsuario -> Reserva: cancelar(idReserva)
Reserva -> ReservaRepository: atualizarStatus(idReserva, "cancelada")
ReservaRepository -> DatabaseManager: atualizarDatabase()
DatabaseManager --> ReservaRepository: sucesso
ReservaRepository --> Reserva: statusAtualizado
Reserva -> Agenda: liberarIntervalo(idReserva)
Agenda --> Reserva: intervaloLiberado
Reserva --> PainelDoUsuario: reservaCancelada
PainelDoUsuario --> Usuario: Reserva cancelada com sucesso

@enduml
````

---------------
Diagrama de Sequência - Finalizar Sessão

![Diagrama de Sequência Finalizar Sessão](Imagens/Etapa%202/Diagrama_Sequência_Finalizar_Sessão.png)
````
@startuml

actor Usuario

Usuario -> PainelDoUsuario: finalizarSessao()
PainelDoUsuario -> Usuario: encerrarSessao()
Usuario --> PainelDoUsuario: sessaoEncerrada
PainelDoUsuario -> PainelDeLogin: redirecionar()
PainelDeLogin --> PainelDoUsuario: redirecionamentoCompleto
PainelDoUsuario --> Usuario: Sessão finalizada, redirecionado para login

@enduml
````

---------------
Diagrama de Sequência - Completo

![Diagrama de Sequência Completo](Imagens/Etapa%202/Diagrama_Sequência_Completo.png)
````
@startuml

actor Cliente

Cliente -> PainelDeLogin: cadastrarUsuario(dados)
PainelDeLogin -> UsuarioRepository: validarEcriarUsuario(dados)
UsuarioRepository -> DatabaseManager: salvarUsuario(dados)
DatabaseManager --> UsuarioRepository: sucesso
UsuarioRepository --> PainelDeLogin: usuarioCriado
PainelDeLogin --> Cliente: Cadastro realizado, redirecionando
Cliente -> PainelDeLogin: fazerLogin(credenciais)
PainelDeLogin -> UsuarioRepository: verificarCredenciais(credenciais)
UsuarioRepository -> DatabaseManager: consultarDatabase()
DatabaseManager --> UsuarioRepository: dadosUsuario
UsuarioRepository --> PainelDeLogin: autenticado = true
PainelDeLogin -> PainelDoUsuario: abrirPainel()
PainelDoUsuario --> PainelDeLogin: painelAberto
PainelDeLogin --> Cliente: Login realizado
Cliente -> PainelDoUsuario: adicionarSaldo(valor)
PainelDoUsuario -> Caixa: processarPagamento(valor)
Caixa --> PainelDoUsuario: pagamentoConfirmado
PainelDoUsuario -> UsuarioRepository: atualizarSaldo(valor)
UsuarioRepository -> DatabaseManager: atualizarDatabase()
DatabaseManager --> UsuarioRepository: sucesso
UsuarioRepository --> PainelDoUsuario: saldoAtualizado
PainelDoUsuario --> Cliente: Saldo adicionado
Cliente -> PainelDoUsuario: alterarCadastro(novoLogin, novoEmail)
PainelDoUsuario -> UsuarioRepository: atualizarDados(novoLogin, novoEmail)
UsuarioRepository -> DatabaseManager: atualizarDatabase()
DatabaseManager --> UsuarioRepository: sucesso
UsuarioRepository --> PainelDoUsuario: dadosAtualizados
PainelDoUsuario --> Cliente: Cadastro alterado
Cliente -> PainelDoUsuario: marcarHorario(aparelho, data)
PainelDoUsuario -> Agenda: selecionarAparelhoEData(aparelho, data)
Agenda -> IntervaloDeUso: verificarDisponibilidade(data)
IntervaloDeUso --> Agenda: disponivel = true
Agenda -> Reserva: calcularValorTotal(aparelho)
Reserva --> Agenda: valorTotal
Agenda -> Reserva: criarReserva(cliente, aparelho, data, valorTotal)
Reserva -> ReservaRepository: salvarReserva(reserva)
ReservaRepository -> DatabaseManager: atualizarDatabase()
DatabaseManager --> ReservaRepository: sucesso
ReservaRepository --> Reserva: reservaSalva
Reserva --> Agenda: reservaConfirmada
Agenda --> PainelDoUsuario: Reserva realizada
PainelDoUsuario -> Cliente: Reserva confirmada
Cliente -> PainelDoUsuario: mostrarReservas()
PainelDoUsuario -> ReservaRepository: obterReservasPorUsuario(idCliente)
ReservaRepository -> DatabaseManager: consultarDatabase(idCliente)
DatabaseManager --> ReservaRepository: listaReservas
ReservaRepository --> PainelDoUsuario: listaReservas
PainelDoUsuario -> MinhasReservas: exibirReservas(listaReservas)
MinhasReservas --> PainelDoUsuario: reservasExibidas
PainelDoUsuario --> Cliente: Reservas exibidas
Cliente -> PainelDoUsuario: cancelarReserva(idReserva)
PainelDoUsuario -> Reserva: cancelar(idReserva)
Reserva -> ReservaRepository: atualizarStatus(idReserva, "cancelada")
ReservaRepository -> DatabaseManager: atualizarDatabase()
DatabaseManager --> ReservaRepository: sucesso
ReservaRepository --> Reserva: statusAtualizado
Reserva -> Agenda: liberarIntervalo(idReserva)
Agenda --> Reserva: intervaloLiberado
Reserva --> PainelDoUsuario: reservaCancelada
PainelDoUsuario --> Cliente: Reserva cancelada
Cliente -> PainelDoUsuario: finalizarSessao()
PainelDoUsuario -> Cliente: encerrarSessao()
Cliente --> PainelDoUsuario: sessaoEncerrada
PainelDoUsuario -> PainelDeLogin: redirecionar()
PainelDeLogin --> PainelDoUsuario: redirecionamentoCompleto
PainelDoUsuario --> Cliente: Sessão finalizada, redirecionado

@enduml
````

---------------
### Casos de uso
Caso de Uso - Fazer Login

| Campo              | Descrição                                                                                  |
|--------------------|--------------------------------------------------------------------------------------------|
| **Nome**           | fazerLogin                                                                                 |
| **Ator Principal** | Cliente                                                                                    |
| **Descrição**      | O cliente acessa o sistema inserindo suas credenciais.                                     |
| **Pré-condições**  | O cliente possui credenciais válidas cadastradas.                                          |
| **Pós-condições**  | O cliente é autenticado e acessa o painel do usuário.                                      |
| **Fluxo Principal**| 1. O cliente insere suas credenciais (login e senha). <br> 2. O sistema verifica as credenciais no banco de dados. <br> 3. O sistema autentica o cliente. <br>  4. O sistema abre o painel do usuário.  |
| **Alternativas**   | 2a. Se as credenciais forem inválidas, o sistema informa o erro e solicita nova tentativa. |


---------------
Caso de Uso - Alterar Cadastro

| Campo            | Descrição                                                                        | 
|--------------------|--------------------------------------------------------------------------------|
| **Nome**           | alterarCadastro                                                                |
| **Ator Principal** | Cliente                                                                        |
| **Descrição**      | O cliente atualiza seus dados pessoais, como login ou email.                   |
| **Pré-condições**  | O cliente precisa estar autenticado.                                           |
| **Pós-condições**  | Os dados do cliente são atualizados no sistema.                                |
| **Fluxo Principal**| 1. O cliente solicita alterar o cadastro. <br> 2. O sistema valida os novos dados. <br> 3. O sistema atualiza os dados no banco de dados. <br> 4. O sistema confirma a alteração ao cliente.|
| **Alternativas**   | 2a. Se os dados forem inválidos, o sistema informa o erro e solicita correção. |


---------------
Caso de Uso - Adicionar Saldo

| Campo              | Descrição                                                                         |
|--------------------|-----------------------------------------------------------------------------------|
| **Nome**           | adicionarSaldo                                                                    |
| **Ator Principal** | Cliente                                                                           |
| **Descrição**      | O cliente adiciona saldo ao perfil para usar os serviços.                         |
| **Pré-condições**  | O cliente precisa estar autenticado.                                              |
| **Pós-condições**  | O saldo do estudante é atualizado e disponível para uso.                          |
| **Fluxo Principal**| 1. O cliente solicita adicionar saldo. <br> 2. O sistema processa o pagamento. <br> 3. O sistema atualiza o saldo no banco de dados. <br> 4. O sistema confirma a operação ao cliente. |
| **Alternativas**   |  2a. Se o pagamento for rejeitado, o sistema informa o erro e cancela a operação. |

---------------
Caso de Uso - Mostrar Reservas

| Campo              | Descrição                                                                 |
|--------------------|---------------------------------------------------------------------------|
| **Nome**           | mostrarReservas                                                           |
| **Ator Principal** | Cliente                                                                   |
| **Descrição**      | O cliente visualiza a lista de suas reservas ativas.                      |
| **Pré-condições**  | O cliente precisa estar autenticado.                                      |
| **Pós-condições**  | A lista de reservas do cliente é exibida com sucesso.                     |
| **Fluxo Principal**| 1. O cliente solicita ver suas reservas. <br> 2. O sistema consulta as reservas no banco de dados. <br> 3. O sistema exibe a lista de reservas ao cliente. |
| **Alternativas**   | 2a. Se não houver reservas, o sistema informa que não há registros.       |
       
---------------
Caso de Uso - Cancelar Reserva

| Campo              | Descrição                                                                                |
|--------------------|----------------------------------------------------------------------------------------- |
| **Nome**           | cancelarReserva                                                        		        |
| **Ator Principal** | Cliente                                                                		        |
| **Descrição**      | O cliente cancela uma reserva de horário previamente feita.             		        |
| **Pré-condições**  | O cliente precisa estar autenticado e ter uma reserva ativa.             		|
| **Pós-condições**  | A reserva é cancelada e o horário fica disponível novamente.              		|
| **Fluxo Principal**| 1. O cliente solicita o cancelamento de uma reserva. <br>2. O sistema localiza a reserva. <br>3. O sistema atualiza o status para "cancelada". <br>4. O sistema libera o horário e confirma ao cliente. |
| **Alternativas**   |   2a. Se a reserva não for encontrada, o sistema informa o erro e cancela a operação.    |
          
---------------
### Casos de Uso Específico

![Diagrama de Casos de Uso Específicos - Daniel](Imagens/Etapa%202/Diagrama_Caso_Uso_Específico.png)

````java
@startuml

actor Usuario

rectangle "Painel de login" {
	usecase "Fazer login" as UC1
	usecase "Fazer cadastro" as UC2
}

rectangle "Painel do usuario" {
	usecase "Minhas reservas" as UC3
	usecase "Adicionar saldo" as UC4
	usecase "Alterar cadastro" as UC5
	usecase "Finalizar sessao" as UC6
}

rectangle "Alterar cadastro" {
	usecase "Alterar login" as UC13
	usecase "Alterar email" as UC14
	usecase "Alterar senha" as UC15
}

rectangle "Adicionar saldo" {
	usecase "Alterar valor" as UC16
	usecase "Alterar forma de pagamento" as UC17
}

rectangle "Minhas reservas" {
	usecase "Fazer reserva" as UC7
	usecase "Mostrar reservas" as UC8
}

rectangle "Fazer Reserva" {
	usecase "Mostrar valor total" as UC9
	usecase "Selecionar aparelho" as UC10
	usecase "Selecionar data" as UC11
}

rectangle "Mostrar Reservas" {
	usecase "Alterar reserva" as UC18
	usecase "Cancelar reserva" as UC12
}

"Painel de login" --> "Painel do usuario"
UC5 --> "Alterar cadastro"
UC3 --> "Minhas reservas"
UC7 --> "Fazer Reserva"
UC8 --> "Mostrar Reservas"
UC4 --> "Adicionar saldo"

Usuario --> UC1
Usuario --> UC2
Usuario --> UC3
Usuario --> UC4
Usuario --> UC5
Usuario --> UC6

@enduml
````

