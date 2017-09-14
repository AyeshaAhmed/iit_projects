""" Assignment 2
"""
from collections import Counter, defaultdict
import math
import re

import numpy as np


class Index(object):

    def __init__(self, docs=None):
        """ Do not modify.
        Create a new index by parsing the given file containing documents,
        one per line."""
        self.documents = docs
        if docs:
            self.documents = [self.tokenize(d) for d in self.documents]
            self.doc_freqs = self.count_doc_frequencies(self.documents)
            self.index = self.create_tf_index(self.documents, self.doc_freqs)
            self.doc_lengths, self.mean_doc_length = self.compute_doc_lengths(self.index)
            self.doc_norms = self.compute_doc_norms(self.index, len(self.documents), self.doc_freqs)

    def compute_doc_norms(self, index, n_docs, doc_freqs):
        """
        Return a dict mapping doc_id to its norm, computed as sqrt(sum(w_i**2)),
        where w_i is the tf-idf weight for each term in the document.
        This differs from the previous assignment, since the index only contains
        term frequency values, not tf-idf values. Thus, you'll have to compute
        tf-idf values while computing the norms.

        If tf_td is the frequency of term t in document d, then the tf-idf
        weight of term t in document d is:
          tfidf_t = (1 + log10(tf_{td})) * log10(N / df_t)
        where N is the total number of documents.

        E.g., in the sample index below, document 0 has two terms 'a' (with
        tf 3) and 'b' (with tf 4). There are 2 total documents, and 'a' has df 1 and
        'b' has df 2. Thus, the length of document 0 is:

          sqrt( ((1 + log10(3)) * log10(2 / 1) )**2  + ((1 + log10(4.)) * log10(2. / 2.) )**2 )
          = 0.444...

        Params:
            index.......A dict mapping term to list of (doc_id, term_frequency) tuples.
            n_docs......The total number of documents in the index.
            doc_freqs...A dict mapping a term to the number of unique documents that contain it.
        Returns:
            A dict mapping a document id to its tfidf vector norm.
        >>> norms = Index().compute_doc_norms({'a': [[0, 3]], 'b': [[0, 4], [1, 5]]}, 2, {'a': 1, 'b': 2})
        >>> norms[0] # doctest:+ELLIPSIS
        0.444...
        """
        ###TODO
        norm_dict = defaultdict(float)
        for _id, tval in index.items():
            for docID in tval:
                a = (1 + math.log10(docID[1]))
                b = math.log10(n_docs / doc_freqs[_id])
                weight = (a * b) ** 2
                norm_dict[docID[0]] = norm_dict[docID[0]] + weight

        for _id, val in norm_dict.items():
            norm_dict[_id] = math.sqrt(norm_dict[_id])
        return dict(norm_dict)
        pass

    def compute_doc_lengths(self, index):
        """
        Count the number of terms in each document. Also return the mean value.

        Params:
          index...A dict mapping term to list of (doc_id, term_frequency) tuples.
        Returns:
          doc_lengths...A dict mapping document id to the number of terms
          mean_length...the mean document length
        >>> lengths, mean = Index().compute_doc_lengths({'a': [[0, 3]], 'b': [[0, 4], [1, 5]]})
        >>> lengths[0]
        7.0
        >>> lengths[1]
        5.0
        >>> mean
        6.0
        """
        ###TODO
        doclen = defaultdict(float)#in a1 I used lambda: 0 instead of float
        for value in index.values():
            for weight in value:
                doclen[weight[0]] += weight[1]  #**2
        #for key,value in doclen.items():
            #doclen[key] = math.sqrt(value)

        meanlen = 0
        if len(doclen) >= 0:
            meanlen = float(sum(doclen.values()) / len(doclen))
        return doclen, meanlen
        pass

    def create_tf_index(self, docs, doc_freqs):
        """
        Create an index in which each postings list contains a list of
        [doc_id, tf weight] pairs. For example:

        {'a': [[0, 1], [10, 2]],
         'b': [[5, 1]]}

        This entry means that the term 'a' appears in document 0 (with tf
        weight 1) and in document 10 (with tf weight 2). The term 'b'
        appears in document 5 (with tf-idf weight 1).

        Note that documents should start at index 1 to match the relevance files.

        Parameters:
        docs........list of lists, where each sublist contains the tokens for one document.
        doc_freqs...dict from term to document frequency (see count_doc_frequencies).

        >>> index = Index().create_tf_index([['a', 'b', 'a'], ['a']], {'a': 2., 'b': 1., 'c': 1.})
        >>> sorted(index.keys())
        ['a', 'b']
        >>> index['a']
        [[1, 2.0], [2, 1.0]]
        """
        ###TODO
        tf_val_dict = defaultdict(list)
        for d in docs:
            for term in list(set(d)):
                term_count = 0.0
                term_count = d.count(term)
                tf_val_dict[term] += [[docs.index(d)+1,float(term_count)]]

        for _id, doc in tf_val_dict.items():
            tf_val_dict[_id]= sorted(tf_val_dict[_id], key=lambda item: item[0])
        return dict(tf_val_dict)
        pass

    def count_doc_frequencies(self, docs):
        """
        Params:
          docs: A list of lists of tokens, one per document. This is the
                output of the tokenize method.
        Returns:
          A dict mapping from a term to the number of documents that contain it.

        >>> res = Index().count_doc_frequencies([['a', 'b', 'a'], ['a', 'b', 'c'], ['a']])
        >>> res['a']
        3.0
        >>> res['b']
        2.0
        >>> res['c']
        1.0
        """
        ###TODO
        docfreq = {}
        for _id, val in enumerate(docs):
            mval =  list(set(val))
            for item in mval:
                if item in docfreq and _id!=0:
                    docfreq[item].append( _id )
                else:
                    docfreq[item] = [_id]
        for word in docfreq:
            docfreq[word] = float(len(docfreq[word]))
        return docfreq
        pass

    def query_to_vector(self, query_terms):
        """ Convert a list of query terms into a dict mapping each term to its
        inverse document frequency: IDF= N / log10(document frequency of T),
        where N is the total number of documents.  You may need to use the
        instance variables of the Index object to compute this. Do not modify
        the method signature.

        If a query term is not in the index, simply omit it from the result.

        The frequency of a term in the query does not affect the result.

        Parameters:
          query_terms....list of terms

        Returns:
          A dict from query term to IDF.

        >>> idx = Index(['a b c', 'b c', 'd'])
        >>> idx.query_to_vector(['a']) # doctest:+ELLIPSIS
        {'a': 0.477...}
        >>> idx.query_to_vector(['a', 'a']) # doctest:+ELLIPSIS
        {'a': 0.477...}
        >>> res = idx.query_to_vector(['a', 'b', 'c', 'd']) # doctest:+ELLIPSIS
        >>> res['a'] # doctest:+ELLIPSIS
        0.477...
        >>> res['b'] # doctest:+ELLIPSIS
        0.176...
        """
        ###TODO
        inv_dict = defaultdict(int)
        for toks in query_terms:
            if toks in self.doc_freqs.keys():
                doclen = float(len(self.documents))
                wordfreq = self.doc_freqs[toks]
                inv_dict[toks] = math.log10(doclen/wordfreq)
        return dict(inv_dict)
        pass

    def tokenize(self, document):
        """ DO NOT MODIFY.
        Convert a string representing one document into a list of
        words. Retain hyphens and apostrophes inside words. Remove all other
        punctuation and convert to lowercase.

        >>> Index().tokenize("Hi there. What's going on? first-class")
        ['hi', 'there', "what's", 'going', 'on', 'first-class']
        """
        return [t.lower() for t in re.findall(r"\w+(?:[-']\w+)*", document)]
