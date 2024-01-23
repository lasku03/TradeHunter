import unittest
from unittest.mock import MagicMock, patch, Mock
from scrapping import Scrapping  # Replace with your actual import for the Scrapping class
from unittest.mock import patch
import datetime

# Importa el módulo que contiene la función que quieres probar
import requests

class Scapping_object:
    def __init__(self):
        self.text = ""

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

    @patch('scrapping.BeautifulSoup')
    def test_births_scrapping(self, mock_beautifulsoup):
        # Set up your mock BeautifulSoup object
        mock_soup = MagicMock()
        scrapping_object = Scapping_object()
        scrapping_object.text = '373'
        mock_soup.find.return_value = scrapping_object  # You can set any string you want here
        mock_beautifulsoup.return_value = mock_soup
        TIME = datetime.datetime.now()
        births = 373/TIME.hour
        births = int(births * 24)

        # Call the method you want to test
        result = Scrapping.births_scrapping(self)

        # Assert that the method returned the expected result
        self.assertEqual(result, births)

    @patch('scrapping.BeautifulSoup')
    def test_deaths_scrapping(self, mock_beautifulsoup):
        # Set up your mock BeautifulSoup object
        mock_soup = MagicMock()
        scrapping_object = Scapping_object()
        scrapping_object.text = '373'
        mock_soup.find.return_value = scrapping_object  # You can set any string you want here
        mock_beautifulsoup.return_value = mock_soup
        TIME = datetime.datetime.now()
        deaths = 373/TIME.hour
        deaths = int(deaths * 24)

        # Call the method you want to test
        result = Scrapping.deaths_scrapping(self)

        # Assert that the method returned the expected result
        self.assertEqual(result, deaths)

    @patch('scrapping.BeautifulSoup')
    def test_IPC_scrapping(self, mock_beautifulsoup):
        mock_soup = MagicMock()
        scrapping_object = Scapping_object()
        scrapping_object.text = '1'
        mock_soup.select.return_value = scrapping_object
        mock_beautifulsoup.return_value = mock_soup

        result = Scrapping.IPC_scrapping(self)

        self.assertEqual(result, 1)


if __name__ == '__main__':
    unittest.main()