package com.example.kbelo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.kbelo.models.Cliente;
import com.example.kbelo.util.LibraryClass;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Firebase firebase;
    private EditText editEmail, editSenha, editNome;
    private String email, senha, nome;
    private Button bLogar;
    private ImageView imgBack, imgFB, imgGmail, imgCadastrar, imgAdm;
    private TextView tvCastrar, tvAdm;
    private boolean isLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //get Firebase existente
        firebase = LibraryClass.getFirebase();
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        initViews();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        if(currentUser == null){
            Toast.makeText(this, "Usuario não autenticado!", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(this, "Usuario já está conectado!", Toast.LENGTH_SHORT).show();
            Intent it = new Intent(this, MainActivity.class);
            startActivity(it);
            finish();
        }
    }

    private void initViews() {
        editEmail = (EditText) findViewById(R.id.editEmail);
        editSenha = (EditText) findViewById(R.id.editSenha);
        editNome = (EditText) findViewById(R.id.editNome);
        bLogar = (Button) findViewById(R.id.bLogar);
        tvCastrar = (TextView) findViewById(R.id.tvCadastrar);
        tvAdm = (TextView) findViewById(R.id.tvAdm);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgCadastrar = (ImageView) findViewById(R.id.imgCadastrar);
        imgAdm = (ImageView) findViewById(R.id.imgAdm);
        imgFB = (ImageView) findViewById(R.id.imgFB);
        imgGmail = (ImageView) findViewById(R.id.imgGmail);
        isLogin = true;

        editEmail.setSelectAllOnFocus(true);
        editSenha.setSelectAllOnFocus(true);
        editNome.setSelectAllOnFocus(true);

        View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    if(editNome.getText().length()==0){
                        editNome.setText("Nome");
                    }
                    if(editEmail.getText().length()==0){
                        editEmail.setText("Email");
                    }
                    if(editSenha.getText().length()==0){
                        editSenha.setText("Senha");
                    }
                }
            }
        };

        editNome.setOnFocusChangeListener(onFocusChangeListener);
        editEmail.setOnFocusChangeListener(onFocusChangeListener);
        editSenha.setOnFocusChangeListener(onFocusChangeListener);

        bLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickAnimation(view);
                if(verificarCampos()){
                    if(isLogin) {
                        signIn(email, senha);
                    } else{
                        //Cliente novoCliente = new Cliente();
                        //novoCliente.setEmail(email);
                        //novoCliente.setSenha(senha);
                        //novoCliente.setNome(nome);
                        //saveUserClient(novoCliente);

                        criarNovaConta(email, senha);
                    }
                } else{
                    editEmail.setError("O email deve ser válido!");
                    editSenha.setError("A senha deve ter ao menos 8 caracteres!");
                    if(!isLogin) editNome.setError("O nome deve ter ao menos 8 caracteres!");
                }
            }
        });

        tvCastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alternarLoginCadastro();
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alternarLoginCadastro();
                clickAnimation(view);
            }
        });

        imgFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Efetuar Logion/Cadastro by FB
                clickAnimation(view);
            }
        });

        imgGmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Efetuar Logion/Cadastro by Gmail
                clickAnimation(view);
            }
        });

        imgGmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Efetuar Logion/Cadastro by Gmail
                clickAnimation(view);
            }
        });

        View.OnClickListener admOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //abrir AdmLoginActivity
                Intent it = new Intent(getBaseContext(), AdmLoginActivity.class);
                startActivity(it);
                clickAnimation(view);
                finish();
            }
        };

        tvAdm.setOnClickListener(admOnClickListener);
        imgAdm.setOnClickListener(admOnClickListener);
    }

    private void saveUserClient(final Cliente cliente) {
        firebase.createUser(
                email,
                senha,
                new Firebase.ValueResultHandler<Map<String, Object>>() {

                    @Override
                    public void onSuccess(Map<String, Object> stringObjectMap) {
                        cliente.setId(stringObjectMap.get("uid").toString());
                        cliente.saveBD();
                        firebase.unauth();

                        Toast.makeText(getApplicationContext(), "Conta criada com sucesso!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FirebaseError firebaseError) {
                        Toast.makeText(getApplicationContext(), "Erro na conexão com o Firebase -> ", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    private void alternarLoginCadastro() {
        if(isLogin){
            isLogin = false;
            tvCastrar.setVisibility(View.GONE);
            imgCadastrar.setVisibility(View.GONE);
            bLogar.setText("Cadastrar");
            editNome.setVisibility(View.VISIBLE);
            imgBack.setVisibility(View.VISIBLE);
            formSlideUp();
        } else{
            isLogin = true;
            tvCastrar.setVisibility(View.VISIBLE);
            imgCadastrar.setVisibility(View.VISIBLE);
            bLogar.setText("Logar");
            editNome.setVisibility(View.GONE);
            imgBack.setVisibility(View.GONE);
            formSlideUp();
        }
    }

    private void clickAnimation(View view) {
        YoYo.with(Techniques.Pulse).duration(300).repeat(0).playOn(view);
    }

    private void formSlideUp() {
        clickAnimation(imgBack);
        YoYo.with(Techniques.SlideInUp).duration(500).repeat(0).playOn(editNome);
        YoYo.with(Techniques.SlideInUp).duration(400).repeat(0).playOn(editEmail);
        YoYo.with(Techniques.SlideInUp).duration(200).repeat(0).playOn(editSenha);
        YoYo.with(Techniques.SlideInLeft).duration(520).repeat(0).playOn(bLogar);
        YoYo.with(Techniques.SlideInLeft).duration(700).repeat(0).playOn(tvCastrar);
        YoYo.with(Techniques.SlideInLeft).duration(750).repeat(0).playOn(imgCadastrar);
        YoYo.with(Techniques.SlideInRight).duration(750).repeat(0).playOn(tvAdm);
        YoYo.with(Techniques.SlideInRight).duration(700).repeat(0).playOn(imgAdm);
        YoYo.with(Techniques.SlideInRight).duration(600).repeat(0).playOn(imgFB);
        YoYo.with(Techniques.SlideInRight).duration(600).repeat(0).playOn(imgGmail);
        YoYo.with(Techniques.Tada).duration(600).repeat(0).playOn(findViewById(R.id.tvTituloApp));
    }

    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Info:", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Info:", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                            // ...
                        }

                        // ...
                    }
                });
    }

    private boolean verificarCampos() {
        boolean isOK = true;
        email = editEmail.getText().toString();
        senha = editSenha.getText().toString();
        nome = editNome.getText().toString();
        if(!email.contains("@") || !email.contains(".com") || email.length()<14 || email.contains(" ")){
            isOK = false;
        }
        if(senha.length()<8 || (!isLogin && nome.length()<8)){
            isOK = false;
        }
        return isOK;
    }

    private void criarNovaConta(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Info:", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Info:", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void desconectarUser(){
        FirebaseAuth.getInstance().signOut();
    }
}