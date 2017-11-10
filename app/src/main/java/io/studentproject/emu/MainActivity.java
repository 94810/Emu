package io.studentproject.emu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button logout;
    private TextView welcome;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        welcome = (TextView) findViewById(R.id.welcome);
        logout = (Button) findViewById(R.id.logout);

        String a = welcome.getText().toString() + firebaseAuth.getCurrentUser().getEmail();
        welcome.setText(a);

        logout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if( view == logout ){
            Toast.makeText(getApplicationContext(), "Clique sur Logout", Toast.LENGTH_LONG);
            firebaseAuth.signOut();
            finish();
            Toast.makeText(getApplicationContext(), "Vous avez bien été déconnecté(e)", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), LoginActivity2.class);
            startActivity(intent);
        }
    }
}
