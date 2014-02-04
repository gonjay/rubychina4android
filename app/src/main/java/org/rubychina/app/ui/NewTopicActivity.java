package org.rubychina.app.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;

import org.rubychina.app.R;
import org.rubychina.app.ui.fragment.NewTopicFragment;
import org.rubychina.app.ui.fragment.PreviewFragment;

/**
 * Created by mac on 14-2-4.
 */
public class NewTopicActivity extends FragmentActivity {
    private boolean mShowingBack = false;

    private NewTopicFragment newFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_topic);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        newFragment = new NewTopicFragment();

        addStackChange();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, newFragment)
                    .commit();
        }

    }

    private void addStackChange() {
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                mShowingBack = ( getSupportFragmentManager().getBackStackEntryCount() > 0 );
                invalidateOptionsMenu();
            }
        });
    }

    private void flipCard() {
        if (mShowingBack) {
            getSupportFragmentManager().popBackStack();
            return;
        }

        mShowingBack = true;
        getSupportFragmentManager()
                .beginTransaction()
//                .setCustomAnimations(
//                        R.anim.card_flip_right_in, R.anim.card_flip_right_out,
//                        R.anim.card_flip_left_in, R.anim.card_flip_left_out)

                .replace(R.id.container, new PreviewFragment(newFragment.getBody()))
                .addToBackStack(null)
                .commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.topic_reply_menu, menu);
        menu.findItem(R.id.action_preview).setVisible(!mShowingBack);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_send:

                return true;
            case R.id.action_preview:
                System.out.println(mShowingBack);
                flipCard();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
