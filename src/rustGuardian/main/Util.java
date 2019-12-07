package rustGuardian.main;

public class Util {
	public static int GCD(int a, int b) {
		if (b == 0)
			return a;
		else
			return (GCD(b, a % b));
	}

	public static int GCD(double a, double b) {
		if ((int) b == 0)
			return (int) a;
		else
			return (GCD((int) b, (int) a % (int) b));
	}
}