using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Note : MonoBehaviour {

	public Rigidbody2D rb;
	public float speed;

	void Awake() {
		rb = GetComponent<Rigidbody2D>();
	}

	void Start () {
		rb.velocity = new Vector2(0, -speed);
	}

	void Update () {
		
	}
}
