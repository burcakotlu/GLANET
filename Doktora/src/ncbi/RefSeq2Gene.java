package ncbi;


public class RefSeq2Gene {
	
	//the unique identifier for a gene
	Integer geneId;
	
	//genomic nucleotide accession.version without version
	// may be null (-) if a RefSeq was provided after the genomic accession was submitted
	String RNA_Nucleotide_Accession;
	
	//genomic nucleotide accession.version
	// may be null (-) if a RefSeq was provided after the genomic accession was submitted
	String RNA_Nucleotide_Accession_WithVersion;
	
	
	
	
	
	public String getRNA_Nucleotide_Accession() {
		return RNA_Nucleotide_Accession;
	}
	public void setRNA_Nucleotide_Accession(String rNA_Nucleotide_Accession) {
		RNA_Nucleotide_Accession = rNA_Nucleotide_Accession;
	}
	public String getRNA_Nucleotide_Accession_WithVersion() {
		return RNA_Nucleotide_Accession_WithVersion;
	}
	public void setRNA_Nucleotide_Accession_WithVersion(
			String rNA_Nucleotide_Accession_WithVersion) {
		RNA_Nucleotide_Accession_WithVersion = rNA_Nucleotide_Accession_WithVersion;
	}
	public Integer getGeneId() {
		return geneId;
	}
	public void setGeneId(Integer geneId) {
		this.geneId = geneId;
	}
	
	
//	overridden equals method
	public boolean equals(Object obj){
		boolean isEqual = false;
		
		if(obj!=null){
			if (this.getClass() == obj.getClass()){
				RefSeq2Gene  refSeq2Gene = (RefSeq2Gene) obj;
				
				if ((refSeq2Gene.getGeneId().equals(this.getGeneId())) && 
				    (refSeq2Gene.getRNA_Nucleotide_Accession().equals(this.getRNA_Nucleotide_Accession())) &&
				    (refSeq2Gene.getRNA_Nucleotide_Accession_WithVersion().equals(this.getRNA_Nucleotide_Accession_WithVersion()))){			
					 	isEqual = true;
					 	return isEqual;
				}
			}			
		}
		
		return isEqual;		
	}
	
	public int hashCode(){
		return this.getRNA_Nucleotide_Accession_WithVersion().hashCode();
	}
	

}
