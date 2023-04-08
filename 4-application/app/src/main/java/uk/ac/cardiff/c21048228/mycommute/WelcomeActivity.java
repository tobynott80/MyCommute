package uk.ac.cardiff.c21048228.mycommute;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import uk.ac.cardiff.c21048228.mycommute.ui.locationSelector.LocationSelectorFragment;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Button btnHomeDeparture = findViewById(R.id.btnHomeDeparture);
        Button btnHomeArrival = findViewById(R.id.btnHomeArrival);
        Button btnWorkDeparture = findViewById(R.id.btnWorkDeparture);
        Button btnWorkArrival = findViewById(R.id.btnWorkArrival);

        btnHomeDeparture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open StationSelectorFragment


            }
        });
    }
}