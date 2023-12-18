import requests
from datetime import datetime
from influxdb_client import InfluxDBClient, Point
from influxdb_client.client.write_api import SYNCHRONOUS

def reorganizar_resultados(result):
    # Inicializa un diccionario para almacenar los datos reorganizados
    datos_reorganizados = []
    i = 0
    # Procesa cada registro en los resultados
    for table in result:
        i = 0
        for record in table.records:
            i = i + 1
            # Extrae la información relevante
            tiempo = record.values['_time']

            #campo = record.values['_field']
            valor = record.values['_value']

            # Convierte el tiempo a una cadena en el formato deseado (ajusta según sea necesario)
            tiempo_str = tiempo.strftime('%Y-%m-%d')

            if len(datos_reorganizados) != 0 and (i-1) in range(len(datos_reorganizados)):
                datos_reorganizados[i-1].append(valor)
            else:
                valores_dia = []
                # Almacena el valor en la lista correspondiente
                valores_dia.append(tiempo_str)
                valores_dia.append(valor)
                datos_reorganizados.append(valores_dia)
    return datos_reorganizados

def get_internal_factors(start, stop):
    url = "http://localhost:8086"
    token = "cXCNQgiokEdLO_S9VLELWXYV4Vpgok-5t3k0Y3bS-CSC0PbK0myWpSyuJhh5Z-8PH3W7wSbjACzboLGeZMoCoQ=="
    org = "Trade Hunter"
    bucket = "Trade Hunter Real Time Data"
    measurement = "Internal_Factors"
    # Crea el cliente InfluxDB
    client = InfluxDBClient(url=url, token=token, org=org)

    # Crea el cliente de escritura
    write_api = client.write_api(write_options=SYNCHRONOUS)

    # Consulta de ejemplo
    query = f'from(bucket: "{bucket}")' \
                f'  |> range(start: {start}, stop: {stop})' \
                f'  |> filter(fn: (r) => r["_measurement"] == "{measurement}")'
    result = client.query_api().query(query, org=org)

    # Uso de la función con tu resultado
    resultados_reorganizados = reorganizar_resultados(result)

    client.close()

    return resultados_reorganizados