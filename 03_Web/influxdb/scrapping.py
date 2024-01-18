import requests
from bs4 import BeautifulSoup
import datetime

class Scrapping:

    def __init__(self):
        self.activity_ocupados = 0
        self.activity_parados = 0
        self.activity_activity_rate = 0
        self.activity_unemployment_rate = 0
        self.births = 0
        self.deaths = 0
        self.total_debt = 0
        self.debt_percentage = 0
        self.debt_per_capita = 0
        self.DJ_close = 0
        self.DJ_low = 0
        self.DJ_high = 0
        self.DJ_open = 0
        self.EUR_open = 0
        self.EUR_low = 0
        self.EUR_high = 0
        self.EUR_current = 0
        self.GDP = 0
        self.IBEX_close = 0
        self.IBEX_adjclose = 0
        self.IBEX_open = 0
        self.IBEX_low = 0
        self.IBEX_high = 0
        self.IPC = 0
        self.row = []

    def activity_scrapping():
        page = requests.get("https://www.ine.es/dyngs/INEbase/es/operacion.htm?c=Estadistica_C&cid=1254736176918&menu=ultiDatos&idp=1254735976595")
        soup = BeautifulSoup(page.text, 'html.parser')

        ocupados = soup.select(".contenTabla.paddingDef tbody tr:nth-of-type(1) td:nth-of-type(2)")
        ocupados = ocupados[0].text.split()
        ocupados = ocupados[0] 
        print(ocupados)

        parados = soup.select(".contenTabla.paddingDef tbody tr:nth-of-type(2) td:nth-of-type(2)")
        parados = parados[0].text.split()
        parados = parados[0]
        print(parados)

        activityRate = soup.select(".contenTabla.paddingDef tbody tr:nth-of-type(3) td:nth-of-type(2)")
        activityRate = activityRate[0].text.split()
        activityRate = activityRate[0]
        print(activityRate)

        unemploymentRate = soup.select(".contenTabla.paddingDef tbody tr:nth-of-type(4) td:nth-of-type(2)")
        unemploymentRate = unemploymentRate[0].text.split()
        unemploymentRate = unemploymentRate[0]
        print(unemploymentRate)

        return ocupados, parados, activityRate, unemploymentRate

    def births_scrapping():
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

        return births

    def deaths_scrapping():
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

        return deaths
    
    def debt_scrapping():
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

        return debtTotal, debtPercentage, debtPerCapita

    def DJ_scrapping():
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

        return close, low, high, open

    def EUR_scrapping():
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

        return open, low, high, current

    def GDP_scrapping():
        page = requests.get("https://datosmacro.expansion.com/pib/espana")
        soup = BeautifulSoup(page.text, 'html.parser')

        gdp = soup.select(".table.tabledat.table-striped.table-condensed.table-hover tbody tr > td:nth-of-type(4)")
        gdp = gdp[0].text.split()[0]
        gdp = gdp.split("%")
        gdp = gdp[0]
        print(gdp)

        return gdp

    def IBEX_scrapping():
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

        return close, adjClose, open, low, high

    def IPC_scrapping():
        page = requests.get("https://www.ine.es/prensa/ipc_tabla.htm")
        soup = BeautifulSoup(page.text, 'html.parser')

        data = soup.select('table.miTabla tr > td:nth-of-type(2)')

        if data:
            IPC_value = data[0].text.strip()
            print(IPC_value)
        else:
            print("No data found")
        return IPC_value

    def init_scrapping(self):
        self.activity_ocupados, self.activity_parados, self.activity_activity_rate, self.activity_unemployment_rate= self.activity_scrapping()
        self.births = self.births_scrapping()
        self.deaths = self.deaths_scrapping()
        self.total_debt, self.debt_percentage, self.debt_per_capita = self.debt_scrapping()
        self.DJ_close, self.DJ_low, self.DJ_high, self.DJ_open = self.DJ_scrapping()
        self.EUR_open, self.EUR_low, self.EUR_high, self.EUR_current = self.EUR_scrapping()
        self.GDP = self.GDP_scrapping()
        self.IBEX_close, self.IBEX_adjclose, self.IBEX_open, self.IBEX_low, self.IBEX_high = self.IBEX_scrapping()
        self.IPC = self.IPC_scrapping()