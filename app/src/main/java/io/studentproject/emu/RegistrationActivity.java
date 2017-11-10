package io.studentproject.emu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener{
    private ProgressDialog progressDialog;

    private EditText email;
    private EditText pass;
    private EditText confPass;

    private TextView alreadyR;

    private Button register;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null) {
            finish();
            Toast.makeText(this, "Deja connecté", Toast.LENGTH_LONG);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.password);
        confPass = (EditText) findViewById(R.id.confPassword);

        alreadyR = (TextView) findViewById(R.id.alreadyRegistered);

        progressDialog = new ProgressDialog(this);

        register = (Button) findViewById(R.id.register);

        register.setOnClickListener(this);
        alreadyR.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if(view == register) {
            registerUser();
        }
        if (view == alreadyR){
            finish();
            Intent intent = new Intent(getApplicationContext(), LoginActivity2.class);
            startActivity(intent);
        }

    }

    private void registerUser() {
        String mail = email.getText().toString().trim();
        String password = pass.getText().toString().trim();
        if(mail.isEmpty() || password.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Mail ou MDP vide", Toast.LENGTH_LONG);
            return;
        }
        progressDialog.setMessage("Enregistrement en cours");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(mail, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            progressDialog.dismiss();
                            finish();
                            Toast.makeText(getApplicationContext(), "Connecté", Toast.LENGTH_LONG);
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Il y a eu un problème réessayer", Toast.LENGTH_LONG);
                        }
                    }
                });
    }
}
