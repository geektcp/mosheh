package com.geektcp.common.core.cache.tiny;

/**
 * @author geektcp on 2023/2/26 17:18.
 */
public enum TinyRemovalCause {

    EXPLICIT {
        boolean wasEvicted() {
            return false;
        }
    },
    REPLACED {
        boolean wasEvicted() {
            return false;
        }
    },
    COLLECTED {
        boolean wasEvicted() {
            return true;
        }
    },
    EXPIRED {
        boolean wasEvicted() {
            return true;
        }
    },
    SIZE {
        boolean wasEvicted() {
            return true;
        }
    };

    private TinyRemovalCause() {
    }

    abstract boolean wasEvicted();


}
