using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ChangingLight : MonoBehaviour {
    private int index = 0;
    private int dummy = 0;
    private float[] red;
    private float[] green;
    private float[] blue;
    private Light light;

	// Use this for initialization
	void Start () {
        red = new float[5];
        green = new float[5];
        blue = new float[5];

        red[0] = 86.0f/255.0f;
        green[0] = 167.0f/255.0f;
        blue[0] = 164.0f/255.0f;

        red[1] = 167.0f/255.0f;
        green[1] = 165.0f/255.0f;
        blue[1] = 86.0f/255.0f;

        red[2] = 219.0f/255.0f;
        green[2] = 118.0f/255.0f;
        blue[2] = 116.0f/255.0f;

        red[3] = 193.0f/255.0f;
        green[3] = 88.0f/255.0f;
        blue[3] = 182.0f/255.0f;

        red[4] = 138.0f/255.0f;
        green[4] = 223.0f/255.0f;
        blue[4] = 116.0f/255.0f;

        light = GetComponent<Light>();
    }
	
	// Update is called once per frame
	void Update () {
        dummy++;
        if (dummy % 20 == 0)
        {
            Color prettyColor = new Color(red[index], green[index], blue[index]);
            light.color = prettyColor;
            incIndex();
        }
    }

    private void incIndex()
    {
        index++;
        if (index >= red.Length)
        {
            index = 0;
        }
    }
}