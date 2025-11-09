package com.example.agenda;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.agenda.controller.ContatoController;
import com.example.agenda.model.Contato;

public class AdicionarContatoActivity extends AppCompatActivity {
    private EditText editNome, editEmail, editTelefone;
    private Button btnSalvar;
    private ImageView imageViewFoto;

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
    }

    public void inicializarViews(){
        editNome = findViewById(R.id.editTextNome);
        editEmail = findViewById(R.id.editTextEmail);
        editTelefone = findViewById(R.id.editTextTelefone);
        btnSalvar = findViewById(R.id.btnSalvar);
        imageViewFoto = findViewById(R.id.imageView2);

        btnSalvar.setOnClickListener(v -> salvarContato());
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
            }
        }
    }

    public void salvarContato(){
        //recuperar os valores dos edits
        String nome = editNome.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String telefone = editTelefone.getText().toString().trim();

        //validacao
        if (nome.isEmpty() || telefone.isEmpty()){
            Toast.makeText(this, "Nome e telefone são obrigatórios", Toast.LENGTH_SHORT).show();
            return; // Não prosseguir se a validação falhar
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
                Toast.makeText(this, "Contato atualizado com sucesso", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Erro ao atualizar contato", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Inserir novo contato
            contatoController.adicionarContato(nome, email, telefone, ""); //foto em branco
            Toast.makeText(this, "Contato adicionado com sucesso", Toast.LENGTH_SHORT).show();
        }

        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        contatoController.close();
    }
}