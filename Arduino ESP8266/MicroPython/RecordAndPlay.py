from machine import Pin, SPI
import time

# Define GPIO pins for ISD1820
record_pin = Pin(14, Pin.OUT)  # D5 Record pin
play_pin = Pin(13, Pin.OUT)    # D7 Play pin
data_pin = Pin(12, Pin.OUT)    # D6 Data pin

# Initialize ISD1820
record_pin.off()
play_pin.off()
data_pin.off()

# Record Function
def record_message(filename="message.wav"):
    print("Recording...")
    record_pin.on()
    time.sleep(10)  # Record for 10 seconds
    record_pin.off()
    print("Recording finished.")

# Play Function
def play_message():
    print("Playing message...")
    play_pin.on()
    time.sleep(10)  # Play for 10 seconds
    play_pin.off()
    print("Playback finished.")

# Main loop
while True:
    record_message()
    time.sleep(2)  # Wait before playing the recorded message
    play_message()
    time.sleep(5)
