import InternalFactors
import Factors
class Search:
    def __init__(self):
        self.search_list = []
        self.initialDate = None
        self.finalDate = None

    def createInternalSearch(self, initialDate, finalDate):
        return InternalFactors.get_internal_factors(initialDate, finalDate)
    
    
    def createSearch(self, initialDate, finalDate):
        return Factors.get_factors(initialDate, finalDate)
    
    def createImages(self, path):
        return Factors.analyzeData(path)
    