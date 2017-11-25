using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class SoundEffectChooser : MonoBehaviour {
    private List<GameObject> musics;
    private int before = -1;
    // Use this for initialization
    void Start () {
        musics = new List<GameObject>();
        foreach (Transform t in transform)
        {
            musics.Add(t.gameObject);
        }
    }
	
	// Update is called once per frame
	void Update () {
    }

    public void PlaySoundEffect(int idx)
    {
        if (before == -1)
        {
            musics[idx].GetComponent<AudioSource>().Play();
        }
        else
        {
            musics[before].GetComponent<AudioSource>().Stop();
            musics[idx].GetComponent<AudioSource>().Play();
        }
        before = idx;
    }
}
