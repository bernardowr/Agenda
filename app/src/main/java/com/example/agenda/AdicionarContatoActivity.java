package com.example.agenda;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.agenda.controller.ContatoController;
import com.example.agenda.model.Contato;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class AdicionarContatoActivity extends AppCompatActivity {
    private TextInputEditText editNome, editEmail, editTelefone;
    private TextInputLayout layoutNome, layoutEmail, layoutTelefone;
    private MaterialButton btnSalvar;
    private TextView textAvatarLarge;
    private MaterialToolbar toolbar;

    ContatoController contatoController;
    private Contato contatoEditando; // Contato sendo editado (null se for novo)
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_adicionar_contato);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        contatoController = new ContatoController(this);
        inicializarViews();
        carregarDadosContato();
        configurarToolbar();
        configurarAvatar();
    }

    private void inicializarViews(){
        toolbar = findViewById(R.id.toolbar3);
        layoutNome = findViewById(R.id.layoutNome);
        layoutEmail = findViewById(R.id.layoutEmail);
        layoutTelefone = findViewById(R.id.layoutTelefone);
        
        editNome = findViewById(R.id.editTextNome);
        editEmail = findViewById(R.id.editTextEmail);
        editTelefone = findViewById(R.id.editTextTelefone);
        btnSalvar = findViewById(R.id.btnSalvar);
        textAvatarLarge = findViewById(R.id.textAvatarLarge);

        btnSalvar.setOnClickListener(v -> salvarContato());
    }

    private void configurarToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void configurarAvatar() {
        // Atualizar avatar quando o nome mudar
        editNome.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                atualizarAvatar(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void atualizarAvatar(String nome) {
        if (nome != null && !nome.trim().isEmpty()) {
            textAvatarLarge.setText(String.valueOf(nome.trim().charAt(0)).toUpperCase());
        } else {
            textAvatarLarge.setText("?");
        }
    }

    private void carregarDadosContato(){
        // Verificar se há um contato passado no Intent (modo de edição)
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("contato")) {
            contatoEditando = (Contato) intent.getSerializableExtra("contato");
            
            // Se há um contato, preencher os campos com os dados
            if (contatoEditando != null) {
                editNome.setText(contatoEditando.getNome());
                editEmail.setText(contatoEditando.getEmail() != null ? contatoEditando.getEmail() : "");
                editTelefone.setText(contatoEditando.getTelefone());
                
                // Atualizar título da toolbar
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(R.string.editar_contato);
                }
                
                // Atualizar avatar
                atualizarAvatar(contatoEditando.getNome());
            }
        } else {
            // Modo novo contato
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(R.string.novo_contato);
            }
        }
    }

    public void salvarContato(){
        //recuperar os valores dos edits
        String nome = editNome.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String telefone = editTelefone.getText().toString().trim();

        //validacao
        boolean valido = true;
        if (nome.isEmpty()) {
            if (layoutNome != null) {
                layoutNome.setError(getString(R.string.nome) + " é obrigatório");
            }
            valido = false;
        } else {
            if (layoutNome != null) {
                layoutNome.setError(null);
            }
        }
        
        if (telefone.isEmpty()) {
            if (layoutTelefone != null) {
                layoutTelefone.setError(getString(R.string.telefone) + " é obrigatório");
            }
            valido = false;
        } else {
            if (layoutTelefone != null) {
                layoutTelefone.setError(null);
            }
        }
        
        if (!valido) {
            Toast.makeText(this, R.string.nome_telefone_obrigatorios, Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Verificar se está editando um contato existente ou adicionando novo
        if (contatoEditando != null) {
            // Atualizar contato existente
            int linhasAfetadas = contatoController.atualizarContato(
                contatoEditando.getId(), 
                nome, 
                email, 
                telefone, 
                contatoEditando.getFoto() != null ? contatoEditando.getFoto() : ""
            );
            if (linhasAfetadas > 0) {
                Toast.makeText(this, R.string.contato_atualizado, Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, R.string.erro_atualizar, Toast.LENGTH_SHORT).show();
            }
        } else {
            // Inserir novo contato
            contatoController.adicionarContato(nome, email, telefone, ""); //foto em branco
            Toast.makeText(this, R.string.contato_adicionado, Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Não fechar o controller aqui, pois pode causar problemas de concorrência
        // O SQLiteOpenHelper gerencia o fechamento automaticamente
        // if (contatoController != null) {
        //     contatoController.close();
        // }
    }
}
