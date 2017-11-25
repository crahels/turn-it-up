using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class DanceSongs : MonoBehaviour {
    private List<GameObject> dancers;
    // Use this for initialization
    void Start () {
        int song = PlayerPrefs.GetInt("songs") + 1;
        dancers = new List<GameObject>();
        foreach (Transform t in transform)
        {
            dancers.Add(t.gameObject);
        }
        for (int i = 0; i < dancers.Count; i++)
        {
            Animator animator = dancers[i].GetComponent<Animator>();
            int temp = i + 1;
            print(song);
            animator.runtimeAnimatorController = Resources.Load("Dancing" + temp + "_song" + song) as RuntimeAnimatorController;
        }
    }
	
	// Update is called once per frame
	void Update () {
		
	}
}
