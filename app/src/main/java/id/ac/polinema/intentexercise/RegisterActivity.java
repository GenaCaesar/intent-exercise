package id.ac.polinema.intentexercise;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.concurrent.ThreadFactory;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = RegisterActivity.class.getCanonicalName();
    private static final int GALLERY_REQUEST_CODE = 1;

    public static final String FULLNAME_KEY = "fullname";
    public static final String EMAIL_KEY = "email";
    public static final String HOMEPAGE_KEY = "homepage";
    public static final String ABOUT_KEY = "about";

    private TextInputEditText inputName;
    private TextInputEditText inputEmail;
    private TextInputEditText inputHomepage;
    private TextInputEditText inputAbout;
    private ImageView avatarImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        avatarImage = findViewById(R.id.image_profile);

        inputName = findViewById(R.id.text_fullname);
        inputEmail = findViewById(R.id.text_email);
        inputHomepage = findViewById(R.id.text_homepage);
        inputAbout = findViewById(R.id.text_about);
    }

    public void handleOK(View view) {
        String name = inputName.getText().toString();
        String email = inputEmail.getText().toString();
        String homepage = inputHomepage.getText().toString();
        String about = inputAbout.getText().toString();

        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra(ABOUT_KEY, about);
        intent.putExtra(FULLNAME_KEY, name);
        intent.putExtra(EMAIL_KEY, email);
        intent.putExtra(HOMEPAGE_KEY, homepage);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }

        if (requestCode == GALLERY_REQUEST_CODE) {
            if (data != null) {
                try {
                    Uri imageUri = data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    avatarImage.setImageBitmap(bitmap);
                } catch (IOException e) {
                    Toast.makeText(this, "Can't load image", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, e.getMessage());
                }
            }
        }
    }

    public void handleGambar(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }
}
