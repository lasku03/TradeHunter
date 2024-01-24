import unittest
import pandas as pd
from datetime import datetime, timedelta
from simulation import Simulation
import coverage

class TestSimulationUpdateExcel(unittest.TestCase):
   def test_update_excel(self):
        instance = Simulation()

        values = [{'name': 'Price_EURO', 'value': 42}, {'name': 'Open_EURO', 'value': 'Test'}]

        instance.update_excel(values)

        updated_data = pd.read_csv("simulation/merged_dataset1.csv")

        self.assertIn('Price_EURO', updated_data.columns)

        if 'Price_EURO' in updated_data.columns:
            self.assertEqual(updated_data.iloc[-1]['Price_EURO'], 42)

if __name__ == '__main__':
    unittest.main()