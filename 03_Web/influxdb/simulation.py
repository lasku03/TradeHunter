import pandas as pd

class Simulation:
    def update_excel(self, values):
        data = pd.read_csv("simulation/mergedataset.csv")
        last_row = data.iloc[-1]

        for name, value in values.items():
            last_row[name] = value

        data.iloc[-1] = last_row

        data.to_csv("simulation/mergedataset.csv", index=True)

simulation_instance = Simulation()
values = {'Births': 1234567, 'Defunciones': 21234567, 'Total_debt': 31234567}
simulation_instance.update_excel(values)