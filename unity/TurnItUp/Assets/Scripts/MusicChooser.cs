using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class MusicChooser : MonoBehaviour {
    private List<GameObject> musics;
	// Use this for initialization
	void Start () {
        // 0 : Shake It Off
        // 1 : Let It Go
        // 2 : Timber
        musics = new List<GameObject>();
        foreach (Transform t in transform)
        {
            musics.Add(t.gameObject);
        }
        musics[PlayerPrefs.GetInt("songs")].GetComponent<AudioSource>().Play();
    }
	
	// Update is called once per frame
	void Update ()
    {
        
        if (!musics[PlayerPrefs.GetInt("songs")].GetComponent<AudioSource>().isPlaying)
        {
            Application.LoadLevel("SelectSong");
        }
    }	
}
