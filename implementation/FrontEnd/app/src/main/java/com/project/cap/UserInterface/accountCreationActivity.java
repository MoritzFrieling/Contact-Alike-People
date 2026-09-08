package com.project.cap.UserInterface;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.project.cap.Backend.Firebase.MyFirebaseMessagingService;
import com.project.cap.Entity.InterestData;

import com.project.cap.Entity.SocialData;
import com.project.cap.Entity.UserReference;
import com.project.cap.Logic.CreationChecker;
import com.project.cap.Logic.LogicController;
import com.project.cap.Entity.UserOnMapMarkerData;
import com.project.cap.Logic.Util.InterestSocialLookUp;
import com.project.cap.UserInterface.ReycleAdapterPackage.RecyclerAdapter;
import com.project.cap.UserInterface.ReycleAdapterPackage.RecyclerAdapterSocials;
import com.project.cap.R;
import com.project.cap.UserInterface.ReycleAdapterPackage.RecyclerAdapterSocialsOverview;
import com.project.cap.UserInterface.ReycleAdapterPackage.RecyclerAdapterInterestData;
import com.project.cap.UserInterface.Utils.Camera;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class accountCreationActivity extends AppCompatActivity {

    //DatePicker Variables START
    private static final String TAG = "giveDetails";
    private TextView displayDate;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    //DatePicker Variables END
    private static ImageButton cameraButton;
    private static String encodedPhoto;
    private Camera camera;
    private static CardView cView;
    //Test
    private List<SocialData> socalDataCache;
    private List<InterestData> hobbyCache;
    //Recycler declarations
    RecyclerView recyclerV;
    RecyclerView.Adapter recyclerAdapter;
    //user
    UserReference creationUser = new UserReference();
    private boolean dateSelected = false;
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_menu);//start at menu
        findViewById(R.id.errorTXTLog).setVisibility(View.INVISIBLE);//set error txt invisible on start//
    }

    /**
     * Create the datepicker in the give details layout.
     */
    public void createDatePicker(){
       //DatePicker START
        displayDate = (TextView) findViewById(R.id.datePicker);//getField
        displayDate.setOnClickListener(new View.OnClickListener() {//create a listener
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        accountCreationActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        dateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//color is transparent, why not
                dialog.show();//show the picker
            }
        });
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                dateSelected = true;
                month = month + 1;// month starts  at 0
                String date = year + "-" + String.format("%02d", month) + "-" + String.format("%02d", day);
                displayDate.setText(date);//display what we got
            }
        };
        //DatePicker END
    }

    /**
     * Log In with the parameters.
     * @param view
     */
    public void logIn(View view) {
        //Getting the value
        EditText email = findViewById(R.id.textEmail);
        EditText pw = findViewById(R.id.textPW);
        String emailString = email.getText().toString();
        String pwString = pw.getText().toString();
        //got the value

        try {
            loginUser(emailString, pwString);

        } catch (Exception e) {
            TextView errroView = findViewById(R.id.errorTXTLog);
            errroView.setVisibility(View.VISIBLE);
            e.printStackTrace();
        }
    }

    private void loginUser(String emailString, String pwString) throws InterruptedException, ExecutionException {
        LogicController.getInstance().getFutureGateway().processLoginAsync(emailString, pwString).get();

        //LogicController.getInstance().getUserCache().setOwnUid(LogicController.getInstance().getUserManagement().getUidAsync().get());
        InterestSocialLookUp.initInterestAndSocialLookUp();

        MyFirebaseMessagingService.getCurrentToken().thenAccept((String token) -> {
            LogicController.getInstance().getFutureGateway().sendDeviceIDAsync(token);
        });

        Intent coreActivityIntent = new Intent(this, CoreActivity.class);
        startActivity(coreActivityIntent);
    }

    public static ImageButton getCameraButton() {
        return cameraButton;
    }

    /**
     * Set the main layout to the creation process. Mover method.
     * @param view
     */
    public void createAccount(View view) {
        setContentView(R.layout.give_email_and_password);//first step of creation, set view
        findViewById(R.id.errorTextEmail).setVisibility(View.INVISIBLE);//set error txt invisible on start//
        findViewById(R.id.textViewPasswordCriteria).setVisibility(View.INVISIBLE);//set error txt invisible on start//
        findViewById(R.id.textViewPasswordMustMatch).setVisibility(View.INVISIBLE);//set error txt invisible on start//

    }

    /**
     * Include additional behavior/functionality as fragment to the current activity
     * @param fragment additonal function/tool/behavior
     * */
    public void loadFragment(Fragment fragment, Boolean bool) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.constraintLGiveDetails, fragment);
        if (bool)
            transaction.addToBackStack(null);
        transaction.commit();
    }

    /**
     * From email and passwort back to the start menu. Mover method.
     * @param view
     */
    public void backToStart(View view) {
        setContentView(R.layout.start_menu);//back to start
        findViewById(R.id.errorTXTLog).setVisibility(View.INVISIBLE);
    }
    /**
     * From email and passwort to the details menu. Mover method.
     * Including camerabutton setup
     *
     * @param view
     */
    public void nextToDetails(View view) {
        CreationChecker checker = new CreationChecker();
        EditText email = findViewById(R.id.editTextTextEmailAddress);
        EditText pw = findViewById(R.id.editTextTextPassword);
        EditText pwMatch = findViewById(R.id.editTextTextPassword2);

        if(!email.getText().toString().isEmpty() && !pw.getText().toString().isEmpty()){//not empty

            if(!checker.emailCriteria(email.getText().toString()) && !checker.checkMailAvailable(email.getText().toString())){//check if email is already in use

                findViewById(R.id.errorTextEmail).setVisibility(View.VISIBLE);
            }else {//deactive hint if solved
                findViewById(R.id.errorTextEmail).setVisibility(View.INVISIBLE);
                if (checker.passwordCriteria(pw.getText().toString())) {//pw must match criteria
                    //matches
                    findViewById(R.id.textViewPasswordCriteria).setVisibility(View.INVISIBLE);
                    if (checker.passwordMatchesPassword(pw.getText().toString(), pwMatch.getText().toString())) {//pws must match
                        findViewById(R.id.textViewPasswordMustMatch).setVisibility(View.VISIBLE);
                        //no error, continue
                        creationUser.setEmail(email.getText().toString());
                        creationUser.setPassword(pw.getText().toString());
                        //
                        setContentView(R.layout.give_details);//to details
                        //Configure camera-button
                        cameraButton = findViewById(R.id.cameraButton);
                        camera = new Camera(cameraButton);
                        loadFragment(camera, false);
                        //Camera Button End
                        createDatePicker();//create datepicker
                        cView = findViewById(R.id.cardView3);
                        //checker
                        //getFields
                        EditText forename = findViewById(R.id.editTextTextPersonName2);
                        EditText lastName = findViewById(R.id.editTextTextPersonName);
                        TextView date = findViewById(R.id.datePicker);
                        EditText bio = findViewById(R.id.textBio3);
                        RadioButton r1 = findViewById(R.id.radioFemale);
                        RadioButton r2 = findViewById(R.id.radioMale);
                        RadioButton r3 = findViewById(R.id.radioDivers);
                        //setters
                        if (creationUser.getProfilePicture() != null) {//picture
                            cameraButton.setImageBitmap(creationUser.getProfilePicture());
                            forename.setText(creationUser.getForename());
                            lastName.setText(creationUser.getLastname());
                            date.setText(creationUser.getBirthDate());
                            if (r1.getText().toString().contentEquals(creationUser.getGender())) {
                                r1.setChecked(true);
                                //genderG.getChildAt(genderG.indexOfChild(r1)).
                            } else if (r2.getText().toString().contentEquals(creationUser.getGender())) {
                                r2.setChecked(true);
                            } else if (r3.getText().toString().contentEquals(creationUser.getGender())) {
                                r3.setChecked(true);
                            }
                            bio.setText(creationUser.getBio());
                        }
                    } else {
                        //throw error
                        findViewById(R.id.textViewPasswordMustMatch).setVisibility(View.VISIBLE);
                    }
                } else {
                    //throw error
                    findViewById(R.id.textViewPasswordCriteria).setVisibility(View.VISIBLE);
                }
            }
            }else{
                //throw error -> empty fields
                findViewById(R.id.errorTextEmail).setVisibility(View.VISIBLE);//set error txt invisible on start//
                findViewById(R.id.textViewPasswordCriteria).setVisibility(View.VISIBLE);//set error txt invisible on start//
                findViewById(R.id.textViewPasswordMustMatch).setVisibility(View.VISIBLE);//set error txt invisible on start//
            }

        }

    /**
     * set the image correctly. added height etc.
     */
    public static void setImage(Bitmap theImage, String photo){
        cameraButton.setImageBitmap(theImage);//setting the image
        cameraButton.setTag(theImage);

        encodedPhoto = photo;
        //TRUE set -> new pic taken
    }
    /**
     * Back to email and passwort layout. Mover method.
     * @param view
     */
    public void backToEmail(View view) {
        setContentView(R.layout.give_email_and_password);//back to email
        findViewById(R.id.errorTextEmail).setVisibility(View.INVISIBLE);//set error txt invisible on start//
        findViewById(R.id.textViewPasswordCriteria).setVisibility(View.INVISIBLE);//set error txt invisible on start//
        findViewById(R.id.textViewPasswordMustMatch).setVisibility(View.INVISIBLE);//set error txt invisible on start//
        //set cache elements//getters
        EditText email = findViewById(R.id.editTextTextEmailAddress);
        EditText pw = findViewById(R.id.editTextTextPassword);
        EditText pwRepeat = findViewById(R.id.editTextTextPassword2);
        //setters
        email.setText(creationUser.getEmail());
        pw.setText(creationUser.getPassword());
        pwRepeat.setText(creationUser.getPassword());
    }

   /* *//**
     * Move to location detail layout. Mover method.
     * @param view
     *//*
    public void nextToLocDetails(View view) {

        //Details saver
        EditText forename = findViewById(R.id.editTextTextPersonName2);
        EditText lastName = findViewById(R.id.editTextTextPersonName);

        EditText bio = findViewById(R.id.textBio3);
        RadioGroup radio = findViewById(R.id.radioGroup);
        //checker

        if(forename.getText().toString().isEmpty()
                || lastName.getText().toString().isEmpty()
                || !dateSelected
                || bio.getText().toString().isEmpty()
                || cameraButton.getTag().equals("no")){
            //Error msg
            findViewById(R.id.errorDetailsTXT).setVisibility(View.VISIBLE);
        }else{
            //saving
            findViewById(R.id.errorDetailsTXT).setVisibility(View.INVISIBLE);

            creationUser.setProfilePicture((Bitmap) cameraButton.getTag());
            creationUser.setEncodedPicture(encodedPhoto);

            creationUser.setForename(forename.getText().toString());
            creationUser.setLastname(lastName.getText().toString());
            creationUser.setBirthDate(displayDate.getText().toString());
            creationUser.setBio(bio.getText().toString());

            RadioButton r1 = findViewById(radio.getCheckedRadioButtonId());
            creationUser.setGender(r1.getText().toString());
            //switch
            setContentView(R.layout.give_location_details);//next to loc details
            //checker
            EditText street = findViewById(R.id.textStreet);
            EditText nr = findViewById(R.id.textHNumber);
            EditText zip = findViewById(R.id.textPostal);
            EditText country = findViewById(R.id.textCountry);
            EditText city = findViewById(R.id.textCity);
            UserOnMapMarkerData loc = creationUser.getLocation();
        }
        System.out.println(creationUser.toString());
    }*/
    /**
     * Move to detail layout. Mover method.
     * @param view
     */
    public void backToDetails(View view) {
        setContentView(R.layout.give_details);//back to details
        cameraButton.setTag("no");
        this.createDatePicker();
        //fill out details
        System.out.println(cameraButton.getTag());
        //setImage(creationUser.getProfilePicture());//TODO ADD taken pic to view

        EditText forename = findViewById(R.id.editTextTextPersonName2);
        EditText lastName = findViewById(R.id.editTextTextPersonName);
        EditText bio = findViewById(R.id.textBio3);
        RadioButton r1 = findViewById(R.id.radioFemale);
        RadioButton r2 = findViewById(R.id.radioMale);
        RadioButton r3 = findViewById(R.id.radioDivers);
        //setters
        if(creationUser.getProfilePicture() != null){//picture
            cameraButton.setImageBitmap(creationUser.getProfilePicture());
            forename.setText(creationUser.getForename());
            lastName.setText(creationUser.getLastname());
            displayDate.setText(creationUser.getBirthDate());
            if(r1.getText().toString().contentEquals(creationUser.getGender()) ){
                r1.setChecked(true);
                //genderG.getChildAt(genderG.indexOfChild(r1)).
            }else if(r2.getText().toString().contentEquals(creationUser.getGender())){
                r2.setChecked(true);
            } else if(r3.getText().toString().contentEquals(creationUser.getGender())){
                r3.setChecked(true);
            }
            bio.setText(creationUser.getBio());
        }
    }

    /**
     * From location details to contacts. Mover method.
     * @param view
     */
    public void nextToContacts(View view) {
        //Details saver
        EditText forename = findViewById(R.id.editTextTextPersonName2);
        EditText lastName = findViewById(R.id.editTextTextPersonName);

        EditText bio = findViewById(R.id.textBio3);
        RadioGroup radio = findViewById(R.id.radioGroup);
        //checker

        if(forename.getText().toString().isEmpty()
                || lastName.getText().toString().isEmpty()
                || !dateSelected
                || bio.getText().toString().isEmpty()
                || cameraButton.getTag().equals("no")){
            //Error msg
            findViewById(R.id.errorDetailsTXT).setVisibility(View.VISIBLE);
        }else{
            //saving
            findViewById(R.id.errorDetailsTXT).setVisibility(View.INVISIBLE);

            creationUser.setProfilePicture((Bitmap) cameraButton.getTag());
            creationUser.setEncodedPicture(encodedPhoto);

            creationUser.setForename(forename.getText().toString());
            creationUser.setLastname(lastName.getText().toString());
            creationUser.setBirthDate(displayDate.getText().toString());
            creationUser.setBio(bio.getText().toString());

            RadioButton r1 = findViewById(radio.getCheckedRadioButtonId());
            creationUser.setGender(r1.getText().toString());
            //switch
            setContentView(R.layout.give_contacts);
            findViewById(R.id.errorTXTSocials).setVisibility(View.INVISIBLE);
            //Intent myIntent = new Intent(this, giveContactsActivity.class);
            //startActivity(myIntent);
            this.changerSocialsHelper();
        }

    }

    /**
     * Helper method. Loads the recyclerView. Used in both next and back depending on the current view.
     */
    private void changerSocialsHelper(){
        //start the recycler fckery
        recyclerV = (RecyclerView)  findViewById(R.id.recyclerSocials);
        try {
            recyclerAdapter = new RecyclerAdapterSocials( LogicController.getInstance().getFutureGateway().getAllSocialsAsync().get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.recyclerInitHelper(recyclerV,recyclerAdapter);
    }
    ///////////////////////////////Contacts Methods////////////////////////////////
    /**
     * Goes to hobbies from contacts. Mover method. (! also has creation built in, checks for empty input)
     * @param view
     */
    public void nextToHobbies(View view) {
        //switch to activity for hobbies
        boolean errorOccured = false;
        int counter = 0;
        for(int tmp = 0; recyclerV.getChildCount()>tmp;tmp++){//check if all checked boxes are filled out
            RecyclerView.ViewHolder holder = recyclerV.getChildViewHolder(recyclerV.getChildAt(tmp));
            RecyclerAdapterSocials.ViewHolderSocials innerHolder = (RecyclerAdapterSocials.ViewHolderSocials) holder;
            if(innerHolder.checky.isChecked() && innerHolder.userInput.getText().toString().isEmpty()){//case: checkbox is checked but no input -> error
                errorOccured = true;
            }
            if(innerHolder.checky.isChecked() && !innerHolder.userInput.getText().toString().isEmpty()){//checked and filled out
                counter++;
            }
        }
       if(!errorOccured && counter>0){
           //no error
           this.socalDataCache = this.getFilledSocials();//cache
           setContentView(R.layout.give_hobbies);
           changerHobbiesHelper();
       }else{
           findViewById(R.id.errorTXTSocials).setVisibility(View.VISIBLE);
       }

    }

    /**
     * Get a list of all hobbies that have a filled out start date.
     * @return a List of Strings
     */
    public List<InterestData> getFilledHobbies(){
        List<InterestData> filledHobbies = new ArrayList<>();
        int countofChilds = recyclerV.getChildCount();
        for(int tmp = 0;countofChilds>tmp;tmp++){//as long as tmp is smaller than count
            RecyclerView.ViewHolder holder = recyclerV.getChildViewHolder(recyclerV.getChildAt(tmp));
            RecyclerAdapter.ViewHolder innerHolder = (RecyclerAdapter.ViewHolder) holder;
            if(!innerHolder.startDate.getText().toString().contentEquals("Click to select a date")){//case: startDate was filled out
                filledHobbies.add(new InterestData(innerHolder.item,innerHolder.startDate.getText().toString(), innerHolder.levelOfSkill.getSelectedItem().toString()));//adding the hobby name to the list
            }
        }
        return filledHobbies;
    }


    /**
     * Get a list of all checked socials with corresponding input ( username ).
     * @return
     */
    public List<SocialData> getFilledSocials(){
        List<SocialData> namesOfSocials = new ArrayList<>();
        for(int tmp = 0; recyclerV.getChildCount()>tmp;tmp++){
            RecyclerView.ViewHolder holder = recyclerV.getChildViewHolder(recyclerV.getChildAt(tmp));
            RecyclerAdapterSocials.ViewHolderSocials innerHolder = (RecyclerAdapterSocials.ViewHolderSocials) holder;
            if(innerHolder.checky.isChecked()){//case: checkbox is checked
                namesOfSocials.add(new SocialData(innerHolder.item.getId(),innerHolder.checky.getText().toString(),innerHolder.userInput.getText().toString()));
            }
        }

        return namesOfSocials;
    }

    //////////////////Hobbies Methods/////////////////////////////

    /**
     * Helper Method. Loads the recycler.
     */
    private void changerHobbiesHelper(){
        recyclerV = (RecyclerView)  findViewById(R.id.recyclerHobbies);
        try {
            recyclerAdapter = new RecyclerAdapter(LogicController.getInstance().getFutureGateway().getAllInterestsAsync().get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.recyclerInitHelper(recyclerV,recyclerAdapter);
        //init end
    }
    /**
     * Mover Method. From hobbies back to contacts.
     * @param view
     */
    public void backToContacts(View view) {
        setContentView(R.layout.give_contacts);
        this.changerSocialsHelper();
    }

    /**
     * Mover method. From hobbies to the overview of all data.
     * @param view
     */
    public void nextToOverview(View view) {
        this.hobbyCache = this.getFilledHobbies();
        if(hobbyCache.size()>0){//must have at least one hobby filled out
            setContentView(R.layout.account_overview);
            //socials
            RecyclerView recyclerViewSocials = findViewById(R.id.recyclerOverSocials);
            recyclerAdapter = new RecyclerAdapterSocialsOverview(this.socalDataCache);
            this.recyclerInitHelper(recyclerViewSocials,recyclerAdapter);
            //hobbies
            RecyclerView recyclerViewHobbies = findViewById(R.id.recyclerOverHobbies);
            recyclerAdapter = new RecyclerAdapterInterestData(this.hobbyCache);
            this.recyclerInitHelper(recyclerViewHobbies,recyclerAdapter);

            //display user input
            TextView firstname = (TextView) findViewById(R.id.firstName);
            firstname.setText(creationUser.getForename());
            TextView lastname = findViewById(R.id.lastName);
            lastname.setText(creationUser.getLastname());
            TextView birthdate = findViewById(R.id.birthDate);
            birthdate.setText(creationUser.getBirthDate().toString());
            TextView gender = findViewById(R.id.gender);
            gender.setText(creationUser.getGender());
            TextView bio = findViewById(R.id.bio);
            bio.setText(creationUser.getBio());
        }else{
            findViewById(R.id.errorHobbiesTXT).setVisibility(View.VISIBLE);
        }


    }

    /**
     * Simplifies init of recyclers.
     * @param recycler
     * @param adapter
     */
    private void recyclerInitHelper(RecyclerView recycler, RecyclerView.Adapter adapter){
        RecyclerView.LayoutManager layoutManagerSocials = new LinearLayoutManager(this);
        recycler.setLayoutManager(layoutManagerSocials);
        recycler.setAdapter(adapter);
    }
    //////////////Overview Methods/////////////////////

    /**
     * Mover Method. From overview to the next window.
     * @param view
     */
    public void fromOverviewNext(View view) {
        //setContentView(R.layout.);

        creationUser.setFilledInterests(this.hobbyCache);
        creationUser.setFilledSocials(this.socalDataCache);

        try {
            LogicController.getInstance().getFutureGateway().registerUserAsync(creationUser).get();
            loginUser(creationUser.getEmail(), creationUser.getPassword());

            Intent coreActivityIntent = new Intent(this, CoreActivity.class);
            startActivity(coreActivityIntent);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Mover method. From overview back to hobbies.
     * @param view
     */
    public void backToHobbies(View view) {
        setContentView(R.layout.give_hobbies);
        changerHobbiesHelper();
    }

}
