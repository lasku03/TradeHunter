import requests
from bs4 import BeautifulSoup

page = requests.get("https://www.ine.es/prensa/ipc_tabla.htm")
soup = BeautifulSoup(page.text, 'html.parser')

data = soup.select('table.miTabla tr > td:nth-of-type(2)')

if data:
    IPC_value = data[0].text.strip()
    print(IPC_value)
else:
    print("No data found")