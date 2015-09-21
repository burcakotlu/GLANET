/**
 * 
 */
package enumtypes;

import common.Commons;

/**
 * @author Burçak Otlu
 * @date Sep 3, 2015
 * @project Common 
 *
 */
public enum DataDrivenExperimentOperatingSystem {
	
	WINDOWS(1),
	LINUX(2),
	TURENG_MACHINE(3),
	TRUBA(4);
	
	private final int dataDrivenExperimentOperatingSystem;

	/*
	 * This constructor is private.
	 * Legal to declare a non-private constructor, but not legal to use such a constructor outside the enum.
	 * Can never use "new" with any enum, even inside the enum class itself.
	 */
	private DataDrivenExperimentOperatingSystem( int dataDrivenExperimentOperatingSystem) {
		this.dataDrivenExperimentOperatingSystem = dataDrivenExperimentOperatingSystem;
	}

	public int getDataDrivenExperimentOperatingSystem() {
		return dataDrivenExperimentOperatingSystem;
	}

	public static DataDrivenExperimentOperatingSystem convertStringtoEnum( String dataDrivenExperimentOperatingSystem) {

		if( Commons.WINDOWS.equals(dataDrivenExperimentOperatingSystem)){
			return WINDOWS;
		}
		
		else if( Commons.LINUX.equals(dataDrivenExperimentOperatingSystem)){
				return LINUX;
		}
		
		else if (Commons.TURENG_MACHINE.equals(dataDrivenExperimentOperatingSystem)){
			return TURENG_MACHINE;
		}
		
		else if (Commons.TRUBA.equals(dataDrivenExperimentOperatingSystem)){
			return TRUBA;
		}
		
		else
			return null;
	}

	public String convertEnumtoString() {

		if( this.equals(DataDrivenExperimentOperatingSystem.WINDOWS))
			return Commons.WINDOWS;
		
		else if( this.equals(DataDrivenExperimentOperatingSystem.LINUX))
			return Commons.LINUX;
		
		else if( this.equals(DataDrivenExperimentOperatingSystem.TURENG_MACHINE))
			return Commons.TURENG_MACHINE;
		
		else if( this.equals(DataDrivenExperimentOperatingSystem.TRUBA))
			return Commons.TRUBA;
		
		else
			return null;
	}

	public boolean isWindows() {
		return ( this == DataDrivenExperimentOperatingSystem.WINDOWS);
	}

	public boolean isLinux() {
		return ( this == DataDrivenExperimentOperatingSystem.LINUX);
	}

	
	public boolean isTurengMachine() {
		return ( this == DataDrivenExperimentOperatingSystem.TURENG_MACHINE);
	}

	
	public boolean isTRUBA() {
		return ( this == DataDrivenExperimentOperatingSystem.TRUBA);
	}


}
