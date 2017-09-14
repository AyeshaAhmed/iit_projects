"""
Assignment 3. Implement a Multinomial Naive Bayes classifier for spam filtering.

You'll only have to implement 3 methods below:

train: compute the word probabilities and class priors given a list of documents labeled as spam or ham.
classify: compute the predicted class label for a list of documents
evaluate: compute the accuracy of the predicted class labels.

"""

from collections import defaultdict
import glob
import math
import os
import collections


class Document(object):
    """ A Document. Do not modify.
    The instance variables are:

    filename....The path of the file for this document.
    label.......The true class label ('spam' or 'ham'), determined by whether the filename contains the string 'spmsg'
    tokens......A list of token strings.
    """

    def __init__(self, filename=None, label=None, tokens=None):
        """ Initialize a document either from a file, in which case the label
        comes from the file name, or from specified label and tokens, but not
        both.
        """
        if label: # specify from label/tokens, for testing.
            self.label = label
            self.tokens = tokens
        else: # specify from file.
            self.filename = filename
            self.label = 'spam' if 'spmsg' in filename else 'ham'
            self.tokenize()

    def tokenize(self):
        self.tokens = ' '.join(open(self.filename).readlines()).split()


class NaiveBayes(object):

    def __init__(self):
        self.ham_prior = 0
        self.spam_prior = 0
        self.cond_probs = defaultdict(lambda: {'spam':0,'ham':0})
        self.ham_txt=[]
        self.spam_txt=[]

    def get_word_probability(self, label, term):
        """
        Return Pr(term|label). This is only valid after .train has been called.

        Params:
          label: class label.
          term: the term
        Returns:
          A float representing the probability of this term for the specified class.

        >>> docs = [Document(label='spam', tokens=['a', 'b']), Document(label='spam', tokens=['b', 'c']), Document(label='ham', tokens=['c', 'd'])]
        >>> nb = NaiveBayes()
        >>> nb.train(docs)
        >>> nb.get_word_probability('spam', 'a')
        0.25
        >>> nb.get_word_probability('spam', 'b')
        0.375
        """
        ###TODO
        if label == 'spam':
            return self.condprobSpam[term]
        else:
            return self.condprobHam[term]
        pass

    def get_top_words(self, label, n):
        """ Return the top n words for the specified class, using the odds ratio.
        The score for term t in class c is: p(t|c) / p(t|c'), where c'!=c.

        Params:
          labels...Class label.
          n........Number of values to return.
        Returns:
          A list of (float, string) tuples, where each float is the odds ratio
          defined above, and the string is the corresponding term.  This list
          should be sorted in descending order of odds ratio.

        >>> docs = [Document(label='spam', tokens=['a', 'b']), Document(label='spam', tokens=['b', 'c']), Document(label='ham', tokens=['c', 'd'])]
        >>> nb = NaiveBayes()
        >>> nb.train(docs)
        >>> nb.get_top_words('spam', 2)
        [(2.25, 'b'), (1.5, 'a')]
        """
        ###TODO
        top = []
        for word in self.condprobSpam.keys():
            cpSPAM = self.condprobSpam[word]
            cpHAM = self.condprobHam[word]
            if label == 'spam'and cpHAM != 0:
                top.append((cpSPAM/cpHAM,word))
            elif label == 'ham' and cpSPAM != 0:
                top.append((cpHAM/cpSPAM,word))
        top.sort(reverse=True)
        return top[:n]
        pass

    def train(self, documents):
        """
        Given a list of labeled Document objects, compute the class priors and
        word conditional probabilities, following Figure 13.2 of your
        book. Store these as instance variables, to be used by the classify
        method subsequently.
        Params:
          documents...A list of training Documents.
        Returns:
          Nothing.
        """
        ###TODO
        vocab = [] #V in algo fig.13.2
        numDocs = len(documents) #N in algo fig.13.2
        uniqVocabs = []
        self.numLabels = defaultdict(int)
        self.prior = defaultdict(float)
        self.condprobSpam = defaultdict(lambda:0)
        self.condprobHam = defaultdict(lambda:0)
        spamtxt = []
        hamtxt = []

        for f in documents:
            for doc in f.tokens:
                vocab.append(doc)
            #end for loop
            self.numLabels[f.label] += 1
            if f.label == "spam":
                for doc in f.tokens:
                    spamtxt.append(doc)
            elif f.label == "ham":
                for doc in f.tokens:
                    hamtxt.append(doc)
            #end if
        #end for loop
        uniqVocabs = list(set(vocab))
        spamD = len(spamtxt)+len(uniqVocabs)
        hamD = len(hamtxt)+len(uniqVocabs)
        numSpamtoks = collections.Counter(spamtxt)
        numHamtoks = collections.Counter(hamtxt)

        for l in self.numLabels:
            self.prior[l] = float(self.numLabels[l]) / numDocs
        #end for loop
        for word in uniqVocabs:
            spamN = numSpamtoks[word]+1
            hamN = numHamtoks[word]+1
            self.condprobSpam[word] = float(spamN/spamD)
            self.condprobHam[word] = float(hamN/hamD)
        #end for loop
        pass

    def classify(self, documents):
        """ Return a list of strings, either 'spam' or 'ham', for each document.
        Params:
          documents....A list of Document objects to be classified.
        Returns:
          A list of label strings corresponding to the predictions for each document.
        """
        ###TODO
        results = []
        score = {}
        for f in documents:
            for k in self.numLabels:
                score[k] = math.log10(self.prior[k])
            for doc in list(set(f.tokens)):
                cpSPAM = self.condprobSpam[doc]
                cpHAM = self.condprobHam[doc]
                doctoks = f.tokens.count(doc)
                if (cpSPAM > 0 and cpHAM > 0):
                   score["spam"] += math.log10(cpSPAM) * doctoks
                   score["ham"] += math.log10(cpHAM) * doctoks
            result = max(score, key=score.get)
            results.append(result)
        return results
        pass

