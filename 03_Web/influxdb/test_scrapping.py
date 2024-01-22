import unittest
from unittest.mock import MagicMock, patch, Mock
from scrapping import Scrapping  # Replace with your actual import for the Scrapping class
from unittest.mock import patch

# Importa el módulo que contiene la función que quieres probar
import requests

class TestScrapping(unittest.TestCase):

    def test_init(self):
        scraper = Scrapping()

        self.assertEqual(scraper.activity_ocupados, 0)
        self.assertEqual(scraper.activity_parados, 0)
        self.assertEqual(scraper.activity_activity_rate, 0)
        self.assertEqual(scraper.activity_unemployment_rate, 0)
        self.assertEqual(scraper.births, 0)
        self.assertEqual(scraper.deaths, 0)
        self.assertEqual(scraper.total_debt, 0)
        self.assertEqual(scraper.debt_percentage, 0)
        self.assertEqual(scraper.debt_per_capita, 0)
        self.assertEqual(scraper.DJ_close, 0)
        self.assertEqual(scraper.DJ_low, 0)
        self.assertEqual(scraper.DJ_high, 0)
        self.assertEqual(scraper.DJ_open, 0)
        self.assertEqual(scraper.EUR_open, 0)
        self.assertEqual(scraper.EUR_low, 0)
        self.assertEqual(scraper.EUR_high, 0)
        self.assertEqual(scraper.EUR_current, 0)
        self.assertEqual(scraper.GDP, 0)
        self.assertEqual(scraper.IBEX_close, 0)
        self.assertEqual(scraper.IBEX_adjclose, 0)
        self.assertEqual(scraper.IBEX_open, 0)
        self.assertEqual(scraper.IBEX_low, 0)
        self.assertEqual(scraper.IBEX_high, 0)
        self.assertEqual(scraper.IPC, 0)
        self.assertEqual(scraper.euribor, 0)
        self.assertEqual(scraper.row, [])


if __name__ == '__main__':
    unittest.main()