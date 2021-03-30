package lamp;

public class Color {

    private Byte R;
    private Byte G;
    private Byte B;

    public Color() {
        R = 0;
        G = 0;
        B = 0;
    }

    public Color(byte r, byte g, byte b) {
        R = r;
        G = g;
        B = b;
    }

    public Color(int r, int g, int b) {
        R = Integer.valueOf(r).byteValue();
        G = Integer.valueOf(g).byteValue();
        B = Integer.valueOf(b).byteValue();
    }

    private void setColor(byte r, byte g, byte b){
        R = r;
        G = g;
        B = b;
    }

    public int unsignedToBytes(byte b) {
        return b & 0xFF;
    }

    @Override
    public String toString() {
        return "(" +
                "R=" + unsignedToBytes(R) +
                ", G=" + unsignedToBytes(G) +
                ", B=" + unsignedToBytes(B) +
                ')';
    }

    @Override
    public boolean equals(Object obj) {
        Color c = (Color) obj;
        return this.R.equals(c.R) && this.G.equals(c.G) && this.B.equals(c.B);
    }
}
