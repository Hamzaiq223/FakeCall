package com.tool.fakecall.Activities.IncomingCall;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.tool.fakecall.Common.BlurBuilder;
import com.tool.fakecall.R;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class IncomingCall extends AppCompatActivity {

    FirebaseStorage storage;
    StorageReference storageRef;
    ImageView imageView,ivUserImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming_call);

        imageView = findViewById(R.id.ivImage);
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        displayImageFromFolder("Ronaldo");
    }

    private void displayImageFromFolder(String folderName) {
        StorageReference folderRef = storageRef.child(folderName);

        folderRef.listAll().addOnSuccessListener(listResult -> {
            // Get the list of items (images) in the folder
            List<StorageReference> imageRefs = listResult.getItems();

            // Check if there are images in the folder
            if (!imageRefs.isEmpty()) {
                // Get the reference of the first image in the list
                StorageReference firstImageRef = imageRefs.get(0); // You can change this logic to select any other image

                firstImageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    String imageUrl = uri.toString();


//                    Bitmap bitmap = BitmapFactory.decodeFile(imageUrl);
//                    Bitmap blurredBitmap = BlurBuilder.blur(this, bitmap);
//                    imageView.setImageBitmap(blurredBitmap);

                    // Load the image from URL using Picasso (or any other library you prefer)
                    Picasso.get().load(imageUrl).into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            // Apply blur effect to the bitmap

                            Bitmap blurredBitmap = BlurBuilder.blur(IncomingCall.this, bitmap);
                            imageView.setImageBitmap(blurredBitmap);
                        }

                        @Override
                        public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                            // Handle failure
                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {
                            // Handle loading
                        }
                    });


                }).addOnFailureListener(e -> {
                    // Handle error
                });
            }
        }).addOnFailureListener(e -> {
            // Handle error
        });
    }


}