def evaluate(predictions, documents):
    """ Evaluate the accuracy of a set of predictions.
    Return a tuple of three values (X, Y, Z) where
    X = percent of documents classified correctly
    Y = number of ham documents incorrectly classified as spam
    Z = number of spam documents incorrectly classified as ham

    Params:
      predictions....list of document labels predicted by a classifier.
      documents......list of Document objects, with known labels.
    Returns:
      Tuple of three floats, defined above.
    """
    ###TODO
    Fspam = 0 #false spam Z
    Fham = 0 #false ham Y
    correctClassif = 0 #X
    for i in range(0,len(documents)):
        if documents[i].label == predictions[i] :
            correctClassif += 1
        else:
            if predictions[i] == 'spam':
                Fspam += 1
            else:
                Fham += 1
    accur = (correctClassif/float(len(documents)))
    return(accur,Fspam,Fham) # accuracy, Z, Y
    pass

def main():
    """ Do not modify. """
    if not os.path.exists('train'):  # download data
       from urllib.request import urlretrieve
       import tarfile
       urlretrieve('http://cs.iit.edu/~culotta/cs429/lingspam.tgz', 'lingspam.tgz')
       tar = tarfile.open('lingspam.tgz')
       tar.extractall()
       tar.close()
    train_docs = [Document(filename=f) for f in glob.glob("train/*.txt")]
    print('read', len(train_docs), 'training documents.')
    nb = NaiveBayes()
    nb.train(train_docs)
    test_docs = [Document(filename=f) for f in glob.glob("test/*.txt")]
    print('read', len(test_docs), 'testing documents.')
    predictions = nb.classify(test_docs)
    results = evaluate(predictions, test_docs)
    print('accuracy=%.3f, %d false spam, %d missed spam' % (results[0], results[1], results[2]))
    print('top ham terms: %s' % ' '.join('%.2f/%s' % (v,t) for v, t in nb.get_top_words('ham', 10)))
    print('top spam terms: %s' % ' '.join('%.2f/%s' % (v,t) for v, t in nb.get_top_words('spam', 10)))

if __name__ == '__main__':
    main()
