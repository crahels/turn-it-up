using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class LifeBar : MonoBehaviour {

	float lb;
	GameObject meter;

	void Start () {
		meter = transform.Find("Meter").gameObject;
	}

	void Update () {
		lb = PlayerPrefs.GetInt("LifeBar");
		meter.transform.localPosition = new Vector3(1 + (lb - 25)/25, 0, 0);
	}
}
