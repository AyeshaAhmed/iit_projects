"""
Assignment 5: K-Means. See the instructions to complete the methods below.
"""

from collections import Counter
import gzip
import math
from collections import defaultdict
import numpy as np

class KMeans(object):

    def __init__(self, k=2):
        """ Initialize a k-means clusterer. Should not have to change this."""
        self.k = k

    def cluster(self, documents, iters=10):
        """
        Cluster a list of unlabeled documents, using iters iterations of k-means.
        Initialize the k mean vectors to be the first k documents provided.
        After each iteration, print:
        - the number of documents in each cluster
        - the error rate (the total Euclidean distance between each document and its assigned mean vector), rounded to 2 decimal places.
        See Log.txt for expected output.
        The order of operations is:
        1) initialize means
        2) Loop
          2a) compute_clusters
          2b) compute_means
          2c) print sizes and error
        """
        ###TODO
        self.meanDocs = documents[0:self.k]
        self.docs = documents

        self.meanVect = []
        for doc in self.meanDocs:
            self.meanVect.append(float(np.dot(list(doc.values()), list(doc.values()))))

        for i in range(0, iters):
            self.clusters = defaultdict(list)
            print(self.compute_clusters(documents))
            self.meanDocs = self.compute_means()
            print("%.2f" % self.error(documents))

        self.clusters = defaultdict(list)
        self.compute_clusters(documents)
        pass

    def compute_means(self):
        """ Compute the mean vectors for each cluster (results stored in an
        instance variable of your choosing)."""
        ###TODO
        self.meanVect = []

        for doc in self.meanDocs:
            self.meanVect.append(float(np.dot(list(doc.values()), list(doc.values()))))

        for clusterID, values in self.clusters.items():
            cntr = Counter()
            for doc_idx, dist in values:
                cntr.update(self.docs[doc_idx])
            for key, value in cntr.items():
                cntr[key] = float(value) / float(len(values))
            self.meanDocs[clusterID] = cntr

        return self.meanDocs
        pass

    def compute_clusters(self, documents):
        """ Assign each document to a cluster. (Results stored in an instance
        variable of your choosing). """
        ###TODO
        for doc_idx in range(0, len(documents)):
            distance = []
            for clusterID in range(0, self.k):
                distance.append((doc_idx, self.distance(documents[doc_idx], self.meanDocs[clusterID], self.meanVect[clusterID])))
            min_distance = min(distance, key = lambda x : x[1])
            self.clusters[distance.index(min_distance)].append(min_distance)

        result = []
        for clusterID in self.clusters.keys():
            result.append(len(self.clusters[clusterID]))
        return result
        pass

    def sqnorm(self, d):
        """ Return the vector length of a dictionary d, defined as the sum of
        the squared values in this dict. """
        ###TODO
        pass

    def distance(self, doc, mean, mean_norm):
        """ Return the Euclidean distance between a document and a mean vector.
        See here for a more efficient way to compute:
        http://en.wikipedia.org/wiki/Cosine_similarity#Properties"""
        ###TODO
        first = mean_norm
        second = float(sum([list(doc.values())[i] ** 2 for i in range(0, len(list(doc.values())))]))
        first_second = 0.0
        for key in doc.keys():
            if key in mean:
                first_second += mean[key] * doc[key]

        return float(math.sqrt(first + second - 2.0 * first_second))
        pass

    def error(self, documents):
        """ Return the error of the current clustering, defined as the total
        Euclidean distance between each document and its assigned mean vector."""
        ###TODO
        result = 0.0
        self.meanVect = []

        for doc in self.meanDocs:
            self.meanVect.append(float(np.dot(list(doc.values()), list(doc.values()))))

        for clusterID, values in self.clusters.items():
            for docID, dist in values:
                result += self.distance(documents[docID], self.meanDocs[clusterID], self.meanVect[clusterID])
        return round(result,2)
        pass

    def print_top_docs(self, n=10):
        """ Print the top n documents from each cluster. These are the
        documents that are the closest to the mean vector of each cluster.
        Since we store each document as a Counter object, just print the keys
        for each Counter (sorted alphabetically).
        Note: To make the output more interesting, only print documents with more than 3 distinct terms.
        See Log.txt for an example."""
        ###TODO
        for clusterID, values in self.clusters.items():
            alist = sorted(values, key=lambda x: x[1])

            print('CLUSTER ' + str(clusterID))
            iter = 0
            if len(alist) > n:
                iter = n
            else:
                iter = len(alist)
            i = 0
            while i < iter:
                if len(self.docs[alist[i][0]]) > 3:
                    print(' '.join(sorted([k for k in self.docs[alist[i][0]]], key=lambda x: x)))
                else:
                    iter += 1
                i += 1
        pass


def prune_terms(docs, min_df=3):
    """ Remove terms that don't occur in at least min_df different
    documents. Return a list of Counters. Omit documents that are empty after
    pruning words.
    >>> prune_terms([{'a': 1, 'b': 10}, {'a': 1}, {'c': 1}], min_df=2)
    [Counter({'a': 1}), Counter({'a': 1})]
    """
    ###TODO
    result = []
    term_cnt = Counter()

    for document in docs:
        for term, value in document.items():
            term_cnt[term] += 1

    for document in docs:
        tmp_dict = {}
        for term in document.keys():
            if term_cnt[term] >= min_df:
                tmp_dict[term]=document[term]
        if len(tmp_dict) > 0:
            result.append(Counter(tmp_dict))

    return result
    pass

def read_profiles(filename):
    """ Read profiles into a list of Counter objects.
    DO NOT MODIFY"""
    profiles = []
    with gzip.open(filename, mode='rt', encoding='utf8') as infile:
        for line in infile:
            profiles.append(Counter(line.split()))
    return profiles


def main():
    profiles = read_profiles('profiles.txt.gz')
    print('read', len(profiles), 'profiles.')
    profiles = prune_terms(profiles, min_df=2)
    km = KMeans(k=10)
    km.cluster(profiles, iters=20)
    km.print_top_docs()

if __name__ == '__main__':
    main()
