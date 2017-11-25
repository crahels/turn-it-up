#include <LiquidCrystal.h>
#include <SoftwareSerial.h>
#define trigPin 7
#define echoPin 6
#define led 9
#define led2 10
#define data_pin A0
#define b_blue A2
#define b_green A1
#define b_red A4
#define b_yellow A3

const int rs = 12, en = 11, d4 = 5, d5 = 4, d6 = 3, d7 = 2;
const int clock_pin = 13;
const int latch_pin = 8;
String avatar = "";

const byte digit_pattern[10] = {
  B0000,  // 0
  B0001,  // 1
  B0010,  // 2
  B0011,  // 3
  B0100,  // 4
  B0101,  // 5
  B0110,  // 6
  B0111,  // 7
  B1000,  // 8
  B1001,  // 9
};

unsigned int counter = 0;
int brightness = 0;
int fadeAmount = 20;
long duration, distance, cm;
LiquidCrystal lcd(rs, en, d4, d5, d6, d7);
SoftwareSerial BTSerial(0, 1);

void setup() {
  BTSerial.begin (9600);
  lcd.begin(16, 2);

  pinMode(data_pin, OUTPUT);
  pinMode(clock_pin, OUTPUT);
  pinMode(latch_pin, OUTPUT); 
  pinMode(echoPin, INPUT);
  pinMode(trigPin, OUTPUT);
  pinMode(led, OUTPUT);
  pinMode(led2, OUTPUT);
}

void loop() {
  if (digitalRead(b_red)) {
    BTSerial.println(1);
    avatar = "Roselina";
  } else if (digitalRead(b_yellow)) {
    BTSerial.println(2);
    avatar = "Dayu";
  } else if (digitalRead(b_green)) {
    BTSerial.println(3);
    avatar = "Rachel";
  } else if (digitalRead(b_blue)) {
    BTSerial.println(4);
    avatar = "Jessica";
  } else {
    BTSerial.println(0);
  }

  digitalWrite(trigPin, LOW);
  delayMicroseconds(2); 
  digitalWrite(trigPin, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPin, LOW);
  
  duration = pulseIn(echoPin, HIGH);
  distance = microsecondsToCentimeters(duration);
  
  lcd.clear();
  lcd.setCursor(0,0);
  
  if (distance < 30) {
    lcd.print("Welcome ");
    lcd.print(avatar);
  }
  else {
    lcd.print("Come closer!");
  }

  lcd.setCursor(0,1);
  
  if (distance >= 99){
    lcd.print("Out of range");
    distance = 99;
  } else if (distance <= 0) {
    lcd.print("Out of range");
    distance = 0;
  } else {
    lcd.print("Dist: ");
    lcd.print(distance);
    lcd.print(" cm");
  }

  update_one_digit(distance % 10, distance / 10);

  analogWrite(led,brightness);
  analogWrite(led2,(240 - brightness));
  brightness = brightness + fadeAmount;
  if (brightness <= 0 || brightness >= 240) {
    fadeAmount = -fadeAmount;
  }
  
  delay(170);
}

long microsecondsToCentimeters(long microseconds) {
  return microseconds / 29 / 2;
}

void update_one_digit(int data1, int data2) {
  int i;
  byte pattern;
  
  pattern = digit_pattern[data1] ^ digit_pattern[data2] << 4;

  digitalWrite(latch_pin, LOW);
  
  shiftOut(data_pin, clock_pin, LSBFIRST, pattern);
  
  digitalWrite(latch_pin, HIGH);
}