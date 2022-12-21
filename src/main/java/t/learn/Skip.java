package t.learn;

public interface Skip extends Sprint {
    public default int getHopCount(Kangaroo kangaroo) {
        return 10;
    }

    public static void skip(int speed) {
    }
}