package com.socialsensei.android.ui.logout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import com.socialsensei.android.R;
import com.socialsensei.android.databinding.FragmentLogoutBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogoutFragment extends Fragment {
    private FragmentLogoutBinding binding;
    private FirebaseAuth mAuth;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentLogoutBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        //Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            NavigationView navigationView = requireActivity().findViewById(R.id.nav_view);
            Menu menu = navigationView.getMenu();
            MenuItem logoutMenuItem = menu.findItem(R.id.nav_logout);
            logoutMenuItem.setOnMenuItemClickListener(item -> {
                //Sign out the user and navigate to the login screen
                mAuth.signOut();
                NavController navController = NavHostFragment.findNavController(this);
                navController.navigate(R.id.nav_login);
                return true;
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
