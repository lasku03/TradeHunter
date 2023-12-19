from http.server import HTTPServer, BaseHTTPRequestHandler
from search import Search 
import json
from urllib.parse import urlparse, parse_qs

class Serv(BaseHTTPRequestHandler):
    def do_GET(self):
        parsed_path = urlparse(self.path)
        path_parts = parsed_path.path.split('/')

        if len(path_parts) == 4 and path_parts[1] == 'search':
            # Extract the path variables
            start_date = path_parts[2]
            end_date = path_parts[3]

            # Assuming you want to add a range of dates to the search list
            search = Search()
            result = search.createSearch(start_date, end_date)

            result_list_of_dicts = []
            for item in result:
                result_dict = {
                "date": item[0],
                "AdjClose": item[1],
                "Close": item[2],
                "High": item[3],
                "Low": item[4],
                "Open": item[5]
                }
                result_list_of_dicts.append(result_dict)

            # Serialize the result to JSON
            result_json = json.dumps(result_list_of_dicts, indent=2)

            # Send the response headers
            self.send_response(200)
            self.send_header('Content-type', 'application/json')
            self.end_headers()

            # Send the JSON data as the response
            self.wfile.write(result_json.encode('utf-8'))

httpd = HTTPServer(('localhost', 8080), Serv)
httpd.serve_forever()