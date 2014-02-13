package org.rubychina.app.ui.fragment;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceScreen;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.rubychina.app.R;

/**
 * Created by mac on 14-2-13.
 */
public class PreferenceFragment extends android.preference.PreferenceFragment {
    public static final String KEY_PREF_CLEAR_CACHE = "pref_clear_cache_key";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);

        // 设置版本号
        Preference versionPreference = findPreference(getString(R.string.pref_key_version));
        PackageInfo packageInfo;
        try {
            packageInfo = getActivity().getPackageManager().getPackageInfo(
                    getActivity().getPackageName(), PackageManager.GET_CONFIGURATIONS);
            versionPreference.setTitle(packageInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onPreferenceTreeClick (PreferenceScreen preferenceScreen, Preference preference){
        if (preference.getKey().equals(KEY_PREF_CLEAR_CACHE)){
            ImageLoader.getInstance().clearDiscCache();
            Toast.makeText(getActivity(), R.string.clear_cache_success, Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }

}

