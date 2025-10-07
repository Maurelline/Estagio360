# Estágio360

O **Estágio360** é uma aplicação para gestão e acompanhamento de estágios acadêmicos, permitindo que alunos, supervisores e instituições mantenham controle sobre:

- Check-ins
- Carga horária
- Locais parceiros
- Períodos letivos
- Especialidades

---

## Tecnologias Utilizadas

- **Linguagem:** Java 17  
- **Framework:** Spring Boot  
- **Gerenciamento de Dependências:** Maven  
- **Testes:** JUnit 5, Mockito  
- **Banco de Dados:** PostgreSQL (produção) / H2 (testes)

---

## Casos de Uso e Regras de Negócio Testadas

| Caso de Uso | Regra de Negócio Testada | Descrição do Cenário Validado |
|-------------|--------------------------|-------------------------------|
| **CheckinUseCaseTest** | **RN01 – Validação de presença e horário de check-in** | Verifica se o aluno só pode realizar check-in dentro do horário permitido e com vínculo ativo no estágio. |
| **EspecialidadeUseCaseTest** | **RN02 – Cadastro e vínculo de especialidades** | Garante que cada especialidade esteja corretamente vinculada a um curso e não possa ser duplicada. |
| **HorasCumpridasUseCaseTest** | **RN03 – Cálculo da carga horária cumprida** | Confirma se as horas registradas são somadas corretamente e se não é possível registrar horas negativas ou nulas. |
| **LocalEspecialidadeUseCaseTest** | **RN04 – Associação entre local e especialidade** | Testa se um local parceiro pode oferecer apenas especialidades previamente aprovadas. |
| **LocalParceiroUseCaseTest** | **RN05 – Cadastro de locais parceiros** | Valida se um local parceiro deve conter informações obrigatórias (nome, CNPJ e endereço) antes de ser salvo. |
| **PeriodoAcademicoUseCaseTest** | **RN06 – Criação e duração do período acadêmico** | Verifica se a data de término é posterior à data de início e se períodos sobrepostos não são permitidos. |
| **PeriodoAcademicoEspecialidadeUseCaseTest** | **RN07 – Vinculação entre período e especialidade** | Garante que cada especialidade esteja ativa apenas durante períodos acadêmicos válidos. |
| **RodizioAlunoUseCaseTest** | **RN08 – Alocação de alunos em rodízios** | Testa se o aluno não pode ser alocado em mais de um rodízio simultaneamente. |
| **RodizioUseCaseTest** | **RN09 – Configuração de rodízios** | Verifica se os rodízios respeitam o número máximo de alunos e especialidades permitidas. |
| **SemestreLetivoUseCaseTest** | **RN10 – Controle de semestre letivo** | Assegura que o semestre letivo tenha início e fim válidos e esteja corretamente associado ao período acadêmico. |
| **UsuarioUseCaseTest** | **RN11 – Cadastro e autenticação de usuários** | Garante que o login seja único, a senha tenha formato válido e que usuários inativos não consigam autenticar. |

---

## Estratégia de Testes

Os testes unitários do **Estágio360** seguem as seguintes diretrizes:

- Casos de uso isolados, sem dependência direta da camada de infraestrutura.  
- Uso de **Mocks** para repositórios e serviços dependentes.  
- Validação das regras de negócio de forma independente.  
- Implementação com **JUnit 5** e **Mockito** para simulação de comportamento.

---

<img width="1913" height="549" alt="image" src="https://github.com/user-attachments/assets/8bbbbf6b-3e17-48c4-9cac-86fd0c975555" />
