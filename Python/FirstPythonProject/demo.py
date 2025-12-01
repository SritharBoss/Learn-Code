import os
import subprocess
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
List1 = [x for x in List if isinstance(x, str)]  # List Comprehension
Tuple = ("Hello", "World")  # tuple - cannot modify
Dict = {1: "Hello", 2: "World"}  # Dict - like map in java
Dict1 = [(1,"Test"),(2,"Pest"),(1,"T")]
Set = {"Hello", "World", "World"}  # set - only contain unique
Cycle = cycle("SritharBoss")
pattern = r"([sS]rithar)"
none = None

# print("Srithar", "Boss", sep="!", end="$$$END\n")
# print(len(pattern))
# print(input("What is that?\n"))
# print(dict(Dict1))

asia = pytz.timezone("Asia/Kolkata")
new_tz = pytz.timezone('America/New_York')
UP = "\033[2A"
CLR = "\033[0K"
GREEN = "\033[32m"
RESET = "\033[0m"

# print("\x09")
# for i in range(100, 0, -1):
#     Time = datetime.now()
#     print(f"{CLR}{UP}{GREEN}{asia.localize(Time).strftime('%Y-%m-%d %H:%M:%S')}{RESET}\n", end="", flush=True)
#     print(f"{CLR}{asia.localize(Time).astimezone(new_tz).strftime('%Y-%m-%d %H:%M:%S')}\n", end="", flush=True)
#     try:
#         time.sleep(1)
#     except KeyboardInterrupt:
#         print("Thank You For Using.")
#         break
# os.system("pause")
# print(subprocess.run(args="ping 192.168.1.1", capture_output=True, text=True).stdout)

# print(str.__class__.__eq__("str", ""))

# if __name__ == '__main__':
#     N = int(input())
#     List = list()
#     for _ in range(N):
#         string = input().split()
#         command = string[0]
#         inputs = list(map(int, string[1:]))
#         if command == 'insert':
#             List.insert(inputs[0], inputs[1])
#         elif command == 'append':
#             List.append(inputs[0])
#         elif command == 'print':
#             print(List)
#         elif command == 'remove':
#             if inputs[0] in List:
#                 List.remove(inputs[0])
#         elif command == 'sort':
#             List = sorted(List)
#         elif command == 'pop':
#             List.pop()
#         elif command == 'reverse':
#             List.reverse()


# if __name__ == '__main__':
#     n = int(input())
#     Tuple1 = map(int, input().split())
#     t = tuple(Tuple1)
#     print(hash(t))
#

# print(any(True if x == 'Hello' else False for x in List))
# print(list(filter(lambda a: a == 'Hello', List)))
# print(int(str("0b11"), 2))
# print([{x:chr(x)} for x in range(30,150) if chr(x).isalnum()])

# data = "BB8 BCD BB0 BC0 BA4 BB0 BCD 20 BA8 BB2 BCD BB2 BB5 BB0 BCD"
# print("".join([chr(int(x,16)) for x in data.split()]))

# data = ("E0 AE B8 E0 AF 8D E0 AE B0 E0 AF 80 E0 AE A4 E0 AE B0 E0 AF 8D 20 E0 "
#         "AE A8 E0 AE B2 E0 AF 8D E0 AE B2 E0 AE B5 E0 AE B0 E0 AF 8D")
# print(bytes.fromhex(data).decode(encoding="utf-8"))


# def count(v):
#     for i in range(v):
#         if i % 1000 == 0:
#             yield i
#
#
# for j in count(10_00_00_000):
#     print(j)

# print(subprocess.run(args="ls -alh", shell=True, capture_output=True, text=True).stdout)
