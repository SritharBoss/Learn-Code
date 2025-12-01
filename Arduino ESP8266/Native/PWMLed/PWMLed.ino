#include <WiFiManager.h>          //https://github.com/tzapu/WiFiManager WiFi Configuration Magic

const int ledPin = D4; 
WiFiManager wifiManager;
void setup() {
  wifiManager.autoConnect("Srithar Arduino Nodemcu")
}

void loop() {
  // increase the LED brightness
  for(int dutyCycle = 0; dutyCycle < 255; dutyCycle++){   
    // changing the LED brightness with PWM
    analogWrite(ledPin, dutyCycle);
    delay(1);
  }

  // decrease the LED brightness
  for(int dutyCycle = 255; dutyCycle > 0; dutyCycle--){
    // changing the LED brightness with PWM
    analogWrite(ledPin, dutyCycle);
    delay(1);
  }
}