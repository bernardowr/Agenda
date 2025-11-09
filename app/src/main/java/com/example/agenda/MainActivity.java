package com.example.agenda;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.agenda.controller.ContatoController;
import com.example.agenda.model.Contato;
import com.example.agenda.util.PreferenceManager;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MaterialToolbar toolbar;
    private ListView listView;
    private TextView textViewEmpty;
    private FloatingActionButton fabAdicionar;
    private List<Contato> contatos;
    private ArrayAdapter<Contato> adapter;
    private ContatoController controller;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        
        preferenceManager = new PreferenceManager(this);
        inicializarViews();
        carregarContatos();
        configurarListeners();
        configurarToolbar();
    }

    private void inicializarViews(){
        toolbar = findViewById(R.id.toolbar);
        listView = findViewById(R.id.listView);
        textViewEmpty = findViewById(R.id.textViewEmpty);
        fabAdicionar = findViewById(R.id.fabAdicionar);

        fabAdicionar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AdicionarContatoActivity.class);
            startActivity(intent);
        });
    }

    private void configurarToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        
        if (id == R.id.menu_configuracoes) {
            mostrarDialogoConfiguracoes();
            return true;
        }
        
        return super.onOptionsItemSelected(item);
    }

    private void mostrarDialogoConfiguracoes() {
        String[] opcoesOrdenacao = {getString(R.string.ordenar_nome), getString(R.string.ordenar_telefone)};
        String ordenacaoAtual = preferenceManager.getSortOrder();
        int selecaoAtual = ordenacaoAtual.equals("nome") ? 0 : 1;

        new AlertDialog.Builder(this)
                .setTitle(R.string.configuracoes)
                .setSingleChoiceItems(opcoesOrdenacao, selecaoAtual, (dialog, which) -> {
                    String novaOrdenacao = which == 0 ? "nome" : "telefone";
                    preferenceManager.setSortOrder(novaOrdenacao);
                    carregarContatos();
                    dialog.dismiss();
                    Toast.makeText(this, getString(R.string.contato_atualizado), Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton(R.string.cancelar, null)
                .show();
    }

    private void carregarContatos(){
        // Recriar controller para garantir conexão fresca com o banco
        if (controller != null) {
            // Não fechar, apenas criar novo se necessário
        }
        controller = new ContatoController(this);
        
        // Carregar contatos do banco de dados
        List<Contato> contatosDoBanco = controller.listarContatos();
        
        // Criar uma nova lista para trabalhar (evitar problemas de referência)
        contatos = new ArrayList<>();
        if (contatosDoBanco != null) {
            contatos.addAll(contatosDoBanco);
        }
        
        // Ordenar contatos de acordo com preferência do usuário
        ordenarContatos();
        
        // Mostrar ou esconder mensagem de lista vazia
        if (contatos.isEmpty()) {
            textViewEmpty.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
            return; // Não criar adapter se não houver contatos
        } else {
            textViewEmpty.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
        }
        
        // Sempre recriar o adapter com uma nova lista para garantir que os dados estejam atualizados
        List<Contato> listaParaAdapter = new ArrayList<>(contatos);
        adapter = new ArrayAdapter<Contato>(this, R.layout.list_item, listaParaAdapter){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                if (convertView == null){
                    convertView = getLayoutInflater().inflate(R.layout.list_item, parent, false);
                }
                Contato contato = getItem(position);
                if (contato != null) {
                    // Preencher dados do contato
                    TextView textNome = convertView.findViewById(R.id.textNomeLista);
                    TextView textTelefone = convertView.findViewById(R.id.textTelLista);
                    TextView textEmail = convertView.findViewById(R.id.textEmailLista);
                    TextView textAvatar = convertView.findViewById(R.id.textAvatar);

                    if (textNome != null) {
                        textNome.setText(contato.getNome() != null ? contato.getNome() : "");
                    }
                    if (textTelefone != null) {
                        textTelefone.setText(contato.getTelefone() != null ? contato.getTelefone() : "");
                    }
                    
                    // Mostrar email se existir
                    if (textEmail != null) {
                        if (contato.getEmail() != null && !contato.getEmail().isEmpty()) {
                            textEmail.setText(contato.getEmail());
                            textEmail.setVisibility(View.VISIBLE);
                        } else {
                            textEmail.setVisibility(View.GONE);
                        }
                    }
                    
                    // Definir avatar com primeira letra do nome
                    if (textAvatar != null) {
                        String nome = contato.getNome();
                        if (nome != null && !nome.isEmpty()) {
                            textAvatar.setText(String.valueOf(nome.charAt(0)).toUpperCase());
                        } else {
                            textAvatar.setText("?");
                        }
                    }
                }
                return convertView;
            }
        };
        
        // Remover adapter anterior se existir
        if (listView.getAdapter() != null) {
            listView.setAdapter(null);
        }
        
        // Definir novo adapter
        listView.setAdapter(adapter);
        
        // Forçar atualização do ListView
        adapter.notifyDataSetChanged();
        listView.invalidateViews();
        
        // Atualizar a referência da lista para uso nos listeners
        this.contatos = listaParaAdapter;
    }

    private void ordenarContatos() {
        String ordenacao = preferenceManager.getSortOrder();
        
        if (ordenacao.equals("nome")) {
            Collections.sort(contatos, (c1, c2) -> {
                String nome1 = c1.getNome() != null ? c1.getNome() : "";
                String nome2 = c2.getNome() != null ? c2.getNome() : "";
                return nome1.compareToIgnoreCase(nome2);
            });
        } else if (ordenacao.equals("telefone")) {
            Collections.sort(contatos, (c1, c2) -> {
                String tel1 = c1.getTelefone() != null ? c1.getTelefone() : "";
                String tel2 = c2.getTelefone() != null ? c2.getTelefone() : "";
                return tel1.compareToIgnoreCase(tel2);
            });
        }
    }

    private void configurarListeners(){
        //clique curto em item do listView - alterar contato
        listView.setOnItemClickListener((parent, view, position, id) -> {
            if (adapter != null) {
                Contato contato = adapter.getItem(position);
                if (contato != null) {
                    Intent intent = new Intent(MainActivity.this, AdicionarContatoActivity.class);
                    intent.putExtra("contato", contato);
                    startActivity(intent);
                }
            }
        });

        //clique longo em item do listView - Excluir contato
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            if (adapter != null) {
                Contato contato = adapter.getItem(position);
                if (contato != null) {
                    //alerta de confirmação de exclusão
                    new AlertDialog.Builder(this)
                            .setTitle(R.string.confirmar_exclusao)
                            .setMessage(getString(R.string.deseja_excluir_contato, contato.getNome()))
                            .setPositiveButton(R.string.sim, (dialog, which) -> {
                                controller.apagarContato(contato.getId());
                                carregarContatos();
                                Toast.makeText(this, R.string.contato_excluido, Toast.LENGTH_SHORT).show();
                            })
                            .setNegativeButton(R.string.nao, null)
                            .show();
                }
            }
            return true;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarContatos(); //ao voltar para esta activity, recarrega
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (controller != null) {
            controller.close();
        }
    }
}
