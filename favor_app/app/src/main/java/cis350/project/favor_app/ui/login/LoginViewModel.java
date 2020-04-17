package cis350.project.favor_app.ui.login;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.os.AsyncTask;
import android.util.Log;
import android.util.Patterns;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import cis350.project.favor_app.data.LoginRepository;
import cis350.project.favor_app.data.Result;
import cis350.project.favor_app.data.model.User;
import cis350.project.favor_app.R;
import cis350.project.favor_app.util.requests.UserApiFindTask;


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
        Result<User> result = loginRepository.login(username, password);

        if (result instanceof Result.Success) {
            verifyEmail(username, password, result);
        } else {
            Log.d("LOGIN FAILURE:", result.toString());
            loginResult.setValue(new LoginResult("Username or password is incorrect"));
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
    private boolean isUserNameValid(String username) {
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

    private void verifyEmail(final String user, final String password, final Result result)
    {

        //if (!validPennEmail(user)) {
        //  failure();
        //}
        FirebaseAuth auth = FirebaseAuth.getInstance();


        try {
            AsyncTask<String, Void, User> getUserTask = new UserApiFindTask();
            getUserTask.execute(user);
            User foundUser = getUserTask.get();
            String email = foundUser.getEmail();
            Log.d("Email located: ", email);
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            failure("Error occurred while verifying email");
                        } else {
                            FirebaseUser userInstance = FirebaseAuth.getInstance().getCurrentUser();
                            if (!userInstance.isEmailVerified()) {
                                FirebaseAuth.getInstance().signOut();
                                failure("Please verify your email");
                            } else {
                                success((Result.Success<User>) result);
                            }
                        }
                    }
                });
        } catch (Exception e) {
            Log.d("Error verifying email:", e.toString());
            failure("Error occurred while verifying email");
        }
    }

    private void failure(String result) {
        //loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        //Log.d("LOGIN FAILURE:", result.toString());
        loginResult.setValue(new LoginResult(result));
    }


    private void success(Result.Success<User> result) {
        //Log.d("LOGIN SUCCESS:", username + " " + password);
        User data = result.getData();
        loginResult.setValue(new LoginResult(new LoggedInUserView(data.getUserId(),
                data.getUsername(),
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
