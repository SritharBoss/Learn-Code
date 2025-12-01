from machine import Pin
import urequests
import utime

pir_sensor = Pin(5, Pin.IN)
led = Pin(2, Pin.OUT)
buzzer = Pin(4, Pin.OUT) #D2

print("PIR Motion Sensor Test")
utime.sleep(2)  # Warm-up time for the sensor

def sendMessage():
    response = urequests.get("http://api.callmebot.com/text.php?user=@SritharBoss&text=MotionDetected")
    print("Status Code for Request :: {}".format(response.status_code))          # Print response body
    response.close()

while True:
    if pir_sensor.value() == 1:
        print("Motion Detected!")
        led.value(1)
        buzzer.value(1)
        utime.sleep(1)
        sendMessage()
        buzzer.value(0)
        utime.sleep(5)
    else:
        led.value(0)
        buzzer.value(0)
        print("No motion")
    
    utime.sleep(0.5)
