package com.project.cap.UserInterface;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.project.cap.Backend.BackendController;
import com.project.cap.Logic.LogicController;
import com.project.cap.R;


/**
 *
 * Init activity. Instantiating main controllers and login/registration
 *
 */
public class StartUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_menu);

        //init area
        LogicController.getInstance(); //init logic
        BackendController.getInstance(); //init backend
        //init area end

        //Start with log-in after initializing necessary stuff
        Intent myIntent = new Intent(this, accountCreationActivity.class);
        startActivity(myIntent);
    }
}