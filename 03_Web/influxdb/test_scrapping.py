import unittest
from unittest.mock import MagicMock, patch, Mock
from scrapping import Scrapping 
import datetime
import coverage

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
        self.assertEqual(scraper.dj_close, 0)
        self.assertEqual(scraper.dj_low, 0)
        self.assertEqual(scraper.dj_high, 0)
        self.assertEqual(scraper.dj_open, 0)
        self.assertEqual(scraper.eur_open, 0)
        self.assertEqual(scraper.eur_low, 0)
        self.assertEqual(scraper.eur_high, 0)
        self.assertEqual(scraper.eur_current, 0)
        self.assertEqual(scraper.GDP, 0)
        self.assertEqual(scraper.ibex_close, 0)
        self.assertEqual(scraper.ibex_adjclose, 0)
        self.assertEqual(scraper.ibex_open, 0)
        self.assertEqual(scraper.ibex_low, 0)
        self.assertEqual(scraper.ibex_high, 0)
        self.assertEqual(scraper.IPC, 0)
        self.assertEqual(scraper.euribor, 0)
        self.assertEqual(scraper.row, [])

    @patch('scrapping.BeautifulSoup')
    def test_births_scrapping(self, mock_beautifulsoup):
        mock_soup = MagicMock()

        mock_soup.find.return_value.text.split.return_value = ['300']
        mock_beautifulsoup.return_value = mock_soup
        births = Scrapping.births_scrapping(self)
        TIME = datetime.datetime.now()
        result = 300/TIME.hour
        result = int(result * 24)
        self.assertEqual(births, result)

    @patch('scrapping.BeautifulSoup')
    def test_deaths_scrapping(self, mock_beautifulsoup):
        mock_soup = MagicMock()

        mock_soup.find.return_value.text.split.return_value = ['300']
        mock_beautifulsoup.return_value = mock_soup
        deaths = Scrapping.deaths_scrapping(self)
        TIME = datetime.datetime.now()
        result = 300/TIME.hour
        result = int(result * 24)
        self.assertEqual(deaths, result)

    @patch('scrapping.BeautifulSoup')
    def test_ipc_scrapping(self, mock_beautifulsoup):

        mock_soup = MagicMock()

        mock_soup.select.side_effect = [
            [MagicMock(text='1')],
        ]

        mock_beautifulsoup.return_value = mock_soup

        ipc_value = Scrapping.ipc_scrapping(self)

        self.assertEqual(ipc_value, '1')
    
    @patch('scrapping.BeautifulSoup')
    def test_eur_scrapping(self, mock_beautifulsoup):
        mock_soup = MagicMock()

        mock_soup.findAll.side_effect = [
            [MagicMock(text=''),
            MagicMock(text='1'),
            MagicMock(text=''),
            MagicMock(text='0 - 2')],
        ]
        mock_soup.find.return_value.text.split.return_value = ['5']

        mock_beautifulsoup.side_effect = [mock_soup, mock_soup]

        open_value, low_value, high_value, current_value = Scrapping.eur_scrapping(self)

        self.assertEqual(open_value, '1')
        self.assertEqual(low_value, '0')
        self.assertEqual(high_value, '2')
        self.assertEqual(current_value, '5')

    @patch('scrapping.BeautifulSoup')
    def test_gdp_scrapping(self, mock_beautifulsoup):
        mock_soup = MagicMock()

        mock_soup.select.side_effect = [
            [MagicMock(text='0.4%')],
        ]

        mock_beautifulsoup.return_value = mock_soup

        gdp_value = Scrapping.gdp_scrapping(self)

        self.assertEqual(gdp_value, '0.4')

    @patch('scrapping.BeautifulSoup')
    def test_dj_scrapping(self, mock_beautifulsoup):
        mock_soup = MagicMock()

        mock_soup.find.return_value.text = '4'
        mock_soup.select.side_effect = [
            [MagicMock(text='1')],
            [MagicMock(text='2')],
            [MagicMock(text='3')],
        ]
        mock_beautifulsoup.return_value = mock_soup

        close, low, high, _open = Scrapping.dj_scrapping(self)

        self.assertEqual(close, "4")
        self.assertEqual(low, "1")
        self.assertEqual(high, "2")
        self.assertEqual(_open, "3")


    @patch('scrapping.BeautifulSoup')
    def test_ibex_scrapping(self, mock_beautifulsoup):
        mock_soup = MagicMock()
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

        close, adj_close, _open, low, high = Scrapping.ibex_scrapping(self)

        self.assertEqual(close, "1")
        self.assertEqual(adj_close, "1")
        self.assertEqual(_open, "2")
        self.assertEqual(low, "3")
        self.assertEqual(high, "4")

    @patch('scrapping.BeautifulSoup')
    def test_euribor_scrapping(self, mock_beautifulsoup):
        mock_soup = MagicMock()

        mock_soup.select.side_effect = [
            [MagicMock(text='3,672')],
        ]

        mock_beautifulsoup.return_value = mock_soup

        euribor_value = Scrapping.euribor_scrapping(self)

        self.assertEqual(euribor_value, '3,672')

    @patch('scrapping.BeautifulSoup')
    def test_debt_scrapping(self, mock_beautifulsoup):
        mock_soup = MagicMock()
        mock_soup.select.side_effect = [
            [MagicMock(text='1')],
            [MagicMock(text='2%')],
            [MagicMock(text='3')],
        ]
        mock_beautifulsoup.return_value = mock_soup

        debt_total, debt_percentage, debt_per_capita = Scrapping.debt_scrapping(self)
        self.assertEqual(debt_total, "1")
        self.assertEqual(debt_percentage, "2")
        self.assertEqual(debt_per_capita, "3")

    @patch('scrapping.BeautifulSoup')
    def test_activity_scrapping(self, mock_beautifulsoup):
        mock_soup = MagicMock()
        
        mock_soup.select.side_effect = [
            [MagicMock(text='3,5')],
            [MagicMock(text='4,5')],
            [MagicMock(text='1')],
            [MagicMock(text='2')]
        ]
        mock_beautifulsoup.return_value = mock_soup

        activos, ocupados, parados, activity_rate, unemployment_rate = Scrapping.activity_scrapping(self)
        self.assertEqual(activos, 8)
        self.assertEqual(ocupados, 3.5)
        self.assertEqual(parados, 4.5)
        self.assertEqual(activity_rate, "1")
        self.assertEqual(unemployment_rate, "2")


if __name__ == '__main__':
    unittest.main()