===============
GLANET Overview
===============

------
GLANET
------

GLANET is Genomic Loci AssociatioN and Enrichment Tool.

GLANET achieves annotation, enrichment analysis and regulatory sequence analysis 
between given two sets of genomic intervals.                                                                                                             
The first set of intervals are the user provided genomic intervals.
They may come from GWAS yielded genomic variants (SNPs, CNVs) or from a NGS experiment.  
The important point is that given genomic intervals must be related with each other.

The second set consists of identified/annotated genomic                                                                                 
intervals such as DNaseI Hypersensitive sites (DHSs), Transcription Factor                                                                              
binding sites (TFs), Histone Modification sites (HMs), exons, introns, proximal and distal of                                                          
genes and so on.  User can provide the second set using GLANET's user defined gene set
or user defined library features.
                                                                                                                                   
In order to gain biological insight, intersecting these two sets, annotation of given genomic intervals 
with respect to the identified/annotated genomic intervals and finding the genomic intervals overlapping significantly are vital 
for a comprehensive understanding and interpretation of the GWAS disease associated variants.

GLANET aims to find the intersections between these two sets which is called Annotation and to find out the 
significant intersections by creating a sampling based null distribution which is called Enrichment. 

Annotation which is finding overlapping intervals is easily achieved by interval tree search where the second set is 
represented as an interval tree. 
However, Enrichment which is finding out the genomic intervals which overlap significantly is not trivial 
since genome is not homogeneous, gene density differs and genome organization is complex. 
For Enrichment Analysis, GLANET utilizes sampling based statistical test which takes genomic biases 
such as GC content and Mappability into account, during random interval generation for samplings. 

And in Regulatory Sequence Analysis, transcription factor annotation results are further analysed whether any SNP (given in the first set) 
increases or decreases the binding affinity of the annotated transcription factor by scanning the reference sequence, 
all possible altered sequences and extended transcription factor sequence with the position frequency matrices (PFMs)
of the annotated transcription factor. 
During regulatory sequence analysis `RSAT <http://www.rsat.eu/>`_'s matrix scan web service is used.

GLANET Pipeline

.. image:: ../images/GLANET_pipeline.jpg
    :alt: GLANET Pipeline

GLANET enables user to load its user defined library and/or user defined gene sets in order to use them as Annotation Library. 
Namely, genomic intervals obtained from user defined library and/or user defined gene sets will be populated in the Annotation Library
and Annotation will be achieved w.r.t. these genomic intervals.

---------------
GLANET Features
---------------

* GLANET accepts given genomic intervals of any length.
* GLANET has developed sampling-based enrichment analysis which compensate for the bias introduced by given genomic intervals of varying length.
* GLANET's sampling-based enrichment analysis accounts for genomic biases in addition to the given interval's length and the chromosome it is located on.
* GLANET considers genomic biases such as GC content and mappability of given genomic intervals.
* GLANET interprets gene sets in three different ways which are exon-based, regulation-based and all-based manner.
  Exon-based gene set takes exons of genes, regulation-based takes introns, upstream and downstream proximal regions of genes 
  and lastly all-based takes all the defined regions in exon-based and regulation-based, plus upstream and downstream distal regions of genes of each gene set into account.
* GLANET enables user to load user defined gene sets and/or user defined library and to achieve Annotation w.r.t the genomic intervals obtained from them.
* GLANET provides Regulatory Sequence Analysis for all of the annotated TFs using `RSAT <http://www.rsat.eu/>`_'s matrix scan web service.
* GLANET has assessed its Type-I error and power by designing data-driven computational experiments on two cell lines, GM12878 and K562, which showed that it has a well-controlled Type-I error rate and high statistical power.


-------------
GLANET Output
-------------

After a successful GLANET execution 

* Annotation results can be found under 

			   | ~path/to/GLANET Folder/Output/givenJobName/Annotation/

* Enrichment results can be found under

			   | ~path/to/GLANET Folder/Output/givenJobName/Enrichment/

* Regulatory Sequence Analysis results can be found under

			   | ~path/to/GLANET Folder/Output/givenJobName/RegulatorySequenceAnalysis/

* GLANET log file (GLANET.log) can be found under the same directory where GLANET.jar is located. If you are running GLANET from the source code, GLANET log file will be created under Glanet project directory.


