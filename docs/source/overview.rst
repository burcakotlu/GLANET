===============
GLANET Overview
===============

------
GLANET
------

GLANET is Genomic Loci ANnotation and Enrichment Tool.

GLANET uses two sets of genomic intervals.
Smaller set comes from the user, bigger set is the Annotation Library provided in the Data.zip.
Smaller set is the user given input data consisting of genomic intervals that may come from GWAS yielded genomic
variants or from a NGS experiment.
On the other hand, bigger set consists of identified/annotated genomic
intervals such as DNaseI Hypersensitive sites (DHs), Transcription Factor
binding sites (TFs), Histone Modification sites (HMs), exons, introns, proximal and distal of
genes and so on.

In order to gain biological insight, intersecting these two sets, association of given genomic intervals 
with identified/annotated genomic intervals and finding the genomic intervals overlapping significantly are vital 
for a comprehensive understanding and interpretation of the GWAS disease associated variants.

GLANET aims to find the intersections between these two sets which is called Association and to find out the 
significant intersections by creating a permutation based null distribution which is called Enrichment. 

Association which is finding overlapping intervals is easily achieved by interval tree search where the bigger set is 
represented as an interval tree. 
However Enrichment which is finding out the genomic intervals which overlap significantly is not trivial 
since genome is not homogenous, gene density differs and genome organization is complex. 
For Enrichment Analysis, GLANET permutation based method takes genomic biases into account such as 
GC content and Mappability during random data generation. 

And Transcription Factor Association results are further analysed whether any SNP (given in the smaller set) 
increases or decreases the binding affinity of the annotated transcription factor by scanning the reference sequence, 
all possible altered sequences and transcription factor extended peak sequence with the position frequency matrices 
of the annotated transcription factor which is called Regulatory Sequence Analysis.

GLANET Pipeline

.. image:: ../images/GLANET_pipeline.jpg
    :alt: GLANET Pipeline

GLANET enables user to load its user defined library and/or user defined gene sets in order to use them in Annotation Library. 
Namely, genomic intervals obtained from user defined library and/or user defined gene sets will be populated in Annotation Library
and Annotation will be achieved w.r.t. these genomic intervals.

---------------
GLANET Features
---------------

* GLANET accepts given genomic intervals of any length.
* GLANET has developed resampling-based enrichment analysis which compensate for the bias introduced by given genomic intervals of varying length.
* GLANET's resampling-based enrichment analysis accounts for genomic biases in addition to the given interval length and the chromosome it is located on.
* GLANET considers genomic biases such as GC content and mappability of given genomic intervals.
* GLANET interprets gene sets in three different ways which are exon-based, regulation-based and extended-based manner.
  Exon-based gene set takes exons of genes, regulation-based takes introns, upstream and downstream proximal regions of genes 
  and lastly extended-based takes all the defined regions in exon-based and regulation-based, plus upstream and downstream distal regions of genes of each gene set into account.
* GLANET enables user to load user defined gene sets and/or user defined library and to achieve Annotation w.r.t the genomic intervals obtained from them.
* GLANET provides Regulatory Sequence Analysis for all of the associated TFs.

