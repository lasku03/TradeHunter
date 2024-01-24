import unittest
from unittest.mock import Mock, patch
from datetime import datetime, timedelta
from InternalFactors import reorganizar_resultados
import coverage

class test_factors(unittest.TestCase):
    def test_reorganizar_resultados(self):
        class Record:
            def _init_(self, time, field, value):
                self.values = {'_time': time, '_field': field, '_value': value}

        class Table:
            def _init_(self, records):
                self.records = records

        time1 = datetime(2022, 1, 1)
        time2 = datetime(2022, 1, 2)
        record1 = Record(time1, 'field1', 10)
        record2 = Record(time1, 'field2', 20)
        record3 = Record(time2, 'field1', 30)
        record4 = Record(time2, 'field2', 40)
        table1 = Table([record1, record2])
        table2 = Table([record3, record4])
        result = [table1, table2]

        expected_result = [
            ['2022-01-02', 10, 30],
            ['2022-01-02', 20, 40] 
        ]

        actual_result = reorganizar_resultados(result)

        print("Actual Result:")
        print(actual_result)
        print("Expected Result:")
        print(expected_result)

        self.assertEqual(actual_result, expected_result)

if __name__ == '__main__':
    unittest.main()