package com.myapplication.made.implicitintentoktober;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EmailActivity extends AppCompatActivity {

    @BindView(R.id.edt_email)
    EditText edtEmail;
    @BindView(R.id.edt_subject)
    EditText edtSubject;
    @BindView(R.id.edt_body_email)
    EditText edtBodyEmail;
    @BindView(R.id.btn_kirim)
    Button btnKirim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_kirim)
    public void onViewClicked() {
        String email = edtEmail.getText().toString().trim();
        String subject = edtSubject.getText().toString().trim();
        String bodyemail = edtBodyEmail.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(subject) || TextUtils.isEmpty(bodyemail)) {
            Toast.makeText(this, "TIdak boleh kosong", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_TEXT, bodyemail);
            intent.setType("message/rfc822");
            startActivity(intent);

        }
    }
}
