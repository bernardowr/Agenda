package com.example.agenda.util;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {
    private static final String PREF_NAME = "AgendaPreferences";
    private static final String KEY_SORT_ORDER = "sort_order";
    private static final String KEY_THEME_MODE = "theme_mode";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_FIRST_LAUNCH = "first_launch";
    
    // Valores padrão
    private static final String DEFAULT_SORT_ORDER = "nome"; // "nome" ou "telefone"
    private static final String DEFAULT_THEME_MODE = "sistema"; // "claro", "escuro" ou "sistema"
    private static final String DEFAULT_USER_NAME = "";
    private static final boolean DEFAULT_FIRST_LAUNCH = true;
    
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    
    public PreferenceManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    
    // Métodos para Ordenação
    public void setSortOrder(String sortOrder) {
        editor.putString(KEY_SORT_ORDER, sortOrder);
        editor.apply();
    }
    
    public String getSortOrder() {
        return sharedPreferences.getString(KEY_SORT_ORDER, DEFAULT_SORT_ORDER);
    }
    
    // Métodos para Tema
    public void setThemeMode(String themeMode) {
        editor.putString(KEY_THEME_MODE, themeMode);
        editor.apply();
    }
    
    public String getThemeMode() {
        return sharedPreferences.getString(KEY_THEME_MODE, DEFAULT_THEME_MODE);
    }
    
    // Métodos para Nome do Usuário
    public void setUserName(String userName) {
        editor.putString(KEY_USER_NAME, userName);
        editor.apply();
    }
    
    public String getUserName() {
        return sharedPreferences.getString(KEY_USER_NAME, DEFAULT_USER_NAME);
    }
    
    // Métodos para Primeiro Lançamento
    public void setFirstLaunch(boolean isFirstLaunch) {
        editor.putBoolean(KEY_FIRST_LAUNCH, isFirstLaunch);
        editor.apply();
    }
    
    public boolean isFirstLaunch() {
        return sharedPreferences.getBoolean(KEY_FIRST_LAUNCH, DEFAULT_FIRST_LAUNCH);
    }
    
    // Limpar todas as preferências
    public void clearPreferences() {
        editor.clear();
        editor.apply();
    }
    
    // Verificar se uma preferência existe
    public boolean contains(String key) {
        return sharedPreferences.contains(key);
    }
}

