package lamp;

public class Color {

    private byte R;
    private byte G;
    private byte B;

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

}
