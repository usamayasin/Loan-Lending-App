package org.jethro.mobile.ui.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import org.jethro.mobile.R;
import org.jethro.mobile.ui.activities.base.BaseActivity;
import org.jethro.mobile.utils.ConfigurationDialogFragmentCompat;
import org.jethro.mobile.utils.ConfigurationPreference;
import org.jethro.mobile.utils.Constants;
import org.jethro.mobile.utils.LanguageHelper;

import androidx.fragment.app.DialogFragment;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

/**
 * Created by dilpreet on 02/10/17.
 */

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.
        OnSharedPreferenceChangeListener {

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.settings_preference);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().
                unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDisplayPreferenceDialog(Preference preference) {
        DialogFragment dialogFragment = null;
        if (preference instanceof ConfigurationPreference) {
            dialogFragment = new ConfigurationDialogFragmentCompat();
            Bundle bundle = new Bundle(1);
            bundle.putString("key", preference.getKey());
            dialogFragment.setArguments(bundle);
        }

        if (dialogFragment != null) {
            dialogFragment.setTargetFragment(this, 0);
            dialogFragment.show(this.getFragmentManager(),
                    "android.support.v7.preference.PreferenceFragment.DIALOG");
        } else {
            super.onDisplayPreferenceDialog(preference);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        Preference preference = findPreference(s);
        if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;
            LanguageHelper.setLocale(getContext(), listPreference.getValue());
            Intent intent = new Intent(getActivity(), getActivity().getClass());
            intent.putExtra(Constants.HAS_SETTINGS_CHANGED, true);
            startActivity(intent);
            getActivity().finish();
        }
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        switch (preference.getKey()) {
            case Constants.PASSWORD:
                ((BaseActivity) getActivity()).replaceFragment(UpdatePasswordFragment
                        .newInstance(), true, R.id.container);
                break;
        }
        return super.onPreferenceTreeClick(preference);
    }
}
