# grupo3
Repositório do grupo 3

## Diagrama de Caso de Uso Específico
Link: https://www.plantuml.com/plantuml/png/ZPFFRXD13CRlynHMxf5e2_rpGIsfE1KreYfti-jkZ2RZnJCR8H0FqnCFaXTZHfbKWnM5tZQVV_xyUUEzZWarJMllF3qbI0BAmof1pqfTOWakSV84irl6GN6Dq1CiC4ei3uzHO0vBbM-KEvVtzpFdi4kYkJ9jdvN5kUA3OIWU3nmJxImwx34cbHdySW1Jf0vZ9XOx6LyCCe0H7XSdBNNZ4nMcqdlcj6K-uazIy39mgEKpz_kVD9-spzyd3bZYtE46jMGnp4DswqNnGEbceD1ZNh_PIYcIRj0izNcdAgn4svtUjj0NKdxYBbCznr42VsNFFVPKuNTc_0Vq6BP_xAmbHLHGcKQCjVFST2wGVvZS8mwuff34kcLTyyzzuWkJ63ReHI59Gb-HI_kQHbJoA-jynyKPQx7sNhN4BKL_23fvKoRipmFcy_T5jCAf5SwiS6w52ojSMk7A2ZdByJl8MFCAtCjZhTWJL8iq5_Z_IOS_C7Vq3w-DHwnVOz2SexlEJjFQ_mK0

Código UML:

@startuml
left to right direction
title "Diagrama de Casos de Uso - Projeto POO"

actor Usuário

rectangle "Sistema de cadastro" {
  usecase "Cadastrar Usuário" as UC1
  usecase "Alterar Cadastro" as UC2
  usecase "Fazer login" as UC3
}

rectangle "Máquina de Lavar" {

}

rectangle "Secadora" {

}

rectangle "Agenda - Sistema de reserva" {
  usecase "Reservar horário" as UC4
  usecase "Verificar disponibilidade" as UC5
}

rectangle "Balança" {
  usecase "Pesar roupas" as UC6
}

rectangle "Caixa - Sistema de pagamento" {
  usecase "Pagar uso" as UC7
  usecase "Ver valor total" as UC8
}

rectangle "Aparelho" {
  usecase "Ligar aparelho" as UC9
  usecase "Desligar aparelho" as UC10
}

Usuário --> UC1
Usuário --> UC2
Usuário --> UC3
Usuário --> UC6
Usuário --> UC7
Usuário --> UC8
Usuário --> UC9
Usuário --> UC10
"Agenda - Sistema de reserva" --> "Aparelho"
"Aparelho" --> "Máquina de Lavar"
"Aparelho" --> "Secadora"
"Agenda - Sistema de reserva" --> "Sistema de cadastro"
"Caixa - Sistema de pagamento" --> "Agenda - Sistema de reserva"
"Caixa - Sistema de pagamento" --> "Balança"
@enduml
