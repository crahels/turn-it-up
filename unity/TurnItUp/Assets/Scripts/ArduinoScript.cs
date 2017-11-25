using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System.IO.Ports;
using System;

public class ArduinoScript : MonoBehaviour {
    SerialPort seri = new SerialPort("COM5", 9600);

    private void Start()
    {
        seri.Open();
        StartCoroutine(ReadDataFromSerialPort()); 
    }

    IEnumerator ReadDataFromSerialPort()
    {
        while (true)
        {
            string values = seri.ReadLine();
            PlayerPrefs.SetString("button", values);
            yield return new WaitForSeconds(.2f);//arduino delay
            //yield return true;
        }
    }

    private void OnApplicationQuit()
    {
        seri.Close();
    }
}
