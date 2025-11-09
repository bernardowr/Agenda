# 📱 Agenda - Aplicativo de Gerenciamento de Contatos

Um aplicativo Android para gerenciar seus contatos pessoais, desenvolvido com Material Design 3 e SQLite para armazenamento local.

## 🎯 Características

- ✅ **Interface Moderna**: Design Material Design 3 com cores vibrantes e animações suaves
- ✅ **CRUD Completo**: Criar, ler, atualizar e excluir contatos
- ✅ **Armazenamento Local**: SQLite para persistência de dados
- ✅ **Preferências do Usuário**: SharedPreferences para salvar configurações
- ✅ **Ordenação**: Ordene contatos por nome ou telefone
- ✅ **Avatares Personalizados**: Avatar circular com inicial do nome
- ✅ **Validação de Dados**: Validação de campos obrigatórios
- ✅ **Busca Rápida**: Interface otimizada para fácil navegação


### Lista de Contatos
- Visualize todos os seus contatos em cards elegantes
- Cada contato exibe nome, telefone, email e avatar personalizado
- Clique para editar, segure para excluir

### Adicionar/Editar Contato
- Interface limpa e intuitiva
- Campos de entrada com validação
- Avatar atualizado em tempo real conforme você digita

## 🛠️ Tecnologias Utilizadas

- **Linguagem**: Java
- **SDK Mínimo**: Android 7.0 (API 24)
- **SDK Alvo**: Android 14 (API 36)
- **Arquitetura**: MVC (Model-View-Controller)
- **Banco de Dados**: SQLite
- **UI/UX**: Material Design 3
- **Bibliotecas**:
  - Material Components
  - CardView
  - CoordinatorLayout
  - AppCompat

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

### Configurar Ordenação

1. Toque no ícone de configurações (⚙️) na toolbar
2. Selecione a opção de ordenação desejada:
   - Por Nome
   - Por Telefone

## 🏗️ Estrutura do Projeto

```
Agenda/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/agenda/
│   │   │   │   ├── MainActivity.java          # Activity principal (lista de contatos)
│   │   │   │   ├── AdicionarContatoActivity.java  # Activity para adicionar/editar
│   │   │   │   ├── controller/
│   │   │   │   │   └── ContatoController.java # Controlador de negócios
│   │   │   │   ├── model/
│   │   │   │   │   ├── Contato.java           # Modelo de dados
│   │   │   │   │   ├── ContatoDAO.java        # Acesso a dados
│   │   │   │   │   └── DatabaseHelper.java    # Helper do SQLite
│   │   │   │   └── util/
│   │   │   │       └── PreferenceManager.java # Gerenciador de preferências
│   │   │   ├── res/
│   │   │   │   ├── layout/                    # Layouts XML
│   │   │   │   ├── values/                    # Cores, strings, temas
│   │   │   │   └── menu/                      # Menus
│   │   │   └── AndroidManifest.xml
│   │   └── test/                              # Testes unitários
│   └── build.gradle.kts
├── gradle/
│   └── libs.versions.toml                     # Versões das dependências
└── README.md
```

## 🗄️ Banco de Dados

O aplicativo utiliza SQLite para armazenamento local. A tabela `contato` possui a seguinte estrutura:

| Campo    | Tipo    | Descrição           |
|----------|---------|---------------------|
| id       | INTEGER | Chave primária (AUTOINCREMENT) |
| nome     | TEXT    | Nome do contato (NOT NULL) |
| email    | TEXT    | Email do contato |
| telefone | TEXT    | Telefone do contato (NOT NULL) |
| foto     | TEXT    | Caminho da foto (futuro) |

## ⚙️ Configurações

O aplicativo utiliza `SharedPreferences` para armazenar as preferências do usuário:

- **Ordenação**: Nome ou Telefone
- **Tema**: Claro, Escuro ou Sistema (preparado para futuro)
- **Primeiro Lançamento**: Controle de primeira execução

## 🎨 Personalização

### Cores

As cores podem ser personalizadas em `app/src/main/res/values/colors.xml`:

```xml
<color name="primary_blue">#2196F3</color>
<color name="primary_dark_blue">#1976D2</color>
<color name="accent_blue">#03A9F4</color>
```

### Temas

Os temas podem ser modificados em `app/src/main/res/values/themes.xml`

## 🧪 Testes

## 📝 Licença

Este projeto foi desenvolvido para fins educacionais.

---

**Versão**: 1.0  
**Última Atualização**: 2025

