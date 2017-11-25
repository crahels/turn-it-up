using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PickSong : MonoBehaviour {

	// Use this for initialization
	void Start () {
		
	}
	
	// Update is called once per frame
	void Update () {
		
	}

    public void shakeItOff()
    {
        PlayerPrefs.SetInt("songs", 0);
        Application.LoadLevel("Menu");
    }

    public void letItGo()
    {
        PlayerPrefs.SetInt("songs", 1);
        Application.LoadLevel("Menu");
    }

    public void timber()
    {
        PlayerPrefs.SetInt("songs", 2);
        Application.LoadLevel("Menu");
    }
}
