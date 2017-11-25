using Firebase;
using Firebase.Database;
using Firebase.Unity.Editor;
using UnityEngine;

public class YouWin : MonoBehaviour {
    private int dummy = 0;
    private int previous = 0;
    private string token;
    private int songs;
    private int score;
    private int login;
	// Use this for initialization
	void Start () {
        FirebaseApp.DefaultInstance.SetEditorDatabaseUrl("https://turnituptrilogy.firebaseio.com/");
        DatabaseReference reference = FirebaseDatabase.DefaultInstance.RootReference;
        
        login = PlayerPrefs.GetInt("login");
        token = PlayerPrefs.GetString("token");
        songs = PlayerPrefs.GetInt("songs");
        score = PlayerPrefs.GetInt("Score");
        if (login == 1)
        {
            FirebaseDatabase.DefaultInstance
                .GetReference("users")
                .GetValueAsync().ContinueWith(task =>
                {
                    if (task.IsFaulted)
                    {
                        // Handle the error...
                    }
                    else if (task.IsCompleted)
                    {
                        DataSnapshot snapshot = task.Result;
                        {
                            if (songs == 0)
                            {
                                previous = System.Convert.ToInt32(snapshot.Child(token).Child("highscore").Child("Shake It Off").Value.ToString());
                                if (score > previous)
                                {
                                    reference.Child("users").Child(token).Child("highscore").Child("Shake It Off").SetValueAsync(score);
                                }
                            }
                            else if (songs == 1)
                            {
                                previous = System.Convert.ToInt32(snapshot.Child(token).Child("highscore").Child("Let It Go").Value.ToString());
                                if (score > previous)
                                {
                                    reference.Child("users").Child(token).Child("highscore").Child("Let It Go").SetValueAsync(score);
                                }
                            }

                            else if (songs == 2)
                            {
                                previous = System.Convert.ToInt32(snapshot.Child(token).Child("highscore").Child("Timber").Value.ToString());
                                if (score > previous)
                                {
                                    reference.Child("users").Child(token).Child("highscore").Child("Timber").SetValueAsync(score);
                                }
                            }
                        }
                    }
                });
        }
    }
	
	// Update is called once per frame
	void Update () {
        dummy++;
        if (dummy == 100)
        {
            Application.LoadLevel("Player");
        }
	}
}
