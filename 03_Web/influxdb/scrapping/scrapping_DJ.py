import requests
from bs4 import BeautifulSoup

page = requests.get("https://markets.businessinsider.com/index/dow_jones")
soup = BeautifulSoup(page.text, 'html.parser')

close = soup.find(class_="price-section__current-value")
close = close.text.split()
close = close[0]
print(close)

low = soup.select(".snapshot__highlow:nth-of-type(1) div:nth-of-type(1)")
low = low[0].text.split()
low = low[0]
print(low)

high = soup.select(".snapshot__highlow:nth-of-type(1) div:nth-of-type(2)")
high = high[0].text.split()
high = high[0]
print(high)

open = soup.select("#snapshot .snapshot .snapshot__data-item:nth-of-type(2)")
open = open[0].text.split()
open = open[0]
print(open)