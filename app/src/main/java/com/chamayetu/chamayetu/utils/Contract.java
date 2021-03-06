package com.chamayetu.chamayetu.utils;

import com.chamayetu.chamayetu.BuildConfig;
import com.chamayetu.chamayetu.auth.loginchama.LoginChamaActivity;
import com.chamayetu.chamayetu.dashboard.DashboardFrag;
import com.chamayetu.chamayetu.auth.loginuser.LoginActivity;
import com.chamayetu.chamayetu.statements.FullStatement;
import com.chamayetu.chamayetu.useraccount.UserAccountActivity;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.utils
 * Created by lusinabrian on 26/09/16.
 * Description: Constants class for the application
 * _SP_ files denote SharedPreferences
 */

public class Contract {
    public static final String TWITTER_KEY = BuildConfig.TWITTER_CONSUMER_KEY;
    public static final String TWITTER_CONSUMER_SECRET = BuildConfig.TWITTER_CONSUMER_KEY;
    public static final int REQUEST_INVITE = 1;

    /*class TAGS*/
    public static final String LOGINACT_TAG = LoginActivity.class.getSimpleName();
    public static final String DASHBOARDVIEW_TAG = DashboardFrag.class.getSimpleName();
    public static final String FULL_STATEMENT_TAG = FullStatement.class.getSimpleName();
    public static final String USERACCT_TAG = UserAccountActivity.class.getSimpleName();
    public static final String LOGINCHAMA_TAG = LoginChamaActivity.class.getSimpleName();

    public static final String NOTIFICATION_SP_FILE = "Notifications";

    /*stores the user's chama in a shared preferences file for app wide access*/
    public static final String CHAMA_SP_FILE = "CHAMA_SP_FILE";
    public static final String CHAMA_NAME_KEY = "CHAMA_NAME_KEY";
    public static final String USER_NAME_SP_PREF = "USERNAME_PREF";
    public static final String USER_NAME_SP_KEY_PREF = "USERNAME_PREF_KEY";

    public static final String NOTIFICATION_SP_KEY="NotificationCount";
    public static final int SHAREPREF_PRIVATE_MODE = 0;

    /*Keys for bundle when moving from Dashboard to FUll Statement*/
    public static final String FULL_STATEMENT_CHOICE = "FULL_STATEMENT_PERIOD_CHOICE";
    public static final String CHAMA_STATEMENT_TITLE = "CHAMA_STATEMENT_TITLE";
    public static final String USERNAME_BUNDLE_KEY = "USERNAME_KEY";
    public static final String USER_LOGGED_IN_CHAMA_AC = "USER_LOGGED_IN_CHAMA_AC";

    public static final int RC_SIGN_IN = 9001;
    public static final String ANONYMOUS = "anonymous";

    /**node in chama node to show the officials of the chama*/
    public static final String CHAMA_ROLES = "roles";
    public static final String CHAIR_PERSION = "chairperson";
    public static final String VICE_CHAIR = "viceChair";
    public static final String TREASURER = "treasurer";
    public static final String SECRETARY = "secretary";

    /*nodes*/
    public static final String CHAMA_NODE = "chamas";
    public static final String STATEMENT_NODE ="statements";
    public static final String MEMBERS_NODE = "members";
    public static final String USERS_NODE = "users";
    public static final String ACTIVITY_NODE = "activity";
    public static final String PROJECTS_NODE ="projects";
    public static final String FULL_STATEMENT_NODE = "fullStatement";
    public static final String CALENDER_NODE = "calender";

    //this is for the user node, to update the user's chamas
    public static final String CHAMA_GROUPS = "chamaGroups";
}

