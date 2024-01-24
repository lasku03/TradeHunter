from datetime import datetime
from influxdb_client import InfluxDBClient, Point
from influxdb_client.client.write_api import SYNCHRONOUS
from datetime import datetime, timedelta
import csv

def reorganizar_resultados(result):
    datos_reorganizados = []
    i = 0
    for table in result:
        i = 0
        for record in table.records:
            i = i + 1
            tiempo = record.values['_time']

            tiempo = tiempo + timedelta(days=1)

            valor = record.values['_value']

            tiempo_str = tiempo.strftime('%Y-%m-%d')

            if len(datos_reorganizados) != 0 and (i-1) in range(len(datos_reorganizados)):
                datos_reorganizados[i-1].append(valor)
            else:
                valores_dia = []
                valores_dia.append(tiempo_str)
                valores_dia.append(valor)
                datos_reorganizados.append(valores_dia)
    return datos_reorganizados

def get_internal_factors(start, stop):
    url = "http://tradehunter.duckdns.org:8086"
    token = "I7MLtkx-A_vJ3-JITkcYQqmhtxvc3zABaMBD-gmWY1eP2rcy4BqMzH_sVhNC7LhyDrGJKdIOxHptmgkuy28VFA=="
    org = "TradeHunter"
    bucket = "Trade Hunter Real Time Data"
    measurement = "Internal_Factors"
    client = InfluxDBClient(url=url, token=token, org=org)

    query = f'from(bucket: "{bucket}")' \
                f'  |> range(start: {start}, stop: {stop})' \
                f'  |> filter(fn: (r) => r["_measurement"] == "{measurement}")'
    result = client.query_api().query(query, org=org)

    resultados_reorganizados = reorganizar_resultados(result)

    client.close()

    return resultados_reorganizados

def insert_internal_factors(path):
    url = "http://tradehunter.duckdns.org:8086"
    token = "I7MLtkx-A_vJ3-JITkcYQqmhtxvc3zABaMBD-gmWY1eP2rcy4BqMzH_sVhNC7LhyDrGJKdIOxHptmgkuy28VFA=="
    org = "TradeHunter"
    bucket = "Trade Hunter Real Time Data"
    measurement = "Internal_Factors"

    client = InfluxDBClient(url=url, token=token, org=org)
    write_api = client.write_api(write_options=SYNCHRONOUS)

    with open(path, 'r') as csv_file:
        csv_reader = csv.DictReader(csv_file)

        for row in csv_reader:

            timestamp = int(datetime.strptime(row['Date'], '%Y-%m-%d').timestamp()) * 1000000000

            data = Point(measurement).time(timestamp)

            for key, value in row.items():
                if key != 'Date' and (key == 'High' or key == 'Low' or key == 'Open' or key == 'Close' or key == 'AdjClose'):
                    data.field(key, float(value))

            write_api.write(bucket=bucket, record=data, timeout=20)