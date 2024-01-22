from datetime import datetime
from influxdb_client import InfluxDBClient, Point
from influxdb_client.client.write_api import SYNCHRONOUS
from datetime import datetime, timedelta
import csv

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

            tiempo = tiempo + timedelta(days=1)

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
    url = "http://tradehunter.duckdns.org:8086"
    token = "I7MLtkx-A_vJ3-JITkcYQqmhtxvc3zABaMBD-gmWY1eP2rcy4BqMzH_sVhNC7LhyDrGJKdIOxHptmgkuy28VFA=="
    org = "TradeHunter"
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

def insertInternalFactors(path):
    url = "http://tradehunter.duckdns.org:8086"
    token = "I7MLtkx-A_vJ3-JITkcYQqmhtxvc3zABaMBD-gmWY1eP2rcy4BqMzH_sVhNC7LhyDrGJKdIOxHptmgkuy28VFA=="
    org = "TradeHunter"
    bucket = "Trade Hunter Real Time Data"
    measurement = "Internal_Factors"

    #Initialize the InfluxDB client
    client = InfluxDBClient(url=url, token=token, org=org)
    #Initialize the synchronous write API
    write_api = client.write_api(write_options=SYNCHRONOUS)

    with open(path, 'r') as csv_file:
        csv_reader = csv.DictReader(csv_file)

        for row in csv_reader:

            # Convert the date string to a timestamp
            timestamp = int(datetime.strptime(row['Date'], '%Y-%m-%d').timestamp()) * 1000000000

            # Create an InfluxDB data point for each row
            data = Point(measurement).time(timestamp)

            # Iterate over each column in the row (excluding 'Date')
            for key, value in row.items():
                if key != 'Date' and (key == 'High' or key == 'Low' or key == 'Open' or key == 'Close' or key == 'AdjClose'):
                    # Convert non-date values to floats and add them as fields
                    data.field(key, float(value))

            # Write the data point to InfluxDB
            write_api.write(bucket=bucket, record=data, timeout=20)