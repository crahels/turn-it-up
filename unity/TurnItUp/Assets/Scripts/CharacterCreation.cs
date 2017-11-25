using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CharacterCreation : MonoBehaviour {
    private List<GameObject> models;
    // default index of the model
    private int selectionIndex = 0;
	// Use this for initialization
	private void Start () {
        models = new List<GameObject>();
        foreach (Transform t in transform)
        {
            models.Add(t.gameObject);
            t.gameObject.SetActive(false);
        }
        models[selectionIndex].SetActive(true);
	}
	
	// Update is called once per frame
	void Update () {
        models[selectionIndex].transform.Rotate(new Vector3(0.0f, Input.GetAxis("Horizontal") * 10.0f, 0.0f));
        string a = PlayerPrefs.GetString("button");
        if (a.Equals("1"))
        {
            Select(0);
        } else if (a.Equals("2"))
        {
            Select(1);
        } else if (a.Equals("3"))
        {
            Select(2);
        }
        else if (a.Equals("4"))
        {
            Select(3);
        }
        //PlayerPrefs.SetString("button","0");
    }

    public void MENU_ACTION_GotoPage()
    {
        PlayerPrefs.SetInt("character",selectionIndex);
        PlayerPrefs.SetString("char_string", models[selectionIndex].tag);
        if (PlayerPrefs.GetInt("songs") == 0)
        {
            Application.LoadLevel("song1");
        }
        else if (PlayerPrefs.GetInt("songs") == 1)
        {
            Application.LoadLevel("song3");
        }
        else
        {
            Application.LoadLevel("song2");
        }
    }

    public void Select(int index)
    {
        if (index == selectionIndex)
        {
            return;
        }
        if (index < 0 || index >= models.Count)
        {
            return;
        }
        models[selectionIndex].SetActive(false);
        models[selectionIndex].transform.SetPositionAndRotation(new Vector3(0.0f,0.0f,0.0f),new Quaternion (0.0f,0.0f,0.0f,0.0f));
        selectionIndex = index;
        models[selectionIndex].SetActive(true);
    }
}