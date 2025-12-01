import network
import ntptime
import utime
from machine import Pin
import tm1637

# 4-digit 8-segment display setup
clk = Pin(5)  # GPIO5 (D1)
dio = Pin(4)  # GPIO4 (D2)
display = tm1637.TM1637(clk=clk, dio=dio)
offset_seconds = 5 * 3600 + 30 * 60 # For IST

display.brightness(5)

# Synchronize time with NTP
def sync_time():
    print("Synchronizing time with NTP...")
    ntptime.settime()  # Fetch time from NTP and set RTC
    print("Time synchronized!")

# Display current time on the 4-digit display
def display_time():
    while True:
        # Get the current local time (adjust for your timezone if needed)
        # Adjust for IST (UTC+5:30)
        now = utime.localtime(utime.time() + offset_seconds)
        hour = now[3]
        minute = now[4]
        # Display the time in HH:MM format
        if utime.time() % 2 == 0:
            display.numbers(hour, minute, colon=True)
        else:
            display.numbers(hour, minute, colon=False)
        
        utime.sleep(2)
        
        display.scroll("HEY YOU",delay=500)

# Main function
def main():
    sync_time()
    display_time()

# Run the program
main()
