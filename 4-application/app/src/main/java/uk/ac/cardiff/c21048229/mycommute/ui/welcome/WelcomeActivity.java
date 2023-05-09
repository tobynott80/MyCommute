package uk.ac.cardiff.c21048229.mycommute.ui.welcome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import uk.ac.cardiff.c21048229.mycommute.R;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Button btnContinue = findViewById(R.id.btnContinue);
        ViewPager2 viewPager2 = findViewById(R.id.ViewPagerWelcome);

        // Setup viewpager


        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open MainActivity
                finish();
            }
        });


    }
}