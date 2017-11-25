using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Player : MonoBehaviour {
    public Animator anim;
    public Rigidbody rbody;
    
    private bool run;
    private float inputH;
    private float inputV;

	// Use this for initialization
	void Start () {
        anim = GetComponent<Animator>();
        rbody = GetComponent<Rigidbody>();
        
        run = false;
	}
	
	// Update is called once per frame
	void Update () {
        if (Input.GetKeyDown("left"))
        {
            transform.eulerAngles = new Vector3(0f, 90.0f, 0f);
        }
        if (Input.GetKeyDown("right"))
        {
            transform.eulerAngles = new Vector3(0f, -90.0f, 0f);
        }
        if (Input.GetKeyDown("up"))
        {
            transform.eulerAngles = new Vector3(0f, 180.0f, 0f);
        }
        if (Input.GetKeyDown("down"))
        {
            transform.eulerAngles = new Vector3(0f, 0.0f, 0f);
        }
        if (Input.GetMouseButtonDown(0))
        {
            anim.Play("damage", -1, 0f);
        }
        if (Input.GetKey(KeyCode.LeftShift))
        {
            run = true;
        }
        else
        {
            run = false;
        }
        if (Input.GetKey(KeyCode.Space))
        {
            anim.SetBool("jump", true);
        }
        else
        {
            anim.SetBool("jump", false);
        }

        inputH = Input.GetAxis("Horizontal");
        inputV = Input.GetAxis("Vertical");

        anim.SetFloat("inputH", inputH);
        anim.SetFloat("inputV", inputV);
        anim.SetBool("run", run);

        float moveX = inputH * 900f * -1 * Time.deltaTime;
        float moveZ = inputV * 700f * -1 * Time.deltaTime;
        if (run)
        {
            moveX *= 2.0f;
            moveZ *= 2.0f;
        }
        rbody.velocity = new Vector3(moveX, 0f, moveZ);
    }
}
