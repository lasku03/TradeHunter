import pandas as pd
from datetime import datetime, timedelta

class Simulation:
    def update_excel(self, values):
        data = pd.read_csv("simulation/merged_dataset1.csv")
        last_row = data.iloc[-1]
        if last_row['Date'] == "2023-12-31":
            data = pd.read_csv("merged_dataset1.csv")
        last_row = data.iloc[-1].copy()

        for entry in values:
            name = entry['name']
            value = entry['value']
            
            if name in last_row.index:
                last_row[name] = value

        date_obj = datetime.strptime(last_row['Date'], "%Y-%m-%d")

        next_date = date_obj + timedelta(days=1)

        last_row['Date'] = next_date.strftime("%Y-%m-%d")

        data = data._append(last_row, ignore_index=True)

        data.to_csv("simulation/merged_dataset1.csv", index=False)