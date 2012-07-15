package project.algorithms.helpers;

import automata.State;
import java.util.SortedSet;
import java.util.TreeSet;

public class StatePair implements Comparable<StatePair> {

    private final State state1;
    private final State state2;
    private boolean mark;
    SortedSet<StatePair> list;

    public boolean isMarked() {
        return mark;
    }

    public void setMarked() {
        this.mark = true;
    }

    public StatePair(State state1, State state2) {
        this.state1 = state1;
        this.state2 = state2;
        this.list = new TreeSet<StatePair>();
    }

    public SortedSet<StatePair> getList() {
        return list;
    }

    public State getState1() {
        return state1;
    }

    public State getState2() {
        return state2;
    }

    public void markAll() {
        if (list.size() > 0) {
            for (StatePair other : this.list) {
                if (other != this) {
                    other.setMarked();
                    other.markAll();
                }
            }
        }
    }

    @Override
    public int compareTo(StatePair o) {
        return this.toString().compareTo(o.toString());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof StatePair) {
            StatePair other = (StatePair) obj;
            return (this.state1.equals(other.state1) && this.state2.equals(other.state2))
                    || (this.state1.equals(other.state2) && this.state2.equals(other.state1));
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + (this.state1 != null ? this.state1.hashCode() : 0);
        hash = 79 * hash + (this.state2 != null ? this.state2.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "{" + state1 + "," + state2 + "}";
    }
}