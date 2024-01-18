import requests
from bs4 import BeautifulSoup

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