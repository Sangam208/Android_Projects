package com.example.egmail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private EditText to,bcc,cc,subject,body;
    private Uri fileUri;

    private final ActivityResultLauncher<Intent> filePickerLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    fileUri = result.getData().getData(); // Get the URI of the selected file
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        to = findViewById(R.id.email);
        bcc = findViewById(R.id.bcc);
        cc = findViewById(R.id.cc);
        subject = findViewById(R.id.subject);
        body = findViewById(R.id.body);
        ImageButton button = findViewById(R.id.button);
        ImageButton attach = findViewById(R.id.attach);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("*/*"); // Only email apps handle this.
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{to.getText().toString()});
                    intent.putExtra(Intent.EXTRA_BCC, new String[]{bcc.getText().toString()});
                    intent.putExtra(Intent.EXTRA_CC, new String[]{cc.getText().toString()});
                    intent.putExtra(Intent.EXTRA_SUBJECT, subject.getText().toString());
                    intent.putExtra(Intent.EXTRA_TEXT, body.getText().toString());
                    if (fileUri != null) {
                        intent.setType("*/*"); // Set the type to handle all file types
                        intent.putExtra(Intent.EXTRA_STREAM, fileUri);
                    }
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }
            }
        });

        attach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
//                filePickerLauncher.launch(Intent.createChooser(intent, "Select a file"));
                filePickerLauncher.launch(intent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}