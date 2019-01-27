# Isolation Forest for anamoly detection

This project focusses on implementation of anamoly detection for reporting top k instances in the given data n-dimesional data input using csv format.Most existing model-based approaches to anomaly detection construct a profile of normal instances, then identify instances that do not conform to the normal profile as anomalies. While the implementation of this model follows a different approach that explicitly isolates anomalies instead of profiles normal points by recursively repeating partitioning of instances until all instances are isolated. This random partitioning produces noticeable shorter paths for anomalies since a) the fewer instances of anomalies result in a smaller number of partitions – shorter paths in a tree structure, and b) instances with distinguishable attribute-values are more likely to be separated in early partitioning.

## Inputs

The program takes inputs from the command line or a csv file can be redirected as an input. The program is built with error handling. So if ny illegeal input is enterred, the program will stop immediately and is required to re-run.

### itree

The node class creates the nodes which later on is called in the itree class and the itree class instances represent the different trees obtained by sub-sampling the original data which goes into making of the forest. The itree algorithm is as follows -

```
Inputs: X - input data, e - current tree height, l – height limit

1: if e ≥ l or |X| ≤ 1 then
2: return exNode{Size ß |X|}
3: else
4: let Q be a list of attributes in X
5: randomly select an attribute q ∈ Q
6: randomly select a split point p from max and min values of
 attribute q in X
7: Xl <- filter(X, q < p)
8: Xr <- filter(X, q ≥ p)
9: return inNode{Left ß iT ree(Xl, e + 1, l),
10: Right ß iTree(Xr, e + 1, l),
11: SplitAtt <- q,
12: SplitValue <- p}
13: end if
```

The tree is built only till the verage height, because we are concerning ourselevs only with the anomalies and nothing else.

### Anomaly detection

An anomaly score is required for any anomaly detection method. The difficulty in deriving such a score from h(x)
is that while the maximum possible height of iTree grows in the order of n, the average height grows in the order of
log n. Normalization of h(x) by any of the above terms is either not bounded or cannot be directly compared. 
Since iTrees have an equivalent structure to Binary Search Tree or BST, the estimation of average h(x) for external node terminations is the same as the unsuccessful search in BST.

The average path length of unsuccessful search in BST is 

```
c(n) = 2H(n − 1) − (2(n − 1)/n),
```

where H(i) is the harmonic number and it can be estimated by ln(i) + 0.5772156649 (Euler’s constant). As c(n) is the
average of h(x) given n, we use it to normalise h(x). 

The anomaly score s of an instance x is defined as:

```
s(x, n) = 2**(−E(h(x))/c(n)) 
```
where E(h(x)) is the average of h(x) from a collection of isolation trees.

## Running the tests

The first line of input should contain the number of data points which are anomaly are needed as outputs followed by the actual data points given in the format of csv, where each line's comma seperated entries represent a dimension.

### Number of trees and sub-samples

The number of trees and the number of subsamples are calculated on the assumption that the data is very large, so it takes a root of the number of datapoints as number of tree and twice the root as subsample points.

### Subsampling

Contrary to existing methods where large sampling size is more desirable, isolation method works best when the
sampling size is kept small. Large sampling size reduces iForest’s ability to isolate anomalies as normal instances can
interfere with the isolation process and therefore reduces its ability to clearly isolate anomalies. Thus, sub-sampling
provides a favourable environment for iForest to work well. So in the code sub-sampling is conducted by random
selection of instances without replacement.

## Evaluation

the relationship between E(h(x)) and s, and the following conditions applied
where 0 < s < 1 for 0 < h(x) < n − 1. Using the
anomaly score s, we are able to make the following assessment:
• (a) if instances return s very close to 1, then they are
definitely anomalies,
• (b) if instances have s much smaller than 0.5, then they
are quite safe to be regarded as normal instances, and
• (c) if all the instances return s < 0.5, then the entire
sample does not really have any distinct anomaly.

## Acknowledgments

* Isolation Forest ,Fei Tony Liu,Zhi-Hua Zhou

