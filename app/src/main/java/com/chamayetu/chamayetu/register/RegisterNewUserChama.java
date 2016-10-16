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
                if(dataSnapshot.child(Contract.CHAMA_NODE).hasChild(chamaNameKey)){
                    registerChamaListener.onChamaNameError();
                    registerChamaListener.chamaNameExistsError("Chama already exists", TastyToast.ERROR);
                } else {
                    /*create the new chama, statement, projects*/
                    Map<String, Object> newChamaNode = new HashMap<>();
                    Map<String, Object> newStatementNode = new HashMap<>();
                    Map<String, Object> newProjectNode = new HashMap<>();
                    Map<String, Object> newActivityNode = new HashMap<>();
                    Map<String, Object> newMembersNode = new HashMap<>();
                    Map<String, Object> newChamaGroups = new HashMap<>();

                    /**todo: get real date of chama creation*/
                    statementPojo = new StatementPojo(currentDate,currentDate, chamaName + " Statement", 0, 0, 0);
                    Map<String, Boolean> newMember = new HashMap<>();
                    newMember.put(username, true);

                    /*add the projects model to a map to later add the map to the node*/
                    projects = new Projects("","");
                    Map<String, Projects> projectsMap = new HashMap<>();
                    projectsMap.put("proj1",projects);

                    /*Create another map for the Activity Node to properly nest the activities*/
                    activityModel = new ActivityModel("","","",0);
                    Map<String, Object> activityModelMap = new HashMap<>();
                    activityModelMap.put("a1", activityModel);

                    newStatementNode.put(chamaNameKey, statementPojo);
                    newChamaNode.put(chamaNameKey, newChama);
                    newProjectNode.put(chamaNameKey, projectsMap);
                    newActivityNode.put(chamaNameKey, activityModelMap);
                    newMembersNode.put(chamaNameKey,newMember);

                    //update all the nodes for the new chama
                    mDatabaseReference.child(Contract.STATEMENT_NODE).updateChildren(newStatementNode);
                    mDatabaseReference.child(Contract.CHAMA_NODE).updateChildren(newChamaNode);
                    mDatabaseReference.child(Contract.PROJECTS_NODE).updateChildren(newProjectNode);
                    mDatabaseReference.child(Contract.ACTIVITY_NODE).updateChildren(newActivityNode);
                    mDatabaseReference.child(Contract.MEMBERS_NODE).updateChildren(newMembersNode);

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
