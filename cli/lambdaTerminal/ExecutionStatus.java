package cli.lambdaTerminal;

import java.util.Arrays;

/**
 * @author David Berezowski
 * @version 1.0
 */
public enum ExecutionStatus {
    NO_MATCH(0), NOT_ACTIVE(1), ARGUMENT_LIST_NOT_MATCHING(2), EXECUTED(3);
    
    private int priority;
    
    private ExecutionStatus(int priority) {
    this.priority = priority;
    }
    
    public static ExecutionStatus returnHigher(ExecutionStatus... statusList) {
    return Arrays.asList(statusList).stream().sorted((s1, s2) -> Integer.compare(s2.priority, s1.priority)).findFirst().orElse(null);
    }
}
