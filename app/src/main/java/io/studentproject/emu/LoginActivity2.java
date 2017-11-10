package io.studentproject.emu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity2 extends AppCompatActivity implements View.OnClickListener {

    private EditText mail;
    private EditText pass;
    private Button send;
    private TextView registered;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null) {
            finish();
            Toast.makeText(this, "Deja connecté", Toast.LENGTH_LONG);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        mail = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.password);
        send = (Button) findViewById(R.id.envoyer);
        registered = (TextView) findViewById(R.id.registered);

        progressDialog = new ProgressDialog(this);

        send.setOnClickListener(this);
        registered.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view == send){
            userLogin();
        }
        if (view == registered){
            finish();
            Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
            startActivity(intent);
        }
    }

    private void userLogin() {
        progressDialog.setMessage("Connection en cours...");
        progressDialog.show();

        String email = mail.getText().toString().trim();
        String password = pass.getText().toString().trim();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()) {
                            finish();
                            Toast.makeText(getApplicationContext(), "Connexion réussie", Toast.LENGTH_LONG);
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
                        else
                            Toast.makeText(getApplicationContext(), "Echec de la connexion", Toast.LENGTH_LONG);
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("CONNEXION", e.toString());
                    }

                });
    }
}
