package com.example.apiconnect.activities.mainwindow;

import android.Manifest;
import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apiconnect.activities.AvailableContacts;
import com.example.apiconnect.activities.chatwindow.ConversationFragment;
import com.example.apiconnect.fragments.ActivityCameraFragment;
import com.example.apiconnect.fragments.ActivityOneFragment;
import com.example.apiconnect.fragments.ActivityTwoFragment;
import com.example.apiconnect.fragments.PreferenceFragment;
import com.example.apiconnect.jabber.connect.APIConnectionService;
import com.example.apiconnect.adapters.fragments.FragmentTabAdapter;
import com.example.apiconnect.R;
import com.example.apiconnect.fragments.ActivityThreeFragment;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity implements ActivityThreeFragment.OnFragmentInteractionListener{



    private TabLayout homeTabLayout;
    private ViewPager homeViewPager;
    FragmentTabAdapter mFragmentTabAdapter;
    private BroadcastReceiver mBroadcastReceiver;
    private static ActivityOneFragment chatsFragment;
    FloatingActionButton chatWith;
    FloatingActionButton addContact;
    private ConstraintLayout eyeFrame;
    private MainWindowAnimator mainWindowAnimator;
    private ImageButton settingsBTN;

    private FrameLayout menuLineZero;
    private FrameLayout menuLineOne;
    private FrameLayout menuLineTwo;
    private FrameLayout menuLineThree;


    private ConstraintLayout tinyMenu;
    private FrameLayout tinyMenu_bg;
    private ImageView cameraIcon;

    private TextView chatLabel;
    private TextView activityLabel;
    private TextView timelineLabel;
    private ImageView more_btn;

    private LinearLayout bottom_tab_expand;
    int menudescmargin;
    private FrameLayout top_scrollBar;
    private FrameLayout bottom_scrollBar;
    FrameLayout main_content;


    private int homeTabHeight;
    private int statusBarHeight;
    private Vibrator myVib;

    //QuickBar

    private static ConstraintLayout quickBarContainer;
    private ConstraintLayout profilesContainer;

    //Navigation System
    private FrameLayout all_bar;
    private FrameLayout contacts_bar;
    private FrameLayout location_bar;
    private FrameLayout platform_bar;
    private FrameLayout camera_bar;

    private TextView all_bar_tag;
    private TextView contacts_bar_tag;
    private TextView contacts_bar_tag_new_messages;
    private TextView location_bar_tag;
    private TextView platform_bat_tag;
    private TextView camera_bar_tag;

    private TextView logo;

    //Navigation LayoutParams

    private ConstraintLayout.LayoutParams all_bar_LP;
    private ConstraintLayout.LayoutParams contacts_bar_LP;
    private ConstraintLayout.LayoutParams location_bar_LP;
    private ConstraintLayout.LayoutParams platform_bar_LP;
    private ConstraintLayout.LayoutParams camera_bar_LP;



    boolean dark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean darkPref = prefs.contains("dark_mode");
        if(darkPref){
            boolean dark = prefs.getBoolean("dark_mode",false);

            if(dark){
                dark = true;
                //set the Theme to Dark
                setTheme(R.style.AppThemeDark);
                setDarkSysBar();

            }
            else{
                dark = false;
                //set the Theme To Light
                setTheme(R.style.AppThemeLight);
                setWhiteSysBar();
            }
        }

        setContentView(R.layout.new_main);




        int MY_PERMISSIONS_REQUEST_CAMERA=0;
// Here, this is the current activity
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA))
            {

            }
            else
            {
                ActivityCompat.requestPermissions(this ,new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.


            }
        }

        main_content = findViewById(R.id.main_content);

        all_bar = findViewById(R.id.all_bar);
        all_bar_tag = findViewById(R.id.all_bar_tag);

        contacts_bar = findViewById(R.id.contacts_bar);
        contacts_bar_tag = findViewById(R.id.contacts_bar_tag);
        contacts_bar_tag_new_messages = findViewById(R.id.contacts_bar_tag_new_messages);

        location_bar = findViewById(R.id.location_bar);
        location_bar_tag = findViewById(R.id.location_bar_tag);

        platform_bar = findViewById(R.id.platform_bar);
        platform_bat_tag = findViewById(R.id.platform_bar_tag);

        camera_bar = findViewById(R.id.camera_bar);
        camera_bar_tag = findViewById(R.id.camera_bar_tag);

        all_bar_LP = (ConstraintLayout.LayoutParams) all_bar.getLayoutParams();
        contacts_bar_LP = (ConstraintLayout.LayoutParams) contacts_bar.getLayoutParams();
        location_bar_LP = (ConstraintLayout.LayoutParams) location_bar.getLayoutParams();
        platform_bar_LP = (ConstraintLayout.LayoutParams) platform_bar.getLayoutParams();
        camera_bar_LP = (ConstraintLayout.LayoutParams) camera_bar.getLayoutParams();

        profilesContainer = findViewById(R.id.profilesContainer);
        logo = findViewById(R.id.logo);

        myVib = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
        settingsBTN = findViewById(R.id.settingsBtn);

        settingsBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    openPreferences();
            }
        });





        //findViews();
        //listeners();
        //launchViewPager();


    }


    private Fragment fragment;


    private void openContacts(){
        main_content.removeAllViews();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        fragment = new ActivityOneFragment();
        // Begin the transaction
        ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        // Replace the contents of the container with the new fragment
        ft.replace(R.id.main_content, fragment);
        // or ft.add(R.id.your_placeholder, new FooFragment());
        // Complete the changes added above
        ft.commit();
        barAlphas(1);
        setStatusbarspace();

        //showMainWindow();
    }
    private void openLocation(){
        main_content.removeAllViews();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Begin the transaction
        fragment = new ActivityTwoFragment();
        ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        // Replace the contents of the container with the new fragment
        ft.replace(R.id.main_content, fragment);
        // or ft.add(R.id.your_placeholder, new FooFragment());
        // Complete the changes added above
        ft.commit();
        //showMainWindow();
        barAlphas(2);
    }
    private void openPlatforms(){
        main_content.removeAllViews();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        fragment = new ActivityThreeFragment();
        // Begin the transaction
        ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        // Replace the contents of the container with the new fragment
        ft.replace(R.id.main_content, fragment);
        // or ft.add(R.id.your_placeholder, new FooFragment());
        // Complete the changes added above
        ft.commit();
        //showMainWindow();
        barAlphas(3);
        transparentStatus();
    }
    private void openAll(){
        main_content.removeAllViews();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Begin the transaction
        fragment = new ActivityCameraFragment();
        ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        // Replace the contents of the container with the new fragment
        ft.replace(R.id.main_content, fragment);
        // or ft.add(R.id.your_placeholder, new FooFragment());
        // Complete the changes added above
        ft.commit();
        //showMainWindow();
        barAlphas(4);
    }

    private void openCamera(){
        main_content.removeAllViews();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Begin the transaction
        fragment = new ActivityCameraFragment();
        ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
// Replace the contents of the container with the new fragment
        ft.replace(R.id.main_content, fragment);
// or ft.add(R.id.your_placeholder, new FooFragment());
// Complete the changes added above
        ft.commit();
        //showMainWindow();
        barAlphas(5);
    }

    private void openPreferences(){
        main_content.removeAllViews();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        fragment = new ActivityThreeFragment();
        // Begin the transaction
        ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
// Replace the contents of the container with the new fragment
        ft.replace(R.id.main_content, new PreferenceFragment());
// or ft.add(R.id.your_placeholder, new FooFragment());
// Complete the changes added above
        ft.commit();
        //getFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).replace(R.id.main_content, new PreferenceFragment()).commit();

    }

    public void openConversation(ImageView chatAvatar, String chatID){

        Bundle bundle = new Bundle();
        bundle.putString("chat_id", chatID);
// set Fragmentclass Arguments
        ConversationFragment convfrag = new ConversationFragment();
        convfrag.setArguments(bundle);

        main_content.removeAllViews();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        //fragment = new ConversationFragment();
        // Begin the transaction
        ft.setReorderingAllowed(true);
        ft.addSharedElement(chatAvatar, chatAvatar.getTransitionName());
// Replace the contents of the container with the new fragment
        ft.replace(R.id.main_content, convfrag);
// or ft.add(R.id.your_placeholder, new FooFragment());
// Complete the changes added above
        ft.addToBackStack(null);
        ft.commit();
        //getFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).replace(R.id.main_content, new PreferenceFragment()).commit();

    }



    public int touchDownNav = 0;


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        return false;
    }

    int tm_bg_margin;

    private void launchViewPager(){


        mFragmentTabAdapter = new FragmentTabAdapter(getSupportFragmentManager());



        homeViewPager.setAdapter(mFragmentTabAdapter);
        //homeViewPager.setOffscreenPageLimit(0);
        homeViewPager.setCurrentItem(1);
        //homeTabLayout.setupWithViewPager(homeViewPager);


        homeViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

                Log.i("Pager Page",""+ i);
                Log.i("Pager Page",""+ i1);
                Log.i("Pager Position",""+ v);

                if(i == 0){
                    tm_bg_margin = (int)(i1 * v);

                    ConstraintLayout.LayoutParams tm_bgLP = (ConstraintLayout.LayoutParams) tinyMenu_bg.getLayoutParams();
                    tm_bgLP.height = (int)((statusBarHeight + (int)convertDpToPixel(20)) * v);
                    tinyMenu_bg.setLayoutParams(tm_bgLP);

                    if(v < 0.99) {
                        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    }
                    else{
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    }


                }
            }

            @Override
            public void onPageSelected(int i) {

                Log.i("Start Camera", "Start Camera" + i);
                if(i == 0){

                    ActivityCameraFragment frag1 = (ActivityCameraFragment) homeViewPager
                            .getAdapter()
                            .instantiateItem(homeViewPager, homeViewPager.getCurrentItem());
                    frag1.startCamera();


                }

            }

            @Override
            public void onPageScrollStateChanged(int i) {
                Log.i("Start Camera", "State "+ i);
            }
        });
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
    FrameLayout statusbarspace;

    public void setWhiteSysBar(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View view = getWindow().getDecorView();
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(Color.WHITE);

            //ContextCompat.getColor(this,R.color.white)

        }
    }

    public void setDarkSysBar(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View view = getWindow().getDecorView();
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            //flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.cardPrimaryDark));

            //ContextCompat.getColor(this,R.color.white)

        }
    }

    public void setStatusbarspace(){
        statusbarspace = findViewById(R.id.statusspace);
        statusbarspace.setVisibility(View.VISIBLE);
        statusBarHeight = getStatusBarHeight();

        ConstraintLayout.LayoutParams sbsLP = (ConstraintLayout.LayoutParams) statusbarspace.getLayoutParams();
        sbsLP.height = statusBarHeight;
        statusbarspace.setLayoutParams(sbsLP);
    }
    public void transparentStatus(){
        statusbarspace = findViewById(R.id.statusspace);

        statusbarspace.setVisibility(View.GONE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View view = getWindow().getDecorView();
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(Color.TRANSPARENT);

            //ContextCompat.getColor(this,R.color.white)

        }
    }
    public static int tmheight;

    //Find all Views at Beginning
    private void findViews(){
        homeTabLayout = findViewById(R.id.hometabs);
        homeViewPager = findViewById(R.id.home_view_pager);


        chatWith = findViewById(R.id.chatWithFAB);
        addContact = findViewById(R.id.addContact);


        menuLineZero = findViewById(R.id.menuslide0);
        menuLineOne = findViewById(R.id.menuslide1);
        menuLineTwo = findViewById(R.id.menuslide2);
        menuLineThree = findViewById(R.id.menuslide3);

        tinyMenu = findViewById(R.id.tinymenu);
        tinyMenu_bg = findViewById(R.id.tinymenu_bg);
        more_btn = findViewById(R.id.tinymenu_more);
        mainWindowAnimator = new MainWindowAnimator(eyeFrame, this, more_btn);



        quickBarContainer = findViewById(R.id.quickbar);
        bottom_tab_expand = findViewById(R.id.bottom_tab_expander);


        tmheight = getStatusBarHeight() + (int)convertDpToPixel(20);
        ConstraintLayout.LayoutParams tm_bgLP = (ConstraintLayout.LayoutParams) tinyMenu_bg.getLayoutParams();
        tm_bgLP.height = tmheight;
        tinyMenu_bg.setLayoutParams(tm_bgLP);

        menudescmargin = (int)convertDpToPixel(20);
        tiny_menu_shadowVOP = tinyMenu_bg.getOutlineProvider();

        //Scrollbar Settings
        top_scrollBar = findViewById(R.id.top_scrollbar);
        //bottom_scrollBar = findViewById(R.id.bottom_scrollbar);
        top_scrollbar_LP = (ConstraintLayout.LayoutParams) top_scrollBar.getLayoutParams();



    }

    ConstraintLayout.LayoutParams top_scrollbar_LP;
    ConstraintLayout.LayoutParams bottom_scrollbar_LP;


    public void scrollbar_move(float percentage){
        int marginTop = (int) Math.round((quickBarContainer.getHeight() / 100) * percentage);

        top_scrollbar_LP.topMargin = marginTop;

        top_scrollBar.setLayoutParams(top_scrollbar_LP);


    }

    private void listeners() {

        chatWith.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent showContacts = new Intent(getApplicationContext(), AvailableContacts.class);
                startActivity(showContacts);

                //Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                //       .setAction("Action", null).show();
            }
        });
        addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        homeViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 1){  // if you want the second page, for example
                    //Your code here
                }
                switch(position){
                    case 0:

                        break;
                    case 1:
                        Log.d("TAB", "One");
                        mainWindowAnimator.flipButtonAdd(addContact);
                        mainWindowAnimator.flipButtonLocation(chatWith);
                        hideLabels(1);
                        //mainWindowAnimator.slideInLabel(menuLineOne, menuLineZero, tinyMenu, cameraIcon, chatLabel, menudescmargin, 1);
                        menuLineOne.setAlpha(0.7f);
                        menuLineTwo.setAlpha(0.2f);
                        menuLineThree.setAlpha(0.2f);
                        break;

                    case 2:
                        Log.d("TAB", "Two");

                        mainWindowAnimator.flipButtonFire(addContact);
                        mainWindowAnimator.flipButtonContact(chatWith);
                        hideLabels(2);
                        //mainWindowAnimator.slideInLabel(menuLineTwo, menuLineZero, tinyMenu, cameraIcon, activityLabel, menudescmargin, 2);
                        menuLineOne.setAlpha(0.2f);
                        menuLineTwo.setAlpha(0.7f);
                        menuLineThree.setAlpha(0.2f);
                        break;

                    case 3:
                        Log.d("TAB", "Three");
                        hideLabels(3);
                        //mainWindowAnimator.slideInLabel(menuLineThree, menuLineZero, tinyMenu, cameraIcon, timelineLabel, menudescmargin, 3);
                        menuLineOne.setAlpha(0.2f);
                        menuLineTwo.setAlpha(0.2f);
                        menuLineThree.setAlpha(0.7f);
                        break;

                    default:
                        Log.d("TAB", "Weird");
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private void hideLabels(int newLabel){

        switch(newLabel){
            case 1:
                if(mainWindowAnimator.visibleLabel == 2){
                    mainWindowAnimator.slideOutLabel(menuLineTwo, menuLineZero, activityLabel, menudescmargin);
                    mainWindowAnimator.slideOutBottomTab(bottom_tab_expand, 0, (int)convertDpToPixel(50));

                }
                if(mainWindowAnimator.visibleLabel == 3){
                    mainWindowAnimator.slideOutLabel(menuLineThree, menuLineZero, timelineLabel, menudescmargin);
                }
                break;
            case 2:
                if(mainWindowAnimator.visibleLabel == 1){
                    mainWindowAnimator.slideOutLabel(menuLineOne, menuLineZero, chatLabel, menudescmargin);

                    mainWindowAnimator.slideOutBottomTab(bottom_tab_expand, (int)convertDpToPixel(50), 0);


                }
                if(mainWindowAnimator.visibleLabel == 3){
                    mainWindowAnimator.slideOutLabel(menuLineThree, menuLineZero, timelineLabel, menudescmargin);
                }
                break;
            case 3:
                if(mainWindowAnimator.visibleLabel == 2){
                    mainWindowAnimator.slideOutLabel(menuLineTwo, menuLineZero, activityLabel, menudescmargin);

                }
                if(mainWindowAnimator.visibleLabel == 1){
                    mainWindowAnimator.slideOutLabel(menuLineOne, menuLineZero, chatLabel, menudescmargin);
                }
                break;
            default:
                break;


        }

    }


    protected void onResume() {
        super.onResume();
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                String action = intent.getAction();
                switch (action)
                {
                    case APIConnectionService.UI_AUTHENTICATED:
                        Log.d("TAG","Got a broadcast to show the main app window");
                        //Show the main app window
                        //showProgress(false);
                        Toast.makeText(context, "User is Authenticated", Toast.LENGTH_SHORT).show();

                        break;

                }

            }
        };
        IntentFilter filter = new IntentFilter(APIConnectionService.UI_AUTHENTICATED);
        this.registerReceiver(mBroadcastReceiver, filter);

        openContacts();
    }


    int latestBubble = 0;
    int numOfBubbles = 0;


    public void appendTextBubble_to_QuickBar(){
        if(numOfBubbles == 0){

            ConstraintSet constraintSet = new ConstraintSet();
            FrameLayout quickline = quickBarContainer.findViewById(R.id.quickbar_line);
            TextView bubble = bubbleText(numOfBubbles);
            constraintSet.clone(quickBarContainer);
            constraintSet.connect(bubble.getId(),ConstraintSet.TOP,quickline.getId(),ConstraintSet.TOP,0);
            constraintSet.connect(bubble.getId(),ConstraintSet.LEFT,ConstraintSet.PARENT_ID,ConstraintSet.LEFT,0);
            constraintSet.connect(bubble.getId(),ConstraintSet.RIGHT,ConstraintSet.PARENT_ID,ConstraintSet.RIGHT,0);
            constraintSet.applyTo(quickBarContainer);

            ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) bubble.getLayoutParams();

            lp.setMargins(0,(int)convertDpToPixel(3),0,0);

            bubble.setLayoutParams(lp);
            latestBubble = bubble.getId();


            numOfBubbles++;

        }
        else{

            ConstraintSet constraintSet = new ConstraintSet();
            FrameLayout quickline = quickBarContainer.findViewById(R.id.quickbar_line);
            TextView bubble = bubbleText(numOfBubbles);
            constraintSet.clone(quickBarContainer);
            constraintSet.connect(bubble.getId(),ConstraintSet.TOP,latestBubble,ConstraintSet.BOTTOM,0);
            constraintSet.connect(bubble.getId(),ConstraintSet.LEFT,ConstraintSet.PARENT_ID,ConstraintSet.LEFT,0);
            constraintSet.connect(bubble.getId(),ConstraintSet.RIGHT,ConstraintSet.PARENT_ID,ConstraintSet.RIGHT,0);
            constraintSet.applyTo(quickBarContainer);
            ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) bubble.getLayoutParams();

            lp.setMargins(0,(int)convertDpToPixel(3),0,0);

            bubble.setLayoutParams(lp);

            latestBubble = bubble.getId();

            numOfBubbles++;
        }


    }



    //Create BubbleViews Dynamically
    private TextView bubbleText(int bubbleNo){

        TextView bbl = new TextView(this);
        bbl.setGravity(Gravity.CENTER);
        ConstraintLayout.LayoutParams lp;
        /*
        if(bubbleNo == 0){ lp = new ConstraintLayout.LayoutParams((int)convertDpToPixel(8),(int)convertDpToPixel(8)); }
        else if(bubbleNo == 1 || bubbleNo == 2 || bubbleNo == 3){ lp = new ConstraintLayout.LayoutParams((int)convertDpToPixel(10),(int)convertDpToPixel(10)); }
        else { lp = new ConstraintLayout.LayoutParams((int) convertDpToPixel(6), (int) convertDpToPixel(6)); }
        */
        lp = new ConstraintLayout.LayoutParams((int)convertDpToPixel(5),(int)convertDpToPixel(5));
        quickBarContainer.addView(bbl);
        int id = ViewCompat.generateViewId();

        bbl.setId(id);

        if(bubbleNo == 0 || bubbleNo == 1){
            bbl.setBackground(ContextCompat.getDrawable(this, R.drawable.quickbar_bubble_green));
            bbl.setText("");
            bbl.setTextColor(Color.WHITE);
            bbl.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9);
            bbl.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
            //lp.setMargins(2,2,2,2);

        }

        else if(bubbleNo == 3){
            bbl.setBackground(ContextCompat.getDrawable(this, R.drawable.quick_bubble_fire));


        }
        else if(bubbleNo == 5 || bubbleNo == 6){
            bbl.setBackground(ContextCompat.getDrawable(this, R.drawable.quickbar_bubble_activity));


        }

        else if(bubbleNo == 7){
            bbl.setBackground(ContextCompat.getDrawable(this, R.drawable.quick_bubble_redfire));


        }
        else{
            bbl.setBackground(ContextCompat.getDrawable(this, R.drawable.quickbar_bubble));
        }

        bbl.setLayoutParams(lp);




        return bbl;

    }


    //Get API Content


        @Override
        public void onFragmentInteraction(Uri uri) {



        }
    public ViewOutlineProvider tiny_menu_shadowVOP;
        public void tinymenu_bgOutlineOff(){

        tinyMenu_bg.setOutlineProvider(null);

        }

    public void tinymenu_bgOutlineOn(){

        tinyMenu_bg.setOutlineProvider(tiny_menu_shadowVOP);

    }
    public static void fixBackgroundRepeat(View view) {
        Drawable bg = view.getBackground();
        if (bg != null) {
            if (bg instanceof BitmapDrawable) {
                BitmapDrawable bmp = (BitmapDrawable) bg;
                bmp.mutate(); // make sure that we aren't sharing state anymore
                bmp.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
            }
        }
    }

    public  float convertPixelsToDp(float px){
        return px / ((float) this.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
    public  float convertDpToPixel(float dp){
        return dp * ((float) this.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static boolean chatScrollTop = false;

    private float x1,x2;
    static final int MIN_DISTANCE = 150;
    private boolean windowDimmed = false;
    private boolean moveTD = false;

    private int toggleFeatureDist = 0;
    private float touchDown = 0f;
    @Override
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {

        switch(motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:


                x1 = motionEvent.getX();

                break;
            case MotionEvent.ACTION_MOVE:
                x2 = motionEvent.getX();
                float deltaX = x2 - x1;
                if (Math.abs(deltaX) > MIN_DISTANCE){
                    //Certain Sideways Swipe

                    //check if Im moving left or Right

                    if(!moveTD)
                    {
                        touchDownNav = (int)motionEvent.getX();

                        if(deltaX > 0){
                            //moving left
                            setNavigationPositions((int)motionEvent.getY(), true);

                        }
                        else{
                            //Moving Right
                            setNavigationPositions((int)motionEvent.getY(), false);

                        }
                        moveTD = true;
                    }



                    if(!windowDimmed){

                        dimmMainWindow();
                        windowDimmed = true;
                    }

                    navigateTo((int)motionEvent.getX());


                }


                return super.dispatchTouchEvent(motionEvent);
            case MotionEvent.ACTION_UP:

                moveTD = false;
                if(windowDimmed){
                    showMainWindow();
                    windowDimmed = false;
                    animateBackBars();
                    return true;
                }

                break;
            default:
                break;

        }

        return super.dispatchTouchEvent(motionEvent);


    }

    private void barAlphas(int bar){
        contacts_bar.setAlpha(.2f);
        all_bar.setAlpha(.2f);
        location_bar.setAlpha(.2f);
        platform_bar.setAlpha(.2f);
        camera_bar.setAlpha(.2f);
        switch(bar){
            case 1:
                contacts_bar.setAlpha(.7f);
                //Contacts
                break;
            case 2:
                location_bar.setAlpha(.7f);
                break;
            case 3:
                platform_bar.setAlpha(.7f);
                break;
            case 4:
                all_bar.setAlpha(.7f);
            case 5:
                camera_bar.setAlpha(.7f);
                break;
        }

    }

    int nav_all_top = 0;
    int nav_contacts_top = 0;
    int nav_contacts_bottom = 0;
    int nav_location_top = 0;
    int nav_platforms_top = 0;
    int nav_camera_top = 0;
    int nav_camera_bottom = 0;

    //Contacts = 1 , Location = 2, Platforms = 3;
    int navigationTab = 0;

    public void setNavigationPositions(int y, boolean moveRight){

        nav_all_top = all_bar.getTop();
        nav_contacts_top = contacts_bar.getTop();
        nav_contacts_bottom = contacts_bar.getBottom();
        nav_location_top = location_bar.getTop();
        nav_platforms_top = platform_bar.getTop();
        nav_camera_top = camera_bar.getTop();
        nav_camera_bottom = camera_bar.getBottom();


        if(moveRight){
            if(y > nav_all_top & y < nav_camera_top){
                //Touch All Section
                navigationTab = 4;
            }

            if(y > nav_camera_top & y < nav_camera_bottom){
                //Touch Camera Section

                navigationTab = 5;
            }

        }
        else{

            if(y > nav_platforms_top & y < nav_location_top){
                //Touch Platforms Section
                Log.i("Section", "Platforms");
                navigationTab = 3;
            }
            if(y > nav_location_top & y < nav_contacts_top){
                //Touch Location Section
                Log.i("Section", "Location");
                navigationTab = 2;
            }
            if(y > nav_contacts_top & y < nav_contacts_bottom){
                //Touch Contacts Section
                Log.i("Section", "Contacts");
                navigationTab = 1;
            }

        }







    }
    int move;
    float alpha;
    boolean vibrated = false;
    public void navigateTo(int x){
        move = (x - touchDownNav)/20;
        alpha = Math.abs(roundFloats((x - touchDownNav) * 0.004f, 2));
        if(alpha > 1f){
            alpha = 1f;

            if(!vibrated) {
                myVib.vibrate(25);
                vibrated = true;
            }
        }
        else{
            vibrated = false;
        }


        setNavTagAlphas();

        if(move < 0){
            Log.i("Moving","Left");
            move = -move;
            //moving left open Sections
            switch(navigationTab){
                case 1:
                    contacts_bar_LP.rightMargin = move + (int)convertDpToPixel(3);
                    contacts_bar.setLayoutParams(contacts_bar_LP);
                    break;
                case 2:
                    location_bar_LP.rightMargin = move + (int)convertDpToPixel(3);
                    location_bar.setLayoutParams(location_bar_LP);
                    break;
                case 3:
                    platform_bar_LP.rightMargin = move + (int)convertDpToPixel(3);
                    platform_bar.setLayoutParams(platform_bar_LP);
                    break;
                default:
                    break;

            }

        }
        else{
            //moving right Open All Activity

            switch(navigationTab){
                case 4:
                    all_bar_LP.leftMargin = move + (int)convertDpToPixel(3);
                    all_bar.setLayoutParams(all_bar_LP);
                    break;
                case 5:
                    camera_bar_LP.leftMargin = move + (int)convertDpToPixel(3);
                    camera_bar.setLayoutParams(camera_bar_LP);
                    break;
                default:
                        break;

            }
        }

    }

    public void setNavTagAlphas(){
        // Log.i("Move2", "x"+ alpha);
        if(navigationTab == 1){
            contacts_bar_tag.setAlpha(alpha);
            contacts_bar_tag.setTextSize(12 + alpha * 10);
            contacts_bar_tag_new_messages.setAlpha(alpha);
        }else{ contacts_bar_tag.setAlpha(alpha * .4f);}
        if(navigationTab == 2){
            location_bar_tag.setAlpha(alpha);
            location_bar_tag.setTextSize(12 + alpha * 10);
        }else{ location_bar_tag.setAlpha(alpha * .4f);}
        if(navigationTab == 3){
            platform_bat_tag.setAlpha(alpha);
            platform_bat_tag.setTextSize(12 + alpha * 10);
        }else{ platform_bat_tag.setAlpha(alpha * .4f);}
        if(navigationTab == 4){
            all_bar_tag.setAlpha(alpha);
            all_bar_tag.setTextSize(12 + alpha * 10);
        }else{ all_bar_tag.setAlpha(alpha * .4f);}
        if(navigationTab == 5){
            camera_bar_tag.setAlpha(alpha);
            camera_bar_tag.setTextSize(12 + alpha * 10);
        }else{ camera_bar_tag.setAlpha(alpha * .4f);}

    }
    public static float roundFloats(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }
    public void dimmMainWindow(){
        profilesContainer.setVisibility(View.VISIBLE);
        ValueAnimator va = ValueAnimator.ofFloat(1, 0.01f);


        va.setDuration(300);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                float animValue = (float) animation.getAnimatedValue();

                main_content.setAlpha(animValue);

            }
        });

        va.start();

        ValueAnimator vaLogo = ValueAnimator.ofFloat(0, 1);

        vaLogo.setDuration(300);
        vaLogo.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                float animValue = (float) animation.getAnimatedValue();

                profilesContainer.setAlpha(animValue);


            }
        });


        vaLogo.start();
    }
    public void showMainWindow(){

        ValueAnimator va = ValueAnimator.ofFloat(0.01f, 1);


        va.setDuration(400);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                float animValue = (float) animation.getAnimatedValue();

                main_content.setAlpha(animValue);

            }
        });

        va.start();

        ValueAnimator vaLogo = ValueAnimator.ofFloat(1, 0);

        vaLogo.setDuration(300);
        vaLogo.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                float animValue = (float) animation.getAnimatedValue();

                profilesContainer.setAlpha(animValue);

            }
        });
        vaLogo.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                profilesContainer.setVisibility(View.GONE);

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        vaLogo.start();
    }

    public void animateBackBars(){
        switch(navigationTab){
            case 1:
                animateBackBackAnimator(contacts_bar, contacts_bar_LP);
                if(alpha == 1){
                    openContacts();
                }
                break;
            case 2:
                animateBackBackAnimator(location_bar, location_bar_LP);
                if(alpha == 1){
                    openLocation();
                }
                break;
            case 3:
                animateBackBackAnimator(platform_bar, platform_bar_LP);
                if(alpha == 1){
                    openPlatforms();
                }
            case 4:
                animateBackBackAnimator(all_bar, all_bar_LP);
                if(alpha == 1){
                    openAll();
                }
            case 5:
                animateBackBackAnimator(camera_bar, camera_bar_LP);
                if(alpha == 1){
                    openCamera();
                }
                break;

        }
    }

    public void animateBackBackAnimator(final FrameLayout bar, final ConstraintLayout.LayoutParams LP){

        if(alpha == 1){
            // Launch new Activity / Fragment
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    // yourMethod();
                    resetLines(bar, LP, 300);
                }
            }, 200);



        }
        else
        {
            resetLines(bar, LP, 300);
        }



        ValueAnimator vaAlpha = ValueAnimator.ofFloat(1, 0);
        int durationAlpha = 200;

        vaAlpha.setDuration(durationAlpha);
        vaAlpha.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                float animValue = (float) animation.getAnimatedValue();
                Log.i("Move2", "x"+ animValue);
                if(navigationTab == 1){
                    if(alpha == 1){
                        //Open Fragment is initiated animate Differently
                        contacts_bar_tag.setAlpha(animValue);
                        contacts_bar_tag.setTextSize(22 + (1 - animValue) * 20);
                    }
                    else{
                        contacts_bar_tag.setAlpha(animValue);
                        contacts_bar_tag.setTextSize(12 + animValue * 10);
                    }
                }else{ contacts_bar_tag.setAlpha(animValue * .4f);}
                if(navigationTab == 2){
                    if(alpha == 1){
                        location_bar_tag.setAlpha(animValue);
                        location_bar_tag.setTextSize(22 + (1 - animValue) * 20);
                    }
                    else{
                        location_bar_tag.setAlpha(animValue);
                        location_bar_tag.setTextSize(12 + animValue * 10);
                    }

                }else{ location_bar_tag.setAlpha(animValue * .4f);}
                if(navigationTab == 3){
                    if(alpha == 1){
                        platform_bat_tag.setAlpha(animValue);
                        platform_bat_tag.setTextSize(22 + (1 - animValue) * 20);
                    }
                    else{
                        platform_bat_tag.setAlpha(animValue);
                        platform_bat_tag.setTextSize(12 + animValue * 10);
                    }
                }else{ platform_bat_tag.setAlpha(animValue * .4f);}
                if(navigationTab == 4){
                    if(alpha == 1){
                        all_bar_tag.setAlpha(animValue);
                        all_bar_tag.setTextSize(22 + (1 - animValue) * 20);
                    }
                    else{
                        all_bar_tag.setAlpha(animValue);
                        all_bar_tag.setTextSize(12 + animValue * 10);
                    }
                }else{ all_bar_tag.setAlpha(animValue * .4f);}
                if(navigationTab == 5){
                    if(alpha == 1){
                        camera_bar_tag.setAlpha(animValue);
                        camera_bar_tag.setTextSize(22 + (1 - animValue) * 20);
                    }
                    else{
                        camera_bar_tag.setAlpha(animValue);
                        camera_bar_tag.setTextSize(12 + animValue * 10);
                    }
                }else{ camera_bar_tag.setAlpha(animValue * .4f);}

            }
        });

        vaAlpha.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                contacts_bar_tag.setTextSize(12);
                all_bar_tag.setTextSize(12);
                location_bar_tag.setTextSize(12);
                platform_bat_tag.setTextSize(12);
                camera_bar_tag.setTextSize(12);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        vaAlpha.start();
    }



    public void resetLines(final FrameLayout bar, final ConstraintLayout.LayoutParams LP, int duration){


        ValueAnimator va = ValueAnimator.ofFloat(move, convertDpToPixel(3));


        va.setDuration(duration);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                float animValue = Math.round((float) animation.getAnimatedValue());

                if(navigationTab == 4 || navigationTab == 5){
                    LP.leftMargin = (int)animValue;
                    bar.setLayoutParams(LP);
                }
                else{
                    LP.rightMargin = (int)animValue;
                    bar.setLayoutParams(LP);
                }
            }
        });

        va.start();
    }

    public void setQuickNavigation(int startID, int childrenNo){

        int i = 0;


       while(i < quickBarContainer.getChildCount()){


                View bubble = quickBarContainer.getChildAt(i);


                ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) bubble.getLayoutParams();

                if (i == startID) {

                    animateBubbleSize((int)convertDpToPixel(10), bubble, lp);
                    bubble.setAlpha(1);
                } else {
                    if (i > startID & i <= startID + childrenNo) {
                        animateBubbleSize((int)convertDpToPixel(6), bubble, lp);
                        bubble.setAlpha(1);

                    } else {

                        animateBubbleSize((int)convertDpToPixel(4), bubble, lp);
                        bubble.setAlpha(.5f);
                    }

                }
                bubble.setLayoutParams(lp);
                i++;

        }
    }

    private void animateBubbleSize(int endval, final View bubble, final ConstraintLayout.LayoutParams lp){

        int startval = bubble.getHeight();
        if (startval == endval){
            //ignore animation leave as is

        }
        else{

            ValueAnimator va = ValueAnimator.ofFloat(startval, endval);
            int duration = 50;


            va.setDuration(duration);
            va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    float animValue = Math.round((float) animation.getAnimatedValue());

                    lp.height = (int)animValue;
                    lp.width = (int)animValue;

                    bubble.setLayoutParams(lp);
                }
            });

            va.start();

        }




    }


    public boolean isChatScrollTop() {
        return chatScrollTop;
    }

    public void setChatScrollTop(boolean chatScrollTop) {
        this.chatScrollTop = chatScrollTop;
    }
}

