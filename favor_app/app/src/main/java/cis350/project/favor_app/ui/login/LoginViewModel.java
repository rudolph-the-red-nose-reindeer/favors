package cis350.project.favor_app.ui.login;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Log;
import android.util.Patterns;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;

import cis350.project.favor_app.data.LoginRepository;
import cis350.project.favor_app.data.Result;
import cis350.project.favor_app.data.model.LoggedInUser;
import cis350.project.favor_app.R;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {
        // can be launched in a separate asynchronous job
        Result<LoggedInUser> result = loginRepository.login(username, password);

        if (result instanceof Result.Success) {
            verifyEmail(username, password);
        } else {
            Log.d("LOGIN FAILURE:", result.toString());
            loginResult.setValue(new LoginResult(R.string.login_failed));
        }
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username, String) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() >= 5;
    }

    private void verifyEmail(final String user, final String password)
    {

        if (!validPennEmail(user)) {
            failure();
        }
        FirebaseAuth auth = FirebaseAuth.getInstance();

        Task<AuthResult> task = auth.signInWithEmailAndPassword(user, password)
                .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            failure();
                        } else {
                            FirebaseUser userInstance = FirebaseAuth.getInstance().getCurrentUser();
                            if (!userInstance.isEmailVerified()) {
                                FirebaseAuth.getInstance().signOut();
                                failure();
                            } else {
                               success();
                            }
                        }
                    }
                });
    }

    private void failure() {
        //loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        Log.d("LOGIN FAILURE:", result.toString());
        loginResult.setValue(new LoginResult(R.string.login_failed));
    }


    private void success() {
        Log.d("LOGIN SUCCESS:", username + " " + password);
        LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
        loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName(),
                data.getEmail(),
                data.getPhoto(),
                data.getBio(),
                data.getRating(),
                data.getPoints())));
    }

    private boolean validPennEmail(String s) {
        if (s.length() < 10) {
            return false;
        }
        return s.substring(s.length() - 9).equals("upenn.edu");
    }

}
