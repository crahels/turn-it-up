using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class SpotLightController : MonoBehaviour {
    private Light light;
    private float[] red;
    private float[] green;
    private float[] blue;
    // Use this for initialization
    void Start () {
        light = GetComponent<Light>();
        red = new float[4];
        green = new float[4];
        blue = new float[4];

        red[0] = 86.0f / 255.0f;
        green[0] = 167.0f / 255.0f;
        blue[0] = 164.0f / 255.0f;

        red[1] = 219.0f / 255.0f;
        green[1] = 118.0f / 255.0f;
        blue[1] = 116.0f / 255.0f;

        red[2] = 193.0f / 255.0f;
        green[2] = 88.0f / 255.0f;
        blue[2] = 182.0f / 255.0f;

        red[3] = 138.0f / 255.0f;
        green[3] = 223.0f / 255.0f;
        blue[3] = 116.0f / 255.0f;

        Color prettyColor = new Color(red[0], green[0], blue[0]);
        light.color = prettyColor;
        PlayerPrefs.SetFloat("red", red[0]);
        PlayerPrefs.SetFloat("green", green[0]);
        PlayerPrefs.SetFloat("blue", blue[0]);
    }
	
	// Update is called once per frame
	void Update () {	
	}

    public void ChangeColor(int idx)
    {
        Color prettyColor = new Color(red[idx], green[idx], blue[idx]);
        light.color = prettyColor;
        PlayerPrefs.SetFloat("red", red[idx]);
        PlayerPrefs.SetFloat("green", green[idx]);
        PlayerPrefs.SetFloat("blue", blue[idx]);
    }
}
