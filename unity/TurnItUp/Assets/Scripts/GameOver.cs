using Firebase;
using Firebase.Database;
using Firebase.Unity.Editor;
using UnityEngine;

public class GameOver : MonoBehaviour
{
    private int dummy = 0;
    // Use this for initialization
    void Start()
    {
    }

    // Update is called once per frame
    void Update()
    {
        dummy++;
        if (dummy == 100)
        {
            Application.LoadLevel("SelectSong");
        }
    }
}

