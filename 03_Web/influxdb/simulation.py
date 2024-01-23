import pandas as pd

class Simulation:
    def update_excel(self, values):
        data = pd.read_csv("simulation/merged_dataset1.csv")
        last_row = data.iloc[-1]

        for value in values:
            last_row[value.name] = value.value

        data.iloc[-1] = last_row

        data.to_csv("simulation/mergedataset.csv", index=True)

class DataObject:
    def __init__(self, name, value):
        self.name = name
        self.value = value

# Crear una lista de objetos
data_objects = [
    DataObject(name='Births', value=100),
    DataObject(name='Defunciones', value=2.789),
    DataObject(name='Total_debt', value=1.1234)
]

simulation_instance = Simulation()
simulation_instance.update_excel(data_objects)