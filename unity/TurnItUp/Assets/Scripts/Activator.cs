using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Activator : MonoBehaviour {

	public SpriteRenderer sr;
	public KeyCode key;
	public bool active = false;
	public GameObject note, gm;
	public Color old;
	public bool createMode = false;
	public GameObject n;

	void Awake() {
		sr = GetComponent<SpriteRenderer>();
	}

	void Start() {
		gm = GameObject.Find("GameManager");
		old = sr.color;
	}

	void Update() {
		if (createMode) {
			if (Input.GetKeyDown(key)) {
				Instantiate(n, transform.position, Quaternion.identity);
			}
		} else {
			if (Input.GetKeyDown(key)) {
				StartCoroutine(Pressed());
			}
			if (Input.GetKeyDown(key) && active) {
				Destroy (note);
				gm.GetComponent<GameManager>().AddStreak();
				AddScore();
				active = false;
			} else if (Input.GetKeyDown(key) && !active) {
				gm.GetComponent<GameManager>().ResetStreak();
			}
		}
	}

	void OnTriggerEnter2D(Collider2D col) {
		if (col.gameObject.tag == "WinNote") {
			gm.GetComponent<GameManager>().Win();
		}
		if (col.gameObject.tag == "Note") {
			active = true;
			note = col.gameObject;
		}
	}

	void OnTriggerExit2D(Collider2D col) {
		active = false;
	}

	void AddScore() {
		PlayerPrefs.SetInt("Score", PlayerPrefs.GetInt("Score") + gm.GetComponent<GameManager>().GetScore());
	}

	IEnumerator Pressed() {
		sr.color = new Color(128, 0, 128);
		yield return new WaitForSeconds(0.2f);
		sr.color = old;
	}

}
