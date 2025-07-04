01- Atribuição de cargo: Inicialmente, foi designada à mim a responsabilidade com relação ao frontend do projeto. No entanto, tivemos que fazer um remanejamento de funções devido à um dos membros do grupo ter saído da disciplina por motivos pessoais. Dito isso, minha função estava próxima a um "programador fullstack", fiquei responsável por intercambiar entre as funções de acordo com a necessidade do grupo.

02- Contribuição: Dentro do que foi possível, contribui com algumas das classes relativas ao backend, como Aparelho, Usuário e a própria Reserva em si, bem como toda a parte relacionada ao database dessas classes(optei por criar um DatabaseManager de forma que fosse possível inicializar todos os repositórios através de um único comando, facilitando a atribuição dos DAOS relativos à cada uma das classes). Além disso, ajudei na atribuição dos botões de alguns dos controllers, fazendo a "linkagem" com suas respectivas funções.

03- Contribuição além do atribuído: Como falei no tópico de atribuição de cargo, minha função inicial era relacionada ao frontend do projeto, no entanto, por conta da saída do outro membro do grupo acabei dando um foco maior nas classes do backend para não sobrecarregar o Miguel. Tentei ajudá-lo na implementação da sua ideia relacionada à agenda, mas como nosso prazo estava bastante curto, fiquei com receio de não conseguirmos entregar o projeto com a ideia dele e, por isso, optei por fazer um refactoring da forma com que as reservas funcionavam para que ao menos conseguíssemos demonstrar pro senhor como seria a ideia do funcionamento do projeto. Esse refactoring está em: 'Arquivos - PROJETO ALTERNATIVO'.

04- Considerações gerais: Apesar das dificuldades, relacionadas principalmente com o acesso e manipulação do banco de dados, acredito que eu tenha conseguido contribuir em vários aspectos do projeto. Meu principal recurso para lidar com essas dificuldades foi justamente pesquisar sobre as documentações relacionadas ao SQLite e ao ORMLite, para entender melhor sobre como as operações de CRUD aconteciam no decorrer do código. Sinto que poderia ter contribuído mais, mas infelizmente não consegui administrar meu tempo da forma mais eficiente.

05- Commits: Seguem meus principais commits e, de forma resumida, o que foi feito em cada um deles:

12e0ebf e 80ec4f7- implementação de uma classe Database, atualização das classes Usuario, criação das subclasses Administrador e Cliente, criação da interface Autenticavel e criação do UsuarioRepository;

55d613a - comentário a cerca do atributo "private String email;" da classe abstrata Usuário;

990b0e5, c098d30, b1dbf90, 4b3f737 - atualização e implementação de arquivos fxml, relativos à interface gráfica do programa. Upload das imagens de demonstração;

6b84f9e - arquivo fxml da tela de simulação de pagamento;

5e0c453- implementação da classe abstrata Aparelho e de suas subclasses MaquinaDeLavar e Secadora, bem como a interface Reservável; implementação da classe AparelhoRepository;

2d6d801- revisão de algumas classes e implementação da classe Caixa refatorada e da classe MetodoDePagamento;

7f3ed42- atualização/revisão de algumas classes; refactoring dos métodos loadFromId e loadAll das classes Repository;

5f73056- complementação da classe Agenda;

7da69e7- refactoring do projeto como um todo para funcionamento temporário para a apresentação;

13b7ae5, 3914eea- adição de alguns botões às telas da interface gráfica;
