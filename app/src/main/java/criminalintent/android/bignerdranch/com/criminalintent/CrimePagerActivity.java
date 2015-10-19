package criminalintent.android.bignerdranch.com.criminalintent;

import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.List;
import java.util.UUID;

/**
 * Created by Brian on 10/18/15.
 */
public class CrimePagerActivity extends FragmentActivity
{
    private static final String EXTRA_CRIME_ID = "com.bignerdranch.android.criminalintent.crime_id";

    private ViewPager mViewPager;
    private List<Crime> mCrimes;

    public static Intent newIntent(Context packageContext, UUID crimeId)
    {
        Intent intent = new Intent(packageContext, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);

        // After finding the ViewPager in the activity's view...

        mViewPager = (ViewPager) findViewById(R.id.activity_crime_pager_view_pager);

        // ... you get your data set from CrimeLab - the List of crimes.

        mCrimes = CrimeLab.get(this).getCrimes();

        // Next, you get the activity's instance of FragmentManager.

        FragmentManager fragmentManager = getSupportFragmentManager();

        // Then you set the adapter to be an unnamed instance of FragmentStatePagerAdapter.
        // This requires the FragmentManager.
        // Remember, FragmentStatePagerAdapter is your agent managing the conversation with
        // ViewPager.

        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager)
        {
            @Override
            public Fragment getItem(int position)
            {
                Crime crime = mCrimes.get(position);

                return CrimeFragment.newInstance(crime.getId());
            }

            @Override
            public int getCount()
            {
                return mCrimes.size();
            }
        });

        for (int i = 0; i < mCrimes.size(); i++)
        {
            if (mCrimes.get(i).getId().equals(crimeId))
            {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
