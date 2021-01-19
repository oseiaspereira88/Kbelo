package com.example.kbelo.models;

import com.example.kbelo.util.LibraryClass;
import com.firebase.client.Firebase;

import java.util.ArrayList;

public class Adm extends Usuario{
    private Barbearia barbearia;

    public Adm(String id, String nome, String email, String senha, Barbearia barbearia) {
        super(id, nome, email, senha);
        this.barbearia = barbearia;
    }

    public Adm(){
        //construtor vazio
    }

    public void saveBD(){
        Firebase firebase = LibraryClass.getFirebase();
        firebase = firebase.child( "users" ).child("adms").child(getId());

        setSenha(null);
        setId(null);
        firebase.setValue( this );
    }

    public Barbearia getBarbearia() {
        return barbearia;
    }

    public void setBarbearia(Barbearia barbearia) {
        this.barbearia = barbearia;
    }
}
