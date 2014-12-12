/**
 * @author burcakotlu
 * @date Apr 2, 2014 
 * @time 5:11:14 PM
 */
package jaxbxjctool;

public class PositionFrequency {
	
	float _AFrequency;
	float _CFrequency;
	float _TFrequency;
	float _GFrequency;
	
	
	

	public float get_AFrequency() {
		return _AFrequency;
	}

	public void set_AFrequency(float _AFrequency) {
		this._AFrequency = _AFrequency;
	}

	public float get_CFrequency() {
		return _CFrequency;
	}

	public void set_CFrequency(float _CFrequency) {
		this._CFrequency = _CFrequency;
	}

	public float get_TFrequency() {
		return _TFrequency;
	}

	public void set_TFrequency(float _TFrequency) {
		this._TFrequency = _TFrequency;
	}

	public float get_GFrequency() {
		return _GFrequency;
	}

	public void set_GFrequency(float _GFrequency) {
		this._GFrequency = _GFrequency;
	}

	/**
	 * 
	 */
	public PositionFrequency() {
		// TODO Auto-generated constructor stub
	}

	
	
	public PositionFrequency(float _AFrequency, float _CFrequency,
			float _GFrequency, float _TFrequency) {
		super();
		this._AFrequency = _AFrequency;
		this._CFrequency = _CFrequency;
		this._GFrequency = _GFrequency;
		this._TFrequency = _TFrequency;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
