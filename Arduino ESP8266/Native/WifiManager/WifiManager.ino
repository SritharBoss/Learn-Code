#include <Arduino.h>
#include <WiFiManager.h>  //https://github.com/tzapu/WiFiManager WiFi Configuration Magic
#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>
#include <WiFiClient.h>

const int ledPin = D4;

WiFiManager wifiManager;
WiFiClient client;
HTTPClient http;

void setup() {
  pinMode(D4, OUTPUT);
  pinMode(D1, INPUT);
  Serial.begin(115200);
  Serial.println("Connecting to Previous network");
  wifiManager.autoConnect("Srithar Arduino Nodemcu");
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("Connected.");
  Serial.print("Local IP :: ");
  Serial.println(WiFi.localIP());

  sendMessage();
  
}

void loop() {
  if (Serial.available() > 0) {
    char x = Serial.read();
    Serial.println(x == '1');
    if (x == '1') {
      digitalWrite(D4, LOW);
    } else {
      digitalWrite(D4, HIGH);
    }
    while(Serial.available()){
      char y = Serial.read();
      Serial.print(y);
      delay(100);
    }
    Serial.println();
  }
}

void sendMessage(){
  if (WiFi.status() == WL_CONNECTED) {
    const char *url = "http://api.callmebot.com/text.php?source=web&user=SritharBoss&text=Wifi%20has%20Connected%20to%20FTTH.";

    http.begin(client, url);

    // If you need Node-RED/server authentication, insert user and password below
    //http.setAuthorization("REPLACE_WITH_SERVER_USERNAME", "REPLACE_WITH_SERVER_PASSWORD");

    // Send HTTP GET request
    int httpResponseCode = http.GET();

    if (httpResponseCode > 0) {
      if (httpResponseCode == 200) {
        Serial.println("Message Sent");
      } else {
        Serial.print("HTTP Response code: ");
        Serial.println(httpResponseCode);
        String payload = http.getString();
        Serial.println(payload);
      }
    } else {
      Serial.print("Error code: ");
      Serial.println(httpResponseCode);
    }
    // Free resources
    http.end();
  } else {
    Serial.println("WiFi Disconnected");
  }
}