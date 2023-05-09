package uk.ac.cardiff.c21048229.mycommute.ui.welcome;

import android.graphics.Picture;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import uk.ac.cardiff.c21048229.mycommute.databinding.FragmentWelcomeItemBinding;

public class WelcomeItemFragment extends Fragment {
    FragmentWelcomeItemBinding binding;
    Icon icon;
    String paragraph;

    public WelcomeItemFragment(Icon icon, String paragraph) {
        this.icon = icon;
        this.paragraph = paragraph;
    }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentWelcomeItemBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ImageView imageView = binding.imWelcomeIcon;
        TextView textView = binding.tvWelcomeParagraph;

        imageView.setImageIcon(icon);
        textView.setText(paragraph);

        return root;
    }
}
