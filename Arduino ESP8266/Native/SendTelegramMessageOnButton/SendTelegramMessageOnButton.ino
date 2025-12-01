#include <Arduino.h>
#include <WiFiManager.h>
#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>
#include <WiFiClient.h>
#include <UrlEncode.h>

WiFiClient client;
HTTPClient http;

const char* ssid = "FTTH";
const char* password = "14789632";
boolean isEnabled = false;
void setup() {
  pinMode(D3, OUTPUT);
  pinMode(D2, INPUT);
  Serial.begin(115200);
  WiFiManager wifiManager;
  Serial.println("Connecting to Previous network");
  wifiManager.autoConnect("Srithar Arduino Nodemcu");
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("Connected to " + WiFi.SSID());
  Serial.print("Local IP :: ");
  Serial.println(WiFi.localIP());
  Serial.print("Gateway IP :: ");
  Serial.println(WiFi.gatewayIP());
  Serial.print("RSSI :: ");
  Serial.println(WiFi.RSSI());
  Serial.print("HostName :: ");
  Serial.println(WiFi.getHostname());
  Serial.print("Channel :: ");
  Serial.println(WiFi.channel());
  Serial.print("MAC Address :: ");
  Serial.println(WiFi.macAddress());
  digitalWrite(D3, HIGH);
}

void loop() {

  if (digitalRead(D2) == HIGH) {
    Serial.println("Button Pushed");
    isEnabled = !isEnabled;
  }

  if (isEnabled) {
    Serial.println("Button Enabled");
    digitalWrite(D3, LOW);
    sendMessage();
    Serial.println("Waiting for 10 Seconds");
    delay(10000);
    isEnabled = !isEnabled;
    digitalWrite(D3, HIGH);
  }
}

void sendMessage() {
  if (WiFi.status() == WL_CONNECTED) {

    String url = "http://api.callmebot.com/text.php?user=@SritharBoss&text=" + urlEncode("Connected to :: " + String(WiFi.SSID())) + "%0A" + urlEncode("Local IP :: " + String(WiFi.localIP().toString())) + "%0A" + urlEncode("RSSI :: " + String(WiFi.RSSI())) + "%0A" + urlEncode("Channel :: " + String(WiFi.channel())) + "%0A" + urlEncode("MAC :: " + String(WiFi.macAddress()));

    http.begin(client, url);
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
    http.end();
  } else {
    Serial.println("WiFi Disconnected");
  }
}