import pandas as pd

class Simulation:
    def update_excel(self, values):
        data = pd.read_csv("simulation/merged_dataset1.csv")
        last_row = data.iloc[-1]

        for value in values:
            last_row[value.name] = value.value

        data.iloc[-1] = last_row

        data.to_csv("simulation/mergedataset.csv", index=True)