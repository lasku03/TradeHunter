import requests
from bs4 import BeautifulSoup
import datetime
import csv
import pandas as pd
import Factors
import InternalFactors

class Scrapping:

    def __init__(self):
        self.activity_activos = 0
        self.activity_ocupados = 0
        self.activity_parados = 0
        self.activity_activity_rate = 0
        self.activity_unemployment_rate = 0
        self.births = 0
        self.deaths = 0
        self.total_debt = 0
        self.debt_percentage = 0
        self.debt_per_capita = 0
        self.dj_close = 0
        self.dj_low = 0
        self.dj_high = 0
        self.dj_open = 0
        self.eur_open = 0
        self.eur_low = 0
        self.eur_high = 0
        self.eur_current = 0
        self.GDP = 0
        self.ibex_close = 0
        self.ibex_adjclose = 0
        self.ibex_open = 0
        self.ibex_low = 0
        self.ibex_high = 0
        self.IPC = 0
        self.euribor = 0
        self.row = []

    def activity_scrapping(self):
        page = requests.get("https://www.ine.es/dyngs/INEbase/es/operacion.htm?c=Estadistica_C&cid=1254736176918&menu=ultiDatos&idp=1254735976595")
        soup = BeautifulSoup(page.text, 'html.parser')

        ocupados = soup.select(".contenTabla.paddingDef tbody tr:nth-of-type(1) td:nth-of-type(2)")
        ocupados = ocupados[0].text.split()
        ocupados = ocupados[0] 

        parados = soup.select(".contenTabla.paddingDef tbody tr:nth-of-type(2) td:nth-of-type(2)")
        parados = parados[0].text.split()
        parados = parados[0]

        ocupados = ocupados.replace('.', '')
        ocupados = ocupados.replace(',', '.')
        ocupados = pd.to_numeric(ocupados, errors='coerce')

        parados = parados.replace('.', '')
        parados = parados.replace(',', '.')
        parados = pd.to_numeric(parados, errors='coerce')

        activos = ocupados + parados

        activity_rate = soup.select(".contenTabla.paddingDef tbody tr:nth-of-type(3) td:nth-of-type(2)")
        activity_rate = activity_rate[0].text.split()
        activity_rate = activity_rate[0]

        unemployment_rate = soup.select(".contenTabla.paddingDef tbody tr:nth-of-type(4) td:nth-of-type(2)")
        unemployment_rate = unemployment_rate[0].text.split()
        unemployment_rate = unemployment_rate[0]

        return activos, ocupados, parados, activity_rate, unemployment_rate

    def births_scrapping(self):
        page = requests.get("https://countrymeters.info/es/Spain")
        soup = BeautifulSoup(page.text, 'html.parser')

        births = soup.find(id='cp7')
        births = births.text.split()
        births = births[0]
        births = int(births)
        TIME = datetime.datetime.now()
        births = births/TIME.hour
        births = int(births * 24)

        return births

    def deaths_scrapping(self):
        page = requests.get("https://countrymeters.info/es/Spain")
        soup = BeautifulSoup(page.text, 'html.parser')

        deaths = soup.find(id='cp9')
        deaths = deaths.text.split()
        deaths = deaths[0]
        deaths = int(deaths)
        TIME = datetime.datetime.now()
        deaths = deaths/TIME.hour
        deaths = int(deaths * 24)

        return deaths
    
    def debt_scrapping(self):
        page = requests.get("https://datosmacro.expansion.com/deuda/espana")
        soup = BeautifulSoup(page.text, 'html.parser')

        debt_total = soup.select(".col-sm-7 .eur tbody tr:nth-of-type(2) td:nth-of-type(2)")
        debt_total = debt_total[0].text.split()
        debt_total = debt_total[0]

        debt_percentage = soup.select(".col-sm-7 .eur tbody tr:nth-of-type(2) td:nth-of-type(3)")
        debt_percentage = debt_percentage[0].text.split()
        debt_percentage = debt_percentage[0]
        debt_percentage = debt_percentage.split("%")
        debt_percentage = debt_percentage[0]

        debt_per_capita = soup.select(".col-sm-7 .eur tbody tr:nth-of-type(2) td:nth-of-type(4)")
        debt_per_capita = debt_per_capita[0].text.split()
        debt_per_capita = debt_per_capita[0]

        return debt_total, debt_percentage, debt_per_capita

    def dj_scrapping(self):
        page = requests.get("https://markets.businessinsider.com/index/dow_jones")
        soup = BeautifulSoup(page.text, 'html.parser')

        close = soup.find(class_="price-section__current-value")
        close = close.text.split()
        close = close[0]

        low = soup.select(".snapshot__highlow:nth-of-type(1) div:nth-of-type(1)")
        low = low[0].text.split()
        low = low[0]

        high = soup.select(".snapshot__highlow:nth-of-type(1) div:nth-of-type(2)")
        high = high[0].text.split()
        high = high[0]

        _open = soup.select("#snapshot .snapshot .snapshot__data-item:nth-of-type(2)")
        _open = _open[0].text.split()
        _open = _open[0]

        return close, low, high, _open

    def eur_scrapping(self):
        page = requests.get("https://finance.yahoo.com/quote/EURUSD=X/?guccounter=1")
        soup = BeautifulSoup(page.text, 'html.parser')

        data = soup.findAll(class_="Ta(end) Fw(600) Lh(14px)")

        _open = data[1].text.split()
        _open = _open[0]
        low_high = data[3].text.split()
        low = low_high[0]
        high = low_high[2]

        page = requests.get("https://www.xe.com/es/currencyconverter/convert/?Amount=1&From=EUR&To=USD")
        soup = BeautifulSoup(page.text, 'html.parser')

        current = soup.find(class_="result__BigRate-sc-1bsijpp-1 dPdXSB")
        current = current.text.split()
        current = current[0]

        return _open, low, high, current

    def gdp_scrapping(self):
        page = requests.get("https://datosmacro.expansion.com/pib/espana")
        soup = BeautifulSoup(page.text, 'html.parser')

        gdp = soup.select(".table.tabledat.table-striped.table-condensed.table-hover tbody tr > td:nth-of-type(4)")
        gdp = gdp[0].text.split()[0]
        gdp = gdp.split("%")
        gdp = gdp[0]

        return gdp

    def ibex_scrapping(self):
        page = requests.get("https://www.bolsamania.com/indice/IBEX-35")
        soup = BeautifulSoup(page.text, 'html.parser')

        data = soup.select(".headline-details-module .chart-range-container .chart-range-box .chart-range-bar span i")

        close = data[0].text.split()
        close = close[0]
        close = close.split(":")
        close = close[1]

        adj_close = close

        _open = data[1].text.split()
        _open = _open[0]
        _open = _open.split(":")
        _open = _open[1]

        data = soup.select(".chart-range-title span")

        low = data[1].text.split()
        low = low[0]

        high = data[2].text.split()
        high = high[0]

        return close, adj_close, _open, low, high

    def ipc_scrapping(self):
        page = requests.get("https://www.ine.es/prensa/ipc_tabla.htm")
        soup = BeautifulSoup(page.text, 'html.parser')

        data = soup.select('table.miTabla tr > td:nth-of-type(2)')

        ipc_value = data[0].text.strip()

        return ipc_value
    
    def euribor_scrapping(self):
        page = requests.get("https://www.expansion.com/mercados/euribor.html")
        soup = BeautifulSoup(page.text, 'html.parser')
        data = soup.select('.col-4.izquierda table:nth-of-type(2)> tbody > tr:nth-of-type(1) > td:nth-of-type(2)')
        data = data[0].text.split()
        data = data[0]
        return data

    def init_scrapping(self):
        self.activity_activos, self.activity_ocupados, self.activity_parados, self.activity_activity_rate, self.activity_unemployment_rate = self.activity_scrapping()
        self.births = self.births_scrapping()
        self.deaths = self.deaths_scrapping()
        self.total_debt, self.debt_percentage, self.debt_per_capita = self.debt_scrapping()
        self.dj_close, self.dj_low, self.dj_high, self.dj_open = self.dj_scrapping()
        self.eur_open, self.eur_low, self.eur_high, self.eur_current = self.eur_scrapping()
        self.GDP = self.gdp_scrapping()
        self.ibex_close, self.ibex_adjclose, self.ibex_open, self.ibex_low, self.ibex_high = self.ibex_scrapping()
        self.IPC = self.ipc_scrapping()
        self.euribor = self.euribor_scrapping()
        headers = ["Date", "Activity(%)","Activos", "AdjClose", "Births", "Close", "Close_DJ", "Total_debt", "Debt_per_capita", "Deaths",
                "Euribor", "GDP_Value", "High_DJ", "High_EUR", "High", "IPC", "Low_DJ", "Low_EUR", "Low", "Ocupados",
                "Open_DJ", "Open_EUR", "Open", "Parados", "Paro(%)", "Percentage", "Price_EUR"]
        new_data = []
        current_date = datetime.date.today().strftime("%Y-%m-%d")

        new_row = [current_date, self.activity_activity_rate, self.activity_activos, self.ibex_adjclose, self.births, self.ibex_close, self.dj_close, self.total_debt, self.debt_per_capita, self.deaths,
                    self.euribor, self.GDP, self.dj_high, self.eur_high, self.ibex_high, self.IPC, self.dj_low, self.eur_low, self.ibex_low, self.activity_ocupados, self.dj_open,
                    self.eur_open, self.ibex_open, self.activity_parados, self.activity_unemployment_rate, self.debt_percentage, self.eur_current]

        new_data.append(headers)
        i = -1
        for col in headers:
            i = i + 1
            if col != 'Date' and not isinstance(new_row[i], int) and not isinstance(new_row[i], float):
                new_row[i] = new_row[i].replace('.', '')
                new_row[i] = new_row[i].replace(',', '.')
                new_row[i] = pd.to_numeric(new_row[i], errors='coerce')
        new_data.append(new_row)
        data = pd.DataFrame(new_data)

        new_data = 'new_data.csv'
        data.to_csv(new_data, index=False, header=None)
        Factors.insert_factors(new_data)
        InternalFactors.insert_internal_factors(new_data)