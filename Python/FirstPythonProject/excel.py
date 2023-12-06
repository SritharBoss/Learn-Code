#!/bin/env python
from openpyxl import Workbook

wb = Workbook()
sheet = wb.active
sheet['A1'] = "Hello"
wb.save("/home/srithar/Hello.xlsx")
