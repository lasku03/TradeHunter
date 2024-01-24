import unittest
from unittest.mock import Mock, patch
from datetime import datetime, timedelta
from Factors import get_factors, reorganizar_resultados
import coverage

class test_factors(unittest.TestCase):
    def test_reorganizar_resultados(self):
        # Create sample data for testing
        class Record:
            def __init__(self, time, field, value):
                self.values = {'_time': time, '_field': field, '_value': value}

        class Table:
            def __init__(self, records):
                self.records = records

        # Set up sample input
        time1 = datetime(2022, 1, 1)
        time2 = datetime(2022, 1, 2)
        record1 = Record(time1, 'field1', 10)
        record2 = Record(time1, 'field2', 20)
        record3 = Record(time2, 'field1', 30)
        record4 = Record(time2, 'field2', 40)
        table1 = Table([record1, record2])
        table2 = Table([record3, record4])
        result = [table1, table2]

        # Define the corrected expected result
        expected_result = [
            ['Date', 'field2', 'field2'],
            ['2022-01-02', 10, 30],
            ['2022-01-02', 20, 40] 
        ]

        # Call the method
        actual_result = reorganizar_resultados(result)

        # Print the actual and expected results for debugging
        print("Actual Result:")
        print(actual_result)
        print("Expected Result:")
        print(expected_result)

        # Assert that the actual result matches the expected result
        self.assertEqual(actual_result, expected_result)


    def test_get_factors(self):
        # Set up mock objects
        mock_reorganizar_resultados = Mock()

        # Set up sample input
        start = "2005-04-05"
        stop = "2005-04-09"

        # Mock the result of reorganizar_resultados
        mock_reorganizar_resultados.return_value = "[[9382.089844], [9392.390625], [9385.790039], [9385.790039], [9382.099609], [9392.400391], [9385.799805], [9385.799805], [9382.099609], [9407.099609], [9442.200195], [9442.200195], [9316.599609], [9335.0], [9369.700195], [9369.700195], ['2005-04-05', 9320.299805], ['2005-04-06', 9377.0], ['2005-04-07', 9439.099609], ['2005-04-08', 9439.099609]]"

        # Call the method
    
        actual_result = get_factors(start, stop)
        
        mocked_result = [['Date', 'Actividad(%)', 'Activos', 'AdjClose', 'Births', 'Close', 'Close_DJ', 'Debt_per_capita', 'Defunciones', 'Euribor', 'GDP_Value', 'High', 'High_DJ', 'High_EUR', 'IPC', 'Low', 'Low_DJ', 'Low_EUR', 'Ocupados', 'Open', 'Open_DJ', 'Open_EUR', 'Parados', 'Paro(%)', 'Percentage', 'Price_EUR', 'Total_debt'], ['2005-04-06', 57.86, 21129.7, 9382.089844, 1277.0, 9382.099609, 10486.02, 8941.0, 1061.0, 2.2651, 0.8886, 9382.099609, 10557.18, 1.2912, 1.4, 9316.599609, 10434.22, 1.2838, 19160.6, 9320.299805, 10453.45, 1.2857, 1969.1, 9.32, 42.4, 1.2872, 393479.0], ['2005-04-07', 57.86, 21129.7, 9392.390625, 1277.0, 9392.400391, 10546.32, 8941.0, 1061.0, 2.2651, 0.8886, 9407.099609, 10589.99, 1.2941, 1.4, 9335.0, 10434.3, 1.2844, 19160.6, 9377.0, 10485.88, 1.2872, 1969.1, 9.32, 42.4, 1.2855, 393479.0], ['2005-04-08', 57.86, 21129.7, 9385.790039, 1277.0, 9385.799805, 10461.34, 8941.0, 1061.0, 2.2651, 0.8886, 9442.200195, 10584.6, 1.2944, 1.4, 9369.700195, 10445.31, 1.2809, 19160.6, 9439.099609, 10546.32, 1.2853, 1969.1, 9.32, 42.4, 1.2938, 393479.0], ['2005-04-09', 57.86, 21129.7, 9385.790039, 1277.0, 9385.799805, 10461.34, 8941.0, 1061.0, 2.2651, 0.8886, 9442.200195, 10584.6, 1.2944, 1.4, 9369.700195, 10445.31, 1.2809, 19160.6, 9439.099609, 10546.32, 1.2853, 1969.1, 9.32, 42.4, 1.2938, 393479.0]]

        # Check if the actual result matches the expected result
        self.assertEqual(actual_result, mocked_result)


if __name__ == '__main__':
    unittest.main()
