package com.manjubs.UploadImage;
//Import Android class files
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

// Main activity class
public class MainActivity extends AppCompatActivity {

    //Declare variables
    private static final int SELECT_PICTURE = 100;
    ImageView image;
    Button upload_btn;
    Button btnClear;
    @Override
    // OnCreate files
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Create objects of controls
        setContentView(R.layout.activity_main);
        upload_btn= (Button)findViewById(R.id.btnBrowseGallary);
        btnClear= (Button)findViewById(R.id.btnClearImage);
        image= (ImageView)findViewById(R.id.imgSelectedPicture);
        upload_btn.setOnClickListener(new View.OnClickListener() {
            // OnClock function to browse gallary
            @Override
            public void onClick(View v) {
                //Declare intent object and browse for image type
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            //on button click clear set null to image view
            public void onClick(View v) {
                image.setImageBitmap(null);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                // Get the url from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // Get the path from the Uri
                    String path = getPathFromURI(selectedImageUri);
                    Log.i("MainActivity", "Image Path : " + path);
                    // Set the image in ImageView
                    image.setImageURI(selectedImageUri);
                }
            }
        }
    }

    /* Get the real path from the URI */
    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

}
