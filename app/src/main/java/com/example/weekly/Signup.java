package com.example.weekly;

import static com.example.weekly.Login.isEmailValid;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Signup#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Signup extends Fragment {
        private EditText etMail, etPassword, etConfirmPassword;
        private Button doneSignup;
        private TextView HaveAccount;
        private FirebaseAuth mAuth;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Signup() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Signup.
     */
    // TODO: Rename and change types and number of parameters
    public static Signup newInstance(String param1, String param2) {
        Signup fragment = new Signup();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        connect();
        doneSignup.setOnClickListener(viwe->{check();});
    }
    public void connect(){
    etMail = getView().findViewById(R.id.etEmail2);
    etPassword = getView().findViewById(R.id.etPassSignUp);
    etConfirmPassword = getView().findViewById(R.id.etConfirmPassSIgnUp);
    doneSignup = getView().findViewById(R.id.signUpBtn);
    HaveAccount = getView().findViewById(R.id.signUoToLogInTxt);
    mAuth=FirebaseAuth.getInstance();
    }
    public void check(){
        String email,password, password2;
        email= etMail.getText().toString().trim();
        password=etPassword.getText().toString().trim();
        password2=etConfirmPassword.getText().toString().trim();
        if(email.isEmpty()||password.isEmpty()|| password2.isEmpty()){
            Toast.makeText(getContext(), "some are empty", Toast.LENGTH_SHORT).show();
            etMail.requestFocus();
            etPassword.requestFocus();
            etConfirmPassword.requestFocus();
            return;
        }
        if(!isEmailValid(email)){
            Toast.makeText(getContext(), "wrong email pattern!", Toast.LENGTH_SHORT).show();
            etMail.requestFocus();
            return;
        }
        if(password.length()<8){
            etPassword.setError("wrong password");
            // Toast.makeText(getContext(), "wrong password", Toast.LENGTH_SHORT).show();
            etPassword.requestFocus();
            return;
        }
        if(!password.equals(password2)){
            etConfirmPassword.setError("passwords are not the same ");
            etConfirmPassword.requestFocus();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getContext(), "sign up is successful", Toast.LENGTH_SHORT).show();
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.HomePage, new HomePage());
                    ft.commit();
                }
            }
        });
}}