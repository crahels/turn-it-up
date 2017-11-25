using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class PPText : MonoBehaviour {

	public string name;

	void Update() {
		switch (name) {
			case "Score":
				UpdateScore();
				break;
			case "Streak":
				UpdateMultiplier();
				break;
		}

	}

	void UpdateScore() {
		string score = PlayerPrefs.GetInt(name) + "";
		string zero = "";
		for (int i = 0; i < 7 - score.Length; i++) {
			zero += "0";
		}
		GetComponent<Text>().text = zero + score;
	}

	void UpdateMultiplier() {
		string multiplier = PlayerPrefs.GetInt(name) + "";
		GetComponent<Text>().text = multiplier + "x";
	}

}
