package com.example.kbelo.models;

import com.example.kbelo.util.LibraryClass;
import com.firebase.client.Firebase;

public class Cliente extends Usuario {
    private String endereco, cep, ciclo;

    public Cliente(String id, String nome, String email, String senha) {
        super(id, nome, email, senha);
    }

    public Cliente(){
        //construtor vazio
    }

    public void saveBD(){
        Firebase firebase = LibraryClass.getFirebase();
        firebase = firebase.child( "users" ).child("clints").child(getId());

        setSenha(null);
        setId(null);
        firebase.setValue( this );
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCiclo() {
        return ciclo;
    }

    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
    }
}
