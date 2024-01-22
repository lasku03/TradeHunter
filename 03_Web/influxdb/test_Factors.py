import unittest
from datetime import datetime, timedelta
from Factors import reorganizar_resultados  # Replace 'Factors' with the actual module name

class test_Factors(unittest.TestCase):
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

if __name__ == '__main__':
    unittest.main()
