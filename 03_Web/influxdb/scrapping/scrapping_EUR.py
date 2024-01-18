import requests
from bs4 import BeautifulSoup

page = requests.get("https://finance.yahoo.com/quote/EURUSD=X/?guccounter=1")
soup = BeautifulSoup(page.text, 'html.parser')

data = soup.findAll(class_="Ta(end) Fw(600) Lh(14px)")

open = data[1].text.split()
open = open[0]
print(open)
lowHigh = data[3].text.split()
low = lowHigh[0]
print(low)
high = lowHigh[2]
print(high)

page = requests.get("https://www.xe.com/es/currencyconverter/convert/?Amount=1&From=EUR&To=USD")
soup = BeautifulSoup(page.text, 'html.parser')

current = soup.find(class_="result__BigRate-sc-1bsijpp-1 dPdXSB").text.split()
current = current[0]
print(current)