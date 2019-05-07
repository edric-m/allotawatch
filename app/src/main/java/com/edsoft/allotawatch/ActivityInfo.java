package com.edsoft.allotawatch;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import android.widget.ImageView;
import android.widget.TextView;

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
                    return CreateContactPage(rootView);
                case 6:
                    return CreateBugReportPage(rootView);
                default:
                    break;
            }
            //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }

        private View CreateAboutPage(View rootView) {
            //View rootView = inflater.inflate(R.layout.fragment_activity_info, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label1);
            textView.setText(getString(R.string.section_content_about));
            return rootView;
        }

        private View CreateGetStartedPage(View rootView) {
            //View rootView = inflater.inflate(R.layout.fragment_activity_info, container, false);
            TextView p1 = (TextView) rootView.findViewById(R.id.section_label1);
            p1.setText(getString(R.string.section_content_getstarted_p1));
            TextView p2 = (TextView) rootView.findViewById(R.id.section_label2);
            p2.setText(getString(R.string.section_content_getstarted_p2));
            TextView p3 = (TextView) rootView.findViewById(R.id.section_label3);
            p3.setText(getString(R.string.section_content_getstarted_p3));
            TextView p4 = (TextView) rootView.findViewById(R.id.section_label4);
            p4.setText(getString(R.string.section_content_getstarted_p4));
            TextView p5 = (TextView) rootView.findViewById(R.id.section_label5);
            p5.setText(getString(R.string.section_content_getstarted_p5));
            TextView p6 = (TextView) rootView.findViewById(R.id.section_label6);
            p6.setText(getString(R.string.section_content_getstarted_p6));
            TextView p7 = (TextView) rootView.findViewById(R.id.section_label7);
            p7.setText(getString(R.string.section_content_getstarted_p7));
            TextView p8 = (TextView) rootView.findViewById(R.id.section_label8);
            p8.setText(getString(R.string.section_content_getstarted_p8));
            return rootView;
        }

        private View CreateActionsPage(View rootView) {
            TextView p1 = (TextView) rootView.findViewById(R.id.section_label1);
            p1.setText(getString(R.string.section_content_getstarted_p1));
            TextView p2 = (TextView) rootView.findViewById(R.id.section_label2);
            p2.setText((getString(R.string.section_content_getstarted_p2)));
            TextView p3 = (TextView) rootView.findViewById(R.id.section_label3);
            p3.setText((getString(R.string.section_content_getstarted_p3)));
            TextView p4 = (TextView) rootView.findViewById(R.id.section_label4);
            p4.setText((getString(R.string.section_content_getstarted_p4)));
            TextView p5 = (TextView) rootView.findViewById(R.id.section_label5);
            p5.setText((getString(R.string.section_content_getstarted_p5)));
            TextView p6 = (TextView) rootView.findViewById(R.id.section_label6);
            p6.setText((getString(R.string.section_content_getstarted_p6)));
            TextView p7 = (TextView) rootView.findViewById(R.id.section_label7);
            p7.setText((getString(R.string.section_content_getstarted_p7)));
            TextView p8 = (TextView) rootView.findViewById(R.id.section_label8);
            p8.setText((getString(R.string.section_content_getstarted_p8)));
            return rootView;
        }

        private View CreateBudgetingPage(View rootView) {
            //View rootView = inflater.inflate(R.layout.fragment_activity_info, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label1);
            textView.setText("Budgeting Time");
            return rootView;
        }

        private View CreateContactPage(View rootView) {
            //View rootView = inflater.inflate(R.layout.fragment_activity_info, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label1);
            textView.setText("Contact");
            return rootView;
        }

        private View CreateBugReportPage(View rootView) {
            //View rootView = inflater.inflate(R.layout.fragment_activity_info, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label1);
            textView.setText("Bug Report");
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
            // Show 3 total pages.
            return 6;
        }
    }
}
