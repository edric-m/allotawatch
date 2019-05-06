package com.edsoft.allotawatch;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DialogAlarm.OnInputListener {

    private static final String BREAK_TIME_TEXT = "work break";/*!< Constant used when setting a textview */
    private enum Direction {
        PREVIOUS,
        NEXT
    }

    private tasks list; /*!< Contains the array list of the tasks */
    private task selectedTask; /*!< The task that the activity will focus on */

    private TextView taskName, taskTime, mtextviewBreak, mtaskIndex; /*!< Controls the TextView that displays the time */
    private ConstraintLayout layout; /*!< Needed to set the background colour of the activity */
    private ImageView mImageView; /*!< Controls the background image */
    private Button mClearBtn;//, mAlarmBtn, mStartBtn;
    //private Menu menu;
    //private MenuItem mAlarmBtn;
    private boolean alarmBtnVisible, playBtnVisable;

    private long timePaused; /*!< Counts the amount of time the pause timer has run */
    private int switchedTime=0;
    private long breakRecommend=0;
    private boolean mTimerRunning; /*!< Bool for whether the timer for a task is running */
    private boolean mHasTasks; /*!< Bool for whether a task has been added to the activity */
    private boolean mServiceStarted;
    private boolean alarmSet=false;
    private int alarmMin=0;

    private float initialX, initialY;
    private NotificationManager notificationManager;
    private BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int x = 3;
            x = intent.getIntExtra("start_pause", 3);
            if(x!=3){
                switch(x){
                    case 0:
                        startWorkBreak();
                        break;
                    case 1:
                        switchedTime = 0;
                        toggleTimer();
                        mTimerRunning = true;
                        toggleTimer();
                        refreshDisplay(false);
                        break;
                    case 2:
                        //switchTask(Direction.NEXT);
                        toggleTimer();
                        break;
                    default:
                        break;
                }
                intent.putExtra("start_pause",3);
                intent.removeExtra("start_pause");
            }
            selectedTask.setTimeAllocated((long) intent.getIntExtra("time_left",(int)selectedTask.getTimeAllocated()));
            timePaused = intent.getIntExtra("pause_time", (int)timePaused);
            checkQuickAlarm();
            timerTick();
        }
    };

    //////////////////////////////////////////////////////////////
    //                    INIT METHODS
    //////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTask();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initView();
        initList();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem mAlarmBtn = (MenuItem) menu.findItem(R.id.brn_alarm);
        mAlarmBtn.setVisible(alarmBtnVisible);
        if(alarmSet) {
            mAlarmBtn.setIcon(R.drawable.ic_white_alarm_off);
        } else {
            mAlarmBtn.setIcon(R.drawable.ic_white_alarm);
        }
        MenuItem mPlayBtn = (MenuItem) menu.findItem(R.id.btn_startTimer);
        if(playBtnVisable){
            mPlayBtn.setIcon(R.drawable.ic_white_play);
        } else {
            mPlayBtn.setIcon(R.drawable.ic_white_stop);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        switch(id) {
            case R.id.brn_alarm:
                showAlarmDialog();
                break;
            case R.id.btn_startTimer:
                toggleTimer();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_about) {
            Intent i = new Intent(this, ActivityInfo.class);
            startActivity(i);
        } else if (id == R.id.nav_get_started) {

        } else if (id == R.id.nav_actions) {

        } else if (id == R.id.nav_budget) {

        } else if (id == R.id.nav_contact) {

        } else if (id == R.id.nav_report_bugs) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initView() {
        //View variables that will be used
        //mStartBtn = findViewById(R.id.btn_startTimer);
        /*mStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleTimer();
            }
        });*/
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        /*mAlarmBtn = findViewById(R.id.brn_alarm);
        mAlarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlarmDialog();
            }
        });*/
        //mAlarmBtn.setVisibility(View.INVISIBLE);
        //mAlarmBtn.setEnabled(false);
        //mAlarmBtn = (MenuItem) findViewById(R.id.brn_alarm);
        //mAlarmBtn.setVisible(false);
        //mAlarmBtn.setEnabled(false);
        alarmBtnVisible = false;
        playBtnVisable = true;
        mtaskIndex = findViewById(R.id.textView_taskIndex);
        taskName = findViewById(R.id.task_name);
        taskTime = findViewById(R.id.task_time);
        layout = findViewById(R.id.layout);
        mtextviewBreak = findViewById(R.id.textview_break);
        mImageView = findViewById(R.id.imageview_back);
        mClearBtn = findViewById(R.id.button_clear_tasks);
        mClearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteTask();
            }
        });
        //Timer variables initialise
        mTimerRunning = false;
        mHasTasks = false;
        mServiceStarted = false;
    }

    private void initList() {
        if(!readDb()) {
            list = new tasks();
            //taskName.setText("bad load db");
            mClearBtn.setVisibility(View.INVISIBLE);
            mClearBtn.setEnabled(false);
            mtaskIndex.setVisibility(View.INVISIBLE);
            //mAlarmBtn.setVisibility(View.INVISIBLE);
            alarmBtnVisible = false;
            invalidateOptionsMenu();
            //mAlarmBtn.setEnabled(false);
            //mAlarmBtn.setVisible(false);
            mTimerRunning = true;
        } else {
            //do something else
        }
        breakRecommend = ((list.getTotalMs()/3600000)*600000);
    }
    //////////////////////////////////////////////////////////////
    //                  ACTIVITY METHODS
    //////////////////////////////////////////////////////////////
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //stop service
        if(mServiceStarted) {
            unregisterReceiver(br); //TODO: this is not a good idea, ondestroy is not always called
            this.stopService(new Intent(this, BroadcastService.class));
        }
        Process.killProcess(Process.myPid()); //app wasnt terminating properly in android studio debug
    }
    @Override
    protected void onPause() {
        saveDb();
        super.onPause(); //TODO: should these be at the beginning of the function?
    }
    @Override
    protected void onResume() {
        super.onResume();
        refreshDisplay(!mTimerRunning);
    }
    @Override
    public boolean dispatchTouchEvent (MotionEvent event) {
        int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                initialX = event.getX();
                initialY = event.getY();
                break;

            case MotionEvent.ACTION_MOVE: //drag?
                break;

            case MotionEvent.ACTION_UP:
                float finalX = event.getX();
                float finalY = event.getY();

                //check that the action up distance is large enough to be considered a swipe
                if(Math.abs(initialX - finalX) < 200 && Math.abs(initialY - finalY) < 200) {
                    break; //motion is likely a press
                }

                if (initialX < finalX && Math.abs(finalY - initialY) < Math.abs(initialX - finalX)) {
                    //Log.d(TAG, "Left to Right swipe performed");
                    switchTask(Direction.PREVIOUS);
                }

                if (initialX > finalX && Math.abs(finalY - initialY) < Math.abs(initialX - finalX)) {
                    //Log.d(TAG, "Right to Left swipe performed");
                    switchTask(Direction.NEXT); //switch task next
                }

                if (initialY < finalY && Math.abs(finalX - initialX) < Math.abs(initialY - finalY)) {
                    //Log.d(TAG, "Up to Down swipe performed");
                    if(mHasTasks)
                        startWorkBreak(); //work break
                }

                if (initialY > finalY && Math.abs(finalX - initialX) < Math.abs(initialY - finalY)) {
                    //Log.d(TAG, "Down to Up swipe performed");
                    //if(mHasTasks)
                    if(mTimerRunning) {  //TODO double check this
                        switchedTime = 0; //TODO: not good to do this here
                        openSettings();
                    } else {
                        switchedTime = 0;
                        toggleTimer();
                        mTimerRunning = true;
                        toggleTimer();
                        refreshDisplay(false);
                        //toast.setText(selectedTask.getName() + " resumed");
                        //toast.show();
                    }
                }
                break;

            case MotionEvent.ACTION_CANCEL:
                //Log.d(TAG,"Action was CANCEL");
                break;

            case MotionEvent.ACTION_OUTSIDE:
                //Log.d(TAG, "Movement occurred outside bounds of current screen element");
                break;
        }
        return super.dispatchTouchEvent(event);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        toggleTimer();
        mTimerRunning = true;

        Log.d("MyActivity", "requestCode:"+Integer.toString(requestCode) + " resultCode:"+Integer.toString(resultCode));
        refreshDisplay(!mTimerRunning); //fix this, its confusing
        if (resultCode == RESULT_OK) {
            switch(requestCode) {
                case 1:
                    if(readDb()) {
                        selectedTask = list.selectNewTask(); //TODO: will this work?
                        mTimerRunning = true;
                        mHasTasks = true;
                        breakRecommend = ((list.getTotalMs() / 3600000) * 600000);
                        refreshDisplay(false);
                    }
                    break;
                case 2: //TODO: instead read from database to get tasks
                    //selectedTask = list.selectFirstTask();
                    list.clear();
                    if(readDb()) {
                        //list.clear();
                        selectedTask = list.selectFirstTask();
                        mTimerRunning = true;
                        breakRecommend = ((list.getTotalMs() / 3600000) * 600000);
                        timePaused = 0;

                    } else {
                        mTimerRunning = true;
                        mHasTasks = false;
                        taskName.setText("");
                        taskTime.setText("");
                        //layout.setBackgroundColor(Color.parseColor("#4576c1"));
                        mClearBtn.setVisibility(View.INVISIBLE);
                        mtaskIndex.setVisibility(View.INVISIBLE);
                        mtaskIndex.setVisibility(View.INVISIBLE);
                        //mAlarmBtn.setVisibility(View.INVISIBLE);
                        invalidateOptionsMenu();
                        alarmBtnVisible = false;
                        //mAlarmBtn.setVisible(false);
                        //mAlarmBtn.setEnabled(false);
                        mImageView.setImageDrawable(null);
                        stopTimer();
                    }
                    refreshDisplay(false);
                    break;
                default:
                    break;
            }
        }
        toggleTimer();
    }
    @Override
    public void setQuickAlarm(int mins) {
        //if(!alarmSet) {
        alarmMin = mins * 1000*60; //repeater = alarmMin
        alarmSet = true;
        //mAlarmBtn.setText("stop "+ Integer.toString(mins)+" min alarm"); //TODO inform user somehow
        invalidateOptionsMenu();
    }
    //////////////////////////////////////////////////////////////
    //                      METHODS
    //////////////////////////////////////////////////////////////
    private boolean readDb() {
        boolean result = true;
        try {
            FeedReaderDbHelper db = new FeedReaderDbHelper(this);
            list = db.readAllTasks();
            if(list.size() > 0) {
                selectedTask = list.selectFirstTask();
                mTimerRunning = true; //start broadcast at mTimerRunning = true
                mHasTasks = true;
                result = true;
                refreshDisplay(false);
            } else {
                result = false;
            }
        } catch (Exception e) {
            //result = false;
        }
        return result;
    }

    private void saveDb() {
        //super.finish();
        Log.d("MyActivity", "save called" );
        //tasks t = new tasks();
        //t.getList() = list.getList();
        try {
            FeedReaderDbHelper db = new FeedReaderDbHelper(this);
            if(list.size() > 0)
                db.updateAllTasks(list.getList(), 0);
        } catch (Exception e) {
            Log.d("MyActivity", e.getMessage());
        }
    }

    private void deleteTask() {
        if(mTimerRunning) {
            FeedReaderDbHelper db = new FeedReaderDbHelper(this);
            db.updateAllTasks(list.getList(), 0);
            db.deleteTask(selectedTask.getName());
            //list.moveToNextTask();
            list.removeTask(selectedTask);
            if(list.size()>0) {
                //selectedTask = list.selectFirstTask();
                //stopTimer(); //TODO test toggle
                toggleTimer();
                selectedTask = list.moveToPrevTask();  //bug fix done here <<<<<<<<<
                //startTimer();
                toggleTimer();
                breakRecommend = ((list.getTotalMs()/3600000)*600000);
                switchedTime = 0;
                refreshDisplay(false);
            } else {
                mTimerRunning = true;
                mHasTasks = false;
                taskName.setText("");
                taskTime.setText("");
                //layout.setBackgroundColor(Color.parseColor("#4576c1"));
                mClearBtn.setVisibility(View.INVISIBLE);
                mtaskIndex.setVisibility(View.INVISIBLE);
                mtaskIndex.setVisibility(View.INVISIBLE);
                //mAlarmBtn.setVisibility(View.INVISIBLE);
                invalidateOptionsMenu();
                alarmBtnVisible = false;
                //mAlarmBtn.setVisible(false);
                //mAlarmBtn.setEnabled(false);
                mImageView.setImageDrawable(null);
                stopTimer();
            }
        }
    }

    private void timerTick() {
        if(mHasTasks) {
            switchedTime = switchedTime + 1000;
            if(mTimerRunning) {
                //selectedTask.decrementTime();
                selectedTask.incrementTimeSpent();
                updateCountDownText(false);
                showTaskInfo();
            }
            else {
                //timePaused = timePaused + 1000;
                updateCountDownText(true);
                showBreakInfo();
            }
        }
    }

    private void checkQuickAlarm() {
        if(alarmSet) {
            if (alarmMin == 0) {
                //notify
                notifyAlarmEnd();
                //turn off reciever
                //mAlarmBtn.setText("set alarm"); //TODO inform user somehow
                invalidateOptionsMenu();
                alarmSet = false;
            } else {
                alarmMin = alarmMin - 1000;
            }
        }
    }

    private void toggleTimer() { //TODO confusing (function) names
        if(mHasTasks) {
            if (mServiceStarted) {
                stopTimer();
                //mStartBtn.setText("Start");
                //menu.getItem(R.id.btn_startTimer).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_white_play));
                invalidateOptionsMenu();
                playBtnVisable = true;

            } else {
                startTimer();
                //mStartBtn.setText("Stop");
                //menu.getItem(R.id.btn_startTimer).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_white_stop));
                invalidateOptionsMenu();
                playBtnVisable = false;
            }
        }
    }

    private void startTimer() {
        if(mHasTasks && selectedTask.getTimeAllocated() > 1) {
            mServiceStarted = true; //TODO test moving this outside. originaly here
            //start service
            Intent i = new Intent(this, BroadcastService.class);
            i.putExtra("pause_time", (int)switchedTime);
            i.putExtra("total_pause", (int)timePaused);
            i.putExtra("set_time", (int) selectedTask.getTimeAllocated());
            i.putExtra("task_name", selectedTask.getName());
            if(mTimerRunning) {

                Log.d("MyActivity", "time send");
            } else {
                i.putExtra("paused",true);

            }
            this.startService(i);
            //register reciever
            this.registerReceiver(br, new IntentFilter(BroadcastService.COUNTDOWN_BR));
            //mAlarmBtn.setVisibility(View.VISIBLE);
            invalidateOptionsMenu();
            alarmBtnVisible = true;
            //mAlarmBtn.setVisible(true);
            //mAlarmBtn.setEnabled(true);
            //no need to pause timer
        }
        //mServiceStarted = true; //because the timer is starting when switched from a finished activity
    }

    private void stopTimer() {
        if (mServiceStarted) {
            stopService(new Intent(this, BroadcastService.class));
            //unregister reciever
            this.unregisterReceiver(br);
            //stop service
            Log.d("MyActivity", "service canceled" );
            //mAlarmBtn.setVisibility(View.INVISIBLE);
            invalidateOptionsMenu();
            alarmBtnVisible = false;
            //mAlarmBtn.setVisible(false);
            //mAlarmBtn.setEnabled(false);
            mServiceStarted = false;
        }
    }

    public void startWorkBreak() {
        toggleTimer();
        switchedTime = 0;
        mTimerRunning = false;
        refreshDisplay(true);
        toggleTimer();
    }

    private void updateCountDownText(boolean paused) {
        long time = 0;

        if (paused) {
            time = switchedTime;
        }
        if (!paused) {
            time = selectedTask.getTimeAllocated();
        }
        int hours = (int) (time / 3600000);
        int minutes = (int) (time / 60000) % 60;
        int seconds = (int) (time / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d:%02d",hours, minutes, seconds);

        taskTime.setText(timeLeftFormatted);
    }

    private String formatMsToTime(long ms) {
        int hours = (int) (ms / 3600000);
        int minutes = (int) (ms / 60000) % 60;
        int seconds = (int) (ms / 1000) % 60;
        return String.format(Locale.getDefault(),"%02d:%02d:%02d",hours, minutes, seconds);
    }

    private void refreshDisplay(boolean paused) {
        if(mHasTasks) { //TODO might need this
            if (paused) {
                taskName.setText("");
                mtextviewBreak.setText(BREAK_TIME_TEXT);
                mClearBtn.setVisibility(View.INVISIBLE);
                mClearBtn.setEnabled(false);
                //mtaskIndex.setVisibility(View.INVISIBLE);
                //mAlarmBtn.setVisibility(View.INVISIBLE);
                //mAlarmBtn.setEnabled(false);
                showBreakInfo();
            } else {
                taskName.setText(selectedTask.getName());
                mtextviewBreak.setText("");
                mClearBtn.setVisibility(View.VISIBLE);
                mClearBtn.setEnabled(true);
                mtaskIndex.setVisibility(View.VISIBLE);
                if(mServiceStarted) {
                    //mAlarmBtn.setVisibility(View.VISIBLE);
                    invalidateOptionsMenu();
                    alarmBtnVisible = true;
                    //mAlarmBtn.setVisible(true);
                    //mAlarmBtn.setEnabled(true);
                }
                //mtaskIndex.setVisibility(View.VISIBLE);
                showTaskInfo();
            }
            setBackground(paused);
            updateCountDownText(paused);
        }
    }

    private void setBackground(boolean paused) {
        //layout.setBackgroundColor(Color.parseColor("#1B1F59")); //in colors.xml
        if(paused) {
            mImageView.setImageResource(R.drawable.background_pause_small);
        }
        else {
            mImageView.setImageResource(R.drawable.background_resume_small);
        }
    }

    private void showBreakInfo() {
        mtaskIndex.setText("\n\nTotal break time: " + formatMsToTime(timePaused));
        mtaskIndex.append("\nRecommended break time: " + formatMsToTime(breakRecommend-timePaused));
    }

    private void showTaskInfo() {
        int index = list.getCurrentTaskIndex();
        //show "Task 1 of 5"
        mtaskIndex.setText("#" + Integer.toString(index) + "/" + Integer.toString(list.size()));
        //finish time and date for this task + time left
        mtaskIndex.append("\n\nTime since last break: " + formatMsToTime(switchedTime));
        //mtaskIndex.append("\nTotal time spent this session: " + formatMsToTime(list.getList().get(index-1).getTimeSpent()));
        mtaskIndex.append("\nTime left for all tasks: " + formatMsToTime(list.getTotalMs()));
        mtaskIndex.append("\nTotal time spent: " + formatMsToTime(selectedTask.getTimeSpent()));
    }

    public void switchTask(Direction direction) {
        if(mTimerRunning && mHasTasks) {
            toggleTimer();
            //switchedTime = 0;
            switch (direction) {
                case PREVIOUS:
                    selectedTask = list.moveToPrevTask();
                    break;
                case NEXT:
                    selectedTask = list.moveToNextTask();
                    break;
                default:
            }
            toggleTimer();
            //Toast toast = Toast.makeText(getApplicationContext(), "Starting "+selectedTask.getName(), Toast.LENGTH_SHORT);
            //toast.show();
            refreshDisplay(false);
        }
    }

    public void addTask() {
        //new task
        toggleTimer();
        mTimerRunning = false;
        toggleTimer();
        refreshDisplay(mTimerRunning);
        Intent i = new Intent(this, NewTaskActivity.class);
        startActivityForResult(i, 1); //request code is so we know who we are hearing back from
        //mTimerRunning = false;
        //refreshDisplay(true);
    }

    public void openSettings() { //open task settings
        toggleTimer();
        mTimerRunning = false;
        toggleTimer();
        refreshDisplay(mTimerRunning);
        //settings //TODO: load current tasks into settings
        Intent i = new Intent(this, SettingsActivity.class);
        int idx = 0;
        for (task t : list.getList()) {
            i.putExtra("item" + Integer.toString(idx), t.getName());
            i.putExtra("itemValue" + Integer.toString(idx), t.getTimeAllocated());
            i.putExtra("itemSpent" + Integer.toString(idx), t.getTimeSpent());
            idx++;
        }
        i.putExtra("item_count", Integer.toString(idx));
        startActivityForResult(i, 2);
    }

    private void showAlarmDialog() {
        if(!alarmSet) {
            DialogAlarm dialog = new DialogAlarm();
            dialog.show(getSupportFragmentManager(), "DialogAlarm");
        } else {
            //mAlarmBtn.setText("set alarm"); //TODO inform user somehow
            invalidateOptionsMenu();
            //this.unregisterReceiver(alarmBroadcast);
            //wl.release();
            //Log.d("alarm", "alarm un-register receiver");
            alarmSet = false;
        }
    }

    private void notifyAlarmEnd() {
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, "default")
                        //.setSmallIcon(R.drawable.abc)
                        .setSmallIcon(R.drawable.ic_action_name)
                        .setLargeIcon(BitmapFactory.decodeResource(this.getResources(),
                                R.mipmap.ic_notification_round))
                        .setContentTitle("Your quick alarm has ended.")
                        //.setContentText("")
                        .setLights(Color.WHITE,1,1)
                        .setSound(alarmSound)
                        .setAutoCancel(true)
                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL; //doesn't need this

        // Add as notification
        notificationManager.notify(0, notification);
    }
}
