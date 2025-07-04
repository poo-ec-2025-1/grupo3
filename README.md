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

![Diagrama de Classe](Imagens/Etapa%202/Diagrama_Classes_Final.png)

---------------
Diagrama separado em partes para facilitar a vizualização

![Diagrama de Classe](Imagens/Etapa%202/Diagrama_Classes_Final_P1.png)
![Diagrama de Classe](Imagens/Etapa%202/Diagrama_Classes_Final_P2.png)

```java
@startuml

skinparam monochrome true
skinparam classAttributeIconSize 0
skinparam nodesep 10
skinparam ranksep 10
top to bottom direction

' Classes
class DatabaseManager {
  - DATABASE_URL: String
  - connectionSource: ConnectionSource
  - usuarioDao: Dao<Usuario, Integer>
  - aparelhoDao: Dao<Aparelho, Integer>
  - reservaDao: Dao<Reserva, Integer>
  + init(): void
  + getUsuarioDao(): Dao<Usuario, Integer>
  + getAparelhoDao(): Dao<Aparelho, Integer>
  + getReservaDao(): Dao<Reserva, Integer>
  + close(): void
}

class Aparelho {
  - id: int
  - modelo: String
  - capacidadeKg: int
  - descricao: String
  - disponivel: boolean
  - custo: double
  + disponibilizarMaquina(): void
  + indisponibilizarMaquina(): void
  + getId(): int
  + setId(id: int): void
  + getModelo(): String
  + setModelo(modelo: String): void
  + getDescricao(): String
  + setDescricao(descricao: String): void
  + getCapacidadeKg(): int
  + setCapacidadeKg(capacidadeKg: int): void
  + getDisponivel(): boolean
  + setDisponivel(disponivel: boolean): void
  + getCusto(): double
  + setCusto(custo: double): void
  + toString(): String
}

class Cliente {
  + login(matricula: int, senha: String): boolean
}

class Caixa {
  - usuarioDao: Dao<Usuario, Integer>
  - aparelhoDao: Dao<Aparelho, Integer>
  - mensagemDeAviso: String
  + adicionarSaldo(usuarioId: int, valor: double): boolean
  + debitarSaldo(usuarioId: int, aparelhoId: int): boolean
}

class Administrador {
  + login(matricula: int, senha: String): boolean
}

interface Autenticavel {
  + login(matricula: int, senha: String): boolean
}

class InstanciarMaquinas {
  + instanciar(): void
}

class AparelhoRepository {
  - dao: Dao<Aparelho, Integer>
  + getDao(): Dao<Aparelho, Integer>
  + create(aparelho: Aparelho): Aparelho
  + update(aparelho: Aparelho): void
  + delete(aparelho: Aparelho): void
  + deletePorId(id: int): void
  + loadFromId(id: int): Aparelho
  + loadAll(): List<Aparelho>
}

interface Reservavel {
  + reservar(): boolean
  + liberar(): void
  + estaReservado(): boolean
}

class UsuarioRepository {
  - dao: Dao<Usuario, Integer>
  + getDao(): Dao<Usuario, Integer>
  + create(usuario: Usuario): Usuario
  + update(usuario: Usuario): void
  + delete(usuario: Usuario): void
  + deletePorId(id: int): void
  + buscarPorMatriculaESenha(matricula: int, senha: String): Usuario
  + loadFromId(id: int): Usuario
  + loadAll(): List<Usuario>
}

class ReservaRepository {
  - dao: Dao<Reserva, Integer>
  + getDao(): Dao<Reserva, Integer>
  + create(reserva: Reserva): Reserva
  + update(reserva: Reserva): void
  + delete(reserva: Reserva): void
  + deletePorId(id: int): void
  + loadFromId(id: int): Reserva
  + loadAll(): List<Reserva>
}

class Reserva {
  - id: int
  - usuario: Usuario
  - aparelho: Aparelho
  - dataReserva: String
  - horaInicio: String
  - horaFim: String
  - dadosReserva: String
  - status: String
  + formatadorData(aparelho: Aparelho, dataReserva: LocalDate, horaInicio: LocalTime, horaFim: LocalTime): String
  + getId(): int
  + setId(id: int): void
  + getUsuario(): Usuario
  + setUsuario(usuario: Usuario): void
  + getAparelho(): Aparelho
  + setAparelho(aparelho: Aparelho): void
  + getDataReserva(): String
  + setDataReserva(dataReserva: LocalDate): void
  + getHoraInicio(): String
  + setHoraInicio(horaInicio: LocalTime): void
  + getHoraFim(): String
  + setHoraFim(horaFim: LocalTime): void
  + getDadosReserva(): String
  + setDadosReserva(dadosReserva: String): void
  + getStatus(): String
  + setStatus(status: String): void
  + toString(): String
}

class Usuario {
  - id: int
  - nomeCompleto: String
  - matricula: int
  - senha: String
  - saldo: double
  + login(matricula: int, senha: String): boolean
  + mostrarDados(): String
  + depositar(valor: double): void
  + debitar(valor: double): void
  + getId(): int
  + setId(id: int): void
  + getNomeCompleto(): String
  + setNomeCompleto(nome: String): void
  + getMatricula(): int
  + setMatricula(matricula: int): void
  + getStringMatricula(): String
  + setSenha(senha: String): void
  + getSenha(): String
  + getSaldo(): double
}

class Secadora {
  + Secadora(\n  modelo: String,\n  capacidadeKg: int,\n  descricao: String,\n  disponivel: boolean,\n  custo: double\n): void
}

class MaquinaDeLavar {
  + MaquinaDeLavar(\n  modelo: String,\n  capacidadeKg: int,\n  descricao: String,\n  disponivel: boolean,\n  custo: double\n): void
}

class DiaComReserva {
  - id: int
  - aparelho: Aparelho
  - ano: int
  - diaDaSemana: int
  - diaDoAno: int
  - tempoDeFuncionamento: double
  - tempoDisponivel: double
  - data: String
  + indisponibilzarTempo(tempoIndisponivel: double): boolean
  + getId(): int
  + getData(): LocalDate
  + getAparelho(): Aparelho
  + getAno(): int
  + getDiaDaSemana(): int
  + getDiaDoAno(): int
  + getTempoDeFuncionamento(): double
  + getTempoDisponivel(): double
  + setTempoDisponivel(tempoDisponivel: double): void
}

class IntervaloDeUso {
  - id: int
  - dia: String
  - inicio: String
  - fim: String
  - aparelho: Aparelho
  + getAparelho(): Aparelho
  + getId(): int
  + getDia(): String
  + getInicio(): String
  + getFim(): String
}

class Intervalo {
  - inicio: LocalDateTime
  - fim: LocalDateTime
  - dia: LocalDate
  - horaInicio: LocalTime
  - horaFim: LocalTime
  + getInicioIntervalo(): LocalDateTime
  + getFimIntervalo(): LocalDateTime
  + getDiaIntervalo(): LocalDate
  + getHoraInicioIntervalo(): LocalTime
  + getHoraFimIntervalo(): LocalTime
}

class DiaComReservaRepository {
  - dao: Dao<DiaComReserva, Integer>
  + create(dia: DiaComReserva): DiaComReserva
  + loadAll(): List<DiaComReserva>
  + update(dia: DiaComReserva): boolean
  + delete(dia: DiaComReserva): boolean
  + loadFromId(id: int): DiaComReserva
}

class IntervaloDeUsoRepository {
  - dao: Dao<IntervaloDeUso, Integer>
  + create(intervalo: IntervaloDeUso): IntervaloDeUso
  + loadAll(): List<IntervaloDeUso>
  + update(intervalo: IntervaloDeUso): boolean
  + delete(intervalo: IntervaloDeUso): boolean
  + loadFromId(id: int): IntervaloDeUso
}

class Agenda {
  - R_Aparelho: AparelhoRepository
  - R_Usuario: UsuarioRepository
  - R_Reserva: ReservaRepository
  - R_Dia: DiaComReservaRepository
  - R_Intervalo: IntervaloDeUsoRepository
  - tempoDeFuncionamentoSemana: double[]
  + fazerReserva(user: Usuario, aparelho: Aparelho, dia: LocalDate, inicio: LocalTime, fim: LocalTime): Reserva
  + agregarMaquinas(inicio: LocalTime, fim: LocalTime, dia: LocalDate): List<Aparelho>
  + getTempoDeFuncionamentoSemana(): double[]
}

class Database {
  - databaseName: String
  - connection: JdbcConnectionSource
  + getConnection(): JdbcConnectionSource
  + close(): void
}

class HorariosFixos {
  - intervalosPadrao: List<IntervaloHorario>
  + getIntervalos(): List<IntervaloHorario>
}

class IntervaloHorario {
  - horaInicio: LocalTime
  - horaFim: LocalTime
  - FORMATTER: DateTimeFormatter
  + getHoraInicio(): LocalTime
  + getHoraFim(): LocalTime
  + toString(): String
}

class MetodoDePagamento {
  - listaMetodos: List<String>
  + getMetodoDePagamento(): List<String>
}

' Posicionamento manual
Reserva -[hidden]down- Database
Database -[hidden]down- HorariosFixos
HorariosFixos -[hidden]down- IntervaloHorario
IntervaloHorario -[hidden]down- MetodoDePagamento

' Relacionamentos
together {
  Cliente --|> Usuario
  Administrador --|> Usuario
  Secadora --|> Aparelho
  MaquinaDeLavar --|> Aparelho
  IntervaloDeUso --|> Intervalo
}
Cliente ..|> Autenticavel
Administrador ..|> Autenticavel
Aparelho ..|> Reservavel
Reserva --> Usuario
Reserva --> Aparelho
DiaComReserva --> Aparelho
IntervaloDeUso --> Aparelho
Caixa --> AparelhoRepository
Caixa --> UsuarioRepository
AparelhoRepository --> DatabaseManager
UsuarioRepository --> DatabaseManager
ReservaRepository --> DatabaseManager
DiaComReservaRepository --> DatabaseManager
IntervaloDeUsoRepository --> DatabaseManager
InstanciarMaquinas --> AparelhoRepository
Agenda --> AparelhoRepository
Agenda --> UsuarioRepository
Agenda --> ReservaRepository
Agenda --> DiaComReservaRepository
Agenda --> IntervaloDeUsoRepository
ReservaRepository --> Database
HorariosFixos --> IntervaloHorario
Agenda --> HorariosFixos
MetodoDePagamento --> Caixa

@enduml
@enduml
````
---------------
### Diagramas de sequência
Diagrama de Sequência - Cadastro Usuário

![Diagrama de Sequência Cadastro Usuário](Imagens/Etapa%202/Diagrama_Sequência_Cadastro_Usuário.png)
````
@startuml

actor Cliente

participant "Usuario" as User
participant "UsuarioRepository" as UsrRepo
participant "DatabaseManager" as DBMgr

Cliente -> User: new Usuario(nome, matricula, senha)
activate User
User -> UsrRepo: create(Usuario)
activate UsrRepo
UsrRepo -> DBMgr: getUsuarioDao()
activate DBMgr
DBMgr --> UsrRepo: Dao<Usuario, Integer>
deactivate DBMgr
UsrRepo --> User: Usuario (salvo)
deactivate UsrRepo
User --> Cliente: Usuario criado
deactivate User

@enduml
````
---------------
Diagrama de Sequência - Fazer Login

![Diagrama de Sequência Fazer Login](Imagens/Etapa%202/Diagrama_Sequência_Fazer_Login.png)
````
@startuml

actor Cliente

participant "Usuario" as User
participant "UsuarioRepository" as UsrRepo
participant "DatabaseManager" as DBMgr

Cliente -> User: login(matricula, senha)
activate User
User -> UsrRepo: buscarPorMatriculaESenha(matricula, senha)
activate UsrRepo
UsrRepo -> DBMgr: getUsuarioDao()
activate DBMgr
DBMgr --> UsrRepo: Dao<Usuario, Integer>
deactivate DBMgr
UsrRepo --> User: Usuario (se encontrado)
deactivate UsrRepo
User --> Cliente: true (autenticado) / false (falha)
deactivate User

@enduml
````
---------------
Diagrama de Sequência - Alterar Cadastro

![Diagrama de Sequência Alterar Cadastro](Imagens/Etapa%202/Diagrama_Sequência_Alterar_Cadastro.png)
````
@startuml

actor Cliente

participant "Usuario" as User
participant "UsuarioRepository" as UsrRepo
participant "DatabaseManager" as DBMgr

Cliente -> User: setNomeCompleto(novoNome) / setSenha(novaSenha)
activate User
User -> UsrRepo: update(Usuario)
activate UsrRepo
UsrRepo -> DBMgr: getUsuarioDao()
activate DBMgr
DBMgr --> UsrRepo: Dao<Usuario, Integer>
deactivate DBMgr
UsrRepo --> User: sucesso
deactivate UsrRepo
User --> Cliente: cadastro atualizado
deactivate User

@enduml
````
---------------
Diagrama de Sequência - Adicionar Saldo

![Diagrama de Sequência Adicionar Saldo](Imagens/Etapa%202/Diagrama_Sequência_Adicionar_Saldo.png)
````
@startuml

actor Cliente

participant "Caixa" as Caixa
participant "UsuarioRepository" as UsrRepo
participant "DatabaseManager" as DBMgr
participant "Usuario" as User

Cliente -> Caixa: adicionarSaldo(usuarioId, valor)
activate Caixa
Caixa -> UsrRepo: loadFromId(usuarioId)
activate UsrRepo
UsrRepo -> DBMgr: getUsuarioDao()
activate DBMgr
DBMgr --> UsrRepo: Dao<Usuario, Integer>
deactivate DBMgr
UsrRepo --> Caixa: Usuario
deactivate UsrRepo
Caixa -> User: depositar(valor)
activate User
User --> Caixa: saldo atualizado
deactivate User
Caixa -> UsrRepo: update(Usuario)
activate UsrRepo
UsrRepo -> DBMgr: getUsuarioDao()
activate DBMgr
DBMgr --> UsrRepo: Dao<Usuario, Integer>
deactivate DBMgr
UsrRepo --> Caixa: sucesso
deactivate UsrRepo
Caixa --> Cliente: "Depósito feito com sucesso!"
deactivate Caixa

@enduml
````
---------------
Diagrama de Sequência - Fazer Reserva

![Diagrama de Sequência Fazer Reserva](Imagens/Etapa%202/Diagrama_Sequência_Fazer_Reserva.png)
````
@startuml

actor Cliente

participant "Agenda" as Agenda
participant "AparelhoRepository" as ApRepo
participant "UsuarioRepository" as UsrRepo
participant "ReservaRepository" as ResRepo
participant "DiaComReservaRepository" as DiaRepo
participant "IntervaloDeUsoRepository" as IntRepo
participant "DatabaseManager" as DBMgr
participant "Reserva" as Reserva
participant "DiaComReserva" as Dia
participant "IntervaloDeUso" as Interval
participant "Aparelho" as Aparelho
participant "Usuario" as User

Cliente -> Agenda: fazerReserva(User, Aparelho, dia, inicio, fim)
activate Agenda
Agenda -> ApRepo: loadFromId(aparelhoId)
activate ApRepo
ApRepo -> DBMgr: getAparelhoDao()
activate DBMgr
DBMgr --> ApRepo: Dao<Aparelho, Integer>
deactivate DBMgr
ApRepo --> Agenda: Aparelho
deactivate ApRepo

Agenda -> DiaRepo: create(DiaReservavel)
activate DiaRepo
DiaRepo -> DBMgr: getDao()
activate DBMgr
DBMgr --> DiaRepo: Dao<DiaComReserva, Integer>
deactivate DBMgr
DiaRepo -> Dia: setAparelho(Aparelho)
activate Dia
Dia --> DiaRepo: DiaComReserva
deactivate Dia
DiaRepo --> Agenda: DiaComReserva
deactivate DiaRepo

Agenda -> IntRepo: create(IntervaloReservavel)
activate IntRepo
IntRepo -> DBMgr: getDao()
activate DBMgr
DBMgr --> IntRepo: Dao<IntervaloDeUso, Integer>
deactivate DBMgr
IntRepo -> Interval: setAparelho(Aparelho)
activate Interval
Interval --> IntRepo: IntervaloDeUso
deactivate Interval
IntRepo --> Agenda: IntervaloDeUso
deactivate IntRepo

Agenda -> ResRepo: create(Reserva)
activate ResRepo
ResRepo -> DBMgr: getReservaDao()
activate DBMgr
DBMgr --> ResRepo: Dao<Reserva, Integer>
deactivate DBMgr
ResRepo -> Reserva: setUsuario(User)
activate Reserva
Reserva -> Reserva: setAparelho(Aparelho)
Reserva -> Reserva: setDataReserva(dia)
Reserva -> Reserva: setHoraInicio(inicio)
Reserva -> Reserva: setHoraFim(fim)
Reserva -> Reserva: formatadorData(Aparelho, dia, inicio, fim)
Reserva --> ResRepo: Reserva
deactivate Reserva
ResRepo --> Agenda: Reserva
deactivate ResRepo

Agenda -> Dia: indisponibilzarTempo(duracao)
activate Dia
Dia --> Agenda: true (se sucesso)
deactivate Dia

Agenda --> Cliente: Reserva
deactivate Agenda

@enduml
````
---------------
Diagrama de Sequência - Mostrar Reservas

![Diagrama de Sequência Mostrar Reservas](Imagens/Etapa%202/Diagrama_Sequência_Mostrar_Reservas.png)
````
@startuml

actor Cliente

participant "ReservaRepository" as ResRepo
participant "DatabaseManager" as DBMgr

Cliente -> ResRepo: loadAll()
activate ResRepo
ResRepo -> DBMgr: getReservaDao()
activate DBMgr
DBMgr --> ResRepo: Dao<Reserva, Integer>
deactivate DBMgr
ResRepo --> Cliente: List<Reserva>
deactivate ResRepo

@enduml
````

---------------
Diagrama de Sequência - Cancelar Reserva

![Diagrama de Sequência Cancelar Reserva](Imagens/Etapa%202/Diagrama_Sequência_Cancelar_Reserva.png)
````
@startuml

actor Cliente

participant "ReservaRepository" as ResRepo
participant "DatabaseManager" as DBMgr
participant "Reserva" as Reserva

Cliente -> ResRepo: deletePorId(reservaId)
activate ResRepo
ResRepo -> DBMgr: getReservaDao()
activate DBMgr
DBMgr --> ResRepo: Dao<Reserva, Integer>
deactivate DBMgr
ResRepo -> Reserva: loadFromId(reservaId)
activate Reserva
Reserva --> ResRepo: Reserva
deactivate Reserva
ResRepo -> ResRepo: delete(Reserva)
ResRepo --> Cliente: sucesso
deactivate ResRepo

@enduml
````

---------------
Diagrama de Sequência - Finalizar Sessão

![Diagrama de Sequência Finalizar Sessão](Imagens/Etapa%202/Diagrama_Sequência_Finalizar_Sessão.png)
````
@startuml
actor Cliente
participant "Usuario" as User

Cliente -> User: logout()
activate User
User --> Cliente: sessão finalizada
deactivate User

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

