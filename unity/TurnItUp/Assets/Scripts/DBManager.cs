using System;
using Firebase;
using Firebase.Auth;
using Firebase.Unity.Editor;
using UnityEngine;
using UnityEngine.UI;
using System.Collections.Generic;
using Firebase.Database;

public class DBManager : MonoBehaviour
{
    public object FirebaseStorage { get; private set; }
    public GameObject username;
    protected FirebaseApp app;
    protected FirebaseAuth auth;
    private FirebaseAuth otherAuth;
    private DataSnapshot snapshot;
    private DataSnapshot user;
    private String token;
    private String nama;
    private DatabaseReference reference;
    void Start()
    {
        FirebaseApp.DefaultInstance.SetEditorDatabaseUrl("https://turnituptrilogy.firebaseio.com/");
        reference = FirebaseDatabase.DefaultInstance.RootReference;

        username = GameObject.FindGameObjectWithTag("Guest");
        FirebaseDatabase.DefaultInstance
            .GetReference("login")
            .ValueChanged += HandleValueChanged;
    }

    void HandleValueChanged(object sender, ValueChangedEventArgs args) 
    {
        if (args.DatabaseError != null)
        {
            Debug.LogError(args.DatabaseError.Message);
            return;
        }
        token = args.Snapshot.Value.ToString();
        Debug.Log(token + " token ");
        PlayerPrefs.SetString("token",token);
        if (token.Equals("none"))
        {
            PlayerPrefs.SetInt("login", 0);
            username.GetComponent<Text>().text = "Welcome, Guest";
        }
        else
        {
            PlayerPrefs.SetInt("login", 1);
            FirebaseDatabase.DefaultInstance
               .GetReference("users")
               .GetValueAsync().ContinueWith(task => {
                   if (task.IsFaulted)
                   {
                       // Handle the error
                   }
                   else if (task.IsCompleted)
                   {
                       user = task.Result;
                       nama = (String)user.Child(token).Child("username").Value.ToString();
                       Debug.Log(nama);
                       Debug.Log(user.Child(token).Child("username").Value);
                       username.GetComponent<Text>().text = "Welcome, " + nama;
                   }
               });
        }
    }

    void Update()
    {
    }
}
