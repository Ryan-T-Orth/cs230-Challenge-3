public class Cell {

    private Type type;
    private Type nextType;

    public Cell() {
        this.type = this.nextType = Type.UNDECIDED;
    }

    public Type getType() {
        return this.type;
    }

    public void setType(Type t) {
        this.type = t;
    }

    public void getInfluence(Cell neighbor, boolean censor) {
        if (this.type.equals(Type.UNDECIDED)) {this.nextType = neighbor.getType();}

        else if (this.type.equals(Type.TRUTH_LOW)) {
            if (neighbor.type.equals(Type.UNTRUTH_HI)) {if (!censor) {this.nextType = Type.TRUTH_HI;}}
        }

        else if (this.type.equals(Type.PARTIAL_A) || this.type.equals(Type.PARTIAL_B)) {
            if (neighbor.type.equals(Type.UNTRUTH_HI)) {if (!censor) {this.nextType = Type.UNTRUTH_LOW;}}
            else if (neighbor.type.equals(Type.TRUTH_HI)) {this.nextType = Type.TRUTH_LOW;}
            else if ((neighbor.type.equals(Type.PARTIAL_A) || neighbor.type.equals(Type.PARTIAL_B)) && !(this.type.equals(neighbor.type))) {
                this.nextType = Type.TRUTH_LOW;
            }
        }

        else if (this.type.equals(Type.UNTRUTH_LOW)) {
            if (neighbor.type.equals(Type.UNTRUTH_LOW)) {this.nextType = Type.UNTRUTH_HI;}
            else if (neighbor.type.equals(Type.UNTRUTH_HI)) {if (!censor) {this.nextType = Type.UNTRUTH_HI;}}
            else if (neighbor.type.equals(Type.TRUTH_HI)) {this.nextType = Type.TRUTH_LOW;}
            else if (neighbor.type.equals(Type.PARTIAL_A) || neighbor.type.equals(Type.PARTIAL_B)) {this.nextType = neighbor.type;}
        }

        else if (this.type.equals(Type.UNTRUTH_HI)) {
            if (neighbor.type.equals(Type.TRUTH_HI)) {this.nextType = Type.TRUTH_LOW;}
            else if (neighbor.type.equals(Type.TRUTH_LOW)) {this.nextType = Type.UNTRUTH_LOW;}
            else if (neighbor.type.equals(Type.PARTIAL_A) || neighbor.type.equals(Type.PARTIAL_B)) {this.nextType = Type.UNTRUTH_LOW;}
        }

        else {this.nextType = this.type;}
    }

    public void shift() {
        this.type = this.nextType;
    }
}
