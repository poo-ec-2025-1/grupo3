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

### Diagramas de sequência

### Casos de uso


| Campo               | Descrição                                                                 |
|--------------------|---------------------------------------------------------------------------|
| **Nome**           | Realizar Matrícula                                                        |
| **Ator Principal** | Estudante                                                                  |
| **Descrição**      | O estudante escolhe uma disciplina de sua grade e realiza a matrícula.    |
| **Pré-condições**  | O estudante deve estar logado.                                             |
| **Pós-condições**  | A matrícula é registrada no sistema.                                       |
| **Fluxo Principal**|                                                                           |
| 1. O sistema apresenta disciplinas disponíveis.                                                 |
| 2. O estudante seleciona uma disciplina.                                                        |
| 3. O sistema verifica vagas.                                                                    |
| 4. O sistema registra a matrícula e confirma.                                                   |
| **Alternativas**   | 3a. Se não houver vagas, o sistema informa e cancela a operação.           |




  | Campo                  | Descrição                                                                                        |
  |--------------------    |--------------------------------------------------------------                                    |
  | **Nome**               | cobrar                                                                                           |
  | **Ator Principal**     |  Caixa                                                                                            |
  | **Descrição**          |Após o estudante usar as máquinas de lavar roupa, ele realiza o pagamento.                        |
  | **Pré-condições**      | O estudante precisa ter passado pelo processo de agendamento, lavagem e estar no momento da cobrança. |
  | **Pós-condições**      | A matrícula é registrada no sistema.    |
  | **Fluxo Principal**    |                                         |
  | 1.O estudante agenda um horário para lavar as roupas.                                     |
  | 2. O estudante lava suas roupas.                                                          |
  | 3. O sistema verifica qual tipo de máquina o estudante utilizou.                          |
  | 4. O sistema cobra o respectivo valor da máquina ao estudante.                            |
  | **Alternativas**    | nda                                                                 |
