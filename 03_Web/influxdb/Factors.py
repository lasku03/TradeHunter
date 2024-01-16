from datetime import datetime
from influxdb_client import InfluxDBClient, Point
from influxdb_client.client.write_api import SYNCHRONOUS
from datetime import timedelta
import pandas as pd
import os
import numpy as np
import matplotlib .pyplot as plt
import seaborn as sns

def reorganizar_resultados(result):
    # Inicializa un diccionario para almacenar los datos reorganizados
    datos_reorganizados = []
    datos_reorganizados_con_nombres = []
    nombres = []
    date = "Date"
    #hutsunea = ""
    #nombres.append(hutsunea)
    nombres.append(date)
    i = 0
    # Procesa cada registro en los resultados
    for table in result:
        if i != 0:
            nombres.append(campo)
        i = 0
        for record in table.records:
            i = i + 1
            # Extrae la información relevante
            tiempo = record.values['_time']

            tiempo = tiempo + timedelta(days=1)

            campo = record.values['_field']
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
    nombres.append(campo)
    datos_reorganizados_con_nombres.append(nombres)
    for a in datos_reorganizados:
        datos_reorganizados_con_nombres.append(a)
    return datos_reorganizados_con_nombres

def get_factors(start, stop):
    url = "http://tradehunter.duckdns.org:8086"
    token = "KYrAp2dOqBHVBNr0XIT--Rm_PaSF2sWP_b7YZO-QD9MCPuejpe0Dzu7j3-6mxSK7xcCbVWJJYHdVgFdAQHbEFw=="
    org = "Trade Hunter"
    bucket = "Trade Hunter Real Time Data"
    measurement = "Factors"
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

def analyzeData(path):

    data = pd.read_csv(path, header=0)

    # Create the 'Images' folder if it doesn't exist
    images_folder = 'Images'
    if not os.path.exists(images_folder):
        os.makedirs(images_folder)

    # Correlation matrix between all the factors
    plt.figure(figsize=(20, 20))  # Adjust the width and height as needed

    cor = sns.heatmap(data[['Births', 'Euribor', 'IPC', 'Price_EURO', 'Open_EURO',
                            'High_EURO', 'Low_EURO', 'Defunciones', 'Debt', 'Percentage',
                            'Debt_per_capita', 'Activos', 'Ocupados', 'Parados', 'Actividad(%)',
                            'Paro(%)', 'GDP_Value', 'Open_DJ', 'High_DJ', 'Low_DJ', 'Close_DJ', 'Open_y', 'High_y', 'Low_y', 'Close', 'AdjClose']].corr(), annot=True, fmt=".2f")

    # Save the plot in the 'Images' folder
    plt.savefig(os.path.join(images_folder, 'heatmap.png'))

    # Create the 'Images' folder if it doesn't exist
    images_folder = 'Images'
    if not os.path.exists(images_folder):
        os.makedirs(images_folder)

    # Convert 'Date' column to datetime for proper plotting
    data['Date'] = pd.to_datetime(data['Date'])

    # Sorting the data by Date to ensure the line is continuous
    data.sort_values('Date', inplace=True)

    # Time Series Plot for 'High_y'
    plt.figure(figsize=(12, 6))
    plt.plot(data['Date'], data['High_y'], label='IBEX High Values (High_y)')
    plt.title('Time Series of IBEX High Values (High_y)')
    plt.xlabel('Date')
    plt.ylabel('IBEX High Value')
    plt.legend()
    plt.tight_layout()

    # Save the plot in the 'Images' folder before showing it
    plt.savefig(os.path.join(images_folder, 'IBEX_High_y_timeseries.png'))

    # Convert 'Date' column to datetime
    data['Date'] = pd.to_datetime(data['Date'])

    # List of columns to plot
    columns_to_plot = ['Euribor', 'IPC', 'Debt', 'Price_EURO', 'Activos', 'Parados', 'Ocupados', 'Actividad(%)', 'Paro(%)', 'GDP_Value', 'High_DJ', 'Births', 'Defunciones']

    # Create the 'Images' folder if it doesn't exist
    images_folder = 'Images'
    if not os.path.exists(images_folder):
        os.makedirs(images_folder)

    # Iterate over the list of columns
    for i, col in enumerate(columns_to_plot):
        # Create a new figure for each plot
        plt.figure(figsize=(15, 5))

        # Create the line plot
        sns.lineplot(x=data['Date'], y=data[col])
        plt.title(f'Time Series of {col}')
        plt.xlabel('Date')
        plt.ylabel(col)

        # Adjust layout
        plt.tight_layout()

        # Save the figure as an image file in the 'Images' folder
        plt.savefig(os.path.join(images_folder, f'time_series_{col}.png'))

        # Close the plot to prevent display issues
        plt.close()

    # Convert 'Date' column to datetime
    data['Date'] = pd.to_datetime(data['Date'])

    # List of columns to compare with 'High_y'
    columns_to_plot = ['Euribor', 'IPC', 'Debt', 'Price_EURO', 'Activos', 'Parados', 'Ocupados', 
                    'Actividad(%)', 'Paro(%)', 'GDP_Value', 'High_DJ', 'Births', 'Defunciones']

    # Create the 'Images' folder if it doesn't exist
    images_folder = 'Images'
    if not os.path.exists(images_folder):
        os.makedirs(images_folder)

    # Creating dual-axis line plots for each column compared with 'High_y'
    for i, col in enumerate(columns_to_plot, 1):
        plt.figure(figsize=(15, 5))
        ax1 = plt.gca()
        sns.lineplot(x=data['Date'], y=data[col], ax=ax1, color='blue', label=col)
        ax1.set_ylabel(col, color='blue')
        
        ax2 = ax1.twinx()
        sns.lineplot(x=data['Date'], y=data['High_y'], ax=ax2, color='red', label='IBEX-35 Values')
        ax2.set_ylabel('IBEX-35 Value', color='red')

        plt.title(f'{col} vs IBEX-35 Value Over Time')
        plt.tight_layout()

        # Save the figure as an image file in the 'Images' folder
        plt.savefig(os.path.join(images_folder, f'{col}_vs_IBEX-35.png'))
        
        # Close the plot to prevent display issues
        plt.close()

    return True