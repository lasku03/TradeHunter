from http.server import HTTPServer, BaseHTTPRequestHandler
from search import Search 
import json
from urllib.parse import urlparse, parse_qs
import pandas as pd
from prophet import Prophet 
import pickle
from simulation import Simulation

class Serv(BaseHTTPRequestHandler):
    def do_GET(self):
        parsed_path = urlparse(self.path)
        path_parts = parsed_path.path.split('/')

        if len(path_parts) == 4 and path_parts[1] == 'search':
            start_date = path_parts[2]
            end_date = path_parts[3]

            search = Search()
            internal_data = search.createInternalSearch(start_date, end_date)
            result = search.createSearch(start_date, end_date)

            result = pd.DataFrame(result)

            result.to_csv('dee.csv', index=False, header=False)

            search.createImages('dee.csv')

            internal_data_dict = []
            for item in internal_data:
                result_dict = {
                "Date": item[0],
                "AdjClose": item[1],
                "Close": item[2],
                "High": item[3],
                "Low": item[4],
                "Open": item[5]
                }
                internal_data_dict.append(result_dict)

            result_json = json.dumps(internal_data_dict, indent=2)

            # response 
            self.send_response(200)
            self.send_header('Content-type', 'application/json')
            self.end_headers()

            # Send the JSON
            self.wfile.write(result_json.encode('utf-8'))

        elif path_parts[1] == 'predict':
            # Load the model from the pkl file
            with open('modelo_prophet.pkl', 'rb') as f:
                model = pickle.load(f)

            data = pd.read_csv("merged_dataset1.csv")
            data['ds'] = data['Date']
            data['y'] = data['AdjClose']

            # prediction
            def make_prediction(input_data):
                forecast = model.predict(input_data)
                return forecast.to_dict(orient='records')

            predictions = make_prediction(data)
            predictionsData = pd.DataFrame(predictions)

            ultimos_dos_yhat = predictionsData['yhat'].tail(2)

            ultimos_dos_yhat_list = ultimos_dos_yhat.tolist()
            result_json = json.dumps(ultimos_dos_yhat_list)

            self.send_response(200)
            self.send_header('Content-type', 'application/json')
            self.end_headers()

            self.wfile.write(result_json.encode('utf-8'))

        elif path_parts[1] == 'start':
            data = pd.read_csv("merged_dataset1.csv")
            data.to_csv("simulation/merged_dataset1.csv", index=False)
            self.send_response(200)
            
        else:
            result_json = json.dumps({"error": "Invalid path"}, indent=2)
            self.send_response(404)
            self.send_header('Content-type', 'application/json')
            self.end_headers()
            self.wfile.write(result_json.encode('utf-8'))

    def do_POST(self):
        parsed_path = urlparse(self.path)
        path_parts = parsed_path.path.split('/')

        if path_parts[1] == "predictValues":
            content_length = int(self.headers['Content-Length'])
            body = self.rfile.read(content_length)
            data = json.loads(body.decode('utf-8'))

            simulation = Simulation()
            simulation.update_excel(data)

            # Load the model 
            with open('simulation/modelo_prophet2.pkl', 'rb') as f:
                model = pickle.load(f)

            data = pd.read_csv("simulation/merged_dataset1.csv")
            data['ds'] = data['Date']
            data['y'] = data['AdjClose']

            # prediction
            def make_prediction(input_data):
                forecast = model.predict(input_data)
                return forecast.to_dict(orient='records')

            predictions = make_prediction(data)
            predictionsData = pd.DataFrame(predictions)

            ultimos_dos_yhat = predictionsData['yhat'].tail(2)

            ultimos_dos_yhat_list = ultimos_dos_yhat.tolist()

            result_json = json.dumps(ultimos_dos_yhat_list)

            self.send_response(200)
            self.send_header('Content-type', 'application/json')
            self.end_headers()

            self.wfile.write(result_json.encode('utf-8'))
        else:
            result_json = json.dumps({"error": "Invalid path"}, indent=2)
            self.send_response(404)
            self.send_header('Content-type', 'application/json')
            self.end_headers()
            self.wfile.write(result_json.encode('utf-8'))

httpd = HTTPServer(('localhost', 8080), Serv)
httpd.serve_forever()