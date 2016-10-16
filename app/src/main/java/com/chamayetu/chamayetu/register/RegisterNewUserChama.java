package com.chamayetu.chamayetu.register;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.chamayetu.chamayetu.models.ActivityModel;
import com.chamayetu.chamayetu.models.ChamaPojo;
import com.chamayetu.chamayetu.models.MembersModel;
import com.chamayetu.chamayetu.models.Projects;
import com.chamayetu.chamayetu.models.StatementPojo;
import com.chamayetu.chamayetu.models.UserPojo;
import com.chamayetu.chamayetu.utils.Contract;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.sdsmdg.tastytoast.TastyToast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.chamayetu.chamayetu.utils.Contract.ACTIVITY_NODE;
import static com.chamayetu.chamayetu.utils.Contract.CHAMA_GROUPS;
import static com.chamayetu.chamayetu.utils.Contract.CHAMA_NODE;
import static com.chamayetu.chamayetu.utils.Contract.MEMBERS_NODE;
import static com.chamayetu.chamayetu.utils.Contract.PROJECTS_NODE;
import static com.chamayetu.chamayetu.utils.Contract.STATEMENT_NODE;
import static com.chamayetu.chamayetu.utils.Contract.USERS_NODE;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.register
 * Created by lusinabrian on 15/10/16.
 * Description: Registers new users and chamas to all nodes
 */

public class RegisterNewUserChama {
    public static final String TAG  = RegisterUserActivity.REGISTERACT_TAG;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseReference;
    private RegisterInteractor.OnRegisterNewChamaFinishedListener registerChamaListener;
    private ChamaPojo chamaPojo;
    private UserPojo userPojo;
    private StatementPojo statementPojo;
    private ActivityModel activityModel;
    private Projects projects;
    private Context context;

    public RegisterNewUserChama(){}

    /**constructor to initialize registration of new Chama*/
    public RegisterNewUserChama(Context context, FirebaseAuth mAuth, DatabaseReference mDatabaseReference, RegisterInteractor.OnRegisterNewChamaFinishedListener registerChamaListener, ChamaPojo chamaPojo){
        this.mAuth = mAuth;
        this.mDatabaseReference = mDatabaseReference;
        this.registerChamaListener = registerChamaListener;
        this.chamaPojo = chamaPojo;
        this.context = context;
    }

    /**registers the new chama to the chama node
     * Creates nodes for the new chama at the specified nodes, activity node, statement, projects, milestones, members*/
    public void newChama(String chamaName, ChamaPojo newChama){
        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("MMMM-dd-yyyy", Locale.ENGLISH);
        String currentDate = df.format(c.getTime());

        String chamaNameKey = chamaName.toLowerCase();

        /*access the username of the current registering user*/
        SharedPreferences mUsername = context.getSharedPreferences("CurrentUser", Contract.SHAREPREF_PRIVATE_MODE);

        String username = mUsername.getString("CurrentUserName", "missing");

        /*check if the chama is already in existence*/
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(CHAMA_NODE).hasChild(chamaNameKey)){
                    registerChamaListener.onChamaNameError();
                    registerChamaListener.chamaNameExistsError("Chama already exists", TastyToast.ERROR);
                } else {

                    /*create the new chama, statement, projects
                    * activity, members, chama groups and roles
                    * All these are created with default values*/
                    Map<String, Object> newChamaNode = new HashMap<>();
                    Map<String, Object> newStatementNode = new HashMap<>();
                    Map<String, Object> newProjectNode = new HashMap<>();
                    Map<String, Object> newActivityNode = new HashMap<>();
                    Map<String, Object> newMembersNode = new HashMap<>();
                    Map<String, Object> newChamaGroups = new HashMap<>();
                    Map<String, Object> chamaRoles = new HashMap<>();

                    /**todo: get real date of chama creation*/
                    statementPojo = new StatementPojo(currentDate,currentDate, chamaName + " Statement", 0, 0, 0);
                    /*update member status of the user for the member's node*/
                    Map<String, Boolean> newMember = new HashMap<>();
                    newMember.put(username, true);

                    /*update chama groups for the user in the chamaGroups node in user node*/
                    Map<String, Boolean> newChamaGroupMap = new HashMap<>();
                    newChamaGroupMap.put(chamaNameKey, true);

                    /*add the projects model to a map to later add the map to the node*/
                    projects = new Projects("","");
                    Map<String, Projects> projectsMap = new HashMap<>();
                    projectsMap.put("proj1",projects);

                    /*Create another map for the Activity Node to properly nest the activities*/
                    activityModel = new ActivityModel("","","",0);
                    Map<String, Object> activityModelMap = new HashMap<>();
                    activityModelMap.put("a1", activityModel);

                    /*update the chama roles, with initial value being for the chairperson*/
                    

                    /*put values in the maps to later update the respective nodes*/
                    newStatementNode.put(chamaNameKey, statementPojo);
                    newChamaNode.put(chamaNameKey, newChama);
                    newProjectNode.put(chamaNameKey, projectsMap);
                    newActivityNode.put(chamaNameKey, activityModelMap);
                    newMembersNode.put(chamaNameKey,newMember);
                    newChamaGroups.put(CHAMA_GROUPS, newChamaGroupMap);


                    //update all the nodes for the new chama with default values
                    mDatabaseReference.child(STATEMENT_NODE).updateChildren(newStatementNode);
                    mDatabaseReference.child(CHAMA_NODE).updateChildren(newChamaNode);
                    mDatabaseReference.child(PROJECTS_NODE).updateChildren(newProjectNode);
                    mDatabaseReference.child(ACTIVITY_NODE).updateChildren(newActivityNode);
                    mDatabaseReference.child(MEMBERS_NODE).updateChildren(newMembersNode);
                    mDatabaseReference.child(USERS_NODE).child(username).updateChildren(newChamaGroups);

                    /*proceed to next step*/
                    registerChamaListener.onChamaSuccess();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                registerChamaListener.onTaskError("Error encountered, please retry", TastyToast.ERROR);
                Log.d(TAG+"DBError", databaseError.getMessage());
            }
        });
    }
}
