import requests
import datetime
from bs4 import BeautifulSoup

page = requests.get("https://countrymeters.info/es/Spain")
soup = BeautifulSoup(page.text, 'html.parser')

deaths = soup.find(id='cp9')
deaths = deaths.text.split()
deaths = deaths[0]
deaths = int(deaths)
TIME = datetime.datetime.now()
deaths = deaths/TIME.hour
deaths = int(deaths * 24)
print(deaths)