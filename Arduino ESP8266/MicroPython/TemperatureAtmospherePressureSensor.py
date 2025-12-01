def run():
    from machine import I2C, Pin
    from gc import collect,mem_free
    from bmp280 import BMP280

    print("Free Memory Before I2C: {} KB".format(mem_free()/1024))

    # Initialize I2C
    i2c = I2C(scl=Pin(5), sda=Pin(4))  # GPIO5 (D1), GPIO4 (D2)
    collect()
    print("Free Memory After I2C : {} KB".format(mem_free()/1024))

    # Initialize BMP280
    collect()
    bmp = BMP280(i2c)

    print("Free Memory after bmp : {} KB".format(mem_free()/1024))
    # Read temperature and pressure
    collect()
    print("Temperature:", bmp.temperature, "°C")
    print("Pressure:", bmp.pressure, "hPa")



run()