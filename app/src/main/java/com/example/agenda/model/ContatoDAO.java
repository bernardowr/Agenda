package com.example.agenda.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;
public class ContatoDAO {
    private SQLiteDatabase database;
    private DatabaseHelper databaseHelper; //nossa classe helper SQLite
    //construtor - instancia o helper
    public ContatoDAO(Context context){
        databaseHelper = new DatabaseHelper(context);
    }

    //abre o database para escrita
    public void open(){
        if (database == null || !database.isOpen()) {
            database = databaseHelper.getWritableDatabase();
        }
    }

    //fecha o database
    public void close(){
        if (database != null && database.isOpen()) {
            database.close();
        }
        // Não fechar o helper, pois pode ser usado novamente
        // databaseHelper.close();
    }

    //metodo para inserir novo constato
    public long adicionarContato(Contato contato){
        // Verificar se o banco está aberto
        if (database == null || !database.isOpen()) {
            open();
        }
        //cria um objeto values para os valores a serem inseridos
        ContentValues values = new ContentValues();
        values.put("nome", contato.getNome() != null ? contato.getNome() : "");
        values.put("email", contato.getEmail() != null ? contato.getEmail() : "");
        values.put("telefone", contato.getTelefone() != null ? contato.getTelefone() : "");
        values.put("foto", contato.getFoto() != null ? contato.getFoto() : "");
        //metodo para inserir - parametros nome da tabela "contato" e os valores
        try {
            long id = database.insert("contato", null, values);
            return id;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    //metodo para SELECT - retornando uma lista dos contatos buscados
    public List<Contato> listarContatos(){
        List<Contato> contatos = new ArrayList<>();
        // Verificar se o banco está aberto
        if (database == null || !database.isOpen()) {
            open();
        }
        //cursor para percorrer os resultados - query na tabela "contato"
        // Não ordenamos aqui, a ordenação será feita na Activity conforme preferências do usuário
        Cursor cursor = null;
        try {
            cursor = database.query("contato", null, null, null, null, null, null);
            //cursor no inicio
            if (cursor != null && cursor.moveToFirst()){
                do{//repete
                    Contato contato = new Contato();//cria uma instancia de Contato com os valores
                    int idIndex = cursor.getColumnIndex("id");
                    int nomeIndex = cursor.getColumnIndex("nome");
                    int emailIndex = cursor.getColumnIndex("email");
                    int telefoneIndex = cursor.getColumnIndex("telefone");
                    int fotoIndex = cursor.getColumnIndex("foto");
                    
                    if (idIndex >= 0) contato.setId(cursor.getInt(idIndex));
                    if (nomeIndex >= 0) contato.setNome(cursor.getString(nomeIndex));
                    if (emailIndex >= 0) contato.setEmail(cursor.getString(emailIndex));
                    if (telefoneIndex >= 0) contato.setTelefone(cursor.getString(telefoneIndex));
                    if (fotoIndex >= 0) contato.setFoto(cursor.getString(fotoIndex));
                    
                    contatos.add(contato); //adiciona o contato à lista
                }while(cursor.moveToNext());//cursor para o proximo
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return contatos; //retorna a lista
    }
    //metodo para fazer o update de um contato
    public int atualizarContato(Contato contato){
        // Verificar se o banco está aberto
        if (database == null || !database.isOpen()) {
            open();
        }
        ContentValues values = new ContentValues();
        values.put("nome", contato.getNome() != null ? contato.getNome() : "");
        values.put("email", contato.getEmail() != null ? contato.getEmail() : "");
        values.put("telefone", contato.getTelefone() != null ? contato.getTelefone() : "");
        values.put("foto", contato.getFoto() != null ? contato.getFoto() : "");
        //update na tabela "contato" com os valores
        return database.update("contato", values, "id=?", new String[]{String.valueOf(contato.getId())});
    }

    //metodo para delete pelo id
    public void apagarContato(int id){
        // Verificar se o banco está aberto
        if (database == null || !database.isOpen()) {
            open();
        }
        //delete na tabela "contato"
        database.delete("contato", "id=?", new String[]{String.valueOf(id)});
    }

    //metodo para SELECT por um id - retorna um contato
    public Contato buscaContatoPorId(int id){
        Cursor cursor = database.query("contato", null, "id=?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()){
            Contato contato = new Contato();
            contato.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            contato.setNome(cursor.getString(cursor.getColumnIndexOrThrow("nome")));
            contato.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("email")));
            contato.setTelefone(cursor.getString(cursor.getColumnIndexOrThrow("telefone")));
            contato.setFoto(cursor.getString(cursor.getColumnIndexOrThrow("foto")));
            cursor.close();
            return contato;
        }
        return null;
    }
}