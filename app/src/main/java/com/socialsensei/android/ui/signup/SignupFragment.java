package com.socialsensei.android.ui.signup;

import static android.content.ContentValues.TAG;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import com.socialsensei.android.R;
import com.socialsensei.android.databinding.FragmentSignupBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupFragment extends Fragment {
    private FragmentSignupBinding binding;
    private FirebaseAuth mAuth;
    public void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.nav_home);
        }
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.nav_home);

        } else {
            //User authentication failed, update UI accordingly
            Toast.makeText(requireContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.nav_signup);
        }
    }
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSignupBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mAuth = FirebaseAuth.getInstance();

        //Set up click listener for the signup button
        Button signupButton = binding.signupButton;
        signupButton.setOnClickListener(v -> {

            mAuth.createUserWithEmailAndPassword(binding.signupEmail.getText().toString(), binding.signupPassword.getText().toString())
                 .addOnCompleteListener(requireActivity(), task -> {
                       if (task.isSuccessful()) {
                          //Sign in success, update UI with the signed-in user's information
                          Log.d(TAG, "createUserWithEmail:success");
                          FirebaseUser user = mAuth.getCurrentUser();
                          Toast.makeText(requireContext(), "Authentication successfully.",
                                  Toast.LENGTH_SHORT).show();
                          updateUI(mAuth.getCurrentUser());
                       } else {
                          //If sign in fails, display a message to the user.
                          Log.w(TAG, "createUserWithEmail:failure", task.getException());
                          Toast.makeText(requireContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                          updateUI(mAuth.getCurrentUser());
                       }
                 });
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
