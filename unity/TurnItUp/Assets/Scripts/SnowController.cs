using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class SnowController : MonoBehaviour {
    private ParticleSystem particle;
	// Use this for initialization
	void Start () {
        particle = GetComponent<ParticleSystem>();
        if (PlayerPrefs.GetInt("songs") == 1)
        {
            particle.enableEmission = true;
        }
        else 
        {
            particle.enableEmission = false;
        }
	}
	
	// Update is called once per frame
	void Update () {
		
	}
}
