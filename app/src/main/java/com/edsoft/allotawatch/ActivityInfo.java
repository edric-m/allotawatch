package com.edsoft.allotawatch;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityInfo extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    public static final String ARG_SECTION = "section";

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(getIntent().getIntExtra(ARG_SECTION, 0));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_info_cancel) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_activity_info, container, false);
            //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            //ImageView imageView = (ImageView) rootView.findViewById(R.id.section_image);
            int section = getArguments().getInt(ARG_SECTION_NUMBER);
            switch(section) {
                case 1:
                    return CreateAboutPage(rootView);
                case 2:
                    return CreateGetStartedPage(rootView);
                case 3:
                    return CreateActionsPage(rootView);
                case 4:
                    return CreateBudgetingPage(rootView);
                case 5:
                    return CreateContactPage(inflater, container);
                case 6:
                    return CreateBugReportPage(inflater, container);
                default:
                    break;
            }
            //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }

        private View CreateAboutPage(View rootView) {
            //View rootView = inflater.inflate(R.layout.fragment_activity_info, container, false);
            TextView title = (TextView) rootView.findViewById(R.id.section_label1);
            title.setText(getString(R.string.section_content_about_title));
            TextView textView = (TextView) rootView.findViewById(R.id.section_label2);
            textView.setText(getString(R.string.section_content_about));
            return rootView;
        }

        private View CreateGetStartedPage(View rootView) {
            //View rootView = inflater.inflate(R.layout.fragment_activity_info, container, false);
            ImageView imageView;
            TextView p1 = (TextView) rootView.findViewById(R.id.section_label1);
            p1.setText(getString(R.string.section_content_getstarted_title));
            imageView = (ImageView) rootView.findViewById(R.id.section_image2);
            imageView.setImageResource(R.drawable.get_started_1);
            TextView p2 = (TextView) rootView.findViewById(R.id.section_label2);
            p2.setText(getString(R.string.section_content_getstarted_p1));
            imageView = (ImageView) rootView.findViewById(R.id.section_image3);
            imageView.setImageResource(R.drawable.get_started_2);
            TextView p3 = (TextView) rootView.findViewById(R.id.section_label3);
            p3.setText(getString(R.string.section_content_getstarted_p2));
            TextView p4 = (TextView) rootView.findViewById(R.id.section_label4);
            p4.setText(getString(R.string.section_content_getstarted_p3));
            TextView p5 = (TextView) rootView.findViewById(R.id.section_label5);
            p5.setText(getString(R.string.section_content_getstarted_p4));
            imageView = (ImageView) rootView.findViewById(R.id.section_image6);
            imageView.setImageResource(R.drawable.get_started_3);
            TextView p6 = (TextView) rootView.findViewById(R.id.section_label6);
            p6.setText(getString(R.string.section_content_getstarted_p5));
            imageView = (ImageView) rootView.findViewById(R.id.section_image7);
            imageView.setImageResource(R.drawable.get_started_4);
            TextView p7 = (TextView) rootView.findViewById(R.id.section_label7);
            p7.setText(getString(R.string.section_content_getstarted_p6));
            imageView = (ImageView) rootView.findViewById(R.id.section_image8);
            imageView.setImageResource(R.drawable.get_started_5);
            TextView p8 = (TextView) rootView.findViewById(R.id.section_label8);
            p8.setText(getString(R.string.section_content_getstarted_p7));
            TextView p9 = (TextView) rootView.findViewById(R.id.section_label9);
            p9.setText(getString(R.string.section_content_getstarted_p8));
            return rootView;
        }

        private View CreateActionsPage(View rootView) {
            TextView p1 = (TextView) rootView.findViewById(R.id.section_label1);
            p1.setText(getString(R.string.section_content_actions_p1));
            TextView p2 = (TextView) rootView.findViewById(R.id.section_label2);
            p2.setText((getString(R.string.section_content_actions_p2)));
            TextView p3 = (TextView) rootView.findViewById(R.id.section_label3);
            p3.setText((getString(R.string.section_content_actions_p3)));
            TextView p4 = (TextView) rootView.findViewById(R.id.section_label4);
            p4.setText((getString(R.string.section_content_actions_p4)));
            TextView p5 = (TextView) rootView.findViewById(R.id.section_label5);
            p5.setText((getString(R.string.section_content_actions_p5)));
            TextView p6 = (TextView) rootView.findViewById(R.id.section_label6);
            p6.setText((getString(R.string.section_content_actions_p6)));
            TextView p7 = (TextView) rootView.findViewById(R.id.section_label7);
            p7.setText((getString(R.string.section_content_actions_p7)));
            TextView p8 = (TextView) rootView.findViewById(R.id.section_label8);
            p8.setText((getString(R.string.section_content_actions_p8)));
            TextView p9 = (TextView) rootView.findViewById(R.id.section_label9);
            p9.setText((getString(R.string.section_content_actions_p9)));
            return rootView;
        }

        private View CreateBudgetingPage(View rootView) {
            //View rootView = inflater.inflate(R.layout.fragment_activity_info, container, false);
            TextView title = (TextView) rootView.findViewById(R.id.section_label1);
            title.setText(getString(R.string.section_content_budget_title));
            TextView textView = (TextView) rootView.findViewById(R.id.section_label2);
            textView.setText(getString(R.string.section_content_budget));
            //TextView p2 = (TextView) rootView.findViewById(R.id.section_label3);
            //p2.setText(getString(R.string.section_content_budget_p2));
            ImageView imageView = (ImageView) rootView.findViewById(R.id.section_image2);
            imageView.setImageResource(R.drawable.planpage_1);
            return rootView;
        }

        private View CreateContactPage(LayoutInflater inflater, ViewGroup container) {
            View rootView = inflater.inflate(R.layout.fragment_activity_contact, container, false);
            final EditText editText2,editText3;
            TextView p1 = (TextView) rootView.findViewById(R.id.section_label1);
            p1.setText(getString(R.string.section_content_contact_p1));
            TextView p2 = (TextView) rootView.findViewById(R.id.section_label2);
            p2.setText((getString(R.string.section_content_contact_p2)));
            editText2 = (EditText) rootView.findViewById(R.id.section_input2);
            editText2.setVisibility(View.VISIBLE);
            TextView p3 = (TextView) rootView.findViewById(R.id.section_label3);
            p3.setText((getString(R.string.section_content_contact_p3)));
            editText3 = (EditText) rootView.findViewById(R.id.section_input3);
            editText3.setVisibility(View.VISIBLE);
            Button sendButton = (Button) rootView.findViewById(R.id.section_send_button);
            sendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("message/rfc822");
                    i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"edsoft7@gmail.com"});
                    i.putExtra(Intent.EXTRA_SUBJECT, editText2.getText().toString());
                    i.putExtra(Intent.EXTRA_TEXT   , editText3.getText().toString());
                    try {
                        startActivity(Intent.createChooser(i, "Send mail..."));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(getContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            return rootView;
        }

        private View CreateBugReportPage(LayoutInflater inflater, ViewGroup container) {
            View rootView = inflater.inflate(R.layout.fragment_activity_contact, container, false);
            final EditText editText2, editText3, editText4, editText5, editText6, editText7;
            TextView p1 = (TextView) rootView.findViewById(R.id.section_label1);
            p1.setText(getString(R.string.section_content_bugs_p1));
            TextView p2 = (TextView) rootView.findViewById(R.id.section_label2);
            p2.setText((getString(R.string.section_content_bugs_p2)));
            editText2 = (EditText) rootView.findViewById(R.id.section_input2);
            editText2.setVisibility(View.VISIBLE);
            TextView p3 = (TextView) rootView.findViewById(R.id.section_label3);
            p3.setText((getString(R.string.section_content_bugs_p3)));
            editText3 = (EditText) rootView.findViewById(R.id.section_input3);
            editText3.setVisibility(View.VISIBLE);
            TextView p4 = (TextView) rootView.findViewById(R.id.section_label4);
            p4.setText((getString(R.string.section_content_bugs_p4)));
            editText4 = (EditText) rootView.findViewById(R.id.section_input4);
            editText4.setVisibility(View.VISIBLE);
            TextView p5 = (TextView) rootView.findViewById(R.id.section_label5);
            p5.setText((getString(R.string.section_content_bugs_p5)));
            editText5 = (EditText) rootView.findViewById(R.id.section_input5);
            editText5.setVisibility(View.VISIBLE);
            TextView p6 = (TextView) rootView.findViewById(R.id.section_label6);
            p6.setText((getString(R.string.section_content_bugs_p6)));
            editText6 = (EditText) rootView.findViewById(R.id.section_input6);
            editText6.setVisibility(View.VISIBLE);
            TextView p7 = (TextView) rootView.findViewById(R.id.section_label7);
            p7.setText((getString(R.string.section_content_bugs_p7)));
            editText7 = (EditText) rootView.findViewById(R.id.section_input7);
            editText7.setVisibility(View.VISIBLE);
            Button sendButton = (Button) rootView.findViewById(R.id.section_send_button);
            sendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String subject = "AllotaWatch Bug Report - " + editText2.getText().toString();
                    String message = "Feature Name:\n" + editText2.getText().toString() +
                            "\n\nEnvironment:\n" + editText3.getText().toString() +
                            "\n\nSteps To Reproduce:\n" + editText4.getText().toString() +
                            "\n\nExpected Result:\n" + editText5.getText().toString() +
                            "\n\nActual Result:\n" + editText6.getText().toString() +
                            "\n\nAdditional Notes:\n" + editText7.getText().toString();

                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("message/rfc822");
                    i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"edsoft7@gmail.com"});
                    i.putExtra(Intent.EXTRA_SUBJECT, subject);
                    i.putExtra(Intent.EXTRA_TEXT   , message);
                    try {
                        startActivity(Intent.createChooser(i, "Send mail..."));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(getContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 6 total pages.
            return 6;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position+1) {
                case 1:
                    return "About";
                case 2:
                    return "Get Started";
                case 3:
                    return "Controls";
                case 4:
                    return "Planning";
                case 5:
                    return "Contact";
                case 6:
                    return "Report Bugs";
            }
            return null;
        }
    }


}
