public class HelloNumbers {
    public static void main(String[] args) {
        int x = 0, prev= 0, output=0;
        while (x < 10) {
            System.out.print(output+ " ");
            prev=output;
            x = x + 1;
            output=x+prev;
        }
    }
}