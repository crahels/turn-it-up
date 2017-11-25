using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ModelChooser : MonoBehaviour {
    private List<GameObject> models;
    // Use this for initialization
    void Start () {
        models = new List<GameObject>();
        foreach (Transform t in transform)
        {
            models.Add(t.gameObject);
            t.gameObject.SetActive(false);
        }
        models[PlayerPrefs.GetInt("character")].SetActive(true);
    }
	
    public GameObject GetModel()
    {
        return models[PlayerPrefs.GetInt("character")];
    }
	// Update is called once per frame
	void Update () {
    }
}
