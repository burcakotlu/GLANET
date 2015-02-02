Overview
========

GLANET Overview

GLANET is Genomic Loci ANnotation and Enrichment Tool.
GLANET has two sets of intervals.
Smaller set is the given input data consisting of genomic intervals that comes from GWAS yielded genomic variants or from a NGS experiment. 
On the other hand, bigger set consists of identified/annotated genomic intervals such as DNaseI hypersensitive sites, transcription factor binding sites, histone modification sites, exons of genes, introns of genes and so on.

In order to gain biological insight, intersecting these two sets, accurate annotation of given genomic intervals with identified/annotated genomic intervals and finding the genomic intervals overlapping significantly are vital for a comprehensive understanding and interpretation of the GWAS disease associated variants.
GLANET aims to find the intersections between these two sets which is called Annotation and to find out the significant intersections by creating a permutation based null distribution which is called Enrichment. 
Annotation which is finding overlapping intervals is easily achieved by interval tree search where the bigger set is represented as an interval tree. 
However Enrichment which is finding out the genomic intervals which overlap significantly is not trivial since genome is not homogenous, gene density differs and genome organization is complex. 
For Enrichment Analysis, GLANET permutation based method takes genomic biases into account such as GC content and Mappability during random data generation. 

And Transcription Factor Annotation results are further analysed whether any SNP (given in the smaller set) increases or decreases the binding affinity of the annotated transcription factor by scanning the reference sequence, all possible altered sequences and transcription factor extended peak sequence with the position frequency matrices of the annotated transcription factor which is called Regulatory Sequence Analysis.

GLANET Pipeline