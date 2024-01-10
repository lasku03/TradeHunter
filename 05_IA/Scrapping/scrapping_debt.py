import requests
from bs4 import BeautifulSoup

page = requests.get("https://datosmacro.expansion.com/deuda/espana")
soup = BeautifulSoup(page.text, 'html.parser')

debtTotal = soup.select(".col-sm-7 .eur tbody tr:nth-of-type(2) td:nth-of-type(2)")
debtTotal = debtTotal[0].text.split()
debtTotal = debtTotal[0]
print(debtTotal)

debtPercentage = soup.select(".col-sm-7 .eur tbody tr:nth-of-type(2) td:nth-of-type(3)")
debtPercentage = debtPercentage[0].text.split()
debtPercentage = debtPercentage[0]
debtPercentage = debtPercentage.split("%")
debtPercentage = debtPercentage[0]
print(debtPercentage)

debtPerCapita = soup.select(".col-sm-7 .eur tbody tr:nth-of-type(2) td:nth-of-type(4)")
debtPerCapita = debtPerCapita[0].text.split()
debtPerCapita = debtPerCapita[0]
print(debtPerCapita)