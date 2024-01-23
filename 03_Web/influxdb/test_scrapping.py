import unittest
from unittest.mock import MagicMock, patch, Mock
from scrapping import Scrapping  # Replace with your actual import for the Scrapping class
from unittest.mock import patch
import datetime

# Importa el módulo que contiene la función que quieres probar
import requests

class Scrapping_object:
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
        scrapping_object = Scrapping_object()
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
        scrapping_object = Scrapping_object()
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
        scrapping_object1 = Scrapping_object()
        scrapping_object2 = Scrapping_object()
        scrapping_object = [scrapping_object1, scrapping_object2]
        scrapping_object[0].text = '1'
        mock_soup.select.return_value = scrapping_object
        mock_beautifulsoup.return_value = mock_soup

        result = Scrapping.IPC_scrapping(self)

        self.assertEqual(result, "1")
    
    @patch('scrapping.BeautifulSoup')
    def test_EUR_scrapping(self, mock_beautifulsoup):
        mock_soup = MagicMock()
        scrapping_object1 = Scrapping_object()
        scrapping_object2 = Scrapping_object()
        scrapping_object3 = Scrapping_object()
        scrapping_object4 = Scrapping_object()
        scrapping_object = [scrapping_object1, scrapping_object2, scrapping_object3, scrapping_object4]
        scrapping_object[1].text = '1'
        scrapping_object[3].text = '0 - 2'
        mock_soup.findAll.return_value = scrapping_object
        scrapping_object5 = Scrapping_object()
        scrapping_object5.text = '3 - Dolares - estadounidenses'
        mock_soup.find.return_value = scrapping_object5
        mock_beautifulsoup.return_value = mock_soup

        open, low, high, current = Scrapping.EUR_scrapping(self)

        self.assertEqual(open, "1")
        self.assertEqual(high, "2")
        self.assertEqual(low, "0")
        self.assertEqual(current, "3")

    @patch('scrapping.BeautifulSoup')
    def test_GDP_scrapping(self, mock_beautifulsoup):
        mock_soup = MagicMock()
        scrapping_object1 = Scrapping_object()
        scrapping_object = [scrapping_object1]
        scrapping_object[0].text = '0,4%'
        mock_soup.select.return_value = scrapping_object
        mock_beautifulsoup.return_value = mock_soup

        result = Scrapping.GDP_scrapping(self)

        self.assertEqual(result, "0,4")

    @patch('scrapping.BeautifulSoup')
    def test_DJ_scrapping(self, mock_beautifulsoup):
        mock_soup = MagicMock()

        mock_soup.find.return_value.text = '4'
        mock_soup.select.side_effect = [
            [MagicMock(text='1')],
            [MagicMock(text='2')],
            [MagicMock(text='3')],
        ]
        mock_beautifulsoup.return_value = mock_soup

        # Call the DJ_scrapping method
        close, low, high, open = Scrapping.DJ_scrapping(self)

        # Perform assertions
        self.assertEqual(close, "4")
        self.assertEqual(low, "1")
        self.assertEqual(high, "2")
        self.assertEqual(open, "3")


    @patch('scrapping.BeautifulSoup')
    def test_IBEX_scrapping(self, mock_beautifulsoup):
        mock_soup = MagicMock()
        # Mocking the necessary soup select and findAll operations
        mock_soup.select.side_effect = [
            [
                MagicMock(text='Close:1'),
                MagicMock(text='Open:2')],
                [MagicMock(text=''),
                MagicMock(text='3'),
                MagicMock(text='4')
            ]
        ]
        mock_beautifulsoup.return_value = mock_soup

        # Call the IBEX_scrapping method
        close, adjClose, open, low, high = Scrapping.IBEX_scrapping(self)

        # Perform assertions
        self.assertEqual(close, "1")
        self.assertEqual(adjClose, "1")
        self.assertEqual(open, "2")
        self.assertEqual(low, "3")
        self.assertEqual(high, "4")

    @patch('scrapping.BeautifulSoup')
    def test_euribor_scrapping(self, mock_beautifulsoup):
        mock_soup = MagicMock()
        scrapping_object1 = Scrapping_object()
        scrapping_object = [scrapping_object1]
        scrapping_object[0].text = '3,672'
        mock_soup.select.return_value = scrapping_object
        mock_beautifulsoup.return_value = mock_soup

        result = Scrapping.euribor_scrapping(self)
        self.assertEqual(result, "3,672")

    @patch('scrapping.BeautifulSoup')
    def test_debt_scrapping(self, mock_beautifulsoup):
        mock_soup = MagicMock()
        # Mocking the necessary soup select and findAll operations
        mock_soup.select.side_effect = [
            [MagicMock(text='1')],
            [MagicMock(text='2%')],
            [MagicMock(text='3')],
        ]
        mock_beautifulsoup.return_value = mock_soup

        debtTotal, debtPercentage, debtPerCapita = Scrapping.debt_scrapping(self)
        self.assertEqual(debtTotal, "1")
        self.assertEqual(debtPercentage, "2")
        self.assertEqual(debtPerCapita, "3")

    @patch('scrapping.BeautifulSoup')
    def test_activity_scrapping(self, mock_beautifulsoup):
        mock_soup = MagicMock()
        # Mocking the necessary soup select and findAll operations
        mock_soup.select.side_effect = [
            [MagicMock(text='3,5')],
            [MagicMock(text='4,5')],
            [MagicMock(text='1')],
            [MagicMock(text='2')],
        ]
        mock_beautifulsoup.return_value = mock_soup

        activos, ocupados, parados, activityRate, unemploymentRate = Scrapping.activity_scrapping(self)
        self.assertEqual(activos, 8)
        self.assertEqual(ocupados, 3.5)
        self.assertEqual(parados, 4.5)
        self.assertEqual(activityRate, "1")
        self.assertEqual(unemploymentRate, "2")


if __name__ == '__main__':
    unittest.main()