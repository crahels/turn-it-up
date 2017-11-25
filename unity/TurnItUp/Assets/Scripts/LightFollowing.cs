using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class LightFollowing : MonoBehaviour {
    private GameObject player;
    private Vector3 offset;
    private Light light;
    // Use this for initialization
    void Start () {
        player = GameObject.FindWithTag(PlayerPrefs.GetString("char_string"));
        offset = transform.position - player.transform.position;

        light = GetComponent<Light>();
        float red_color = PlayerPrefs.GetFloat("red");
        float green_color = PlayerPrefs.GetFloat("green");
        float blue_color = PlayerPrefs.GetFloat("blue");
        Color prettyColor = new Color(red_color, green_color, blue_color);
        light.color = prettyColor;
    }
	
	// Update is called once per frame
	void Update () {
        transform.position = player.transform.position + offset;
    }
}
