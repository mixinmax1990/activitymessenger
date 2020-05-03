package com.example.apiconnect.fragments;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.AsyncLayoutInflater;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apiconnect.activities.chatwindow.ChatWindow;
import com.example.apiconnect.R;
import com.example.apiconnect.activities.chatwindow.ConversationFragment;
import com.example.apiconnect.activities.mainwindow.MainActivity;
import com.example.apiconnect.adapters.ChatRecylerEmptyAdapter;
import com.example.apiconnect.data.database.local.controller.DatabaseController;
import com.example.apiconnect.data.database.local.models.ModelChat;
import com.example.apiconnect.interfaces.RecyclerViewClickListener;
import com.example.apiconnect.managers.CustomRecyclerLayoutManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;



/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ActivityOneFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActivityOneFragment extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    DatabaseController myDB;
    ArrayList<String> chat_ids = new ArrayList<>();
    private TabLayout homeTabLayout;
    private FrameLayout chatFeatureMenu;
    private ViewPager viewPager;
    private ConstraintLayout constraintLayout;
    public static Context context;
    private static MainActivity mainActivity;
    private EditText quickReply;
    private ImageView quickAvatar;
    static Handler handler;
    private CardView addContactChat;

    //Dimens

    //Bottom Tab
    //Top Tab
    ImageView quickCall;
    ImageView quickVideo;
    FrameLayout quickDevider;

    //Scroll Tab

    ImageView quick_send;
    CardView quickAvatarCont;



    @Override
    public void onResume() {
        super.onResume();


    }

    public ActivityOneFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ActivityOneFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ActivityOneFragment newInstance(String param1, String param2) {
        ActivityOneFragment fragment = new ActivityOneFragment();
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
        handler = new Handler();
    }

    // List of Items array
    RecyclerView chatRecyclerView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myDB = new DatabaseController(context);
        this.context = context;


    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainActivity = (MainActivity) getActivity();
        //final View root = inflater.inflate(R.layout.testfragment, container, false);
        final View root = inflater.inflate(R.layout.empty_fragment_layout, container, false);

        AsyncLayoutInflater asyncLayoutInflater = new AsyncLayoutInflater(getContext());

        try {
            asyncLayoutInflater.inflate(R.layout.fragment_activity_one, container, new AsyncLayoutInflater.OnInflateFinishedListener() {
                @Override
                public void onInflateFinished(@NonNull View view, int resid, @Nullable ViewGroup parent) {
                    parent.addView(view);
                    onViewCreated(view, savedInstanceState);

                    Log.i("Asyn","true");

                    chatRecyclerView = view.findViewById(R.id.chats_recyclerview);
                    chatFeatureMenu = view.findViewById(R.id.chat_feature_menu);
                    quickReply = view.findViewById(R.id.quickreply);
                    quickAvatar = view.findViewById(R.id.chatAvatar);
                    homeTabLayout = getActivity().findViewById(R.id.hometabs);
                    viewPager = getActivity().findViewById(R.id.home_view_pager);
                    constraintLayout = getActivity().findViewById(R.id.home_constraint);
                    addContactChat = view.findViewById(R.id.addContactChat);

                    //find Bottom Tab Views
                    quickCall = view.findViewById(R.id.quickCall);
                    quickDevider = view.findViewById(R.id.devider_chat_bottom);
                    quickVideo = view.findViewById(R.id.quickVideo);

                    quick_send = view.findViewById(R.id.quick_send);
                    quickAvatarCont = view.findViewById(R.id.replyAvatarCont);


                    ConstraintLayout.LayoutParams rvLP = (ConstraintLayout.LayoutParams) chatRecyclerView.getLayoutParams();
                    rvLP.topMargin = mainActivity.tmheight - 3;
                    chatRecyclerView.setLayoutParams(rvLP);

                    createChatRecycler(view);

                    List<ModelChat> chats = myDB.chat.getAllChats();


                    for(ModelChat chat: chats){

                        //message.getFrom();
                        Log.i("FROM --------",""+ chat.getLastMessageID());
                        Log.i("Description --------",""+ chat.getChatDescription());
                   //

                        //message.
                    }

                    listeners();
                }
            });
        }
        catch (Exception e){
            Log.i("Asyn","false");
        }



        return root;

    }

    public void listeners() {


    }
    boolean bottomTabToggled = true;

    private void createChatRecycler(View root){

        chatRecyclerView.hasFixedSize();
        chatRecyclerView.setDrawingCacheEnabled(true);
        final List<ModelChat> chats = loadChats();


        final RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position, ImageView chatAvatar) {
                String chatID = chat_ids.get(position-1);
                startChatWindow(view, chatID, chatAvatar);
            }
        };



        ChatRecylerEmptyAdapter adapter = new ChatRecylerEmptyAdapter(root.getContext(), 0, chats, listener, myDB);


        RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
            int currentScrollPosition = 0;
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {

                Log.i("STate", ""+newState);
                super.onScrollStateChanged(recyclerView, newState);

            }
            View prevChild;
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                // Create List of all childrens Position
                currentScrollPosition += dy;

                int offset = recyclerView.computeVerticalScrollOffset();
                int extent = recyclerView.computeVerticalScrollExtent();
                int range = recyclerView.computeVerticalScrollRange();

                float percentage = (100.0f * offset / (float)(range - extent)) / 3;

                //mainActivity.scrollbar_move(percentage);

                Log.i("ScrollPercentage","" + percentage);

                if(currentScrollPosition == 0){

                    toggleAddUser(false);
                    bottomTabToggled = false;
                    //mainActivity.tinymenu_bgOutlineOff();
                    switchBottomTab(true);

                    //Switch between Bottom Tabs
                }
                else{
                    if(!bottomTabToggled){
                        toggleAddUser(true);
                        bottomTabToggled = true;
                        //mainActivity.tinymenu_bgOutlineOn();
                        switchBottomTab(false);
                    }

                }

                View child = chatRecyclerView.getChildAt(1);

                if(child != prevChild){
                    if(child != null) {
                        try {

                            //((TextView) quickOverview.findViewById(R.id.quick_u_name)).setText(((TextView) child.findViewById(R.id.chatlist_name)).getText());
                            quickReply.setText("Write to "+((TextView) child.findViewById(R.id.chatlist_name)).getText());
                            Picasso picasso = Picasso.get();
                            picasso.load(child.getTag().toString()).into(quickAvatar);

                            //slideQuickPosition(quickOverview, child.getHeight());
                            //fadeQuickCover();
                            //setQuickNavigation(startID, childrenNo);

                            View prevChild = chatRecyclerView.getChildAt(0);
                            View nextChild = chatRecyclerView.getChildAt(2);
                            //expandChatMessage(child);

                            //prevChild.setElevation(0);
                            //resetLines((TextView) prevChild.findViewById(R.id.chatlist_message));
                            //prevChild.setBackgroundColor(Color.WHITE);
                            prevChild.findViewById(R.id.chat_expand).setVisibility(View.INVISIBLE);
                            //child.setElevation(0);
                            //child.setBackgroundColor(context.getColor(R.color.lightgrey));
                            child.findViewById(R.id.chat_expand).setVisibility(View.VISIBLE);
                            //nextChild.setElevation(0);
                            //resetLines((TextView) nextChild.findViewById(R.id.chatlist_message));
                            //nextChild.setBackgroundColor(Color.WHITE);
                            nextChild.findViewById(R.id.chat_expand).setVisibility(View.INVISIBLE);


                        }catch(Exception e){

                        }

                    }

                }

            }

        };
        chatRecyclerView.setAdapter(adapter);

        LinearLayoutManager llm = new CustomRecyclerLayoutManager(root.getContext());
        llm.setStackFromEnd(false);
        //llm.scrollToPositionWithOffset(0, 0);

        chatRecyclerView.setLayoutManager(llm);
        chatRecyclerView.addOnItemTouchListener(itemTListenerSCROLL);
        chatRecyclerView.addOnScrollListener(scrollListener);
    }

    TextView prevMessage;
    ValueAnimator vaChExp;


    private void expandChatMessage(View child){

        //vaChExp.cancel();

            handler.removeCallbacksAndMessages(null);

            prevMessage = child.findViewById(R.id.chatlist_message);

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    //Toast.makeText(context, "WOrks", Toast.LENGTH_SHORT).show();


                    vaChExp = ValueAnimator.ofInt(1, 10);
                    int duration = 400;


                    vaChExp.setDuration(duration);
                    vaChExp.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        public void onAnimationUpdate(ValueAnimator animation) {
                            int animValue = Math.round((int) animation.getAnimatedValue());
                            try {
                                prevMessage.setMaxLines(animValue);

                            }catch(Exception e){}
                        }
                    });

                    vaChExp.start();



                }
            }, 50);




    }
    private void toggleAddUser(Boolean addUserOpened){

        ValueAnimator va;
        ValueAnimator vao;

        final TextView tag = addContactChat.findViewById(R.id.addContactTag);


        if(addUserOpened){
            va = ValueAnimator.ofInt(addContactChat.getWidth(), (int)convertDpToPixel(45));
            vao = ValueAnimator.ofFloat(1, 0);
        }
        else {
            va = ValueAnimator.ofInt(addContactChat.getWidth(), (int)convertDpToPixel(170));
            vao = ValueAnimator.ofFloat(0, 1);
        }

        int duration = 200;
        final ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) addContactChat.getLayoutParams();



                va.setDuration(duration);
                va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int animValue = Math.round((int) animation.getAnimatedValue());

                        lp.width = animValue;
                        addContactChat.setLayoutParams(lp);
                    }
                });

                vao.setDuration(duration);
                vao.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {

                        float animValue = (float)animation.getAnimatedValue();
                        tag.setAlpha(animValue);
                    }
                });

                va.start();
                vao.start();


    }
    private void switchBottomTab(boolean cond){
        if(cond){
            //Show Call and Video Tab
            //Show
            fadeIn(quickDevider, 200);
            fadeIn(quickCall, 200);
            fadeIn(quickVideo, 200);

            //Hide
            fadeOut(quickAvatarCont, 200);
            fadeOut(quickReply, 200);
            fadeOut(quick_send, 200);

        }
        else{
            //Show Reply Tab

            //Show
            fadeOut(quickDevider, 200);
            fadeOut(quickCall, 200);
            fadeOut(quickVideo, 200);

            //Hide
            fadeIn(quickAvatarCont, 200);
            fadeIn(quickReply, 200);
            fadeIn(quick_send, 200);
        }

    }

    private void fadeOut(final View view, int duration){

        ValueAnimator va;
        va = ValueAnimator.ofFloat(1, 0);

        va.setDuration(duration);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                float animValue = Math.round((float) animation.getAnimatedValue());

               view.setAlpha(animValue);
            }
        });

        va.start();
    }

    private void fadeIn(final View view, int duration){

        ValueAnimator va;
        va = ValueAnimator.ofFloat(0, 1);

        va.setDuration(duration);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                float animValue = Math.round((float) animation.getAnimatedValue());

                view.setAlpha(animValue);
            }
        });

        va.start();
    }
    ValueAnimator vaChRes;
    private void resetLines(final TextView prevMessage){

            try {
                prevMessage.setMaxLines(1);

            } catch (Exception e) {
            }





    }

    private void setQuickNavigation(int startID, int childrenCount){

        MainActivity activity = (MainActivity) getActivity();
        activity.setQuickNavigation(startID, childrenCount);

    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private float x1,x2;
    static final int MIN_DISTANCE = 150;
    private boolean windowDimmed = false;

    private int toggleFeatureDist = 0;
    private float touchDown = 0f;

    private RecyclerView.OnItemTouchListener itemTListenerSCROLL = new RecyclerView.OnItemTouchListener() {

        // Cheks if the top touchdown has been initiated
        @Override
        public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

            //Log.i("Is Running", "Yes");

          return false;
        }

        @Override
        public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean b) {

        }

    };


    public static float convertPixelsToDp(float px){
        return px / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
    public static float convertDpToPixel(float dp){
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }


    public void startChatWindow(View root, String chat_id, ImageView chatAvatar){

        mainActivity.openConversation(chatAvatar, chat_id);
       // intent.putExtra("chat_id", chat_id);

    }

    private List<ModelChat> loadChats(){
        //List of ID
        List<ModelChat> allChats = myDB.chat.getAllChats();

        for(ModelChat chat: allChats){
            chat_ids.add(chat.getChat_id());



           //((MainActivity) getActivity()).appendTextBubble_to_QuickBar();

        }
        return allChats;
    }



    public void startChatWindow(View view, View root){

        Intent intent = new Intent(root.getContext(), ChatWindow.class);
        startActivity(intent);
    }


}
