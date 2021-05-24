package com.example.mywechat.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Patterns;

import com.example.mywechat.data.LoginRepository;
import com.example.mywechat.data.Result;
import com.example.mywechat.data.model.LoggedInUser;
import com.example.mywechat.R;

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
            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));
        } else {
            loginResult.setValue(new LoginResult(R.string.LogInActivity_LogInFailed));
        }
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.LogInActivity_InvalidPhoneNumber, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.LogInActivity_InvalidPassword));
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
        char letter1 = 'a' - 1, letter2 = 'A' - 1;
        boolean answer = password != null && password.trim().length() > 7, temp1 = false, temp2 = false;
        for (int i = 0; i < 9; i++) {
            if (password.contains(Integer.toString(i))) {
                temp1 = true;
            }
        }
        for (int i = 0; i < 26; i++) {
            letter1++;
            letter2++;
            if (password.contains(Character.toString(letter1))
                    || password.contains(Character.toString(letter2))) {
                temp2 = true;
            }
        }
        answer = answer && temp1 && temp2;
        return answer;
    }
}