import requests
from bs4 import BeautifulSoup
url = 'https://www.reddit.com/r/Python/'
r = requests.get(url)
html_data = BeautifulSoup(r.text, 'html.parser')
headings = html_data.findAll("h1")
print(headings[0])