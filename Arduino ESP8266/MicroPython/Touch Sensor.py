from machine import Pin
import time

# Set up the TTP223 touch sensor
touch_sensor = Pin(14, Pin.IN)
buzzer=Pin(5,Pin.OUT)

def main():
    print("Touch sensor ready. Touch to see the response.")
    while True:
        if touch_sensor.value() == 1:  # Detect touch
            buzzer.value(1)
        else:
            buzzer.value(0)
            
        #time.sleep(0.1)  # Debounce delay

# Run the main loop
main()
