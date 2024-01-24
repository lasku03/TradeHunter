import pickle
import pandas as pd
from prophet import Prophet
import json

  
data = pd.read_csv("merged_dataset1.csv")


# Charge the model. Pkl archive.
with open('modelo_prophet.pkl', 'rb') as f:
    model = pickle.load(f)

    
    data['ds'] = data['Date']
    data['y'] = data['AdjClose']


# Do the prediction using the created model
def make_prediction(input_data):
    forecast = model.predict(input_data)
    return forecast


predictions = make_prediction(data)
ultimos_dos_yhat = predictions['yhat'].tail(2)

result_json = ultimos_dos_yhat.to_json(orient="split")
# Convierte el resultado a un diccionario JSON válido
import json
result_dict = json.loads(result_json)
result_json = json.dumps({"yhat": result_dict["data"]}, indent=2)

