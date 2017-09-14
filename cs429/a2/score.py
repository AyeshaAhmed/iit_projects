""" Assignment 2
"""
import abc
from collections import defaultdict
import math

import index


def idf(term, index):
    """ Compute the inverse document frequency of a term according to the
    index. IDF(T) = log10(N / df_t), where N is the total number of documents
    in the index and df_t is the total number of documents that contain term
    t.

    Params:
      terms....A string representing a term.
      index....A Index object.
    Returns:
      The idf value.

    >>> idx = index.Index(['a b c a', 'c d e', 'c e f'])
    >>> idf('a', idx) # doctest:+ELLIPSIS
    0.477...
    >>> idf('d', idx) # doctest:+ELLIPSIS
    0.477...
    >>> idf('e', idx) # doctest:+ELLIPSIS
    0.176...
    """
    ###TODO
    totalDocs = len(index.documents)
    termFreq = index.doc_freqs[term]
    return math.log10( totalDocs/termFreq)
    pass


class ScoringFunction:
    """ An Abstract Base Class for ranking documents by relevance to a
    query. """
    __metaclass__ = abc.ABCMeta

    @abc.abstractmethod
    def score(self, query_vector, index):
        """
        Do not modify.

        Params:
          query_vector...dict mapping query term to weight.
          index..........Index object.
        """
        return


class RSV(ScoringFunction):
    """
    See lecture notes for definition of RSV.

    idf(a) = log10(3/1)
    idf(d) = log10(3/1)
    idf(e) = log10(3/2)
    >>> idx = index.Index(['a b c', 'c d e', 'c e f'])
    >>> rsv = RSV()
    >>> rsv.score({'a': 1.}, idx)[1]  # doctest:+ELLIPSIS
    0.4771...
    """

    def score(self, query_vector, index):
        ###TODO
        qidf_value_dict = defaultdict(float)
        res_RSVscore = defaultdict(float)

        for qkey, qval in set(query_vector.items()):
            qidf_value_dict[qkey] = idf(qkey, index)

        docID = 0
        for doc in index.documents:
            docID +=1
            for qvec_tok in set(query_vector.keys()):
                if qvec_tok in doc:
                    res_RSVscore[docID] += qidf_value_dict[qvec_tok]

        return res_RSVscore   #document ID score
        pass

    def __repr__(self):
        return 'RSV'


class BM25(ScoringFunction):
    """
    See lecture notes for definition of BM25.

    log10(3) * (2*2) / (1(.5 + .5(4/3.333)) + 2) = log10(3) * 4 / 3.1 = .6156...
    >>> idx = index.Index(['a a b c', 'c d e', 'c e f'])
    >>> bm = BM25(k=1, b=.5)
    >>> bm.score({'a': 1.}, idx)[1]  # doctest:+ELLIPSIS
    0.61564032...
    """
    def __init__(self, k=1, b=.5):
        self.k = k
        self.b = b

    def score(self, query_vector, index):
        ###TODO
        ###resultRSV = RSV().score(self, query_vector, index)
        qidf_value_dict = defaultdict(float)
        docscore_dict = defaultdict(float)

        for qkey, qval in set(query_vector.items()):
            qidf_value_dict[qkey] = idf(qkey, index)

        docID = 0
        for doc in index.documents:
            docID +=1
            for qvec_tok in set(query_vector.keys()):
                if qvec_tok in doc:
                    tf = 0
                    tf_Llists = index.index[qvec_tok]
                    for tf_l in tf_Llists:
                        if(tf_l[0] == docID):
                            tf = tf_l[1]
                    docscore_dict[docID] += qidf_value_dict[qvec_tok]*float(((self.k + 1)*tf)/((self.k*((1-self.b)+(self.b*(index.doc_lengths[docID]/index.mean_doc_length)))) + tf))

        return docscore_dict
        pass

    def __repr__(self):
        return 'BM25 k=%d b=%.2f' % (self.k, self.b)


class Cosine(ScoringFunction):
    """
    See lecture notes for definition of Cosine similarity.  Be sure to use the
    precomputed document norms (in index), rather than recomputing them for
    each query.

    >>> idx = index.Index(['a a b c', 'c d e', 'c e f'])
    >>> cos = Cosine()
    >>> cos.score({'a': 1.}, idx)[1]  # doctest:+ELLIPSIS
    0.792857...
    """
    def score(self, query_vector, index):
        ###TODO
        cosineIndex = defaultdict(int)
        #For each term or token
        for token in query_vector:
            #Retrieve the index and apply formulae on it
            for token_List in index.index[token]:
                #weight using tf and idf
                weight = query_vector[token] * float((1 + math.log10(token_List[1])) * (idf(token, index)))
                cosineIndex[token_List[0]] = cosineIndex[token_List[0]]+ weight
        #Using the precomputed norms
        for doc_id in cosineIndex.keys():
            norms = index.doc_norms[doc_id]
            cosineIndex[doc_id] = cosineIndex[doc_id]/norms
        return dict(cosineIndex)
        pass

    def __repr__(self):
        return 'Cosine'
