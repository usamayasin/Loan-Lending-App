package org.jethro.mobile.injection.component;

import android.app.Application;
import android.content.Context;

import org.jethro.mobile.api.BaseApiManager;
import org.jethro.mobile.api.DataManager;
import org.jethro.mobile.api.local.DatabaseHelper;
import org.jethro.mobile.api.local.PreferencesHelper;
import org.jethro.mobile.injection.ApplicationContext;
import org.jethro.mobile.injection.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author ishan
 * @since 08/07/16
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @ApplicationContext
    Context context();

    Application application();
    DataManager dataManager();
    PreferencesHelper prefManager();
    BaseApiManager baseApiManager();
    DatabaseHelper databaseHelper();

}
