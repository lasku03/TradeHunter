from datetime import datetime
from influxdb_client import InfluxDBClient, Point
from influxdb_client.client.write_api import SYNCHRONOUS
from datetime import timedelta
import pandas as pd
import os
import numpy as np
import matplotlib .pyplot as plt
import seaborn as sns
import csv

def reorganizar_resultados(result):
    datos_reorganizados = []
    datos_reorganizados_con_nombres = []
    nombres = []
    date = "Date"
    nombres.append(date)
    i = 0
    for table in result:
        if i != 0:
            nombres.append(campo)
        i = 0
        for record in table.records:
            i = i + 1
            tiempo = record.values['_time']

            tiempo = tiempo + timedelta(days=1)

            campo = record.values['_field']
            valor = record.values['_value']

            tiempo_str = tiempo.strftime('%Y-%m-%d')

            if len(datos_reorganizados) != 0 and (i-1) in range(len(datos_reorganizados)):
                datos_reorganizados[i-1].append(valor)
            else:
                valores_dia = []
                valores_dia.append(tiempo_str)
                valores_dia.append(valor)
                datos_reorganizados.append(valores_dia)
    nombres.append(campo)
    datos_reorganizados_con_nombres.append(nombres)
    for a in datos_reorganizados:
        datos_reorganizados_con_nombres.append(a)
    return datos_reorganizados_con_nombres

def get_factors(start, stop):
    url = "http://tradehunter.duckdns.org:8086"
    token = "I7MLtkx-A_vJ3-JITkcYQqmhtxvc3zABaMBD-gmWY1eP2rcy4BqMzH_sVhNC7LhyDrGJKdIOxHptmgkuy28VFA=="
    org = "TradeHunter"
    bucket = "Trade Hunter Real Time Data"
    measurement = "Factors"
    client = InfluxDBClient(url=url, token=token, org=org)

    query = f'from(bucket: "{bucket}")' \
                f'  |> range(start: {start}, stop: {stop})' \
                f'  |> filter(fn: (r) => r["_measurement"] == "{measurement}")'
    result = client.query_api().query(query, org=org)

    resultados_reorganizados = reorganizar_resultados(result)

    client.close()

    return resultados_reorganizados

def insert_factors(path):
    url = "http://tradehunter.duckdns.org:8086"
    token = "I7MLtkx-A_vJ3-JITkcYQqmhtxvc3zABaMBD-gmWY1eP2rcy4BqMzH_sVhNC7LhyDrGJKdIOxHptmgkuy28VFA=="
    org = "TradeHunter"
    bucket = "Trade Hunter Real Time Data"
    measurement = "Factors"

    client = InfluxDBClient(url=url, token=token, org=org)
    write_api = client.write_api(write_options=SYNCHRONOUS)

    with open(path, 'r') as csv_file:
        csv_reader = csv.DictReader(csv_file)

        for row in csv_reader:

            timestamp = int(datetime.strptime(row['Date'], '%Y-%m-%d').timestamp()) * 1000000000

            data = Point(measurement).time(timestamp)

            for key, value in row.items():
                if key != 'Date':
                    data.field(key, float(value))

            write_api.write(bucket=bucket, record=data, timeout=20)

def analyze_data(path):

    data = pd.read_csv(path, header=0)

    images_folder = 'Images'
    #if not os.path.exists(images_folder):
        #os.makedirs(images_folder)

    plt.figure(figsize=(20, 20))

    cor = sns.heatmap(data[['Births', 'Euribor', 'IPC', 'Price_EUR', 'Open_EUR',
                            'High_EUR', 'Low_EUR', 'Defunciones', 'Total_debt', 'Percentage',
                            'Debt_per_capita', 'Activos', 'Ocupados', 'Parados', 'Actividad(%)',
                            'Paro(%)', 'GDP_Value', 'Open_DJ', 'High_DJ', 'Low_DJ', 'Close_DJ', 'Open', 'High', 'Low', 'Close', 'AdjClose']].corr(), annot=True, fmt=".2f")

    plt.savefig(os.path.join(images_folder, 'heatmap.png'))
    #if not os.path.exists(images_folder):
        #os.makedirs(images_folder)

    data['Date'] = pd.to_datetime(data['Date'])

    data.sort_values('Date', inplace=True)

    plt.figure(figsize=(12, 6))
    plt.plot(data['Date'], data['High'], label='IBEX High Values (High)')
    plt.title('Time Series of IBEX High Values (High)')
    plt.xlabel('Date')
    plt.ylabel('IBEX High Value')
    plt.legend()
    plt.tight_layout()

    plt.savefig(os.path.join(images_folder, 'IBEX_High_timeseries.png'))

    data['Date'] = pd.to_datetime(data['Date'])

    columns_to_plot = ['Euribor', 'IPC', 'Total_debt', 'Price_EUR', 'Activos', 'Parados', 'Ocupados', 'Actividad(%)', 'Paro(%)', 'GDP_Value', 'High_DJ', 'Births', 'Defunciones']
    #if not os.path.exists(images_folder):
       # os.makedirs(images_folder)

    for i, col in enumerate(columns_to_plot):
        plt.figure(figsize=(15, 5))

        sns.lineplot(x=data['Date'], y=data[col])
        plt.title(f'Time Series of {col}')
        plt.xlabel('Date')
        plt.ylabel(col)

        plt.tight_layout()

        plt.savefig(os.path.join(images_folder, f'time_series_{col}.png'))

        plt.close()

    data['Date'] = pd.to_datetime(data['Date'])

    columns_to_plot = ['Euribor', 'IPC', 'Total_debt', 'Price_EUR', 'Activos', 'Parados', 'Ocupados', 
                    'Actividad(%)', 'Paro(%)', 'GDP_Value', 'High_DJ', 'Births', 'Defunciones']
    #if not os.path.exists(images_folder):
       # os.makedirs(images_folder)

    for i, col in enumerate(columns_to_plot, 1):
        plt.figure(figsize=(15, 5))
        ax1 = plt.gca()
        sns.lineplot(x=data['Date'], y=data[col], ax=ax1, color='blue', label=col)
        ax1.set_ylabel(col, color='blue')
        
        ax2 = ax1.twinx()
        sns.lineplot(x=data['Date'], y=data['High'], ax=ax2, color='red', label='IBEX-35 Values')
        ax2.set_ylabel('IBEX-35 Value', color='red')

        plt.title(f'{col} vs IBEX-35 Value Over Time')
        plt.tight_layout()

        plt.savefig(os.path.join(images_folder, f'{col}_vs_IBEX-35.png'))
        
        plt.close()

    return True