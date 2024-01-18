import requests
from bs4 import BeautifulSoup

page = requests.get("https://datosmacro.expansion.com/pib/espana")
soup = BeautifulSoup(page.text, 'html.parser')

gdp = soup.select(".table.tabledat.table-striped.table-condensed.table-hover tbody tr > td:nth-of-type(4)")
gdp = gdp[0].text.split()[0]
gdp = gdp.split("%")
gdp = gdp[0]
print(gdp)