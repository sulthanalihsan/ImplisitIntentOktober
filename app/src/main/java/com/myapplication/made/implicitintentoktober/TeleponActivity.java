package com.myapplication.made.implicitintentoktober;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TeleponActivity extends AppCompatActivity {

    @BindView(R.id.edt_nomor_tujuan)
    EditText edtNomorTujuan;
    @BindView(R.id.btn_panggil_langsung)
    Button btnPanggilLangsung;
    @BindView(R.id.btn_dial_phone)
    Button btnDialPhone;
    @BindView(R.id.btn_open_contact)
    ImageButton btnOpenContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telepon);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.edt_nomor_tujuan, R.id.btn_panggil_langsung, R.id.btn_dial_phone, R.id.btn_open_contact})
    public void onViewClicked(View view) {
        String noTelp = edtNomorTujuan.getText().toString().trim();
        switch (view.getId()) {
            case R.id.edt_nomor_tujuan:

                break;
            case R.id.btn_open_contact:
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                startActivityForResult(intent, 100);
                break;
            case R.id.btn_panggil_langsung:
                if (TextUtils.isEmpty(noTelp)) {
                    Toast.makeText(this, "Jangan kosong", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intentDial = new Intent(Intent.ACTION_CALL);
                    intentDial.setData(Uri.parse("tel:" + noTelp));
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {
                        } else {
                            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 10);
                        }
                    }
                    startActivity(intentDial);
                }
                break;
            case R.id.btn_dial_phone:
                if (TextUtils.isEmpty(noTelp)) {
                    Toast.makeText(this, "Jangan kosong", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intentDial = new Intent(Intent.ACTION_DIAL);
                    intentDial.setData(Uri.parse("tel:" + noTelp));
                    startActivity(intentDial);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                Cursor cursor = null;
                Uri uri = data.getData();
                cursor = getContentResolver().query(uri, new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},
                        null, null, null, null);
                if (cursor != null && cursor.moveToNext()) {
                    String nomorTelepon = cursor.getString(0);
                    edtNomorTujuan.setText(nomorTelepon);
                }
            } else if (requestCode == RESULT_CANCELED) {
                Toast.makeText(this, "batal ambil nomor telepon", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
