import InternalFactors
import Factors
class Search:
    def __init__(self):
        self.search_list = []
        self.initial_date = None
        self.final_date = None

    def create_internal_search(self, initial_date, final_date):
        return InternalFactors.get_internal_factors(initial_date, final_date)
    
    
    def create_search(self, initial_date, final_date):
        return Factors.get_factors(initial_date, final_date)
    
    def create_images(self, path):
        return Factors.analyze_data(path)
    