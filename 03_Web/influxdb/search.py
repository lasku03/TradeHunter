import getInternalFactors
class Search:
    def __init__(self):
        self.search_list = []
        self.initialDate = None
        self.finalDate = None

    def createSearch(self, initialDate, finalDate):
        return getInternalFactors.get_internal_factors(initialDate, finalDate)
    