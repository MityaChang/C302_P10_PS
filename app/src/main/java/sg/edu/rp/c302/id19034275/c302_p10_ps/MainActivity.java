package sg.edu.rp.c302.id19034275.c302_p10_ps;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class MainActivity extends AppCompatActivity {
    private TextView tvTitle;
    private EditText etTitle;
    private TextView tvDate;
    private EditText etDate;
    private Button btnAdd;

    private FirebaseFirestore db;
    private CollectionReference colRef;
    private DocumentReference docRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvTitle = findViewById(R.id.textViewTitle);
        etTitle = findViewById(R.id.editTextTitle);
        tvDate = findViewById(R.id.textViewDate);
        etDate = findViewById(R.id.editTextDate);
        btnAdd = findViewById(R.id.buttonAdd);
        db = FirebaseFirestore.getInstance();

        colRef = db.collection("messages");
        docRef = colRef.document("message");
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable  DocumentSnapshot documentSnapshot, @Nullable  FirebaseFirestoreException error) {
                if(error != null){
                    return;
                }
                if(documentSnapshot != null && documentSnapshot.exists()){
                    Message msg = documentSnapshot.toObject(Message.class);
                    String title = msg.getTitle();
                    String date = msg.getDate();
                    tvTitle.setText(title);
                    tvDate.setText(date);
                }
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = etTitle.getText().toString();
                String date = etDate.getText().toString();
                Message msg = new Message(title, date);
                docRef.set(msg);
            }
        });
    }
}