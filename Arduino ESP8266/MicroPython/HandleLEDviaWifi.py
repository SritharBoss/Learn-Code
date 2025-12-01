import socket
from machine import Pin

led = Pin(2, Pin.OUT)

def web_page():
    html = f"""<!DOCTYPE html>
    <html>
    <body>
    <h1>ESP8266 Web Server</h1>
    <p>LED is {'ON' if led.value() == 1 else 'OFF'}</p>
    <a href="/?led=on"><button>Turn On</button></a>
    <a href="/?led=off"><button>Turn Off</button></a>
    </body>
    </html>"""
    return html

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.bind(('', 80))
s.listen(5)

while True:
    conn, addr = s.accept()
    print(f'Got connection from {addr}')
    request = conn.recv(1024).decode()
    if '/?led=on' in request:
        led.value(0)
    elif '/?led=off' in request:
        led.value(1)
    response = web_page()
    conn.send('HTTP/1.1 200 OK\nContent-Type: text/html\n\n')
    conn.send(response)
    conn.close()
