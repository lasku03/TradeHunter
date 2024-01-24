from datetime import datetime
from influxdb_client import InfluxDBClient, Point
from influxdb_client.client.write_api import SYNCHRONOUS
from datetime import datetime, timedelta

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
    token = "KYrAp2dOqBHVBNr0XIT--Rm_PaSF2sWP_b7YZO-QD9MCPuejpe0Dzu7j3-6mxSK7xcCbVWJJYHdVgFdAQHbEFw=="
    org = "Trade Hunter"
    bucket = "Trade Hunter Real Time Data"
    measurement = "Internal_Factors"
    client = InfluxDBClient(url=url, token=token, org=org)

    write_api = client.write_api(write_options=SYNCHRONOUS)

    query = f'from(bucket: "{bucket}")' \
                f'  |> range(start: {start}, stop: {stop})' \
                f'  |> filter(fn: (r) => r["_measurement"] == "{measurement}")'
    result = client.query_api().query(query, org=org)

    resultados_reorganizados = reorganizar_resultados(result)

    client.close()

    return resultados_reorganizados