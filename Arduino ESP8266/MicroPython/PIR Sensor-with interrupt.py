from machine import Pin
import time

# Define the PIR sensor pin
pir = Pin(5, Pin.IN) #GPIO14 (D5)
led = Pin(2, Pin.OUT) # 
buzzer = Pin(4, Pin.OUT) #D2

# Define the interrupt service routine (ISR)
def motion_detected(pin):
    print("Motion detected!")
    led.value(1)
    buzzer.value(1)
    time.sleep(1)
    buzzer.value(0)
    # Add additional actions here (e.g., turn on light, send notification)

# Attach the ISR to the PIR sensor pin
pir.irq(trigger=Pin.IRQ_RISING, handler=motion_detected)

# Main loop
while True:
    led.value(0)
    time.sleep(0.1)
