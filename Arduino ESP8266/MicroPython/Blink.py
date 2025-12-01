from machine import Pin
import time

led = Pin(2, Pin.OUT)  # Pin 2 is usually the onboard LED

while True:
    led.value(1)  # Turn LED on
    time.sleep(1)
    led.value(0)  # Turn LED off
    time.sleep(1)
