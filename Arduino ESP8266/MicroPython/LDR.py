from machine import ADC, Pin
import time

# Set up ADC pin for LDR
ldr = ADC(0)  # GPIO34 is ADC on ESP8266
buzzer=Pin(5,Pin.OUT)
#ldr.atten(ADC.ATTN_11DB)  # Configure the ADC range (0-3.3V)

def read_light_level():
    # Read raw ADC value (0-4095)
    value = ldr.read()
    # Convert to voltage (Optional)
    voltage = value * 3.3 / 4095
    return value, voltage

def main():
    print("Reading light intensity...")
    while True:
        light_level, light_voltage = read_light_level()
        print(f"Light Level: {light_level}, Voltage: {light_voltage:.2f}V")
        if light_level<10:
            buzzer.value(1)
        else:
            buzzer.value(0)
        time.sleep(1)

# Run the main loop
main()
