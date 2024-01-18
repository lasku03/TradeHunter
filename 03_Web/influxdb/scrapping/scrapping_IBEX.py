import requests
from bs4 import BeautifulSoup

page = requests.get("https://www.bolsamania.com/indice/IBEX-35")
soup = BeautifulSoup(page.text, 'html.parser')

data = soup.select(".headline-details-module .chart-range-container .chart-range-box .chart-range-bar span i")

close = data[0].text.split()
close = close[0]
close = close.split(":")
close = close[1]
print(close)

adjClose = close

open = data[1].text.split()
open = open[0]
open = open.split(":")
open = open[1]
print(open)

data = soup.select(".chart-range-title span")

low = data[1].text.split()
low = low[0]
print(low)

high = data[2].text.split()
high = high[0]
print(high)

page = requests.get("https://es.marketscreener.com/cotizacion/indice/IBEX-35-7629/")
soup = BeautifulSoup(page.text, 'html.parser')

data = soup.findAll('h1')
print(data)