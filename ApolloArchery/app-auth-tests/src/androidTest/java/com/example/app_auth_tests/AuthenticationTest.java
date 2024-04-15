package com.example.app_auth_tests;

import android.app.Activity;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.example.app_auth_tests.R;
import com.fullsail.apolloarchery.LoginActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class AuthenticationTest {


  @Rule
  public ActivityScenarioRule<LoginActivity> rule =
          new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void testSuccessfulLogin() {


        // Get scenario from rule
        ActivityScenario<LoginActivity> scenario = rule.getScenario();

        // Launch activity using scenario
        scenario.onActivity(activity -> {
            // Perform login actions

            Activity activity1 = new Activity();
            activity.findViewById(R.id.login_email_entry).setText("test@example.com");
            activity.findViewById(R.id.login_password_entry).setText("password");
            activity.findViewById(R.id.login_btn).performClick();

            // Verify login success


        });
    }

}
