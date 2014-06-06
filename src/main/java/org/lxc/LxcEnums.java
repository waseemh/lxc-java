package org.lxc;

/**
 * Class which contains all general enum types.
 * @author waseem
 *
 */
public class LxcEnums {
	
	public enum BackendStore {
		
		None("none"),Aufs("aufs"),Overlayfs("overlayfs"),Directory("dir"),LVM("lvm"),Loopback("loopback"),Btrfs("btrfs"),Best("best");
		
		String value;
		
		BackendStore(String value) {
			this.value=value;
		}
		
		public String toString() {
			return this.value;
		}

	}
	
	public enum ContainerState {
		
		STOPPED,STARTING,RUNNING,ABORTING,STOPPING,FROZEN,UNKNOWN;
	}

	public enum ContainerListFilter {
		Active("active"),Running("running"),Stopped("stopped"),Frozen("frozen"),All("");
		
		String value;
		
		ContainerListFilter(String value) {
			this.value=value;
		}
		
		public String toString() {
			return this.value;
		}

	}

}
