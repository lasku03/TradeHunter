import datetime
import requests
from bs4 import BeautifulSoup

page = requests.get("https://countrymeters.info/es/Spain")
soup = BeautifulSoup(page.text, 'html.parser')

births = soup.find(id='cp7')
births = births.text.split()
births = births[0]
births = int(births)
TIME = datetime.datetime.now()
births = births/TIME.hour
births = int(births * 24)
print(births)