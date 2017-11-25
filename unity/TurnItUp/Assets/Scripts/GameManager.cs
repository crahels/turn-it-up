using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class GameManager : MonoBehaviour {

	int multiplier = 0;
	int streak = 0;

	void Start () {
        PlayerPrefs.SetInt("Streak", 0);
        PlayerPrefs.SetInt("Multiplier", 0);
        PlayerPrefs.SetInt("Score", 0);
		PlayerPrefs.SetInt("LifeBar", 25);
	}

	void Update () {
		
	}

	void OnTriggerEnter2D(Collider2D col) {
		Destroy(col.gameObject);
		if (col.gameObject.tag == "Note") {
			ResetStreak();
		}
	}

	public void AddStreak() {
		if (PlayerPrefs.GetInt ("LifeBar") < 50) {
			PlayerPrefs.SetInt ("LifeBar", PlayerPrefs.GetInt ("LifeBar") + 1);
		}
		streak++;
		multiplier++;
		UpdateGUI();
	}

	public void ResetStreak() {
		PlayerPrefs.SetInt("LifeBar", PlayerPrefs.GetInt("LifeBar") - 2);
		if (PlayerPrefs.GetInt ("LifeBar") < 0) {
			Lose();
		}
		streak = 0;
		multiplier = 0;
		UpdateGUI();
	}

	public void Lose() {
        Application.LoadLevel("GameOver");
    }

	public void Win() {
        Application.LoadLevel("YouWin");
    }

	void UpdateGUI() {
		PlayerPrefs.SetInt("Streak", streak);
		PlayerPrefs.SetInt("Multiplier", multiplier);
	}

	public int GetScore() {
		return 50 * multiplier;
	}

}
