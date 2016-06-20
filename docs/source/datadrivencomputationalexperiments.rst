.. include:: <isogrk1.txt>

============================================
GLANET Data Driven Computational Experiments
============================================

**Motivation**

In order to assess the statistical power and Type-I error control of GLANET, we designed data-driven computational experiments using large collections of ENCODE ChIP-seq and RNA-seq data. 
These experiments indicated that while GLANET enrichment test often performs conservatively in terms of Type-I error, it has high statistical power.

**Data**

We have used histone modification, DNA polymerase II (POL2) ChIP-seq and RNA-seq data.
We have focused on 12 histone modifications and POL2 in promoter regions of expressed and non-expressed genes.

**Genomic Elements**

As ground truth, we considered histone modifications  and POL2 occupancy in three groups:

* **Activator elements**: H2AZ, H3K27ac, H3K4me2, H3K4me3, H3K79me2, H3K9ac, H3K9acb, and POL2
* **Repressor elements**: H3K27me3
* **Ambigious elements** (exhibit both activator and repressor features): H3K36me3, H3K4me1, H3K9me3 and H4K20me1
  
  
**Interval Pools**

We have filled our genomic interval pool by promoter regions of genes by considering 500 bps upstream and 100 bps downstream of genes in GM12878 and K562 RNA-seq data.

.. figure:: ../images/ddce/DataDrivenExperimentInterval.png
   :align: center
   :alt: DataDrivenExperimentInterval

   Data-driven Computational Experiment Interval

We have labeled genes with zero Transcript Per Million (TPM) as non-expressed genes.
We have defined **2 genomic interval pools from non-expressed genes**.
It has been shown that DNaseI hypersensitivity and gene expression correlate.
Therefore we have excluded DNaseI overlap from these promoter regions in two modes:

1. **CompletelyDiscard**: If promoter region overlaps with any DNaseI hypersensitive sites of the corresponding cell line, discard the interval completely.
2. **TakeTheLongest**: : If promoter region overlaps with any DNaseI hypersensitive sites of the corresponding cell line, remove that overlap, there might be more than one remaining intervals, choose the longest one among remaining intervals.

We have defined **3 genomic interval pools from expressed genes**.
We have sorted the genes w.r.t. their TPM values in descending order.

1. **Top5**: We considered the top 5th percentile of the genes in expressed genes interval pool.
2. **Top10**: We considered the top 10th percentile of the genes in expressed genes interval pool.
3. **Top20**: We considered the top 20th percentile of the genes in expressed genes interval pool.

As a result, we have 5 interval pools at total, 2 of them from non-expressed genes and 3 of them from expressed genes.

**Note:**
GM12878 and K562 RNA-seq data have two biological replicates.
We have considered the lowest and highest TPM values across replicates for defining the expressed and non-expressed genes, respectively.

**Data-driven Computational Experiment Design**

We have performed the data-driven computational experiments summarized in table below under the six enrichment analysis settings of GLANET by varying association measures as **EOO** or **NOOB** and random interval generation mode as **wGCM** or **woGCM** and tested **wGCM** with two modes of Isochore Family, **wIF** or **woIF** for null distribution estimation.

+----------------------------------------------------------------------------------------------------+ 
|                                               Null Hypotheses                                      |
+====================+=====================================+=========================================+
| Genomic Elements   | Promoter regions of expressed genes | Promoter regions of non-expressed genes |
|                    |    are not enriched for genomic     |      are not enriched for genomic       |
|                    |          element j                  |            element j                    |              
+--------------------+-------------------------------------+-----------------------------------------+                                            
| Activator Elements |             Power                   |             Type-I Error                |
+--------------------+-------------------------------------+-----------------------------------------+ 
| Repressor Elements |          Type-I Error               |                 Power                   |                
+--------------------+-------------------------------------+-----------------------------------------+ 

We varied the definitions of non-expressed and expressed genes and repeated the experiments for each setting. 
 
For each interval pool, we had 1000 simulations.
For each simulation, we have sampled 500 random non-overlapping intervals from 	the corresponding interval pool.

We have run each simulation with 6 different settings of GLANET by varying association measures such as **EOO** or **NOOB** and random interval generation mode such as **wGCM** or **woGCM**.
We have tested **wGCM** with two modes of Isochore Family, **wIF** or **woIF**.

* *(wGCM, EOO, woIF)*
* *(wGCM, EOO, wIF)*
* *(woGCM, EOO)*
* *(wGCM, NOOB, woIF)*
* *(wGCM, NOOB, wIF)*
* *(woGCM,NOOB)*

+------------------------------------------------------+ 
| Data-Driven Computational Experiment                 |
+==========================================+===========+ 
| Number of interval pools                 | 5         | 
+------------------------------------------+-----------+ 
| Number of Simulations                    | 1000      | 
+------------------------------------------+-----------+ 
| Number of GLANET runs for each simulation| 6         | 
+------------------------------------------+-----------+ 
| Number of cell lines (GM12878 and K562)  | 2         |
+------------------------------------------+-----------+ 
| Total number of GLANET Runs              | 60000     |
+------------------------------------------+-----------+ 

In these experiment, our null hypothesis is that there is no enrichment.s

**Data-driven Computational Experiments Results**

We provide Type-I error and power for significance level of |agr| = 0.05 and |agr| = 0.001.

**Repressor elements**
For **woIF**
Experiments in K562 cell line for H3K27me3 resulted in power of 1 and Type-I error of 0 in all parameter settings. 
Experiments conducted in GM12878 cell line for H3K27me3 resulted in power of $1$ and Type-I error of $0$ except few cases listed in table below. 
In each case, Type-I error is below the significance level of |agr| = 0.05.

+-------------------------------------------+ 
| Expressed Genes, |agr| = 0.05.            |
+===========================+=======+=======+ 
| Parameters         | Top5 | Top10 | Top20 |
+--------------------+------+-------+-------+
| GLANET(woGCM,EOO)  | 0    | 0     | 0.006 |
+--------------------+------+-------+-------+
| GLANET(woGCM,NOOB) | 0    | 0.002 | 0.008 |
+--------------------+------+-------+-------+
| GLANET(wGCM,EOO)   | 0    | 0     | 0     |
+--------------------+------+-------+-------+
| GLANET(wGCM,NOOB)  | 0    | 0     | 0     |
+--------------------+------+-------+-------+

For **wIF**
Experiments in K562 and GM12878 cell lines for H3K27me3 resulted in power of 1 and Type-I error of 0 in all parameter settings. 

**Figures**

All the figures are provided in the Supplementary Materials for GLANET paper.

.. figure:: ../images/ddce/woIF_empiricalPValues/GM12878_NonExp_Activators_TypeIError_SigLev_0_05_Facet_CompletelyDiscard.png
   :align: center
   :alt: GM12878_Non_expressing_genes_alpha_0_05_CompletelyDiscard

   GM12878, Non-expressing genes, alpha=0.05, CompletelyDiscard, Type-I Error
   
.. figure:: ../images/ddce/woIF_empiricalPValues/GM12878_Exp_Activators_Power_SigLev_0_05_Facet_Top5.png
   :align: center
   :alt: GM12878_Exp_Activators_Power_SigLev_0_05_Facet_Top5

   GM12878, Expressing genes, alpha=0.05, Top5, Power