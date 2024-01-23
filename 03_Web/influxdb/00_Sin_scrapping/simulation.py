import pandas as pd

class Simulation:
    def update_excel(self, values):
        data = pd.read_csv("simulation/merged_dataset1.csv")
        last_row = data.iloc[-1]

        for entry in values:
            name = entry['name']
            value = entry['value']
            
            if name in last_row.index:
                last_row[name] = value

        data.iloc[-1] = last_row

        data.to_csv("simulation/merged_dataset1.csv", index=True)