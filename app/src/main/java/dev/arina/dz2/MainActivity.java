
package dev.arina.dz2;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MainActivity extends Activity {

    private ImageView mainImage;
    private ProgressBar progressBar;
    private TextView imageLabel;
    private TextView statusText;

    private Button btnPhoto1;
    private Button btnPhoto2;
    private Button btnGif;
    private Button btnClear;

    private final String PHOTO_1 =
            "https://images.unsplash.com/photo-1500534623283-312aade485b7?w=800";

    private final String PHOTO_2 =
            "https://images.unsplash.com/photo-1519501025264-65ba15a82390?w=800";
    private final String GIF_URL =
            "https://media.giphy.com/media/Ju7l5y9osyymQ/giphy.gif";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainImage = findViewById(R.id.mainImage);
        progressBar = findViewById(R.id.progressBar);
        imageLabel = findViewById(R.id.imageLabel);
        statusText = findViewById(R.id.statusText);

        btnPhoto1 = findViewById(R.id.btnPhoto1);
        btnPhoto2 = findViewById(R.id.btnPhoto2);
        btnGif = findViewById(R.id.btnGif);
        btnClear = findViewById(R.id.btnClear);

        btnPhoto1.setOnClickListener(v -> {
            loadPhoto(PHOTO_1, "NATURE");
        });

        btnPhoto2.setOnClickListener(v -> {
            loadPhoto(PHOTO_2, "CITY");
        });

        btnGif.setOnClickListener(v -> {
            loadGif();
        });

        btnClear.setOnClickListener(v -> {
            clearImage();
        });

        loadPhoto(PHOTO_1, "NATURE");
    }

    private void loadPhoto(String url, String label) {

        progressBar.setVisibility(View.VISIBLE);
        statusText.setText("Loading image...");
        imageLabel.setText(label);

        Glide.with(this)
                .load(url)
                .centerCrop()
                .listener(new RequestListener<Drawable>() {

                    @Override
                    public boolean onLoadFailed(
                            @Nullable GlideException e,
                            Object model,
                            Target<Drawable> target,
                            boolean isFirstResource) {

                        progressBar.setVisibility(View.GONE);
                        statusText.setText("Failed to load image");
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(
                            Drawable resource,
                            Object model,
                            Target<Drawable> target,
                            DataSource dataSource,
                            boolean isFirstResource) {

                        progressBar.setVisibility(View.GONE);
                        statusText.setText(
                                "Image loaded successfully"
                        );
                        return false;
                    }
                })
                .into(mainImage);
    }

    private void loadGif() {

        progressBar.setVisibility(View.VISIBLE);
        imageLabel.setText("ANIMATED GIF");
        statusText.setText("Loading animation...");

        Glide.with(this)
                .asGif()
                .load(GIF_URL)
                .listener(
                        new RequestListener<
                                com.bumptech.glide.load.resource.gif.GifDrawable>() {

                            @Override
                            public boolean onLoadFailed(
                                    @Nullable GlideException e,
                                    Object model,
                                    Target<com.bumptech.glide.load.resource.gif.GifDrawable> target,
                                    boolean isFirstResource) {

                                progressBar.setVisibility(View.GONE);
                                statusText.setText("GIF loading failed");
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(
                                    com.bumptech.glide.load.resource.gif.GifDrawable resource,
                                    Object model,
                                    Target<com.bumptech.glide.load.resource.gif.GifDrawable> target,
                                    DataSource dataSource,
                                    boolean isFirstResource) {

                                progressBar.setVisibility(View.GONE);
                                statusText.setText("GIF animation playing");
                                return false;
                            }
                        }
                )
                .into(mainImage);
    }

    private void clearImage() {

        Glide.with(this).clear(mainImage);

        mainImage.setImageDrawable(null);
        progressBar.setVisibility(View.GONE);

        imageLabel.setText("EMPTY");
        statusText.setText("Image cleared");
    }
}
