package create.ucscgenome;

public class FivePrimeThreePrime {
	
//	5p1, 5p2, 5d is always from gene start to five prime which is upstream of a refseq gene
//	3p1, 3p2, 3d is always from gene end to three prime which is downstream of a refseq gene

//	For + strand, gene start at transcription start position
//	For + strand  ----5prime---txStart---------txEnd-----3prime-------------  gene flows from 5 to 3 prime therefore gene starts at txStart

//	For - strand, gene start at transcription end position
//	For - strand  ----3prime---txStart---------txEnd-----5prime-------------  gene flows from 5 to 3 prime therefore gene starts at txEnd

	
//	For + strand, from transcription start position to 5 prime is the upstream of a refseq gene
//	For + strand, from transcription end position to 3 prime is the downstream of a refseq gene 
	
//	For - strand, from transcription end position to 5 prime is the upsteam of a refseq gene
//	For - strand, from transcription start position to 3 prime is the downstream of a refseq gene

//	Since gene always flow from 5 prime to 3 prime
//	important point is where the gene starts whether at transcription start position or transcription end position
	
	
//	proximal 1
	Integer _5p1Start=0;
	Integer _5p1End =0;
	
//	proximal 2
	Integer _5p2Start = 0;
	Integer _5p2End =0;
	
//	distant
	Integer _5dStart = 0;
	Integer _5dEnd =0;
	
//	proximal 1
	Integer _3p1Start =0;
	Integer _3p1End =0;
	
//	proximal 2
	Integer _3p2Start =0;
	Integer _3p2End =0;
	
//	distant
	Integer _3dStart=0;
	Integer _3dEnd =0;
	
	
	public Integer get_5p1Start() {
		return _5p1Start;
	}
	
	public void set_5p1Start(Integer start) {
		_5p1Start = start;
	}
	public Integer get_5p1End() {
		return _5p1End;
	}
	public void set_5p1End(Integer end) {
		_5p1End = end;
	}
	public Integer get_5p2Start() {
		return _5p2Start;
	}
	public void set_5p2Start(Integer start) {
		_5p2Start = start;
	}
	public Integer get_5p2End() {
		return _5p2End;
	}
	public void set_5p2End(Integer end) {
		_5p2End = end;
	}
	public Integer get_5dStart() {
		return _5dStart;
	}
	public void set_5dStart(Integer start) {
		_5dStart = start;
	}
	public Integer get_5dEnd() {
		return _5dEnd;
	}
	public void set_5dEnd(Integer end) {
		_5dEnd = end;
	}
	public Integer get_3p1Start() {
		return _3p1Start;
	}
	public void set_3p1Start(Integer start) {
		_3p1Start = start;
	}
	public Integer get_3p1End() {
		return _3p1End;
	}
	public void set_3p1End(Integer end) {
		_3p1End = end;
	}
	public Integer get_3p2Start() {
		return _3p2Start;
	}
	public void set_3p2Start(Integer start) {
		_3p2Start = start;
	}
	public Integer get_3p2End() {
		return _3p2End;
	}
	public void set_3p2End(Integer end) {
		_3p2End = end;
	}
	public Integer get_3dStart() {
		return _3dStart;
	}
	public void set_3dStart(Integer start) {
		_3dStart = start;
	}
	public Integer get_3dEnd() {
		return _3dEnd;
	}
	public void set_3dEnd(Integer end) {
		_3dEnd = end;
	}
	
}
