# 📱 Agenda - Aplicativo de Gerenciamento de Contatos

Um aplicativo Android para gerenciar seus contatos pessoais, desenvolvido com SQLite para armazenamento local.

## 🎯 Características

- ✅ **CRUD Completo**: Criar, ler, atualizar e excluir contatos
- ✅ **Armazenamento Local**: SQLite para persistência de dados

### Lista de Contatos
- Visualize todos os seus contatos em cards 
- Cada contato exibe nome, telefone e email 
- Clique para editar, segure para excluir

### Adicionar/Editar Contato
- Interface limpa e intuitiva

## 🛠️ Tecnologias Utilizadas

- **Linguagem**: Java
- **SDK Mínimo**: Android 7.0 (API 24)
- **SDK Alvo**: Android 14 (API 36)
- **Arquitetura**: MVC (Model-View-Controller)
- **Banco de Dados**: SQLite


## 📋 Pré-requisitos
- Android Studio (última versão recomendada)
- JDK 11 ou superior
- Android SDK com API 24 ou superior
- Dispositivo Android ou Emulador

## 📱 Como Usar

### Adicionar um Novo Contato

1. Toque no botão flutuante **+** no canto inferior direito
2. Preencha os campos:
   - **Nome** (obrigatório)
   - **Email** (opcional)
   - **Telefone** (obrigatório)
3. Toque em **Salvar**

### Editar um Contato

1. Toque no contato que deseja editar
2. Modifique os campos desejados
3. Toque em **Salvar**

### Excluir um Contato

1. Mantenha pressionado o contato que deseja excluir
2. Confirme a exclusão no diálogo

## 🗄️ Banco de Dados

O aplicativo utiliza SQLite para armazenamento local. A tabela `contato` possui a seguinte estrutura:

| Campo    | Tipo    | Descrição           |
|----------|---------|---------------------|
| id       | INTEGER | Chave primária (AUTOINCREMENT) |
| nome     | TEXT    | Nome do contato (NOT NULL) |
| email    | TEXT    | Email do contato |
| telefone | TEXT    | Telefone do contato (NOT NULL) |
| foto     | TEXT    | Caminho da foto (futuro) |

## 📝 Licença

Este projeto foi desenvolvido para fins educacionais.

---

**Versão**: 1.0  
**Última Atualização**: 2025



