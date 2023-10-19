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
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager2.setAdapter(adapter);
        adapter.addFragment(new WelcomeItemFragment(getDrawable(R.drawable.ic_baseline_search_24), getString(R.string.timetablepara)));
        adapter.addFragment(new WelcomeItemFragment(getDrawable(R.drawable.ic_notifications_black_24dp), getString(R.string.stay_notified_para)));
        adapter.addFragment(new WelcomeItemFragment(getDrawable(R.drawable.ic_baseline_train_24), getString(R.string.dailycommutepara)));


        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open MainActivity
                if(viewPager2.getCurrentItem() != (adapter.getItemCount() -1)){
                    viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
                } else {
                    finish(); //Exit to main activity if viewpager is finished
                }
            }
        });


    }
}