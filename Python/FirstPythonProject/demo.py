import sys,math,os,datetime,subprocess

a="The-quick brown fox jumps over the lazy-dog"
a1=-2
a2=3.0
a3=True
a4=1+3j
b=["Hello","World",3,5] #list
b1=[x for x in b if isinstance(x,str)] 
c=("Hello","World") #tuple - cannot modify
d={1:"Hello",2:"World"} #Dict - like map in java
e={"Hello","World","World"} #set - only contain unique


print(subprocess.run("curl ifconfig.me",shell=True,capture_output=True,text=True).stdout)



# table=21
# for i in range(1,21):
#     print(i,"X",table,"=",i*table)


# while True:
#     try:
#         int(input())
#         break
#     except ValueError:
#         print("Please Enter Number")