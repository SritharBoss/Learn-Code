import os
import sys
import time
from datetime import datetime, timedelta, timezone
import pytz
from itertools import cycle

Str = "The-quick brown fox jumps over the lazy-dog"
Int = -2
Float = 3.0
Bool = True
Comp = 1 + 3j
List = ["Hello", "World", 3, 5]  # list
List1 = [x for x in List if isinstance(x, str)]
Tuple = ("Hello", "World")  # tuple - cannot modify
Dict = {1: "Hello", 2: "World"}  # Dict - like map in java
Set = {"Hello", "World", "World"}  # set - only contain unique
Cycle = cycle("SritharBoss")
pattern = r"([sS]rithar)"

asia = pytz.timezone("Asia/Kolkata")
new_tz = pytz.timezone('America/New_York')
UP = "\033[2A"
CLR = "\033[0K"
GREEN = "\033[32m"
RESET = "\033[0m"
print("\x09")
for i in range(100, 0, -1):
    Time = datetime.now()
    print(f"{UP}{GREEN}{asia.localize(Time).strftime('%Y-%m-%d %H:%M:%S')}{RESET}{CLR}\n", end="", flush=True)
    print(f"{asia.localize(Time).astimezone(new_tz).strftime('%Y-%m-%d %H:%M:%S')}{CLR}\n", end="", flush=True)
    time.sleep(1)

# print(subprocess.run(args="ping 192.168.1.150", shell=True, capture_output=True, text=True).stdout)

# def count(v):
#     for i in range(v):
#         yield i


# for j in count(100000000):
#     print(j)

# print(subprocess.run(args="ls -alh", shell=True, capture_output=True, text=True).stdout)
