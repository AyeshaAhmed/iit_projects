""" Assignment 2
"""
import abc

import numpy as np


class EvaluatorFunction:
    """
    An Abstract Base Class for evaluating search results.
    """
    __metaclass__ = abc.ABCMeta

    @abc.abstractmethod
    def evaluate(self, hits, relevant):
        """
        Do not modify.
        Params:
          hits...A list of document ids returned by the search engine, sorted
                 in descending order of relevance.
          relevant...A list of document ids that are known to be
                     relevant. Order is insignificant.
        Returns:
          A float indicating the quality of the search results, higher is better.
        """
        return


class Precision(EvaluatorFunction):

    def evaluate(self, hits, relevant):
        """
        Compute precision.

        >>> Precision().evaluate([1, 2, 3, 4], [2, 4])
        0.5
        """
        ###TODO
        tpos = 0.0
        fpos = 0.0
        for docID in range(0,len(hits)):
            if hits[docID] in relevant:
                tpos = tpos + 1
            else:
                fpos = fpos + 1
        sum = tpos + fpos
        precs = 0.0
        if sum != 0:
            precs = tpos / sum
        return precs
        pass

    def __repr__(self):
        return 'Precision'


class Recall(EvaluatorFunction):

    def evaluate(self, hits, relevant):
        """
        Compute recall.

        >>> Recall().evaluate([1, 2, 3, 4], [2, 5])
        0.5
        """
        ###TODO
        tpos = 0.0
        fpos = 0.0
        for docID in range(0,len(hits)):
            if hits[docID] in relevant:
                tpos = tpos + 1
            else:
                fpos = fpos + 1
        fneg = len(relevant) - tpos
        sum = tpos + fneg
        if sum != 0:
            recall = tpos / sum
        return recall
        pass

    def __repr__(self):
        return 'Recall'


class F1(EvaluatorFunction):
    def evaluate(self, hits, relevant):
        """
        Compute F1.

        >>> F1().evaluate([1, 2, 3, 4], [2, 5])  # doctest:+ELLIPSIS
        0.333...
        """
        ###TODO
        precis_res = Precision().evaluate(hits, relevant)
        recall_res = Recall().evaluate(hits, relevant)
        if(precis_res * recall_res != 0):
           f1_result = float(2 * precis_res * recall_res / (precis_res + recall_res)) #lecture 9
        else:
            return

        return f1_result
        pass

    def __repr__(self):
        return 'F1'


class MAP(EvaluatorFunction):
    def evaluate(self, hits, relevant):
        """
        Compute Mean Average Precision.

        >>> MAP().evaluate([1, 2, 3, 4, 5, 6, 7, 8, 9, 10], [1, 4, 6, 11, 12, 13, 14, 15, 16, 17])
        0.2
        """
        ###TODO
        resultMAP = 0
        for q in hits:
            i = 0
            for denom, doc in enumerate(hits):
                if doc in relevant:
                    i += 1
                    resultMAP += round(float(i / (denom+1)), 1)
            if len(hits) != 0:
                resultMAP = round(float(resultMAP / len(hits)), 1)#lecture 9 mean avg precis

        return resultMAP
        pass

    def __repr__(self):
        return 'MAP'
